package com.acss.core.dataentry;

import org.springframework.ui.Model;

import com.acss.core.model.application.PromotionRules;
import com.acss.core.model.dataentry.DataEntryDTO;

public interface DataEntryService {
	/**
	 * saves the data entry information to HPS
	 * @param dataEntry
	 * @return true if success false if failed.
	 */
	public boolean save(DataEntryDTO dataEntry);
	
	/**
	 * 
	 */
	public DataEntryDTO getDetails(String customerCd);
	
	
	/**
	 * binds all the ENUM to model for drop downs.
	 */
	public void bindAllEnumsToModel(Model model);
	
	/**
	 * get promotion rules
	 * @param promotionCd
	 * @return PromotionRules
	 */
	public PromotionRules getPromotionDetails(String promotionCd);
}
