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
public class RemoveBookServlet extends HttpServlet {
	private AmazonBookService service;
	
	@Override
	public void init() throws ServletException {
		service = AmazonBookService.getInstance();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		service.removeBook(Long.parseLong(id));
		List<Book> books = (List<Book>) service.getBooks();
		req.setAttribute("books", books);
		req.getRequestDispatcher("books.jsp").forward(req, resp);
	}
}
