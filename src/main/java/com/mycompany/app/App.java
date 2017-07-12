package com.mycompany.app;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public final class App {

    public static void main(String... args) throws InterruptedException, ParseException, IOException {

        do { //one iteration of the do/while loop equates to one day

            System.out.println(JsonHandler.matchesToday());
            Thread.sleep(timeUntil(1, 0, 0));
            Thread.sleep(timeUntil(0, firstOfDay()[0], firstOfDay()[1]));
            
            for (String[] row : matchesToday()) {
                produceImage(findScore(row));
            }
            
            tweet();
            
            Thread.sleep(timeUntil(0, 23, 55));
            
            summary();
            
        } while (true);
    }
    
    public static void odds(){
        int[] odds = {581, 286, 182};
        int[] oddInterval = new int[odds.length];
        Random rand = new Random();
        int oddsCount = 0;

        for (int i = 0; i < odds.length; i++) {
            oddsCount += odds[i];
            oddInterval[i] = oddsCount;
        }

        int n = rand.nextInt(oddsCount);

        for (int i = 0; i < odds.length; i++) {
            if (n < oddInterval[i]) {
                System.out.println("Outcome " + ++i);
                break;
            }
        }
    }

    public static long timeUntil(int daysFromNow, int hourOfDay, int minute) {

        Calendar c = Calendar.getInstance();

        c.add(Calendar.DAY_OF_MONTH, daysFromNow);
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTimeInMillis() - System.currentTimeMillis();
    }

    public static int[] firstOfDay() throws ParseException, InterruptedException {
        //go through JSON finding first with same day_of_month, month & year as today
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss");
        String date = "2017-08-12T14:00:00Z";
        Date dateSubstring = dateFormat.parse(date.substring(0, date.length() - 1));
        int hour = 0;
        
        if(Calendar.getInstance().getTimeZone().useDaylightTime()){
            hour = dateSubstring.getHours();
        }
        else{
            hour = dateSubstring.getHours() - 1;
        }
        int minute = dateSubstring.getDate();
        int[] time = {hour, minute};
        return time;
    }

    public static List<String[]> matchesToday() {
        List<String[]> rowList = new ArrayList<String[]>();

        rowList.add(new String[]{"A", "B"});
        rowList.add(new String[]{"C", "D"});
        rowList.add(new String[]{"E", "F"});

        return rowList;
    }

    public static void summary() {
        //summary to date
    }

    public static String[] findScore(String[] teams) {
        String[] score = {"1-0"};
        return score;
    }

    public static void produceImage(String[] results) {

    }

    public static void tweet() {

    }
}
