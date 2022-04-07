package com.cst2335.utilities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nullable;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
/*
File: ApiService.java
Author: Dustyn Gibb
Lab Section: 012
Date: April 7th 20222
 */



/**
 * The class for accessing the api and parsing the json data , loading the data into RecipeData Objects
 * and passing the new list to the relevant adapter.
 * @author Dustyn Gibb
 *
 */
public class ApiService {
    /**
     * The Adapter.
     */
    ListAdapter adapter;
    private static final String BaseSearchUrl = "https://api.edamam.com/api/recipes/v2?type=public&q=";
    private static final String appIdApiKey = "&app_id=77b3cee9&app_key=1f638fb97d020f33df4bec25ae109145";
    private static final String popularUrl = "https://api.edamam.com/api/recipes/v2?type=public&q=popular&app_id=77b3cee9&app_key=1f638fb97d020f33df4bec25ae109145&random=true";

    /**
     * The Recipe data list storing all of the recipes from this particular api call
     */
    public ArrayList<RecipeData> recipeData = new ArrayList<>();


    /**
     * Parses the json and loads it into the listview via the adapter passed in the API method
     *
     * @param json the json
     * @throws JSONException the json exception
     */
    public void loadIntoListView(String json) throws JSONException {
        //creating a json array from the json string


        //JSONArray jsonArray = new JSONArray(json);
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("hits");



        //creating a string array for listview


        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {
            //getting json object from json array
            JSONObject obj = jsonArray.getJSONObject(i);
            JSONObject recipe = obj.getJSONObject("recipe");

            //getting the name from the json object and putting it inside string array
            String title = recipe.getString("label");
            String url = recipe.getString("url");
            String ingredients = recipe.getString("ingredientLines");


            recipeData.add(new RecipeData(title, ingredients, url));


        }

    }

    /**
     * Api call, can take a search term or can pass null, in which case it makes a call for popular recipes instead
     *
     * @param searchTerm  the search term
     * @param adapter     the adapter for updating the listview
     * @param progressBar the progress bar
     */
    public void apiCall(@Nullable String searchTerm, ListAdapter adapter, ProgressBar progressBar) {
        this.adapter = adapter;
        @SuppressLint("StaticFieldLeak")
        class GetJSON extends AsyncTask<Void, Void, String> {
            /**
             * This method executes before the API call is made
             */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility((View.VISIBLE));
            }


            /**
             * This method executes once the API call is completed
             * @param s the json string that was grabbed in the API call in the doInBackground method
             */
            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.setRecipeList(recipeData);
                adapter.notifyDataSetChanged();
                if (progressBar != null){
                    progressBar.setVisibility(View.INVISIBLE);
                }



            }

            /**
             * For main asych task, in this case it is the call to the edamam API
             * @return returns the json string
             */
            @Override
            protected String doInBackground(Void... voids) {
                try {

                    //creating a URL
                    URL url;

                    if (searchTerm == null) {
                        url = new URL(popularUrl);
                    } else url = new URL(BaseSearchUrl + searchTerm + appIdApiKey);

                    //Opening the URL using HttpUrlConnection
                   HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();


                    //String builder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //Using buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

                    //A simple string to read values from each line
                    String json;

                    //reading until we dont find null
                    while ((json = bufferedReader.readLine()) != null) {
                        //appending it to a string builder
                        sb.append(json).append("\n");
                    }

                    //finally returning the read string

                    return sb.toString().trim();


                } catch (Exception e) {
                    e.printStackTrace();
                    return null;

                }
            }



        }
        //creating async task object and executing it
        recipeData.clear();
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
}

