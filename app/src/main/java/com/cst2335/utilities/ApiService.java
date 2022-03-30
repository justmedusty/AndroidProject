package com.cst2335.utilities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import javax.annotation.Nullable;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ApiService {

    public static final String BaseSearchUrl = "https://api.edamam.com/api/recipes/v2?type=public&q=";
    public static final String appIdApiKey = "&app_id=77b3cee9&app_key=1f638fb97d020f33df4bec25ae109145";
    public static final String popularUrl = "https://api.edamam.com/api/recipes/v2?type=public&q=popular&app_id=77b3cee9&app_key=1f638fb97d020f33df4bec25ae109145&random=true";

    public ArrayList<RecipeData> recipeData = new ArrayList();


    public void loadIntoListView(String json) throws JSONException {
        //creating a json array from the json string


        //JSONArray jsonArray = new JSONArray(json);
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("hits");
        System.out.println(jsonArray);



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
            System.out.println(ingredients);


            recipeData.set(i, new RecipeData(title, ingredients, url));


        }

    }

    public void apiCall(@Nullable String searchTerm) {

        class GetJSON extends AsyncTask<Void, Void, String> {
            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

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
        GetJSON getJSON = new GetJSON();
        getJSON.execute();


    }
}

