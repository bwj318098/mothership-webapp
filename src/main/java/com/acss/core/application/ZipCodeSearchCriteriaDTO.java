package com.acss.core.application;

public class ZipCodeSearchCriteriaDTO {
	
	private String postId;
	private String postCode;
	private String provinceNm;
	private String cityNm;
	private String areaNm;
	private String region;
	private String areaCd;
	
	public ZipCodeSearchCriteriaDTO(){
		
	}
	
	public ZipCodeSearchCriteriaDTO(String postCode, String postId, String areaNm, String cityNm, 
	String region){
		
		this.postCode = postCode;
		this.postId = postId;
		this.areaNm = areaNm;
		this.cityNm = cityNm;
		this.region = postCode;
	}
	
	public String appendParameters(String uri){
		uri=postCode!=null?uri+"postCode="+postCode+"&":uri;
		uri=postId!=null?uri+"postId="+postId+"&":uri;
		uri=areaNm!=null?uri+"areaNm="+areaNm+"&":uri;
		uri=cityNm!=null?uri+"cityNm="+cityNm+"&":uri;
		uri=region!=null?uri+"region="+region+"&":uri;
		return uri;
	}
	
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getProvinceNm() {
		return provinceNm;
	}
	public void setProvinceNm(String provinceNm) {
		this.provinceNm = provinceNm;
	}
	public String getCityNm() {
		return cityNm;
	}
	public void setCityNm(String cityNm) {
		this.cityNm = cityNm;
	}
	public String getAreaNm() {
		return areaNm;
	}
	public void setAreaNm(String areaNm) {
		this.areaNm = areaNm;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getAreaCd() {
		return areaCd;
	}
	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}
	
}
