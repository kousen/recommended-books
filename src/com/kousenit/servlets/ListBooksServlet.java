package com.kousenit.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kousenit.beans.Book;
import com.kousenit.services.AmazonBookService;

@SuppressWarnings("serial")
public class ListBooksServlet extends HttpServlet {
	private AmazonBookService service;
	
	@Override
	public void init() throws ServletException {
		service = AmazonBookService.getInstance();
		if (((List<Book>) service.getBooks()).size() == 0) {
			service.addBook("1932394842","I've spent many a pleasant evening with GinA");
			service.addBook("0978739299","Rockin' recipes");
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws IOException, ServletException {
		List<Book> books = (List<Book>) service.getBooks();
		req.setAttribute("books", books);
		req.getRequestDispatcher("books.jsp").forward(req, resp);
	}
}
