package com.acss.core.dataentry;

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
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.View;

import com.acss.core.config.ApplicationConfig;
import com.acss.core.config.SecurityConfig;
import com.acss.core.config.WebAppInitializer;
import com.acss.core.config.WebMvcConfig;
import com.acss.core.model.builder.DataEntryDTOBuilder;
import com.acss.core.model.builder.NameFieldBuilder;
import com.acss.core.model.dataentry.DataEntryDTO;
import com.acss.kaizen.jooq.poc.configuration.PersistenceContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationConfig.class,
		SecurityConfig.class, 
		WebMvcConfig.class, 
		PersistenceContext.class }, loader = AnnotationConfigWebContextLoader.class)
public class OsaDataEntryControllerTest {

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
		
		MvcResult mvcResult = mockMvc
				.perform(
						post("/dataentry") //.param("applicantName.firstName", "test")
						).andReturn();
		
		
		assertThat(mvcResult.getResolvedException(), is(instanceOf(BindException.class)));

		BindingResult bindingResult = ((BindException) mvcResult.getResolvedException()).getBindingResult();

		for(String propertyName : fieldsToCheck){
			FieldError fieldError = bindingResult.getFieldError(propertyName); 
			assertThat("[" + propertyName + "] expected to have an error binding but has not.", fieldError, notNullValue());
			assertThat("[" + propertyName + "] message is not as expected.", fieldError.getDefaultMessage(), is("may not be null"));	
		}
	}
	
	@Test
	public void testDataEntry_Empty_TopLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"companyName",
				"sourceOfIncome",
				"occupation"
		};
		
		MvcResult mvcResult = mockMvc
				.perform(
						post("/dataentry") //.param("applicantName.firstName", "test")
						).andReturn();
		
		
		assertThat(mvcResult.getResolvedException(), is(instanceOf(BindException.class)));

		BindingResult bindingResult = ((BindException) mvcResult.getResolvedException()).getBindingResult();

		for(String propertyName : fieldsToCheck){
			FieldError fieldError = bindingResult.getFieldError(propertyName); 
			assertThat("[" + propertyName + "] expected to have an error binding but has not.", fieldError, notNullValue());
			assertThat("[" + propertyName + "] message is not as expected.", fieldError.getDefaultMessage(), is("may not be empty"));	
		}
	}
		
	@Test
	public void testDataEntry_NameField_InnerLevelFields() throws Exception {
		String[] fieldsToCheck = {
				"applicantName",
		};
		
		DataEntryDTO input = DataEntryDTOBuilder.create()
								.setApplicantName(NameFieldBuilder.create().get())
								.get();
				
		MvcResult mvcResult = mockMvc
				.perform(post("/dataentry")
						.contentType(APPLICATION_JSON_UTF8)
						.content(mapper.writeValueAsBytes(input)))
				.andReturn();
		
		
		assertThat(mvcResult.getResolvedException(), is(instanceOf(BindException.class)));

		BindingResult bindingResult = ((BindException) mvcResult.getResolvedException()).getBindingResult();

		for(String propertyName : fieldsToCheck){
			FieldError fieldError = bindingResult.getFieldError(propertyName); 
			assertThat("[" + propertyName + "] expected to have an error binding but has not.", fieldError, notNullValue());
			assertThat("[" + propertyName + "] message is not as expected.", fieldError.getDefaultMessage(), is("may not be empty"));	
		}
	}
}
