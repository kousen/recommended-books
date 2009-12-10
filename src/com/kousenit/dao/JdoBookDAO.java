package com.kousenit.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.kousenit.beans.Book;

public class JdoBookDAO implements BookDAO {

	private PersistenceManagerFactory pmf = PMF.get();

	public Long addBook(Book b) {
		Book book = findByAsin(b.getAsin());
		if (book != null) {
			return book.getId();
		}

		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			pm.makePersistent(b);
			pm.currentTransaction().commit();
		} finally {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().commit();
			}
			pm.close();
		}
		return b.getId();
	}

	public boolean updateBook(Long id, Book newData) {
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			Book b = pm.getObjectById(Book.class, id);
			b.setAuthor(newData.getAuthor());
			b.setDetailPageURL(newData.getDetailPageURL());
			b.setFormattedPrice(newData.getFormattedPrice());
			b.setMediumImageURL(newData.getMediumImageURL());
			b.setTitle(newData.getTitle());
		} finally {
			pm.close();
		}
		return true;
	}

	public Set<Book> findAllBooks() {
		Set<Book> results = new HashSet<Book>();
		PersistenceManager pm = pmf.getPersistenceManager();
		Query q = pm.newQuery(Book.class);
		q.setOrdering("asin desc");
		try {
			List<Book> books = (List<Book>) q.execute();
			for (Book b : books) {
				results.add(b);
			}
		} finally {
			pm.close();
		}
		return results;
	}

	public Book findById(Long id) {
		Book result = null;
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			result = pm.getObjectById(Book.class, id);
		} finally {
			pm.close();
		}
		return result;
	}

	public Book findByAsin(String asin) {
		Book result = null;
		PersistenceManager pm = pmf.getPersistenceManager();
		Query q = pm.newQuery(Book.class);
		q.setFilter("asin == '" + asin + "'");
		try {
			List<Book> books = (List<Book>) q.execute();
			if (books.size() == 1) {
				result = books.get(0);
			}
		} finally {
			pm.close();
		}
		return result;
	}

	public boolean removeBook(Long id) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Book b = pm.getObjectById(Book.class, id);
			pm.deletePersistent(b);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return true;
	}
}
