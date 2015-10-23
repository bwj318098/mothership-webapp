package com.acss.core.dataentry;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.acss.core.dataentry.common.AddressField;
import com.acss.core.dataentry.common.IdField;
import com.acss.core.dataentry.common.InstallmentDetail;
import com.acss.core.dataentry.common.NameField;
import com.acss.core.dataentry.common.PhoneField;
import com.acss.core.dataentry.common.ReferenceData;
import com.acss.core.dataentry.common.constants.BankAccountType;
import com.acss.core.dataentry.common.constants.Citizenship;
import com.acss.core.dataentry.common.constants.CivilStatus;
import com.acss.core.dataentry.common.constants.DaysOfMonth;
import com.acss.core.dataentry.common.constants.EducationalAttainment;
import com.acss.core.dataentry.common.constants.EmploymentStatus;
import com.acss.core.dataentry.common.constants.Gender;
import com.acss.core.dataentry.common.constants.MailTo;
import com.acss.core.dataentry.common.constants.NatureOfBusiness;
import com.acss.core.dataentry.common.constants.TypeOfEmployment;
import com.acss.core.dataentry.common.constants.TypeOfResidence;

/**
 * Data Transfer Object for Data Entry Module used in Forms and Display Screens.
 * 
 * @author gvargas
 *
 */
public class DataEntryDTO {
	//* * * * * * * * * * * * * * * *Applicant Information Starts Here * * * * * * * * * * * * * * * *
	private NameField applicantName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private DateTime dateOfBirth;
	private IdField applicantId;
	private IdField otherId;
	private List<IdField> additionalIds;
	private Gender gender;
	private Citizenship citizenship;
	private AddressField address;
	private TypeOfResidence residence;
	private PhoneField homePhone;
	private PhoneField homeMobile;
	private PhoneField otherPhone;
	private Integer yearsOfStay;
	private Integer noOfDependents;
	private EducationalAttainment educationalAttainment;
	private CivilStatus civilStatus;
	private String email;
	private NameField spouseName;
	private AddressField permanentAddress;
	private NameField motherMaidenName;
	private MailTo mailTo;
	//* * * * * * * * * * * * * * * *Applicant Information Ends Here * * * * * * * * * * * * * * *
	
	//* * * * * * * * * * * * * * * *Employment Information Starts Here * * * * * * * * * * * * * *
	private TypeOfEmployment employmentType;
	private BigDecimal monthlyNetIncome;
	private BigDecimal otherIncome;
	private String companyName;
	private String sourceOfIncome;
	private AddressField corpAddress;
	private PhoneField corpPhone;
	private DaysOfMonth salaryDate;
	//years of service
	private Integer yosYears;
	private Integer yosMonths;
	private String occupation;
	private EmploymentStatus employmentStatus;
	private NatureOfBusiness natureOfBusiness;
	//* * * * * * * * * * * * * * * *Employment Information Ends Here * * * * * * * * * * * * * * *
	
	//* * * * * * * * * * * * * * * *Bank Account Information Starts Here * * * * * * * * * * * * *
	private String bankName;
	private String accountNo;
	private BankAccountType accountType;
	private String accountName;
	//* * * * * * * * * * * * * * * *Bank Account Information Ends Here * * * * * * * * * * * * * *
	
	//* * * * * * * * * * * * * * * *Reference Information Starts Here * * * * * * * * * * * * * *
	private ReferenceData referenceData;
	//* * * * * * * * * * * * * * * *Reference Information Ends Here * * * * * * * * * * * * * * *
	
	//* * * * * * * * * * * * * * * *Installment Information Starts Here * * * * * * * * * * * * * *
	private InstallmentDetail installment;
	//* * * * * * * * * * * * * * * *Installment Information Ends Here * * * * * * * * * * * * * * *
	
	public NameField getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(NameField applicantName) {
		this.applicantName = applicantName;
	}
	public DateTime getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(DateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public IdField getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(IdField applicantId) {
		this.applicantId = applicantId;
	}
	
	public IdField getOtherId() {
		return otherId;
	}
	public void setOtherId(IdField otherId) {
		this.otherId = otherId;
	}
	public List<IdField> getAdditionalIds() {
		return additionalIds;
	}
	public void setAdditionalIds(List<IdField> additionalIds) {
		this.additionalIds = additionalIds;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Citizenship getCitizenship() {
		return citizenship;
	}
	public void setCitizenship(Citizenship citizenship) {
		this.citizenship = citizenship;
	}
	public AddressField getAddress() {
		return address;
	}
	public void setAddress(AddressField address) {
		this.address = address;
	}
	public TypeOfResidence getResidence() {
		return residence;
	}
	public void setResidence(TypeOfResidence residence) {
		this.residence = residence;
	}
	public PhoneField getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(PhoneField homePhone) {
		this.homePhone = homePhone;
	}
	public PhoneField getHomeMobile() {
		return homeMobile;
	}
	public void setHomeMobile(PhoneField homeMobile) {
		this.homeMobile = homeMobile;
	}
	public PhoneField getOtherPhone() {
		return otherPhone;
	}
	public void setOtherPhone(PhoneField otherPhone) {
		this.otherPhone = otherPhone;
	}
	public Integer getYearsOfStay() {
		return yearsOfStay;
	}
	public void setYearsOfStay(Integer yearsOfStay) {
		this.yearsOfStay = yearsOfStay;
	}
	public Integer getNoOfDependents() {
		return noOfDependents;
	}
	public void setNoOfDependents(Integer noOfDependents) {
		this.noOfDependents = noOfDependents;
	}
	public EducationalAttainment getEducationalAttainment() {
		return educationalAttainment;
	}
	public void setEducationalAttainment(EducationalAttainment educationalAttainment) {
		this.educationalAttainment = educationalAttainment;
	}
	public CivilStatus getCivilStatus() {
		return civilStatus;
	}
	public void setCivilStatus(CivilStatus civiStatus) {
		this.civilStatus = civiStatus;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public NameField getSpouseName() {
		return spouseName;
	}
	public void setSpouseName(NameField spouseName) {
		this.spouseName = spouseName;
	}
	public AddressField getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(AddressField permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	public NameField getMotherMaidenName() {
		return motherMaidenName;
	}
	public void setMotherMaidenName(NameField motherMaidenName) {
		this.motherMaidenName = motherMaidenName;
	}
	public MailTo getMailTo() {
		return mailTo;
	}
	public void setMailTo(MailTo mailTo) {
		this.mailTo = mailTo;
	}
	
	
	
	
	public TypeOfEmployment getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(TypeOfEmployment employmentType) {
		this.employmentType = employmentType;
	}
	public EmploymentStatus getEmploymentStatus() {
		return employmentStatus;
	}
	public void setEmploymentStatus(EmploymentStatus employmentStatus) {
		this.employmentStatus = employmentStatus;
	}
	public NatureOfBusiness getNatureOfBusiness() {
		return natureOfBusiness;
	}
	public void setNatureOfBusiness(NatureOfBusiness natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}
	public BigDecimal getMonthlyNetIncome() {
		return monthlyNetIncome;
	}
	public void setMonthlyNetIncome(BigDecimal monthlyNetIncome) {
		this.monthlyNetIncome = monthlyNetIncome;
	}
	public BigDecimal getOtherIncome() {
		return otherIncome;
	}
	public void setOtherIncome(BigDecimal otherIncome) {
		this.otherIncome = otherIncome;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSourceOfIncome() {
		return sourceOfIncome;
	}
	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}
	public AddressField getCorpAddress() {
		return corpAddress;
	}
	public void setCorpAddress(AddressField corpAddress) {
		this.corpAddress = corpAddress;
	}
	public PhoneField getCorpPhone() {
		return corpPhone;
	}
	public void setCorpPhone(PhoneField corpPhone) {
		this.corpPhone = corpPhone;
	}
	
	public DaysOfMonth getSalaryDate() {
		return salaryDate;
	}
	public void setSalaryDate(DaysOfMonth salaryDate) {
		this.salaryDate = salaryDate;
	}
	public Integer getYosYears() {
		return yosYears;
	}
	public void setYosYears(Integer yosYears) {
		this.yosYears = yosYears;
	}
	public Integer getYosMonths() {
		return yosMonths;
	}
	public void setYosMonths(Integer yosMonths) {
		this.yosMonths = yosMonths;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public BankAccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(BankAccountType accountType) {
		this.accountType = accountType;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public ReferenceData getReferenceData() {
		return referenceData;
	}
	public void setReferenceData(ReferenceData referenceData) {
		this.referenceData = referenceData;
	}
	public InstallmentDetail getInstallment() {
		return installment;
	}
	public void setInstallment(InstallmentDetail installment) {
		this.installment = installment;
	}
	
	
	
}
