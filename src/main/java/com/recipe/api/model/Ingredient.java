package com.recipe.api.model;

import java.time.LocalDate;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.recipe.api.util.LocalDateSerializer;

public class Ingredient implements Comparable<Ingredient> {

	private String title;

	@JsonProperty("best-before")
	@JsonSerialize(using=LocalDateSerializer.class)
	@JsonDeserialize(using=LocalDateDeserializer.class)
	private LocalDate bestBefore;

	@JsonProperty("use-by")
	@JsonSerialize(using=LocalDateSerializer.class)
	@JsonDeserialize(using=LocalDateDeserializer.class)
	private LocalDate useBy;

	public Ingredient(String title, LocalDate bestBefore, LocalDate useBy) {
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

	public LocalDate getBestBefore() {
		return bestBefore;
	}

	public void setBestBefore(LocalDate bestBefore) {
		this.bestBefore = bestBefore;
	}

	public LocalDate getUseBy() {
		return useBy;
	}

	public void setUseBy(LocalDate useBy) {
		this.useBy = useBy;
	}

	public boolean isPastUseByDate() {
		return this.useBy.isBefore(LocalDate.now());
	}

	public boolean isPastBestBeforeDate() {
		return this.bestBefore.isBefore(LocalDate.now());
	}

	@Override
	public int compareTo(Ingredient o) {
		return Comparator.comparing(Ingredient::getBestBefore).compare(this, o);
	}

}
