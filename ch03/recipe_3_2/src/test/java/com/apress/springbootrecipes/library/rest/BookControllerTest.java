package com.apress.springbootrecipes.library.rest;

import com.apress.springbootrecipes.library.Book;
import com.apress.springbootrecipes.library.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@Test
	public void shouldReturnListOfBooks() throws Exception {

		when(bookService.findAll()).thenReturn(Arrays.asList(
				new Book("123", "Spring 5 Recipes", "Marten Deinum", "Josh Long"),
				new Book("321", "Pro Spring MVC", "Marten Deinum", "Colin Yates")));

		mockMvc.perform(get("/books"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].isbn", containsInAnyOrder("123", "321")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].title", containsInAnyOrder("Spring 5 Recipes", "Pro Spring MVC")));
	}

	@Test
	public void shouldReturn404WhenBookNotFound() throws Exception {

		when(bookService.find(anyString())).thenReturn(Optional.empty());

		mockMvc.perform(get("/books/123"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void shouldReturnBookWhenFound() throws Exception {

		when(bookService.find(anyString())).thenReturn(
						Optional.of(new Book("123", "Spring 5 Recipes", "Marten Deinum", "Josh Long")));

		mockMvc.perform(get("/books/123"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isbn", equalTo("123")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", equalTo("Spring 5 Recipes")));
	}

}
