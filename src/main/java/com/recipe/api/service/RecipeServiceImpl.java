package com.recipe.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.api.dao.IngredientDao;
import com.recipe.api.dao.RecipeDao;
import com.recipe.api.model.Ingredient;
import com.recipe.api.model.Recipe;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeDao recipeDao;

	@Autowired
	private IngredientDao ingredientDao;

	@Override
	public Recipe[] findLunchRecipes() {
		// Find usable ingredients which have future use by date
		Map<String, Ingredient> usableIngredients = findUsableIngredients(ingredientDao.findAll());
		if (usableIngredients.isEmpty()) {
			return new Recipe[0];
		}
		// Find all recipes
		Recipe[] recipes = recipeDao.findAll();
		List<Recipe> suitableRecipes = new ArrayList<Recipe>();
		for (Recipe recipe : recipes) {
			// Check whether usable ingredients have all required ingredients for the recipe
			// and update the ingredient with oldest best before date
			if (recipe.validateIngredientsAvailability(usableIngredients)) {
				suitableRecipes.add(recipe);
			}
		}

		Collections.sort(suitableRecipes);
		return suitableRecipes.toArray(new Recipe[suitableRecipes.size()]);
	}

	private Map<String, Ingredient> findUsableIngredients(Ingredient[] ingredients) {
		Map<String, Ingredient> usableIngredients = new HashMap<String, Ingredient>();
		// Filter out ingredients with past use by date
		for (Ingredient ingredient : ingredients) {
			if (!ingredient.isPastUseByDate()) {
				usableIngredients.put(ingredient.getTitle(), ingredient);
			}
		}
		return usableIngredients;
	}

}
