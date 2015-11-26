package com.acss.core.dataentry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.validation.FieldError;

import com.acss.core.model.ACSSDateUtil;
import com.acss.core.model.application.ProductPromoCategory;
import com.acss.core.model.application.PromotionRules;
import com.acss.core.model.application.TermsPromo;
import com.acss.core.model.dataentry.DataEntryDTO;


public class ValidatePromotion {
	
	/**
	 *  To validate promotion rules
	 *  @author fcortez
	 *	return List<FieldError>
	 *
	 */
	public HashSet<FieldError> promotionErrors(DataEntryDTO dataEntry, PromotionRules promotionRules){
		
		HashSet<FieldError> fieldErrorList = new HashSet<FieldError>();
		
		// Check if promotion code is available
		if(promotionRules.getPromotion()==null){
			
			FieldError fieldError_4 = new FieldError(String.valueOf(dataEntry.getInstallment().getPromotionCode()), 
					"installment.promotionCode", "Promotion code is not available");
			
			fieldErrorList.add(fieldError_4);
			
			return fieldErrorList;
			
		}
			
			// Check if the term exist in the list of terms
			for(TermsPromo term : promotionRules.getTerm()){
			
				if(term.getMonthId().equals(String.valueOf(String.valueOf(dataEntry.getInstallment().getTerm().getCode())))){
					
					fieldErrorList.clear();
					break;
					
				}else{
					
					FieldError fieldError_5 = new FieldError(String.valueOf(dataEntry.getInstallment().getTerm()),"installment.term",
							"Loan term is not applicable for promotion number " + dataEntry.getInstallment().getPromotionCode());
					
						fieldErrorList.add(fieldError_5);
					
				}
			}
			
			if(!promotionRules.getPromotion().getPcIsRetricted().equals(new BigDecimal(2))){
				
				if(!dataEntry.getInstallment().getFirstProduct().getCategory().equals("")){
					
					for(ProductPromoCategory category : promotionRules.getProductproductCategory()){
						
						if(category.getpCategoryCd().equals(String.valueOf(dataEntry.getInstallment().getFirstProduct().getCategoryCd()))){
							
							fieldErrorList.clear();
							break;
							
						}else{
							
							// Check if the product category of the first product is equal to the input of the 1st product category.
							if(!category.getpCategoryCd().equals(dataEntry.getInstallment().getFirstProduct().getCategoryCd())){
								
								FieldError fieldError_6 = new FieldError(String.valueOf(dataEntry.getInstallment().getFirstProduct().getCategory()),"installment.firstProduct.category", 
										"1st Product category is not valid for promotion number " + promotionRules.getPromotion().getPromotionCd());
								
								fieldErrorList.add(fieldError_6);

							}
						}
									
					}
				}
				
				if(!dataEntry.getInstallment().getSecondProduct().getCategory().equals("")){
					
					for(ProductPromoCategory category : promotionRules.getProductproductCategory()){
						
						if(category.getpCategoryCd().equals(String.valueOf(dataEntry.getInstallment().getSecondProduct().getCategoryCd()))){
							
							fieldErrorList.clear();
							break;
							
						}else{
							
							// Check if the product category of the first product is equal to the input of the 1st product category.
							if(!category.getpCategoryCd().equals(dataEntry.getInstallment().getSecondProduct().getCategoryCd())){
								
								FieldError fieldError_6 = new FieldError(String.valueOf(dataEntry.getInstallment().getSecondProduct().getCategory()),"installment.secondProduct.category", 
										"2nd Product category is not valid for promotion number " + promotionRules.getPromotion().getPromotionCd());
								
								fieldErrorList.add(fieldError_6);

							}
						}
									
					}
				}
				
				if(!dataEntry.getInstallment().getSecondProduct().getCategory().equals("")){
					for(ProductPromoCategory category : promotionRules.getProductproductCategory()){
						
						if(category.getpCategoryCd().equals(String.valueOf(dataEntry.getInstallment().getThirdProduct().getCategoryCd()))){
							
							fieldErrorList.clear();
							break;
							
						}else{
							
							// Check if the product category of the first product is equal to the input of the 1st product category.
							if(!category.getpCategoryCd().equals(dataEntry.getInstallment().getThirdProduct().getCategoryCd())){
								
								FieldError fieldError_6 = new FieldError(String.valueOf(dataEntry.getInstallment().getThirdProduct().getCategory()),"installment.thirdProduct.category", 
										"3rd Product category is not valid for promotion number " + promotionRules.getPromotion().getPromotionCd());
								
								fieldErrorList.add(fieldError_6);

							}
						}
									
					}
				}
			}
			
		
			if(promotionRules.getFpfrom()!=null || promotionRules.getFpTo()!=null){
			
						// Check if the finance price is less than Finance Price From
						if((dataEntry.getInstallment().totalFinancePrice().compareTo(promotionRules.getFpfrom())<=0)){
							
							FieldError fieldError_1 = new FieldError(String.valueOf(dataEntry.getInstallment().totalFinancePrice()), "installment.totalFinancePrice", 
									"Finance price is less than "+ promotionRules.getFpfrom());
							
							fieldErrorList.add(fieldError_1);
							
						}

						// Check if the finance price is greater than Finance Price From
						if((dataEntry.getInstallment().totalFinancePrice().compareTo(promotionRules.getFpTo())>=0)){
							
							FieldError fieldError_2 = new FieldError(String.valueOf(dataEntry.getInstallment().totalFinancePrice()),"installment.totalFinancePrice",
									"Finance price is greater than " + promotionRules.getFpTo());
							
							fieldErrorList.add(fieldError_2);
							
						}
			
			}
			
			// Check if rate is equal to rate
			if(!dataEntry.getInstallment().getRate().equals(promotionRules.getInterestRate())){
				
				FieldError fieldError_3 = new FieldError(String.valueOf(dataEntry.getInstallment().getRate()), 
						"installment.rate", "Interest rate should be "+ promotionRules.getInterestRate() + "%");
				
				fieldErrorList.add(fieldError_3);
				
			}
			

			// Check the promotion date range
			if(promotionRules.getPeriodFrom()!=null||promotionRules.getPeriodTo()!=null){
				
				if(!(promotionRules.getPeriodFrom().isBefore(dataEntry.getStore().getReceivedDate()))){
					
					FieldError fieldError_9 = new FieldError(String.valueOf(dataEntry.getStore().getReceivedDate()), "store.receivedDate", 
							"Promotion is only valid from " + promotionRules.getPeriodFrom() + " to " + promotionRules.getPeriodTo());
					
					fieldErrorList.add(fieldError_9);
				}

				if(!(promotionRules.getPeriodTo().isAfter(dataEntry.getStore().getReceivedDate()))){
					
					FieldError fieldError_10 = new FieldError(String.valueOf(dataEntry.getStore().getReceivedDate()), "store.receivedDate", 
							"Promotion is only valid from " + ACSSDateUtil.getDateAsYYYYMMDDFromDateTime(promotionRules.getPeriodFrom()) + " to " + ACSSDateUtil.getDateAsYYYYMMDDFromDateTime(promotionRules.getPeriodTo()));
					
					fieldErrorList.add(fieldError_10);
				}
				
			}
			
			// Check the total product price
			if(promotionRules.getPpFrom()!=null || promotionRules.getPpTo()!=null){
				
				if(!(promotionRules.getPpFrom().compareTo(dataEntry.getInstallment().totalProductPrice())>=0)){
					
					FieldError fieldError_11 = new FieldError(String.valueOf(dataEntry.getInstallment().totalProductPrice()), "installment.totalProductPrice", 
							"Finance price is less than "+ promotionRules.getPpFrom());
					
					fieldErrorList.add(fieldError_11);
					
				}

				if(!(promotionRules.getPpTo().compareTo(dataEntry.getInstallment().totalProductPrice())<=0)){

					FieldError fieldError_12 = new FieldError(String.valueOf(dataEntry.getInstallment().totalProductPrice()), "installment.totalProductPrice", 
							"Finance price is less than "+ promotionRules.getPpTo());
					
					fieldErrorList.add(fieldError_12);
				}
				
			}
						
			return fieldErrorList;
	}
}
