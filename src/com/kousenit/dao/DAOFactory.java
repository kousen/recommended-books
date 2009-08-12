package com.kousenit.dao;

public class DAOFactory {
	private static final DAOFactory INSTANCE = new DAOFactory();
	
	private DAOFactory() {}
	
	public static DAOFactory getInstance() {
		return INSTANCE;
	}
	
	public BookDAO getBookDAO() {
		return new JdoBookDAO();
	}
}
