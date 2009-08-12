package com.kousenit.dao;

import java.util.Set;

import com.kousenit.beans.Book;

public interface BookDAO {
	Book findById(Long id);
	Book findByAsin(String asin);
	Set<Book> findAllBooks();
	Long addBook(Book b);
	boolean removeBook(Long id);
}
