/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author jamie
 */
public class JsonHandler {

    final static String API = "http://api.football-data.org/v1/competitions/445/fixtures";

    public static List<List<String>> matchesToday() throws MalformedURLException, IOException, ParseException {

        URL url = new URL(API);
        Calendar today = Calendar.getInstance();
        //String currentDate = today.get(Calendar.YEAR) + "-" + today.get(Calendar.MONTH) + "-" + today.get(Calendar.DAY_OF_WEEK);
        String currentDate = "2018-01-01";
        List<List<String>> matchesToday = new ArrayList<>();
        List<String> teams = new ArrayList<>();

        try (InputStream is = url.openStream(); JsonReader rdr = Json.createReader(is)) {

            JsonObject obj = rdr.readObject();
            JsonArray results = obj.getJsonArray("fixtures");
            results.getValuesAs(JsonObject.class).forEach((result) -> {
                String date = result.getString("date");
                if (date.contains(currentDate)) {
                    teams.add(result.getString("homeTeamName"));
                    teams.add(result.getString("awayTeamName"));    
                    matchesToday.add(teams);
                }
            });
        }
        return matchesToday;
    }
}
