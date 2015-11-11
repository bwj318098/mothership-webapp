package com.acss.core.dataentry;

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
import com.acss.core.model.dataentry.DataEntryDTO;
import com.acss.kaizen.jooq.poc.configuration.PersistenceContext;

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

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(osaDataEntryController)
				.setSingleView(mockView).build();
	}

	@Test
	public void testDataEntry_EmptyTopLevelFields() {
		try {

			MvcResult mvcResult = mockMvc
					.perform(
							post("/dataentry") //.param("applicantName.firstName", "test")
							).andReturn();
			
			
			assertThat(mvcResult.getResolvedException(), is(instanceOf(BindException.class)));

			BindingResult bindingResult = ((BindException) mvcResult.getResolvedException()).getBindingResult();

			assertThat(bindingResult.getFieldError("applicantName"), nullValue());

			for (FieldError error : bindingResult.getFieldErrors()) {
				System.out.println(error.getField());
			}

			assertThat(bindingResult.getFieldError("applicantName.lastName"), notNullValue());
			assertThat(bindingResult.getFieldError("applicantName.lastName").getDefaultMessage(), is("may not be null"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
