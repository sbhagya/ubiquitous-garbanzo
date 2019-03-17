package com.recipe.api.dao;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import com.recipe.api.exception.DataNotAvailableException;
import com.recipe.api.exception.JsonDataException;
import com.recipe.api.model.Ingredient;
import com.recipe.api.util.JsonUtil;

@Repository
public class IngredientDao {

	@Value("classpath:ingredients.json")
	private Resource ingredientsResource;

	public Ingredient[] findAll() throws DataNotAvailableException, JsonDataException {
		try {
			return JsonUtil.jsonFile2Object(ingredientsResource.getInputStream(), Ingredient[].class);
		} catch (IOException e) {
			throw new DataNotAvailableException("Cannot read data from file", e);
		}
	}
}
