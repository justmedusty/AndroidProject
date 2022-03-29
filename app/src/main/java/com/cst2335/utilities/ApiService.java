package com.cst2335.utilities;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nullable;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiService {

    public static final String BaseSearchUrl = "https://api.edamam.com/api/recipes/v2?type=public&q=";
    public static final String appIdApiKey = "&app_id=77b3cee9&app_key=1f638fb97d020f33df4bec25ae109145";
    public static final String popularUrl = "https://api.edamam.com/api/recipes/v2?type=public&q=popular&app_id=77b3cee9&app_key=1f638fb97d020f33df4bec25ae109145&random=true";


    public String apiCall(@Nullable String searchTerm) throws IOException {

        URL url;

        if (searchTerm == null) {
            url = new URL(popularUrl);
        } else url = new URL(BaseSearchUrl + searchTerm + appIdApiKey);


        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();


        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String json;

        while ((json = bufferedReader.readLine()) != null) {
            //appending it to a string builder
            sb.append(json).append("\n");
        }

        return sb.toString().trim();


    }

    public void loadIntoListView(String json) throws JSONException {
        //creating a json array from the json string
        JSONArray jsonArray = new JSONArray(json);

        //creating a string array for listview
        String[] data = new String[jsonArray.length()];

        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {
            //getting json object from json array
            JSONObject obj = jsonArray.getJSONObject(i);

            //getting the name from the json object and putting it inside string array
            data[i] = obj.getString("hits.recipe.images.THUMBNAIL.url");
            data[i] = obj.getString("");
            data[i] = obj.getString("");
            data[i] = obj.getString("");

        }


    }
}

