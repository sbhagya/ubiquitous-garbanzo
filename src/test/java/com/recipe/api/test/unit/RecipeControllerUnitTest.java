package com.recipe.api.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.api.controller.RecipeController;
import com.recipe.api.model.Recipe;
import com.recipe.api.service.RecipeService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RecipeControllerUnitTest {

	private MockMvc mockMvc;

	@Autowired
	private RecipeController recipeController;

	@MockBean
	private RecipeService recipeService;

	@Before
	public void setup() {
		this.mockMvc = standaloneSetup(this.recipeController).build();
	}

	@Test
	public void findLunchRecipes_availableValidIngredients() throws Exception {
		// Prepare mock data
		Recipe salad = new Recipe("Salad", new String[] { "Lettuce", "Tomato", "Cucumber", "Beetroot" });
		Recipe hotdog = new Recipe("Hotdog", new String[] { "Hotdog Bun", "Sausage", "Ketchup", "Mustard" });
		Recipe[] availableRecipes = new Recipe[] { salad, hotdog };

		when(recipeService.findLunchRecipes()).thenReturn(availableRecipes);
		MvcResult result = this.mockMvc.perform(get("/lunch")).andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
		ObjectMapper objectMapper = new ObjectMapper();
		Recipe[] recievedRecipes = objectMapper.readValue(response, Recipe[].class);
		assertEquals(recievedRecipes[0].getTitle(), "Salad");
		assertEquals(recievedRecipes[1].getTitle(), "Hotdog");
	}

	@Test
	public void findlunchRecipes_noAvailableIngredients() throws Exception {
		when(recipeService.findLunchRecipes()).thenReturn(new Recipe[0]);

		MvcResult result = this.mockMvc.perform(get("/lunch")).andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
		ObjectMapper objectMapper = new ObjectMapper();
		Recipe[] recievedRecipes = objectMapper.readValue(response, Recipe[].class);
		assertEquals(recievedRecipes.length, 0);
	}
}
