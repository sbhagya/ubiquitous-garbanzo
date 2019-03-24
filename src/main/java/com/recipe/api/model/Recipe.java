package com.recipe.api.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Recipe implements Comparable<Recipe> {

	private String title;

	/**
	 * Titles of the ingredients of the recipe
	 */
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
	 * Validate whether the given list of ingredients contains all required
	 * ingredients of the recipe and set the ingredient with oldest past best before
	 * date if exists
	 * 
	 * @param usableIngredients
	 * @return <b>true</b> if all required ingredients found in the list
	 */
	public boolean validateIngredientsAvailability(List<Ingredient> usableIngredients) {

		// Use parallelStream if required and machine is multi core
		List<Ingredient> recipeIngredients = usableIngredients.stream().filter(
				ingredient -> Arrays.stream(this.ingredients).anyMatch(title -> title.equals(ingredient.getTitle())))
				.collect(Collectors.toList());

		if (recipeIngredients.size() == this.ingredients.length) {
			List<Ingredient> pastBestBeforeIngredients = recipeIngredients.stream()
					.filter(ingredient -> ingredient.isPastBestBeforeDate()).sorted().collect(Collectors.toList());

			if (!pastBestBeforeIngredients.isEmpty()) {
				this.setOldestBestBeforeIngredient(pastBestBeforeIngredients.get(0));
			}
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(Recipe o) {
		return Comparator
				.comparing(Recipe::getOldestBestBeforeIngredient, Comparator.nullsFirst(Comparator.reverseOrder()))
				.compare(this, o);
	}

}
