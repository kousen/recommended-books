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
public class AddBookServlet extends HttpServlet {
	private AmazonBookService service;
	
	@Override
	public void init() throws ServletException {
		service = AmazonBookService.getInstance();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		service.addBook(
				req.getParameter("asin"),
				req.getParameter("rec"));
		List<Book> books = (List<Book>) service.getBooks();
		req.setAttribute("books", books);
		req.getRequestDispatcher("books.jsp").forward(req, resp);
	}
}
