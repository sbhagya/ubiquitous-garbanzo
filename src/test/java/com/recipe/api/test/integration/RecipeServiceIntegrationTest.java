package com.recipe.api.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.recipe.api.exception.DataNotAvailableException;
import com.recipe.api.exception.JsonDataException;
import com.recipe.api.model.Recipe;
import com.recipe.api.service.RecipeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIntegrationTest {

	@Autowired
	private RecipeService recipeService;

	@Test
	public void testFindRecipes() throws DataNotAvailableException, JsonDataException {
		Recipe[] recievedRecipes = recipeService.findLunchRecipes();
		assertNotNull(recievedRecipes);
		assertEquals(recievedRecipes.length, 3);
		assertEquals(recievedRecipes[0].getTitle(), "Ham and Cheese Toastie");
		assertEquals(recievedRecipes[1].getTitle(), "Salad");
		assertEquals(recievedRecipes[2].getTitle(), "Hotdog");
	}

}
