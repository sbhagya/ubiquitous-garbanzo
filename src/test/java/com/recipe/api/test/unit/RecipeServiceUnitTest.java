package com.recipe.api.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import com.recipe.api.dao.IngredientDao;
import com.recipe.api.dao.RecipeDao;
import com.recipe.api.exception.DataNotAvailableException;
import com.recipe.api.exception.JsonDataException;
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
	public void setUp() throws JsonDataException, IOException {
		// Load sample recipes set
		recipes = JsonUtil.jsonFile2Object(testRecipes.getInputStream(), Recipe[].class);
	}

	@Test
	public void findLunchRecipes_expiredIngredients() throws DataNotAvailableException, JsonDataException {
		LocalDate pastUseByDate = LocalDate.now().minusDays(2);
		LocalDate pastBestBeforeDate = LocalDate.now().minusDays(5);
		// Create test data
		Ingredient lettuce = new Ingredient("Lettuce", pastBestBeforeDate, pastUseByDate);
		Ingredient tomato = new Ingredient("Tomato", pastBestBeforeDate, pastUseByDate);
		Ingredient cucumber = new Ingredient("Cucumber", pastBestBeforeDate, pastUseByDate);
		Ingredient beetroot = new Ingredient("Beetroot", pastBestBeforeDate, pastUseByDate);
		Ingredient cheese = new Ingredient("Cheese", pastBestBeforeDate, pastUseByDate);
		ingredients = new Ingredient[] { lettuce, tomato, cucumber, beetroot, cheese };

		when(recipeDao.findAll()).thenReturn(recipes);
		when(ingredientDao.findAll()).thenReturn(ingredients);

		Recipe[] lunchRecipes = recipeService.findLunchRecipes();
		assertNotNull(lunchRecipes);
		assertEquals(lunchRecipes.length, 0);
	}

	@Test
	public void findLunchRecipes_availableValidIngredientsAndOrderByBestBefore()
			throws JsonDataException, DataNotAvailableException, IOException {
		// Ingredients with future use by and best before dates
		Ingredient eggs = new Ingredient("Eggs", LocalDate.now().plusDays(2), LocalDate.now().plusDays(4));
		Ingredient mushroom = new Ingredient("Mushroom", LocalDate.now().plusDays(3), LocalDate.now().plusDays(5));
		Ingredient milk = new Ingredient("Milk", LocalDate.now().plusDays(4), LocalDate.now().plusDays(6));
		Ingredient salt = new Ingredient("Salt", LocalDate.now().plusDays(1), LocalDate.now().plusDays(5));
		Ingredient pepper = new Ingredient("Pepper", LocalDate.now().plusDays(2), LocalDate.now().plusDays(6));
		Ingredient spinach = new Ingredient("Spinach", LocalDate.now().plusDays(3), LocalDate.now().plusDays(7));
		// Ingredients with future use by but past best before dates
		Ingredient cucumber = new Ingredient("Cucumber", LocalDate.now().minusDays(3), LocalDate.now().plusDays(6));
		Ingredient beetroot = new Ingredient("Beetroot", LocalDate.now().minusDays(1), LocalDate.now().plusDays(4));
		Ingredient bacon = new Ingredient("Bacon", LocalDate.now().minusDays(7), LocalDate.now().plusDays(7));
		Ingredient bakedBeans = new Ingredient("Baked Beans", LocalDate.now().minusDays(8),
				LocalDate.now().plusDays(7));
		Ingredient mushrooms = new Ingredient("Mushrooms", LocalDate.now().minusDays(6), LocalDate.now().plusDays(7));
		Ingredient bread = new Ingredient("Bread", LocalDate.now().minusDays(3), LocalDate.now().plusDays(7));
		Ingredient sausage = new Ingredient("Sausage", LocalDate.now().minusDays(4), LocalDate.now().plusDays(3));
		// Ingredients with today use by but past best before dates
		Ingredient lettuce = new Ingredient("Lettuce", LocalDate.now().minusDays(2), LocalDate.now());
		Ingredient tomato = new Ingredient("Tomato", LocalDate.now().minusDays(4), LocalDate.now());
		// Ingredients with past use by and best before dates
		Ingredient hotdogBun = new Ingredient("Hotdog Bun", LocalDate.now().minusDays(4), LocalDate.now().minusDays(2));
		Ingredient ketchup = new Ingredient("Ketchup", LocalDate.now().minusDays(4), LocalDate.now().minusDays(2));
		Ingredient mustard = new Ingredient("Mustard", LocalDate.now().minusDays(4), LocalDate.now().minusDays(4));
		Ingredient ham = new Ingredient("Ham", LocalDate.now().minusDays(5), LocalDate.now().minusDays(2));
		Ingredient cheese = new Ingredient("Cheese", LocalDate.now().minusDays(6), LocalDate.now().minusDays(3));

		ingredients = new Ingredient[] { eggs, mushroom, milk, salt, pepper, spinach, lettuce, tomato, cucumber,
				beetroot, bacon, bakedBeans, mushrooms, bread, sausage, hotdogBun, ketchup, mustard, ham, cheese };

		when(recipeDao.findAll()).thenReturn(recipes);
		when(ingredientDao.findAll()).thenReturn(ingredients);

		Recipe[] lunchRecipes = recipeService.findLunchRecipes();
		assertNotNull(lunchRecipes);
		assertEquals(lunchRecipes.length, 3);
		assertEquals(lunchRecipes[0].getTitle(), "Omelette");
		assertEquals(lunchRecipes[1].getTitle(), "Salad");
		assertEquals(lunchRecipes[2].getTitle(), "Fry-up");
	}
}
