package com.recipe.api.service;

import com.recipe.api.exception.DataNotAvailableException;
import com.recipe.api.exception.JsonDataException;
import com.recipe.api.model.Recipe;

public interface RecipeService {

	Recipe[] findLunchRecipes() throws DataNotAvailableException, JsonDataException;
}
