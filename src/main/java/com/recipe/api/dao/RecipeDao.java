package com.recipe.api.dao;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import com.recipe.api.exception.DataNotAvailableException;
import com.recipe.api.exception.JsonDataException;
import com.recipe.api.model.Recipe;
import com.recipe.api.util.JsonUtil;

@Repository
public class RecipeDao {

	@Value("classpath:recipes.json")
	private Resource recipesResource;

	public Recipe[] findAll() throws DataNotAvailableException, JsonDataException {
		try {
			return JsonUtil.jsonFile2Object(recipesResource.getInputStream(), Recipe[].class);
		} catch (IOException e) {
			throw new DataNotAvailableException("Cannot read data from file", e);
		}
	}
}
