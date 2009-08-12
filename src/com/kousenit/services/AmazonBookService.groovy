package com.kousenit.services

import com.kousenit.dao.BookDAOimport com.kousenit.dao.JdoBookDAOimport groovy.util.XmlParserimport com.kousenit.beans.Book
import com.kousenit.dao.DAOFactoryimport javax.cache.Cacheimport javax.cache.CacheManagerimport java.util.Collectionsimport com.google.appengine.api.memcache.stdimpl.GCacheFactoryimport java.util.Setimport java.util.HashSetclass AmazonBookService {
	def baseUrl = 'http://ecs.amazonaws.com/onca/xml'
	
	def params = ['Service':'AWSECommerceService',
	              'Operation':'ItemLookup',
	              'AssociateTag':'kouitinc-20',
	              'ResponseGroup':'Medium']
	
	BookDAO dao
	Cache cache
	SignedRequestsHelper helper = new SignedRequestsHelper()
	
	private static AmazonBookService service = new AmazonBookService()
	
	private AmazonBookService() {
		dao = DAOFactory.instance.bookDAO
		Map props = [(GCacheFactory.EXPIRATION_DELTA):60*60*24]
		
		cache = CacheManager.instance.cacheFactory.createCache(props)
	}
	
	static AmazonBookService getInstance() { service }
	
	def getBooks() {
		def books = dao.findAllBooks()
		def results = []
		books.each { book ->
			if (cache.get(book.asin)) {
				book = cache.get(book.asin)
			} else {
				book = fillInBookDetails(book)
				cache.put(book.asin,book)
			}
			results << book
		}
		return results
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
//		def url = "${baseUrl}?${queryString}&ItemId=${book.asin}"
		def response = new XmlSlurper().parse(url)
		def item = response.Items.Item
		book.title = item.ItemAttributes.Title
		book.author = item.ItemAttributes.Author.collect { it }.join(', ')
		book.formattedPrice = item.ItemAttributes.ListPrice.FormattedPrice
		book.mediumImageURL = item.MediumImage.URL
		book.detailPageURL = item.DetailPageURL
		return book
	}
}