package org.zttc.itat.model;

public class Book {
	
	private String id;
	private String name;
	private double price;
	private int bookPage;
	
	
	
	public int getBookPage() {
		return bookPage;
	}
	public void setBookPage(int bookPage) {
		this.bookPage = bookPage;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
