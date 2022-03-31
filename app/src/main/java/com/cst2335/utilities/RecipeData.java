package com.cst2335.utilities;

public class RecipeData {
    String title;
    String ingredients;
    String url;
    boolean isFavourited;

    public RecipeData(String title,
                      String ingredients,
                      String url) {
        this.title = title;
        this.ingredients = ingredients;
        this.url = url;
        this.isFavourited = false;
    }

    public RecipeData(String title,
                      String ingredients,
                      String url, boolean isFavourited) {
        this.title = title;
        this.ingredients = ingredients;
        this.url = url;
        this.isFavourited = isFavourited;
    }

    public String getTitle() {
        return title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getURL() {
        return url;
    }
}
