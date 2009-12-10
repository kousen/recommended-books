package com.kousenit.services

import com.kousenit.dao.BookDAOimport com.kousenit.dao.JdoBookDAO
import groovy.lang.Singleton;
import groovy.util.XmlParserimport com.kousenit.beans.Book
import com.kousenit.dao.DAOFactoryimport javax.cache.Cacheimport javax.cache.CacheManagerimport java.util.Collectionsimport com.google.appengine.api.memcache.stdimpl.GCacheFactoryimport java.util.Setimport java.util.HashSet
import java.util.logging.Logger
// @Singletonclass AmazonBookService {
	private static final Logger log = Logger.getLogger(AmazonBookService.class.name);
	
	def params = ['Service':'AWSECommerceService',
	              'Operation':'ItemLookup',
	              'AssociateTag':'kouitinc-20',
	              'ResponseGroup':'Medium']

	static AmazonBookService instance = new AmazonBookService()
    
	BookDAO dao = DAOFactory.instance.bookDAO
	Cache cache
	SignedRequestsHelper helper = new SignedRequestsHelper()

	private AmazonBookService() {
		int seconds = 60*(24*60 - 1)
		Map props = [(GCacheFactory.EXPIRATION_DELTA):seconds]
        cache = CacheManager.instance.cacheFactory.createCache(props)
	}
	
	def getBooks() {
		def results = []
		def books = dao.findAllBooks()
		if (cache.size() == 0) {
			books.each { book ->
		   		cache.put(book.asin,book)
			   	results << book
			}
		} else {
			books.each { book ->
				results << cache.get(book.asin)
			}
		}
		return results
	}

	def refreshDatabase() {
		def books = dao.findAllBooks()
		books.each { book ->
			Book b = new Book(book.asin,book.recommendation)
			b = fillInBookDetails(b)
			dao.updateBook(book.id, b)
			cache.put(b.asin,b)
		}
	}
	
	def addBook(asin,rec) {
		def book = new Book(asin,rec)
		book = fillInBookDetails(book)
		dao.addBook(book)
		cache.put(book.asin,book)
	}
	
	def removeBook(id) {
		def book = dao.findById(id)
		if (book == null) return
		dao.removeBook(id)
		cache.put(book.asin,null)
	}
	
	def fillInBookDetails(Book book) {
		params['ItemId'] = book.asin
		def url = helper.sign(params)
		
//		def queryString = params.collect { k,v -> "$k=$v" }.join('&')
//		def url = "${baseUrl}?${queryString}"
		def response = new XmlSlurper().parse(url)
		def item = response.Items.Item
		book.title = item.ItemAttributes.Title
		book.author = item.ItemAttributes.Author.collect { it }.join(', ')
		book.formattedPrice = item.ItemAttributes.ListPrice.FormattedPrice
		book.mediumImageURL = item.MediumImage.URL
		book.detailPageURL = item.DetailPageURL

		log.info book.toString()
		return book
	}
}