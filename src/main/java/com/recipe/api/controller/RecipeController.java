package com.recipe.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.api.exception.DataNotAvailableException;
import com.recipe.api.exception.JsonDataException;
import com.recipe.api.model.Recipe;
import com.recipe.api.service.RecipeService;

@RestController
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;

	@GetMapping("/lunch")
	public Recipe[] findLunchRecipes() throws DataNotAvailableException, JsonDataException {
		return recipeService.findLunchRecipes();
	}
}
