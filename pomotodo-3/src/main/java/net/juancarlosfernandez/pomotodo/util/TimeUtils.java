/*
 *  Copyright 2011 www.juancarlosfernadez.net
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package net.juancarlosfernandez.pomotodo.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtils {

    private static final String TAG = "JkTimerUtils";

    public static final String RESET_DATE = "01-01-2000";

    public static final int MILISECONDS = 1000;

    public static final int MINUTES_MILISECONDS = 60 * 1000;

    public static final int MINUTES_SECONDS = 60;

    public static int milisecondsToSeconds(long miliseconds) {

        return (int) miliseconds / MILISECONDS;
    }

    public static int minutesToSeconds(int minutes) {

        return minutes * MINUTES_SECONDS;
    }

    public static long minutesToMiliseconds(int minutes) {

        long result = (long) minutes * MINUTES_MILISECONDS;

        return result;
    }

    public static String secondsToMinutesAndSeconds(int secondsUntilFinish) {

        int minutes = secondsToMinutes(secondsUntilFinish);
        int seconds = secondsUntilFinish - minutes * MINUTES_SECONDS;

        return normalizeTime(minutes) + " : " + normalizeTime(seconds);
    }

    private static String normalizeTime(int time) {

        if (time < 10)
            return "0" + time;
        else
            return "" + time;
    }

    private static int secondsToMinutes(int seconds) {

        return seconds / MINUTES_SECONDS;
    }

    public static String getWeek() {
        DateFormat dateFormat = new SimpleDateFormat("ww");
        Calendar cal = Calendar.getInstance();

        Log.d(TAG, "week " + dateFormat.format(cal.getTime()));

        return dateFormat.format(cal.getTime());
    }

    public static String getMonth() {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Calendar cal = Calendar.getInstance();

        Log.d(TAG, dateFormat.format(cal.getTime()));

        return dateFormat.format(cal.getTime());
    }

    public static String getDay() {
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Calendar cal = Calendar.getInstance();

        Log.d(TAG, dateFormat.format(cal.getTime()));

        return dateFormat.format(cal.getTime());
    }

    public static String getYear() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Calendar cal = Calendar.getInstance();

        Log.d(TAG, dateFormat.format(cal.getTime()));

        return dateFormat.format(cal.getTime());
    }

    public static String getDate() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();

        Log.d(TAG, dateFormat.format(cal.getTime()));

        return dateFormat.format(cal.getTime());
    }
}
