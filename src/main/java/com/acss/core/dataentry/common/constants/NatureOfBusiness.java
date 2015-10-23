package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum NatureOfBusiness {

	ACCOMODATION_HOTEL(1,"natureofbusiness.hotel"),
	AGENCY(2,"natureofbusiness.agency"),
	AGRICULTURE(3,"natureofbusiness.agri"),
	ARMY_POLICE(4,"natureofbusiness.army"),
	AUTOMOTIVE_CARDEALER(5,"natureofbusiness.cardealer"),
	BANKING_FINANCING(6,"natureofbusiness.finance"),
	BEAUTY_WELLNESS(7,"natureofbusiness.wellness"),
	BUSINESS_SERVICES(8,"natureofbusiness.business"),
	CALL_CENTER(9,"natureofbusiness.callcenter"),
	CONSTRUCTION(10,"natureofbusiness.construction"),
	CONSULTANCY(11,"natureofbusiness.consultancy"),
	DIRECT_SELLING(12,"natureofbusiness.selling"),
	EDUCATION_LEARNING(13,"natureofbusiness.education"),
	ENTERTAINMENT_MEDIA(14,"natureofbusiness.media"),
	GOVERNMENT(15,"natureofbusiness.government"),
	INSURANCE_SECURITIES(16,"natureofbusiness.insurance"),
	MANUFACTURING(17,"natureofbusiness.manu"),
	MEDICAL_CLINIC(18,"natureofbusiness.clinic"),
	MINING_RESOURCES(19,"natureofbusiness.mining"),
	PHARMACEUTICAL(20,"natureofbusiness.pharma"),
	PRINTING_PUBLISHING(21,"natureofbusiness.printing"),
	REAL_ESTATE_DEVELOPER(22,"natureofbusiness.realestate"),
	RENTAL_LEASING(23,"natureofbusiness.rental"),
	SOCIAL_SERVICES(24,"natureofbusiness.social"),
	RESTAURANT_FOOD_STALL(25,"natureofbusiness.resto"),
	RETAIL_MERCHANDISING(26,"natureofbusiness.retail"),
	SECURITY_SERVICES(27,"natureofbusiness.security"),
	SPORTS_RECREATION(28,"natureofbusiness.sports"),
	TRADING_BROKERAGE(29,"natureofbusiness.trade"),
	TRAVEL_LEISURE(30,"natureofbusiness.travel"),
	TRANSPORTATION(31,"natureofbusiness.transpo"),
	UTILITIES(32,"natureofbusiness.utilities"),
	WHOLESALE_DISTRIBUTOR(33,"natureofbusiness.wholesale"),
	NA(34,"natureofbusiness.na"),
	OTHERS(35,"natureofbusiness.others");
	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "natureOfBusinessList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, NatureOfBusiness> lookupByValue = new HashMap<String, NatureOfBusiness>();
		public static final Map<BigDecimal, NatureOfBusiness> lookupByCode = new HashMap<BigDecimal, NatureOfBusiness>();
	}
	
	NatureOfBusiness(int code, String value) {
		this.code = code;
		this.value = value;
		BootstrapSingleton.lookupByValue.put(value, this);
		BootstrapSingleton.lookupByCode.put(new BigDecimal(code), this);
	}
	
	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
	
}
