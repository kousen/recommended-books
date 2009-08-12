package com.kousenit.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.kousenit.beans.Book;
import com.kousenit.services.AmazonBookService;

public class BookServiceTest extends LocalDatastoreTestCase {
	private BookDAO dao;
	private AmazonBookService service;

	@Before
	public void setUp() throws Exception {
		super.setUp();
        dao = new JdoBookDAO();
		dao.addBook(new Book("1932394842","I've spent many a pleasant evening with GinA"));
		dao.addBook(new Book("0978739299","Rockin' recipes"));
		service = AmazonBookService.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetBooks() {
		List<Book> books = (List<Book>) service.getBooks();
		assertEquals(2,books.size());
	}
	
	@Test
	public void testFillInBookDetails() {
		Book book = dao.findByAsin("0978739299");
		book = (Book) service.fillInBookDetails(book);
		assertEquals("0978739299",book.getAsin());
		assertEquals("Groovy Recipes: Greasing the Wheels of Java (Pragmatic Programmers)",book.getTitle());
		assertEquals("Scott Davis",book.getAuthor());
		assertEquals("$34.95",book.getFormattedPrice());
		assertEquals("http://ecx.images-amazon.com/images/I/41si5WLHXNL._SL160_.jpg",book.getMediumImageURL());
		assertEquals("http://www.amazon.com/Groovy-Recipes-Greasing-Pragmatic-Programmers/dp/0978739299%3FSubscriptionId%3D0MWZJAPHC4QKRJ239X82%26tag%3Dkouitinc-20%26linkCode%3Dxm2%26camp%3D2025%26creative%3D165953%26creativeASIN%3D0978739299",book.getDetailPageURL());
	}
}
