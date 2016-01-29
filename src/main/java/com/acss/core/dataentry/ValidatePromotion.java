package com.acss.core.dataentry;

import java.math.BigDecimal;
import java.util.HashSet;

import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import com.acss.core.model.application.ProductPromoCategory;
import com.acss.core.model.application.PromotionRules;
import com.acss.core.model.application.StorePromo;
import com.acss.core.model.application.TermsPromo;
import com.acss.core.model.dataentry.DataEntryDTO;

@Service
public class ValidatePromotion {
	
	/**
	 *  To validate promotion rules
	 *  @author fcortez
	 *	return HashSet<FieldError>
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
				FieldError fieldError_5 = null;
				if(term.getMonthId().equals(String.valueOf(dataEntry.getInstallment().getTerm().getCode()))){
					
					fieldErrorList.remove(fieldError_5);
					break;
					
				}else{
					
					fieldError_5 = new FieldError(String.valueOf(dataEntry.getInstallment().getTerm()),"installment.term",
							"Loan term is not applicable for promotion number " + dataEntry.getInstallment().getPromotionCode());
					
						fieldErrorList.add(fieldError_5);
					
				}
			}
			
			if(promotionRules.getPromotion().getPcIsRetricted().equals(new BigDecimal(2))){
				
				FieldError fieldError_product_1 = null;
				FieldError fieldError_product_2 = null;
				FieldError fieldError_product_3 = null;
				
				if(!dataEntry.getInstallment().getFirstProduct().getCategory().equals("")){
					
					for(ProductPromoCategory category : promotionRules.getProductproductCategory()){
						
						if(category.getpCategoryCd().equals(String.valueOf(dataEntry.getInstallment().getFirstProduct().getCategoryCd()))){
							
							fieldErrorList.remove(fieldError_product_1);
							break;
							
						}else{
							
							// Check if the product category of the first product is equal to the input of the 1st product category.
							if(!category.getpCategoryCd().equals(dataEntry.getInstallment().getFirstProduct().getCategoryCd())){
								
								fieldError_product_1 = new FieldError(String.valueOf(dataEntry.getInstallment().getFirstProduct().getCategory()),"installment.firstProduct.category", 
										"1st Product category is not valid for promotion number " + promotionRules.getPromotion().getPromotionCd());
								
								fieldErrorList.add(fieldError_product_1);

							}
						}
									
					}
				}
				
				if(!dataEntry.getInstallment().getSecondProduct().getCategory().equals("")){
					
					for(ProductPromoCategory category : promotionRules.getProductproductCategory()){
						
						if(category.getpCategoryCd().equals(String.valueOf(dataEntry.getInstallment().getSecondProduct().getCategoryCd()))){
							
							fieldErrorList.remove(fieldError_product_2);
							break;
							
						}else{
							
							// Check if the product category of the first product is equal to the input of the 1st product category.
							if(!category.getpCategoryCd().equals(dataEntry.getInstallment().getSecondProduct().getCategoryCd())){
								
								fieldError_product_2 = new FieldError(String.valueOf(dataEntry.getInstallment().getSecondProduct().getCategory()),"installment.secondProduct.category", 
										"2nd Product category is not valid for promotion number " + promotionRules.getPromotion().getPromotionCd());
								
								fieldErrorList.add(fieldError_product_2);

							}
						}
									
					}
				}
				
				if(!dataEntry.getInstallment().getThirdProduct().getCategory().equals("")){
					for(ProductPromoCategory category : promotionRules.getProductproductCategory()){
						
						if(category.getpCategoryCd().equals(String.valueOf(dataEntry.getInstallment().getThirdProduct().getCategoryCd()))){
							
							fieldErrorList.remove(fieldError_product_3);
							break;
							
						}else{
							
							// Check if the product category of the first product is equal to the input of the 1st product category.
							if(!category.getpCategoryCd().equals(dataEntry.getInstallment().getThirdProduct().getCategoryCd())){
								
								fieldError_product_3 = new FieldError(String.valueOf(dataEntry.getInstallment().getThirdProduct().getCategory()),"installment.thirdProduct.category", 
										"3rd Product category is not valid for promotion number " + promotionRules.getPromotion().getPromotionCd());
								
								fieldErrorList.add(fieldError_product_3);

							}
						}
									
					}
				}
			}
			
		
			if(promotionRules.getFpfrom()!=null || promotionRules.getFpTo()!=null){
				
				if(promotionRules.getPromotion().getFpIsRetricted().equals(new BigDecimal(2))){
					// Check if the finance price is less than Finance Price From
					if((dataEntry.getInstallment().totalFinancePrice().compareTo(promotionRules.getFpfrom())<0)){
						
						FieldError fieldError_1 = new FieldError(String.valueOf(dataEntry.getInstallment().totalFinancePrice()), "installment.totalFinancePrice", 
								"Finance price is less than "+ promotionRules.getFpfrom());
						
						fieldErrorList.add(fieldError_1);
						
					}

					// Check if the finance price is greater than Finance Price From
					if((dataEntry.getInstallment().totalFinancePrice().compareTo(promotionRules.getFpTo())>0)){
						
						FieldError fieldError_2 = new FieldError(String.valueOf(dataEntry.getInstallment().totalFinancePrice()),"installment.totalFinancePrice",
								"Finance price is greater than " + promotionRules.getFpTo());
						
						fieldErrorList.add(fieldError_2);
						
					}
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
				
				//if(!(promotionRules.getPeriodFrom().isBefore(dataEntry.getStore().getReceivedDate()))){
				if(!(promotionRules.getPeriodFrom().isBeforeNow())){	
					FieldError fieldError_9 = new FieldError(String.valueOf(dataEntry.getStore().getReceivedDate()), "", 
							"Promotion " + promotionRules.getPromotion().getPromotionName() +" will start on " + promotionRules.getPromotion().getFpfrom());
					
					fieldErrorList.add(fieldError_9);
				}
				//if(!(promotionRules.getPeriodTo().isAfter(dataEntry.getStore().getReceivedDate())))
				if(!(promotionRules.getPeriodTo().isAfterNow())){
					
					FieldError fieldError_10 = new FieldError(String.valueOf(dataEntry.getStore().getReceivedDate()), "", 
							"Promotion " + promotionRules.getPromotion().getPromotionName() +" is already expired");
					
					fieldErrorList.add(fieldError_10);
				}
				
			}
			
			// Check the total product price
			if(promotionRules.getPpFrom()!=null || promotionRules.getPpTo()!=null){
				
				if(!(promotionRules.getPpFrom().compareTo(dataEntry.getInstallment().totalProductPrice())>0)){
					
					FieldError fieldError_11 = new FieldError(String.valueOf(dataEntry.getInstallment().totalProductPrice()), "installment.totalProductPrice", 
							"Finance price is less than "+ promotionRules.getPpFrom());
					
					fieldErrorList.add(fieldError_11);
					
				}

				if(!(promotionRules.getPpTo().compareTo(dataEntry.getInstallment().totalProductPrice())<0)){

					FieldError fieldError_12 = new FieldError(String.valueOf(dataEntry.getInstallment().totalProductPrice()), "installment.totalProductPrice", 
							"Finance price is less than "+ promotionRules.getPpTo());
					
					fieldErrorList.add(fieldError_12);
				}
				
			}
			
			FieldError fieldError_13 = null;

			for(StorePromo store : promotionRules.getStore()){
				
				if(dataEntry.getStore().getStoreCd().equals(store.getSmCd())){
					
					fieldErrorList.remove(fieldError_13);
					break;
					
				}else{
					
					fieldError_13 = new FieldError(String.valueOf(dataEntry.getStore().getStoreCd()), "installment.totalProductPrice", 
							"Store code #" + dataEntry.getStore().getStoreCd() + " is invalid for " + promotionRules.getPromotion().getPromotionCd());
					fieldErrorList.add(fieldError_13);
					
				}
			}
						
			return fieldErrorList;
	}
}
