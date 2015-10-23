package com.acss.core.dataentry.common;

import java.math.BigDecimal;

public class ProductDetail {
	
	public String category;
	public String description;
	public String brand;
	public String modelNo;
	public BigDecimal price;
	public BigDecimal downPayment;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModelNo() {
		return modelNo;
	}
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getDownPayment() {
		return downPayment;
	}
	public void setDownPayment(BigDecimal downPayment) {
		this.downPayment = downPayment;
	}
	
	
}
