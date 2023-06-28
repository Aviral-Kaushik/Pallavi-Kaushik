package com.pallavikaushik.Utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CheckTime {

    private static final String TAG = "CheckTime";

    public static boolean isTimeGreaterThanSevenPM() {

        DateFormat dateFormat = new SimpleDateFormat("kk");
        Calendar calendar = Calendar.getInstance();

        String currentDateAndTime = dateFormat.format(calendar.getTime());
        Log.d(TAG, "isTimeGreaterThanSevenPM: Current time is: " + currentDateAndTime);

        if (Integer.parseInt(currentDateAndTime) >= 19) {
            Log.d(TAG, "isTimeGreaterThanSevenPM: Time is or greater then 7:00 PM");
            return true;
        } else {
            Log.d(TAG, "isTimeGreaterThanSevenPM: Time is lesser then 7:00 PM");
            return false;
        }
    }

    public static boolean isTimeGreaterThanNineAM() {
        DateFormat dateFormat = new SimpleDateFormat("kk");
        Calendar calendar = Calendar.getInstance();

        String currentDateAndTime = dateFormat.format(calendar.getTime());
        Log.d(TAG, "isTimeGreaterThanSevenPM: Current time is: " + currentDateAndTime);

        if (Integer.parseInt(currentDateAndTime) >= 9) {
            Log.d(TAG, "isTimeGreaterThanSevenPM: Time is or greater then 9:00 AM");
            return true;
        } else {
            Log.d(TAG, "isTimeGreaterThanSevenPM: Time is lesser then 9:00 AM");
            return false;
        }
    }
}
