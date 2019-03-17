package com.recipe.api.model;

import java.util.Comparator;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient implements Comparable<Ingredient> {

	private String title;

	@JsonProperty("best-before")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date bestBefore;

	@JsonProperty("use-by")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date useBy;

	public Ingredient(String title, Date bestBefore, Date useBy) {
		this.title = title;
		this.bestBefore = bestBefore;
		this.useBy = useBy;
	}

	public Ingredient() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getBestBefore() {
		return bestBefore;
	}

	public void setBestBefore(Date bestBefore) {
		this.bestBefore = bestBefore;
	}

	public Date getUseBy() {
		return useBy;
	}

	public void setUseBy(Date useBy) {
		this.useBy = useBy;
	}

	public boolean isPastUseByDate() {
		return new Date().after(this.useBy);
	}

	public boolean isPastBestBeforeDate() {
		return new Date().after(this.bestBefore);
	}

	@Override
	public int compareTo(Ingredient o) {
		return Comparator.comparing(Ingredient::getBestBefore).compare(this, o);
	}

}
