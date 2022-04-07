package com.cst2335.utilities;
/*
File: RecipeData.java
Author: Lucas Ross
Lab Section: 012
Date: April 7, 2022
 */
/**
 * This is a RecipeData object responsible for storing the relevant values for each recipe.
 * These objects will be stored in lists for use throughout the application.
 */
public class RecipeData {
    /**
     * The name/title of the recipe.
     */
    String title;
    /**
     * A comma-separated list of ingredients stored as a string.
     */
    String ingredients;
    /**
     * The url for the recipe's webpage.
     */
    String url;
    /**
     * A boolean value representing whether the recipe is currently stored in the database or not.
     */
    boolean isFavourited;

    /**
     * RecipeData constructor used when getting RecipeData objects from an api call,
     * automatically assigning the isFavourited value to false.
     *
     * @param title       the name/title of the recipe
     * @param ingredients the ingredients list (string format) of the recipe
     * @param url         the url for the recipe's web page
     */
    public RecipeData(String title,
                      String ingredients,
                      String url) {
        this.title = title;
        this.ingredients = ingredients;
        this.url = url;
        this.isFavourited = false;
    }

    /**
     * RecipeData constructor used when getting RecipeData objects from a database
     * query, with the database storing a true value and assigning it for the
     * isFavourited property.
     *
     * @param title        the title of the recipe
     * @param ingredients  the ingredients list (string format) of the recipe
     * @param url          the url for the recipe's web page
     * @param isFavourited the isFavourited boolean value, true if recipe is favourited(stored in database),
     *                     false otherwise.
     */
    public RecipeData(String title,
                      String ingredients,
                      String url, boolean isFavourited) {
        this.title = title;
        this.ingredients = ingredients;
        this.url = url;
        this.isFavourited = isFavourited;
    }

    /**
     * Accessor method for recipe name/title.
     *
     * @return the title of the recipe
     */
    public String getTitle() {
        return title;
    }

    /**
     * Accessor method for recipe ingredient list, returned as a string containing
     * comma-separated list of ingredients.
     *
     * @return the ingredients list (string format) of the recipe
     */
    public String getIngredients() {
        return ingredients;
    }

    /**
     * Accessor method for the recipe's website url.
     *
     * @return the url for the recipe's web page
     */
    public String getURL() {
        return url;
    }
}
