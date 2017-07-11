package com.mycompany.app;

import java.util.Calendar;
import java.util.Random;

public final class App {

    public static void main(String... args) throws InterruptedException {

        do { //one iteration of the do/while loop equates to one day

            Thread.sleep(timeUntil(1, 0, 0));

            Thread.sleep(timeUntil(0, firstOfDay()[0], firstOfDay()[1]));

            //function to return array of matches
            Random r = new Random();
            double T1 = r.nextGaussian() * 1.29 + 1.51;
            r = new Random();
            double T2 = r.nextGaussian() * 1.09 + 1.09;
            int home = (int) Math.round(T1);
            int away = (int) Math.round(T2);
            if (T1 < 0) {
                home = 0;
            }
            if (T2 < 0) {
                away = 0;
            }
            System.out.println(home + " - " + away);
        } while (true);
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

    public static int[] firstOfDay() {
        int hour = 15;
        int minute = 0;
        hour--;
        int[] time = {hour, minute};
        return time;
    }
}
