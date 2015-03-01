package com.acss.core.dataentry.common;

import java.math.BigDecimal;

import com.acss.core.dataentry.common.constants.ProcessingFeePayType;
import com.acss.core.dataentry.common.constants.Term;

public class InstallmentDetail {
	
	private ProductDetail firstProduct;
	private ProductDetail secondProduct;
	private ProductDetail thirdProduct;
	
	private BigDecimal rate;
	private Term term;
	private String promotionCode;
	private ProcessingFeePayType processingFeePayType;
	private BigDecimal promoVoucher;
	
	public InstallmentDetail(){
		//initialize into normal processing fee payment type.
		setProcessingFeePayType(ProcessingFeePayType.NORMAL);
	}
	
	public ProductDetail getFirstProduct() {
		return firstProduct;
	}
	public void setFirstProduct(ProductDetail firstProduct) {
		this.firstProduct = firstProduct;
	}
	public ProductDetail getSecondProduct() {
		return secondProduct;
	}
	public void setSecondProduct(ProductDetail secondProduct) {
		this.secondProduct = secondProduct;
	}
	public ProductDetail getThirdProduct() {
		return thirdProduct;
	}
	public void setThirdProduct(ProductDetail thirdProduct) {
		this.thirdProduct = thirdProduct;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}
	public String getPromotionCode() {
		return promotionCode;
	}
	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}
	public ProcessingFeePayType getProcessingFeePayType() {
		return processingFeePayType;
	}
	public void setProcessingFeePayType(ProcessingFeePayType processingFeePayType) {
		this.processingFeePayType = processingFeePayType;
	}
	
	public BigDecimal getPromoVoucher() {
		return promoVoucher;
	}
	public void setPromoVoucher(BigDecimal promoVoucher) {
		this.promoVoucher = promoVoucher;
	}
	
	
}
