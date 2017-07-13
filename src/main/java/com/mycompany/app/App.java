package com.mycompany.app;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jamie
 */
public final class App {

    public static void main(String... args) throws InterruptedException, ParseException, IOException {
        
        //do { //one iteration of the do/while loop equates to one day
            JsonHandler jsonHandlerMorning = new JsonHandler();
            List<String[]> matchesToday = jsonHandlerMorning.matchesToday(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
            //List<String[]> matchesToday = JsonHandler.matchesToday("2017-07-13");

            //Thread.sleep(timeUntil(1, 6, 0)); //TODO enable this
            System.out.println(timeUntil(0, firstGameKO_GMT(JsonHandler.firstKOtime)[0], firstGameKO_GMT(JsonHandler.firstKOtime)[1]));
            Thread.sleep(timeUntil(0, firstGameKO_GMT(JsonHandler.firstKOtime)[0], firstGameKO_GMT(JsonHandler.firstKOtime)[1]));
            
            int[] prediction = new int[matchesToday.size()];

            for (int i = 0; i < matchesToday.size(); i++) {
                System.out.print("Match " + Integer.toString(i + 1) + " is " + matchesToday.get(i)[0] + " vs " + matchesToday.get(i)[1] + ". ");

                double[] odds = {Double.parseDouble(matchesToday.get(i)[2]), Double.parseDouble(matchesToday.get(i)[3]), Double.parseDouble(matchesToday.get(i)[4])};

                prediction[i] = odds(odds);
                if (prediction[i] == 2) {
                    System.out.println("Prediction: Draw.");
                } else {
                    System.out.println("Prediction: " + matchesToday.get(i)[prediction[i]] + " win.");
                }
            }

            //Thread.sleep(timeUntil(0, 23, 00));
            JsonHandler jsonHandler = new JsonHandler();
                        
            List<int[]> scoresToday = jsonHandler.scoresToday(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
            for (int i = 0; i < scoresToday.size(); i++) {
                System.out.print("Match " + Integer.toString(i + 1) + " score was " + scoresToday.get(i)[0] + " - " +scoresToday.get(i)[1] + ". ");
                System.out.print("Prediction was " + Boolean.toString(wasPredictionCorrect(prediction[i], scoresToday.get(i))) + ".\n");
            }

            summary();

        //} while (true);
    }

    public static int odds(double[] oddsDouble) {

        int[] odds = new int[oddsDouble.length];

        for (int i = 0; i < odds.length; i++) {
            odds[i] = (int) Math.round((1 / oddsDouble[i]) * 1000);
        }

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
                return i;
            }
        }
        return 3;
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

    public static int[] firstGameKO_GMT(String dateStamp) throws ParseException, InterruptedException {
        int hour;

        if (Calendar.getInstance().getTime().toString().contains("BST")) {
            hour = Integer.parseInt(dateStamp.substring(11, 13)) + 1;
        } else {
            hour = Integer.parseInt(dateStamp.substring(11, 13));
        }
        int minute = Integer.parseInt(dateStamp.substring(14, 16));
        int[] time = {hour, minute};
        return time;
    }
    
    private static boolean wasPredictionCorrect(int prediction, int[] score){
        switch (prediction) {
            case 0:
                if(score[0] > score[1]){
                    return true;
                }   break;
            case 1:
                if(score[0] < score[1]){
                    return true;
                }   break;
            case 2:
                if(score[0] == score[1]){
                    return true;
                }   break;
            default:
                break;
        }
        return false;
    }

    public static void summary() {
        //summary to date
    }

    public static void produceImage(String[] results) {

    }

    public static void tweet() {

    }
}
