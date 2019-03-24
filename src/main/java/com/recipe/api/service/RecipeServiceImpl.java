package com.recipe.api.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.api.dao.IngredientDao;
import com.recipe.api.dao.RecipeDao;
import com.recipe.api.exception.DataNotAvailableException;
import com.recipe.api.exception.JsonDataException;
import com.recipe.api.model.Ingredient;
import com.recipe.api.model.Recipe;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeDao recipeDao;

	@Autowired
	private IngredientDao ingredientDao;

	interface ValidateReceipes {
		void validate(Recipe recipe, List<Recipe> suitableRecipes);
	}

	@Override
	public Recipe[] findLunchRecipes() throws DataNotAvailableException, JsonDataException {
		Ingredient[] ingredients = ingredientDao.findAll();
		if (ingredients.length == 0) {
			return new Recipe[0];
		}
		// Filter expired ingredients
		List<Ingredient> usableIngredients = Arrays.stream(ingredients)
				.filter(ingredient -> !ingredient.isPastUseByDate()).collect(Collectors.toList());
		if (usableIngredients.isEmpty()) {
			return new Recipe[0];
		}

		Recipe[] recipes = recipeDao.findAll();
		// Find matching recipes for available ingredient
		return Arrays.stream(recipes).filter(recipe -> recipe.validateIngredientsAvailability(usableIngredients))
				.sorted().collect(Collectors.toList()).toArray(new Recipe[0]);
	}

}
