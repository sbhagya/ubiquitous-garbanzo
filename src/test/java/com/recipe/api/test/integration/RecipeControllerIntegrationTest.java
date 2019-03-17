package com.recipe.api.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.api.controller.RecipeController;
import com.recipe.api.model.Recipe;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RecipeControllerIntegrationTest {

	private MockMvc mockMvc;

	@Autowired
	private RecipeController recipeController;
	
	@Before
	public void setup() {
		this.mockMvc = standaloneSetup(this.recipeController).build();
	}
	
	@Test
	public void findlunchRecipes() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/lunch")).andReturn();
		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
		ObjectMapper objectMapper = new ObjectMapper();
		Recipe[] recievedRecipes = objectMapper.readValue(response, Recipe[].class);
		assertEquals(recievedRecipes.length, 3);
		assertEquals(recievedRecipes[0].getTitle(), "Ham and Cheese Toastie");
		assertEquals(recievedRecipes[1].getTitle(), "Salad");
		assertEquals(recievedRecipes[2].getTitle(), "Hotdog");
	}
}
