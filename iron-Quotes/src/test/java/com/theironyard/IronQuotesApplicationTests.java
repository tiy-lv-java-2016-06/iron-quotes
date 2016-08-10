package com.theironyard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.entities.Quote;
import com.theironyard.services.QuoteRepository;
import com.theironyard.services.TagRepository;
import com.theironyard.services.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IronQuotesApplication.class)
@WebAppConfiguration
public class IronQuotesApplicationTests {

	@Autowired
	WebApplicationContext wap;

	@Autowired
	UserRepository userRepository;

	@Autowired
	QuoteRepository quoteRepository;

	@Autowired
	TagRepository tagRepository;

	MockMvc mockMvc;

	@Before
	public void before(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
	}

	@Test
	public void createQuote() throws JsonProcessingException {
		Quote quote = new Quote();
		quote.setText("Hello Test");
		quote.setAuthor("Tester");

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(quote);

		mockMvc.perform(
				MockMvcRequestBuilders.post())
	}

}
