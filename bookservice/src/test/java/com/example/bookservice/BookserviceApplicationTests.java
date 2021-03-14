package com.example.bookservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookserviceApplicationTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper mapper;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetBook() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/book")
		)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("Spring Boot 2 Recipes")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("Cloud-Native Applications in Java")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("Spring Boot in Action")));
	}

	@Test
	public void testGetBookById() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/book/1")
		)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("Spring Boot in Action")));
	}

	@Test
	public void testPostBook() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/book")
		)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(not(containsString("New Book"))));

		mvc.perform(
				MockMvcRequestBuilders.post("/book")
						.content(mapper.writeValueAsString(new Book(null, "New Book", "New Author", 10)))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
		)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("New Book")));

		mvc.perform(
				MockMvcRequestBuilders.get("/book")
		)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("New Book")));
	}

	@Test
	public void testPutBook() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/book/6")
		)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("Mastering Spring Boot 2.0")));

		mvc.perform(
				MockMvcRequestBuilders.put("/book/6")
						.content(mapper.writeValueAsString(new Book(6L, "Mastering Spring Boot 3.0", "Dinesh Rajput", 10)))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
		)
				.andExpect(status().is2xxSuccessful());

		mvc.perform(
				MockMvcRequestBuilders.get("/book/6")
		)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("Mastering Spring Boot 3.0")));
	}

	@Test
	public void testDeleteBook() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/book/8")
		)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("Cloud-Native Applications in Java")));

		mvc.perform(
				MockMvcRequestBuilders.delete("/book/8")
		)
				.andExpect(status().is2xxSuccessful());

		mvc.perform(
				MockMvcRequestBuilders.get("/book")
		)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(not(containsString("Cloud-Native Applications in Java"))));
	}
}
