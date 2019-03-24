package com.recipe.api.service;

import com.recipe.api.exception.DataNotAvailableException;
import com.recipe.api.exception.JsonDataException;
import com.recipe.api.model.Recipe;

public interface RecipeService {

	/**
	 * Find lunch recipes based on provided ingredients. Expired ingredients
	 * shouldn't be used and ingredients with past best before dates should be
	 * sorted to the bottom
	 * 
	 * @return An array of available {@link Recipe}s
	 * @throws DataNotAvailableException
	 * @throws JsonDataException
	 */
	Recipe[] findLunchRecipes() throws DataNotAvailableException, JsonDataException;

}
