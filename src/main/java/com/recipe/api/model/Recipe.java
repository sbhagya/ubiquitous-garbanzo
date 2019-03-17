package com.recipe.api.model;

public class Recipe {

	private String title;

	private String[] ingredients;

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

}
