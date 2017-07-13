/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
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
    final static JsonObject APIREQUEST;
    static String firstKOtime = "";

    static {
        try {
            //URL url = new URL(API);
            URL url = new File("src/main/java/com/mycompany/app/newjson.json").toURI().toURL(); //TODO remove this
            JsonObject obj;

            try (InputStream is = url.openStream(); JsonReader rdr = Json.createReader(is)) {
                obj = rdr.readObject();
            }
            APIREQUEST = obj;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static List<String[]> matchesToday(String currentDate) throws MalformedURLException, IOException, ParseException {

        List<String[]> fixtures = new ArrayList<>();

        JsonArray results = APIREQUEST.getJsonArray("fixtures");
        results.getValuesAs(JsonObject.class).forEach((result) -> {
            String date = result.getString("date");
            if (firstKOtime.equals("")) {
                firstKOtime = date;
            }
            if (date.contains(currentDate)) { //change to currentDate
                fixtures.add(new String[]{result.getString("homeTeamName"), result.getString("awayTeamName"), "1.72", "4.5", "3.5"}); //homeOdds, awayOdds, drawOdds.
            }
        });

        return fixtures;
    }
}
