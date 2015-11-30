package com.acss.core.model.builder;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;

import com.acss.core.model.dataentry.DataEntryDTO;
import com.acss.core.model.dataentry.common.AddressField;
import com.acss.core.model.dataentry.common.IdField;
import com.acss.core.model.dataentry.common.InstallmentDetail;
import com.acss.core.model.dataentry.common.NameField;
import com.acss.core.model.dataentry.common.PhoneField;
import com.acss.core.model.dataentry.common.ReferenceData;
import com.acss.core.model.dataentry.common.StoreInformation;
import com.acss.core.model.dataentry.common.constants.BankAccountType;
import com.acss.core.model.dataentry.common.constants.Citizenship;
import com.acss.core.model.dataentry.common.constants.CivilStatus;
import com.acss.core.model.dataentry.common.constants.DaysOfMonth;
import com.acss.core.model.dataentry.common.constants.EducationalAttainment;
import com.acss.core.model.dataentry.common.constants.EmploymentStatus;
import com.acss.core.model.dataentry.common.constants.Gender;
import com.acss.core.model.dataentry.common.constants.LivePerson;
import com.acss.core.model.dataentry.common.constants.MailTo;
import com.acss.core.model.dataentry.common.constants.NatureOfBusiness;
import com.acss.core.model.dataentry.common.constants.TypeOfEmployment;
import com.acss.core.model.dataentry.common.constants.TypeOfResidence;

public class DataEntryDTOBuilder {
	
	DataEntryDTO dataEntryDTO = new DataEntryDTO();

	/**
	 * @param applicationNo
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setApplicationNo(java.lang.String)
	 */
	public DataEntryDTOBuilder setApplicationNo(String applicationNo) {
		dataEntryDTO.setApplicationNo(applicationNo);
		return this;
	}

	/**
	 * @param applicantName
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setApplicantName(com.acss.core.model.dataentry.common.NameField)
	 */
	public DataEntryDTOBuilder setApplicantName(NameField applicantName) {
		dataEntryDTO.setApplicantName(applicantName);
		return this;
	}

	/**
	 * @param dateOfBirth
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setDateOfBirth(org.joda.time.DateTime)
	 */
	public DataEntryDTOBuilder setDateOfBirth(DateTime dateOfBirth) {
		dataEntryDTO.setDateOfBirth(dateOfBirth);
		return this;
	}

	/**
	 * @param applicantId
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setApplicantId(com.acss.core.model.dataentry.common.IdField)
	 */
	public DataEntryDTOBuilder setApplicantId(IdField applicantId) {
		dataEntryDTO.setApplicantId(applicantId);
		return this;
	}

	/**
	 * @param otherId
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setOtherId(com.acss.core.model.dataentry.common.IdField)
	 */
	public DataEntryDTOBuilder setOtherId(IdField otherId) {
		dataEntryDTO.setOtherId(otherId);
		return this;
	}

	/**
	 * @param additionalIds
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setAdditionalIds(java.util.List)
	 */
	public DataEntryDTOBuilder setAdditionalIds(List<IdField> additionalIds) {
		dataEntryDTO.setAdditionalIds(additionalIds);
		return this;
	}

	/**
	 * @param gender
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setGender(com.acss.core.model.dataentry.common.constants.Gender)
	 */
	public DataEntryDTOBuilder setGender(Gender gender) {
		dataEntryDTO.setGender(gender);
		return this;
	}

	/**
	 * @param citizenship
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setCitizenship(com.acss.core.model.dataentry.common.constants.Citizenship)
	 */
	public DataEntryDTOBuilder setCitizenship(Citizenship citizenship) {
		dataEntryDTO.setCitizenship(citizenship);
		return this;
	}

	/**
	 * @param address
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setAddress(com.acss.core.model.dataentry.common.AddressField)
	 */
	public DataEntryDTOBuilder setAddress(AddressField address) {
		dataEntryDTO.setAddress(address);
		return this;
	}

	/**
	 * @param residence
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setResidence(com.acss.core.model.dataentry.common.constants.TypeOfResidence)
	 */
	public DataEntryDTOBuilder setResidence(TypeOfResidence residence) {
		//dataEntryDTO.setResidence(residence);
		return this;
	}

	/**
	 * @param homePhone
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setHomePhone(com.acss.core.model.dataentry.common.PhoneField)
	 */
	public DataEntryDTOBuilder setHomePhone(PhoneField homePhone) {
		dataEntryDTO.setHomePhone(homePhone);
		return this;
	}

	/**
	 * @param homeMobile
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setHomeMobile(com.acss.core.model.dataentry.common.PhoneField)
	 */
	public DataEntryDTOBuilder setHomeMobile(PhoneField homeMobile) {
		dataEntryDTO.setHomeMobile(homeMobile);
		return this;
	}

	/**
	 * @param otherPhone
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setOtherPhone(com.acss.core.model.dataentry.common.PhoneField)
	 */
	public DataEntryDTOBuilder setOtherPhone(PhoneField otherPhone) {
		dataEntryDTO.setOtherPhone(otherPhone);
		return this;
	}

	/**
	 * @param yearsOfStay
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setYearsOfStay(java.lang.Integer)
	 */
	public DataEntryDTOBuilder setYearsOfStay(Integer yearsOfStay) {
		dataEntryDTO.setYearsOfStay(yearsOfStay);
		return this;
	}

	/**
	 * @param noOfDependents
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setNoOfDependents(java.lang.Integer)
	 */
	public DataEntryDTOBuilder setNoOfDependents(Integer noOfDependents) {
		dataEntryDTO.setNoOfDependents(noOfDependents);
		return this;
	}

	/**
	 * @param livingWith
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setLivingWith(com.acss.core.model.dataentry.common.constants.LivePerson)
	 */
	public DataEntryDTOBuilder setLivingWith(LivePerson livingWith) {
		dataEntryDTO.setLivingWith(livingWith);
		return this;
	}

	/**
	 * @param educationalAttainment
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setEducationalAttainment(com.acss.core.model.dataentry.common.constants.EducationalAttainment)
	 */
	public DataEntryDTOBuilder setEducationalAttainment(
			EducationalAttainment educationalAttainment) {
		dataEntryDTO.setEducationalAttainment(educationalAttainment);
		return this;
	}

	/**
	 * @param civiStatus
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setCivilStatus(com.acss.core.model.dataentry.common.constants.CivilStatus)
	 */
	public DataEntryDTOBuilder setCivilStatus(CivilStatus civiStatus) {
		dataEntryDTO.setCivilStatus(civiStatus);
		return this;
	}

	/**
	 * @param email
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setEmail(java.lang.String)
	 */
	public DataEntryDTOBuilder setEmail(String email) {
		dataEntryDTO.setEmail(email);
		return this;
	}

	/**
	 * @param spouseName
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setSpouseName(com.acss.core.model.dataentry.common.NameField)
	 */
	public DataEntryDTOBuilder setSpouseName(NameField spouseName) {
		dataEntryDTO.setSpouseName(spouseName);
		return this;
	}

	/**
	 * @param permanentAddress
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setPermanentAddress(com.acss.core.model.dataentry.common.AddressField)
	 */
	public DataEntryDTOBuilder setPermanentAddress(AddressField permanentAddress) {
		dataEntryDTO.setPermanentAddress(permanentAddress);
		return this;
	}

	/**
	 * @param motherMaidenName
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setMotherMaidenName(com.acss.core.model.dataentry.common.NameField)
	 */
	public DataEntryDTOBuilder setMotherMaidenName(NameField motherMaidenName) {
		dataEntryDTO.setMotherMaidenName(motherMaidenName);
		return this;
	}

	/**
	 * @param mailTo
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setMailTo(com.acss.core.model.dataentry.common.constants.MailTo)
	 */
	public DataEntryDTOBuilder setMailTo(MailTo mailTo) {
		dataEntryDTO.setMailTo(mailTo);
		return this;
	}

	/**
	 * @param employmentType
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setEmploymentType(com.acss.core.model.dataentry.common.constants.TypeOfEmployment)
	 */
	public DataEntryDTOBuilder setEmploymentType(TypeOfEmployment employmentType) {
		dataEntryDTO.setEmploymentType(employmentType);
		return this;
	}

	/**
	 * @param employmentStatus
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setEmploymentStatus(com.acss.core.model.dataentry.common.constants.EmploymentStatus)
	 */
	public DataEntryDTOBuilder setEmploymentStatus(EmploymentStatus employmentStatus) {
		dataEntryDTO.setEmploymentStatus(employmentStatus);
		return this;
	}

	/**
	 * @param natureOfBusiness
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setNatureOfBusiness(com.acss.core.model.dataentry.common.constants.NatureOfBusiness)
	 */
	public DataEntryDTOBuilder setNatureOfBusiness(NatureOfBusiness natureOfBusiness) {
		dataEntryDTO.setNatureOfBusiness(natureOfBusiness);
		return this;
	}

	/**
	 * @param department
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setDepartment(java.lang.String)
	 */
	public DataEntryDTOBuilder setDepartment(String department) {
		dataEntryDTO.setDepartment(department);
		return this;
	}

	/**
	 * @param monthlyNetIncome
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setMonthlyNetIncome(java.math.BigDecimal)
	 */
	public DataEntryDTOBuilder setMonthlyNetIncome(BigDecimal monthlyNetIncome) {
		dataEntryDTO.setMonthlyNetIncome(monthlyNetIncome);
		return this;
	}

	/**
	 * @param otherIncome
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setOtherIncome(java.math.BigDecimal)
	 */
	public DataEntryDTOBuilder setOtherIncome(BigDecimal otherIncome) {
		dataEntryDTO.setOtherIncome(otherIncome);
		return this;
	}

	/**
	 * @param companyName
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setCompanyName(java.lang.String)
	 */
	public DataEntryDTOBuilder setCompanyName(String companyName) {
		dataEntryDTO.setCompanyName(companyName);
		return this;
	}

	/**
	 * @param sourceOfIncome
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setSourceOfIncome(java.lang.String)
	 */
	public DataEntryDTOBuilder setSourceOfIncome(String sourceOfIncome) {
		dataEntryDTO.setSourceOfIncome(sourceOfIncome);
		return this;
	}

	/**
	 * @param corpAddress
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setCorpAddress(com.acss.core.model.dataentry.common.AddressField)
	 */
	public DataEntryDTOBuilder setCorpAddress(AddressField corpAddress) {
		dataEntryDTO.setCorpAddress(corpAddress);
		return this;
	}

	/**
	 * @param corpPhone
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setCorpPhone(com.acss.core.model.dataentry.common.PhoneField)
	 */
	public DataEntryDTOBuilder setCorpPhone(PhoneField corpPhone) {
		dataEntryDTO.setCorpPhone(corpPhone);
		return this;
	}

	/**
	 * @param salaryDate
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setSalaryDate(com.acss.core.model.dataentry.common.constants.DaysOfMonth)
	 */
	public DataEntryDTOBuilder setSalaryDate(DaysOfMonth salaryDate) {
		dataEntryDTO.setSalaryDate(salaryDate);
		return this;
	}

	/**
	 * @param yosYears
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setYosYears(java.lang.Integer)
	 */
	public DataEntryDTOBuilder setYosYears(Integer yosYears) {
		dataEntryDTO.setYosYears(yosYears);
		return this;
	}

	/**
	 * @param yosMonths
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setYosMonths(java.lang.Integer)
	 */
	public DataEntryDTOBuilder setYosMonths(Integer yosMonths) {
		dataEntryDTO.setYosMonths(yosMonths);
		return this;
	}

	/**
	 * @param occupation
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setOccupation(java.lang.String)
	 */
	public DataEntryDTOBuilder setOccupation(String occupation) {
		dataEntryDTO.setOccupation(occupation);
		return this;
	}

	/**
	 * @param bankName
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setBankName(java.lang.String)
	 */
	public DataEntryDTOBuilder setBankName(String bankName) {
		dataEntryDTO.setBankName(bankName);
		return this;
	}

	/**
	 * @param accountNo
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setAccountNo(java.lang.String)
	 */
	public DataEntryDTOBuilder setAccountNo(String accountNo) {
		dataEntryDTO.setAccountNo(accountNo);
		return this;
	}

	/**
	 * @param accountType
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setAccountType(com.acss.core.model.dataentry.common.constants.BankAccountType)
	 */
	public DataEntryDTOBuilder setAccountType(BankAccountType accountType) {
		dataEntryDTO.setAccountType(accountType);
		return this;
	}

	/**
	 * @param accountName
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setAccountName(java.lang.String)
	 */
	public DataEntryDTOBuilder setAccountName(String accountName) {
		dataEntryDTO.setAccountName(accountName);
		return this;
	}

	/**
	 * @param referenceData
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setReferenceData(com.acss.core.model.dataentry.common.ReferenceData)
	 */
	public DataEntryDTOBuilder setReferenceData(ReferenceData referenceData) {
		dataEntryDTO.setReferenceData(referenceData);
		return this;
	}

	/**
	 * @param installment
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setInstallment(com.acss.core.model.dataentry.common.InstallmentDetail)
	 */
	public DataEntryDTOBuilder setInstallment(InstallmentDetail installment) {
		dataEntryDTO.setInstallment(installment);
		return this;
	}

	/**
	 * @param store
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setStore(com.acss.core.model.dataentry.common.StoreInformation)
	 */
	public DataEntryDTOBuilder setStore(StoreInformation store) {
		dataEntryDTO.setStore(store);
		return this;
	}

	/**
	 * @param comment
	 * @see com.acss.core.model.dataentry.DataEntryDTO#setComment(java.lang.String)
	 */
	public DataEntryDTOBuilder setComment(String comment) {
		dataEntryDTO.setComment(comment);
		return this;
	} 
	
	public static DataEntryDTOBuilder create(){
		return new DataEntryDTOBuilder();
	}
	
	public DataEntryDTO get(){
		return this.dataEntryDTO;
	}
}
