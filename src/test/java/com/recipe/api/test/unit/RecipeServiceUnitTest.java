package com.recipe.api.test.unit;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.recipe.api.dao.IngredientDao;
import com.recipe.api.dao.RecipeDao;
import com.recipe.api.model.Ingredient;
import com.recipe.api.model.Recipe;
import com.recipe.api.service.RecipeService;
import com.recipe.api.util.JsonUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceUnitTest {

	@Autowired
	private RecipeService recipeService;

	@MockBean
	private RecipeDao recipeDao;

	@MockBean
	private IngredientDao ingredientDao;

	@Value("classpath:testIngredients.json")
	private Resource testIngredientsResource;

	@Value("classpath:testRecipes.json")
	private Resource testRecipes;

	private Ingredient[] ingredients;

	private Recipe[] recipes;
	
	@Before
	public void setUp() throws JsonParseException, JsonMappingException, IOException {
		// Load sample recipes set
		recipes = JsonUtil.jsonFile2Object(testRecipes.getInputStream(), Recipe[].class);
	}
	
	@Test
	public void findLunchRecipes_expiredIngredients() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -2);
		Date pastUseByDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, -4);
		Date pastBestBeforeDate = calendar.getTime();
		// Create test data
		Ingredient lettuce = new Ingredient("Lettuce", pastUseByDate, pastBestBeforeDate);
		Ingredient tomato = new Ingredient("Tomato", pastUseByDate, pastBestBeforeDate);
		Ingredient cucumber = new Ingredient("Cucumber", pastUseByDate, pastBestBeforeDate);
		Ingredient beetroot = new Ingredient("Beetroot", pastUseByDate, pastBestBeforeDate);
		ingredients = new Ingredient[] {lettuce, tomato, cucumber, beetroot};
		
		when(recipeDao.findAll()).thenReturn(recipes);
		when(ingredientDao.findAll()).thenReturn(ingredients);
		
		Recipe[] lunchRecipes = recipeService.findLunchRecipes();
		assertNotNull(lunchRecipes);
		assertEquals(lunchRecipes.length, 0);
	}
	

	@Test
	public void findLunchRecipes_availableValidIngredientsAndOrderByBestBefore()
			throws JsonParseException, JsonMappingException, IOException {
		// TODO Create dynamic data set to avoid sample test data files
		// Load sample test ingredient set
		ingredients = JsonUtil.jsonFile2Object(testIngredientsResource.getInputStream(), Ingredient[].class);
		
		when(recipeDao.findAll()).thenReturn(recipes);
		when(ingredientDao.findAll()).thenReturn(ingredients);
		
		Recipe[] lunchRecipes = recipeService.findLunchRecipes();
		assertNotNull(lunchRecipes);
		assertEquals(lunchRecipes.length, 3);
		assertEquals(lunchRecipes[0].getTitle(), "Ham and Cheese Toastie");
		assertEquals(lunchRecipes[1].getTitle(), "Salad");
		assertEquals(lunchRecipes[2].getTitle(), "Hotdog");
	}
}
