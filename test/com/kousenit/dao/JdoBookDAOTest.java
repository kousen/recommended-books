package com.kousenit.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import com.kousenit.beans.Book;

public class JdoBookDAOTest extends LocalDatastoreTestCase {
	private BookDAO dao;
	private Book b;

	@Test
	public void testFindAllBooks() {
        dao = new JdoBookDAO();
		dao.addBook(new Book("1932394842","I've spent many a pleasant evening with GinA"));
		dao.addBook(new Book("0978739299","Rockin' recipes"));

		int numBooks = dao.findAllBooks().size();
		
		assertEquals(2, numBooks);
		
		Query q = new Query(Book.class.getSimpleName());
		assertEquals(numBooks,
				DatastoreServiceFactory.getDatastoreService()
					.prepare(q).countEntities());
	}

	@Test
	public void testAddBook() {
        dao = new JdoBookDAO();

		dao.addBook(new Book("0978739299","Rockin' recipes"));
		dao.addBook(new Book("1932394842","I've spent many a pleasant evening with GinA"));
		dao.addBook(new Book("1590599950","Doin' DGG"));
		
		Query q = new Query(Book.class.getSimpleName());
		assertEquals(3,
				DatastoreServiceFactory.getDatastoreService()
					.prepare(q).countEntities());
	}

	@Test
	public void testFindById() {
        dao = new JdoBookDAO();
        b = new Book("0978739299","Rockin' recipes");
		dao.addBook(new Book("1932394842","I've spent many a pleasant evening with GinA"));
		dao.addBook(b);

		Book book = dao.findById(b.getId());
		assertEquals("0978739299",book.getAsin());
	}

	@Test
	public void testFindByAsin() {
        dao = new JdoBookDAO();
        b = new Book("0978739299","Rockin' recipes");
		dao.addBook(new Book("1932394842","I've spent many a pleasant evening with GinA"));
		dao.addBook(b);

		Book b = dao.findByAsin("0978739299");
		assertEquals("0978739299",b.getAsin());
	}

	@Test
	public void testRemoveBook() {
        dao = new JdoBookDAO();
        b = new Book("0978739299","Rockin' recipes");
		dao.addBook(new Book("1932394842","I've spent many a pleasant evening with GinA"));
		dao.addBook(b);

		Book book = dao.findAllBooks().iterator().next();
		dao.removeBook(book.getId());
		
		Query q = new Query(Book.class.getSimpleName());
		assertEquals(1,
				DatastoreServiceFactory.getDatastoreService()
					.prepare(q).countEntities());
	}
}
