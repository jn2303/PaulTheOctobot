package com.mycompany.app;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jamie
 */
public final class App {

    public static void main(String... args) throws InterruptedException, ParseException, IOException {

        do { //one iteration of the do/while loop equates to one day

            //List<String[]> matchesToday = JsonHandler.matchesToday(Calendar.getInstance().get(Calendar.YEAR) + "-" + Calendar.getInstance().get(Calendar.MONTH) + "-" + Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
            List<String[]> matchesToday = JsonHandler.matchesToday("2017-08-12");

            System.out.println(timeUntil(0, firstGameKO_GMT(JsonHandler.firstKOtime)[0], firstGameKO_GMT(JsonHandler.firstKOtime)[1]));

            for (int i = 0; i < matchesToday.size(); i++) {
                System.out.print("Match " + Integer.toString(i + 1) + " is " + matchesToday.get(i)[0] + " vs " + matchesToday.get(i)[1] + ". ");

                double[] odds = {Double.parseDouble(matchesToday.get(i)[2]), Double.parseDouble(matchesToday.get(i)[3]), Double.parseDouble(matchesToday.get(i)[4])};

                int prediction = odds(odds);
                if (prediction == 2) {
                    System.out.print("Prediction: Draw.\n\n");
                } else {
                    System.out.print("Prediction: " + matchesToday.get(i)[prediction] + " win.\n\n");
                }
            }

            Thread.sleep(timeUntil(1, 0, 0));
            Thread.sleep(timeUntil(0, firstGameKO_GMT(JsonHandler.firstKOtime)[0], firstGameKO_GMT(JsonHandler.firstKOtime)[1]));

            tweet();

            Thread.sleep(timeUntil(0, 23, 55));

            summary();

        } while (true);
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
        int hour = 0;

        if (Calendar.getInstance().getTimeZone().useDaylightTime()) {
            hour = Integer.parseInt(dateStamp.substring(11, 13));
        } else {
            hour = Integer.parseInt(dateStamp.substring(11, 13)) - 1;
        }
        int minute = Integer.parseInt(dateStamp.substring(14, 16));
        int[] time = {hour, minute};
        return time;
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
