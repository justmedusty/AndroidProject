package com.cst2335.androidproject;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiService {

    public static final String BaseSearchUrl = "https://api.edamam.com/api/recipes/v2?type=public&q=";
    public static final String appIdApiKey = "&app_id=77b3cee9&app_key=1f638fb97d020f33df4bec25ae109145";
    public static final String popularUrl = "https://api.edamam.com/api/recipes/v2?type=public&q=popular&app_id=77b3cee9&app_key=1f638fb97d020f33df4bec25ae109145&random=true";



    public String apiCall(@Nullable String searchTerm) throws MalformedURLException {

        URL url;

        if(searchTerm == null){
            url = new URL(popularUrl);
        }
        else url = new URL(BaseSearchUrl + searchTerm + appIdApiKey);

    }

}
