package com.cst2335.utilities;

/**
 * The type Recipe data.
 */
public class RecipeData {
    /**
     * The Title.
     */
    String title;
    /**
     * The Ingredients.
     */
    String ingredients;
    /**
     * The Url.
     */
    String url;
    /**
     * The Is favourited.
     */
    boolean isFavourited;

    /**
     * Instantiates a new Recipe data.
     *
     * @param title       the title
     * @param ingredients the ingredients
     * @param url         the url
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
     * Instantiates a new Recipe data.
     *
     * @param title        the title
     * @param ingredients  the ingredients
     * @param url          the url
     * @param isFavourited the is favourited
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
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets ingredients.
     *
     * @return the ingredients
     */
    public String getIngredients() {
        return ingredients;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getURL() {
        return url;
    }
}
