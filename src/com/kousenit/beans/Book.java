package com.kousenit.beans;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Book implements Serializable {
	
	private static final long serialVersionUID = 2224649533817426805L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String asin;
	
	@Persistent
	private String recommendation;
	
	// Attributes populated by XML response
	private String title;
	private String author;  // multiple are separated by commas
	private String formattedPrice;
	private String mediumImageURL;
	private String detailPageURL;
	
	public Book() {}
	
	public Book(String asin, String recommendation) {
		this.asin = asin;
		this.recommendation = recommendation;
	}
	
	public Long getId() {
		return id;
	}

	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
		
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getFormattedPrice() {
		return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
	}
	
	public void setMediumImageURL(String mediumImageURL) {
		this.mediumImageURL = mediumImageURL;
	}
	
	public String getMediumImageURL() {
		return mediumImageURL;
	}
	
	public void setDetailPageURL(String detailPageURL) {
		this.detailPageURL = detailPageURL;
	}
	
	public String getDetailPageURL() {
		return detailPageURL;
	}

	@Override
	public String toString() {
		return "(" + id + "," + asin + "," + 
			title + "," + author + "," + 
			formattedPrice + "," + recommendation + ")";
	}
}
