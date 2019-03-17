package com.recipe.api.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Recipe implements Comparable<Recipe> {

	private String title;

	private String[] ingredients;

	@JsonIgnore
	private Ingredient oldestBestBeforeIngredient;

	public Recipe(String title, String[] ingredients) {
		this.title = title;
		this.ingredients = ingredients;
	}

	public Recipe() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(String[] ingredients) {
		this.ingredients = ingredients;
	}

	public Ingredient getOldestBestBeforeIngredient() {
		return oldestBestBeforeIngredient;
	}

	public void setOldestBestBeforeIngredient(Ingredient oldestBestBeforeIngredient) {
		this.oldestBestBeforeIngredient = oldestBestBeforeIngredient;
	}

	/**
	 * Validate whether given ingredients map contains all required ingredients for
	 * the recipe and set the ingredient with oldest past best before date if exists
	 * 
	 * @param availableIngredients
	 * @return boolean
	 */
	public boolean validateIngredientsAvailability(Map<String, Ingredient> availableIngredients) {
		List<Ingredient> pastBestBeforeIngredients = new ArrayList<Ingredient>();
		for (String ingredientTitle : this.ingredients) {
			Ingredient ingredient = availableIngredients.get(ingredientTitle);
			if (ingredient == null) {
				return false;
			}
			if (ingredient.isPastBestBeforeDate()) {
				pastBestBeforeIngredients.add(ingredient);
			}
		}
		if (!pastBestBeforeIngredients.isEmpty()) {
			Collections.sort(pastBestBeforeIngredients);
			this.setOldestBestBeforeIngredient(pastBestBeforeIngredients.get(0));
		}
		return true;
	}

	@Override
	public int compareTo(Recipe o) {
		if (o.getOldestBestBeforeIngredient() == null && this.getOldestBestBeforeIngredient() != null) {
			return 1;
		}
		if (o.getOldestBestBeforeIngredient() != null && this.getOldestBestBeforeIngredient() == null) {
			return -1;
		}
		return o.getOldestBestBeforeIngredient().compareTo(this.getOldestBestBeforeIngredient());
	}

}
