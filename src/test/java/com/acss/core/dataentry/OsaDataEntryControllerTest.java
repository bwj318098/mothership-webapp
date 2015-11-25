package com.acss.core.dataentry;

import static com.acss.core.RequestParamUtils.addParams;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import com.acss.core.config.ApplicationConfig;
import com.acss.core.config.SecurityConfig;
import com.acss.core.config.WebMvcConfig;
import com.acss.core.model.builder.AddressFieldBuilder;
import com.acss.core.model.builder.DataEntryDTOBuilder;
import com.acss.core.model.builder.IdFieldBuilder;
import com.acss.core.model.builder.NameFieldBuilder;
import com.acss.core.model.builder.PhoneFieldBuilder;
import com.acss.core.model.builder.ReferenceDataBuilder;
import com.acss.core.model.builder.StoreInformationBuilder;
import com.acss.core.model.dataentry.DataEntryDTO;
import com.acss.kaizen.jooq.poc.configuration.PersistenceContext;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
		ApplicationConfig.class,
		SecurityConfig.class, 
		WebMvcConfig.class, 
		PersistenceContext.class}, loader = AnnotationConfigWebContextLoader.class)
public class OsaDataEntryControllerTest {

	private static final String STRING_101_CHARS = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
	
	private static final String STRING_71_CHARS = "01234567890123456789012345678901234567890123456789012345678901234567890";
	
	private static final String STRING_51_CHARS = "012345678901234567890123456789012345678901234567890";
	
	private static final String STRING_31_CHARS = "0123456789012345678901234567890";
	
	private static final String STRING_21_CHARS = "012345678901234567890";
	
	private static final String STRING_07_CHARS = "0123456";
	
	@InjectMocks
	OsaDataEntryController osaDataEntryController;

	@Autowired
	@Mock
	private DataEntryService dataEntryService;

	private MockMvc mockMvc;

	@Mock
	View mockView;

	static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	ObjectMapper mapper = new ObjectMapper(); 
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(osaDataEntryController)
				.setSingleView(mockView).build();
	}
		
	@Test
	public void testDataEntry_Null_TopLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"applicantName",
				"dateOfBirth",
				"applicantId",
				"otherId",
				"gender",
				"citizenship",
				"address",
				"residence",
				"homePhone",
				"homeMobile",
				"otherPhone",
				"livingWith",
				"educationalAttainment",
				"civilStatus",
				"permanentAddress",
				"mailTo",
				"employmentType",
				"monthlyNetIncome",
				"corpAddress",
				"corpPhone",
				"salaryDate",
				"yosYears",
				"yosMonths",
				"employmentStatus",
				"natureOfBusiness",
				"referenceData"
		};
		
		ResultActions resultActions = mockMvc.perform(post("/dataentry"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
				
		for(String field : fieldsToCheck){
			resultActions = resultActions
					.andExpect(jsonPath("$.fieldErrors['" + field + "']", notNullValue())) 
					.andExpect(jsonPath("$.fieldErrors['" + field + "']", is("required"))); 
		}
				
	}
	
	@Test
	public void testDataEntry_Empty_TopLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"companyName",
				"sourceOfIncome",
				"occupation"
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
							.setCompanyName("")
							.setSourceOfIncome("")
							.setOccupation("")
							.get();
		
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
		
		for(String field : fieldsToCheck){
			resultActions = resultActions
					.andExpect(jsonPath("$.fieldErrors['" + field + "']", notNullValue())) 
					.andExpect(jsonPath("$.fieldErrors['" + field + "']", is("required"))); 
		}

	}
	
	@Test
	public void testDataEntry_InvalidNumberBounds_TopLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"monthlyNetIncome",
				"otherIncome"
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
							.setMonthlyNetIncome(new BigDecimal("12345678901"))
							.setOtherIncome(new BigDecimal("12345678901"))
							.get();
		
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
		
		for(String field : fieldsToCheck){
			resultActions = resultActions
					.andExpect(jsonPath("$.fieldErrors['" + field + "']", notNullValue())) 
					.andExpect(jsonPath("$.fieldErrors['" + field + "']", is("numeric value out of bounds (<10 digits>.<2 digits> expected)"))); 
		}
			
		input = DataEntryDTOBuilder.create()
							.setMonthlyNetIncome(new BigDecimal("78.901"))
							.setOtherIncome(new BigDecimal("78.901"))
							.get();
		
		resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));

		for(String field : fieldsToCheck){
			resultActions = resultActions
					.andExpect(jsonPath("$.fieldErrors['" + field + "']", notNullValue())) 
					.andExpect(jsonPath("$.fieldErrors['" + field + "']", is("numeric value out of bounds (<10 digits>.<2 digits> expected)"))); 
		}
		
	}
		
	@Test
	public void testDataEntry_InvalidSize_TopLevelFields() throws Exception {
		String[][] fieldsToCheck = {
				{"companyName", STRING_71_CHARS},
				{"sourceOfIncome", STRING_51_CHARS},
				{"occupation", STRING_51_CHARS},
				{"bankName", STRING_51_CHARS},
				{"accountNo", STRING_51_CHARS},
				{"accountName", STRING_101_CHARS}
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
							.setCompanyName(STRING_71_CHARS)
							.setSourceOfIncome(STRING_51_CHARS)
							.setOccupation(STRING_51_CHARS)
							.setBankName(STRING_51_CHARS)
							.setAccountNo(STRING_51_CHARS)
							.setAccountName(STRING_101_CHARS)
							.get();
		
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
		
		
		for(String[] propertyName : fieldsToCheck){
			resultActions = resultActions
					.andExpect(jsonPath("$.fieldErrors['" + propertyName[0] + "']", notNullValue())) 
					.andExpect(jsonPath("$.fieldErrors['" + propertyName[0] + "']", is("size must be between 0 and " + (propertyName[1].length() - 1)))); 
		}
	}
	
	@Test
	public void testDataEntry_NameField_Empty_InnerLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"applicantName",
				"spouseName",
				"motherMaidenName"
		};
		
		String[] propsToCheck = {
				"firstName",
				"surName"
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
								.setApplicantName(NameFieldBuilder.create().setMiddleName("").get())
								.setSpouseName(NameFieldBuilder.create().setMiddleName("").get())
								.setMotherMaidenName(NameFieldBuilder.create().setMiddleName("").get())
								.get();
				
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));

		for(String fieldName : fieldsToCheck){
			for(String prop : propsToCheck){
				String propertyName = fieldName + "." + prop;  
				
				resultActions = resultActions
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", notNullValue())) 
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", is("required")));			
			}	
		}
	}
	
	@Test
	public void testDataEntry_NameField_InvalidSize_InnerLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"applicantName",
				"spouseName",
				"motherMaidenName"
		};
		
		String[] propsToCheck = {
				"firstName",
				"middleName",
				"surName"
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
								.setApplicantName(NameFieldBuilder.create()
													.setFirstName(STRING_31_CHARS)
													.setMiddleName(STRING_31_CHARS)
													.setSurName(STRING_31_CHARS).get())
								.setSpouseName(NameFieldBuilder.create()
													.setFirstName(STRING_31_CHARS)
													.setMiddleName(STRING_31_CHARS)
													.setSurName(STRING_31_CHARS).get())
								.setMotherMaidenName(NameFieldBuilder.create()
													.setFirstName(STRING_31_CHARS)
													.setMiddleName(STRING_31_CHARS)
													.setSurName(STRING_31_CHARS).get())
								.get();
				
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
		
		for(String fieldName : fieldsToCheck){
			for(String prop : propsToCheck){
				String propertyName = fieldName + "." + prop;  
				
				resultActions = resultActions
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", notNullValue())) 
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", is("size must be between 0 and 30")));			
			}	
		}

	}
	
	
	@Test
	public void testDataEntry_IdField_Empty_InnerLevelFields() throws Exception {
		String[] fieldsToCheck = {
				//"applicantId",
				//"otherId",
				"additionalIds[0]",
				"additionalIds[1]"
		};
		
		String[] propsToCheck = {
				"idType",
				"idCardNo"
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
								.setApplicantId(IdFieldBuilder.create()
											.setPrimaryStatus(true).get())
								.setOtherId(IdFieldBuilder.create()
											.setPrimaryStatus(true).get())
								.setAdditionalIds(IdFieldBuilder.create()
											.setPrimaryStatus(true)
												.next()
											.setPrimaryStatus(true)
												.list())
								.get();
				
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));		
		
		for(String fieldName : fieldsToCheck){
			for(String prop : propsToCheck){
				String propertyName = fieldName + "." + prop;  
				
				resultActions = resultActions
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", notNullValue())) 
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", is("required")));			
			}	
		}
	}
	
	@Test
	public void testDataEntry_IdField_LongSize_InnerLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"applicantId",
				"otherId",
				"additionalIds[0]",
				"additionalIds[1]"
		};
		
		String[] propsToCheck = {
				"idCardNo"
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
								.setApplicantId(IdFieldBuilder.create()
											.setIdCardNo(STRING_21_CHARS).get())
								.setOtherId(IdFieldBuilder.create()
											.setIdCardNo(STRING_21_CHARS).get())
								.setAdditionalIds(IdFieldBuilder.create()
											.setIdCardNo(STRING_21_CHARS)
												.next()
											.setIdCardNo(STRING_21_CHARS).list())
								.get();
				
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
		
		for(String fieldName : fieldsToCheck){
			for(String prop : propsToCheck){
				String propertyName = fieldName + "." + prop;  
				
				resultActions = resultActions
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", notNullValue())) 
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", is("size must be between 0 and 20")));			
			}	
		}
	}
	
	@Test
	public void testDataEntry_PhoneNumber_Empty_InnerLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"homePhone",
				"homeMobile",
				"otherPhone",
				"corpPhone"
		};
		
		String[] propsToCheck = {
				"region",
				"phoneNo"
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
								.setHomePhone(PhoneFieldBuilder.create()
										.setLocal("").get())
								.setHomeMobile(PhoneFieldBuilder.create()
										.setLocal("").get())
								.setOtherPhone(PhoneFieldBuilder.create()
										.setLocal("").get())
								.setCorpPhone(PhoneFieldBuilder.create()
										.setLocal("").get())
								.get();
				
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
		
		for(String fieldName : fieldsToCheck){
			for(String prop : propsToCheck){
				String propertyName = fieldName + "." + prop;  
				
				resultActions = resultActions
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", notNullValue())) 
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", is("required")));			
			}	
		}
	}
	
	@Test
	public void testDataEntry_PhoneNumber_WrongFormat_InnerLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"homePhone",
				"homeMobile",
				"otherPhone",
				"corpPhone"
		};
		
		String[] propsToCheck = {
				"region",
				"phoneNo"
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
								.setHomePhone(PhoneFieldBuilder.create()
										.setRegion("AB12")
										.setPhoneNo("123456789C").get())
								.setHomeMobile(PhoneFieldBuilder.create()
										.setRegion("AB12")
										.setPhoneNo("123456789C").get())
								.setOtherPhone(PhoneFieldBuilder.create()
										.setRegion("AB12")
										.setPhoneNo("123456789C").get())
								.setCorpPhone(PhoneFieldBuilder.create()
										.setRegion("AB12")
										.setPhoneNo("123456789C").get())
								.get();
				
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
		
		for(String fieldName : fieldsToCheck){
			for(String prop : propsToCheck){
				String propertyName = fieldName + "." + prop;  
				
				resultActions = resultActions
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", notNullValue())) 
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", is("incorrect format")));			
			}	
		}
	}
	
	@Test
	public void testDataEntry_PhoneNumber_LongSize_InnerLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"homePhone",
				"homeMobile",
				"otherPhone",
				"corpPhone"
		};
		
		String[] propsToCheck = {
				"region",
				"local"
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
								.setHomePhone(PhoneFieldBuilder.create()
										.setRegion("12345")
										.setLocal("123456").get())
								.setHomeMobile(PhoneFieldBuilder.create()
										.setRegion("12345")
										.setLocal("123456").get())
								.setOtherPhone(PhoneFieldBuilder.create()
										.setRegion("12345")
										.setLocal("123456").get())
								.setCorpPhone(PhoneFieldBuilder.create()
										.setRegion("12345")
										.setLocal("123456").get())
								.get();
				
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
		
		for(String fieldName : fieldsToCheck){
			for(String prop : propsToCheck){
				String propertyName = fieldName + "." + prop;  
				
				resultActions = resultActions
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", notNullValue())) 
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", anyOf(is("size must be between 0 and 5"), is("length must not be greater than 4"))));			
			}	
		}
	}
	
	@Test
	public void testDataEntry_AddressField_Empty_InnerLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"address",
				"permanentAddress",
				"corpAddress"
		};
		
		String[] propsToCheck = {
				"zipCode",
				"unitNo",
				"street",
				"baranggay"
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
								.setAddress(AddressFieldBuilder.create()
										.setLandMark("").get())
								.setPermanentAddress(AddressFieldBuilder.create()
										.setLandMark("").get())
								.setCorpAddress(AddressFieldBuilder.create()
										.setLandMark("").get())
								.get();
				
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
		
		for(String fieldName : fieldsToCheck){
			for(String prop : propsToCheck){
				String propertyName = fieldName + "." + prop;  
				
				resultActions = resultActions
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", notNullValue())) 
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", is("required")));			
			}	
		}
	}
	
	@Test
	public void testDataEntry_AddressField_Size_InnerLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"address",
				"permanentAddress",
				"corpAddress"
		};
		
		String[] propsToCheck = {
				"zipCode",
				"unitNo",
				"street",
				"baranggay",
				"city",
				"landMark"
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
								.setAddress(AddressFieldBuilder.create()
										.setZipCode(1234567)
										.setUnitNo(STRING_07_CHARS)
										.setStreet(STRING_71_CHARS)
										.setBaranggay(STRING_71_CHARS)
										.setCity(STRING_71_CHARS)
										.setLandMark(STRING_71_CHARS).get())
								.setPermanentAddress(AddressFieldBuilder.create()
										.setZipCode(1234567)
										.setUnitNo(STRING_07_CHARS)
										.setStreet(STRING_71_CHARS)
										.setBaranggay(STRING_71_CHARS)
										.setCity(STRING_71_CHARS)
										.setLandMark(STRING_71_CHARS).get())
								.setCorpAddress(AddressFieldBuilder.create()
										.setZipCode(1234567)
										.setUnitNo(STRING_07_CHARS)
										.setStreet(STRING_71_CHARS)
										.setBaranggay(STRING_71_CHARS)
										.setCity(STRING_71_CHARS)
										.setLandMark(STRING_71_CHARS).get())
								.get();
				
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
		
		for(String fieldName : fieldsToCheck){
			for(String prop : propsToCheck){
				String propertyName = fieldName + "." + prop;  
				
				resultActions = resultActions
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", notNullValue())) 
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", anyOf(is("numeric value out of bounds (<6 digits>.<0 digits> expected)"), is("size must be between 0 and 6"), is("size must be between 0 and 70"))));			
			}	
		}
	}
		
	@Test
	public void testDataEntry_InstallmentDetail_Null_InnerLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"referenceData",
		};
		
		String[] propsToCheck = {
				"refName",
				"refAddress",
				"relationship"
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
								.setReferenceData(ReferenceDataBuilder.create()
													.setRefPhone(PhoneFieldBuilder.create()
																	.setLocal("").get())
													.get())
								.get();
				
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
		
		for(String fieldName : fieldsToCheck){
			for(String prop : propsToCheck){
				String propertyName = fieldName + "." + prop;  
				
				resultActions = resultActions
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", notNullValue())) 
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", is("required")));			
			}	
		}
	}
	
	@Test
	public void testDataEntry_ReferenceData_Null_InnerLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"referenceData",
		};
		
		String[] propsToCheck = {
				"refName",
				"refAddress",
				"relationship"
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
				.setReferenceData(ReferenceDataBuilder.create()
									.setRefPhone(PhoneFieldBuilder.create()
													.setLocal("").get())
									.get())
				.get();
				
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
		
		for(String fieldName : fieldsToCheck){
			for(String prop : propsToCheck){
				String propertyName = fieldName + "." + prop;  
				
				resultActions = resultActions
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", notNullValue())) 
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", is("required")));			
			}	
		}
	}
	
	@Test
	public void testDataEntry_ReferenceData_EmptyInnerFields_InnerLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"referenceData",
		};
		
		String[] propsToCheck = {
				"refName.firstName",
				"refName.surName",
				"refAddress.zipCode",
				"refAddress.unitNo",
				"refAddress.street",
				"refAddress.baranggay",
				"refPhone.region",
				"refPhone.phoneNo",
				"refMobile.region",
				"refMobile.phoneNo",
				"refCorpPhone.region",
				"refCorpPhone.phoneNo"
		};
		DataEntryDTO input = DataEntryDTOBuilder.create()
				.setReferenceData(ReferenceDataBuilder.create()
									.setRefName(NameFieldBuilder.create()
													.setMiddleName("")
													.get())
									.setRefAddress(AddressFieldBuilder.create()
													.setLandMark("")
													.get())
									.setRefPhone(PhoneFieldBuilder.create()
													.setLocal("")
													.get())
									.setRefMobile(PhoneFieldBuilder.create()
													.setLocal("")
													.get())
									.setRefCorpPhone(PhoneFieldBuilder.create()
													.setLocal("")
													.get())
									.get())
				.get();	
	
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
		
		for(String fieldName : fieldsToCheck){
			for(String prop : propsToCheck){
				String propertyName = fieldName + "." + prop;  
				
				resultActions = resultActions
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", notNullValue())) 
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", is("required")));			
			}	
		}
	}
	
	@Test
	public void testDataEntry_StoreInformation_Null_InnerLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"store",
		};
		
		String[] propsToCheck = {
				"storePhone.region",
				"storePhone.phoneNo"
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
				.setStore(StoreInformationBuilder.create()
									.setStorePhone(PhoneFieldBuilder.create()
													.setLocal("").get())
									.get())
				.get();
				
		ResultActions resultActions = mockMvc.perform(addParams(post("/dataentry"), input))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
		
		for(String fieldName : fieldsToCheck){
			for(String prop : propsToCheck){
				String propertyName = fieldName + "." + prop;  
				
				resultActions = resultActions
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", notNullValue())) 
						.andExpect(jsonPath("$.fieldErrors['" + propertyName + "']", is("required")));			
			}	
		}
	}
}
