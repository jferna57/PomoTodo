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

package net.juancarlosfernandez.pomotodo.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;
import net.juancarlosfernandez.pomotodo.R;
import net.juancarlosfernandez.pomotodo.exception.SettingsConfigurationException;

import java.util.regex.Pattern;

/**
 * The Class Preferences.
 */
public class Preferences extends PreferenceActivity implements OnSharedPreferenceChangeListener {

    /**
     * The Constant PREF_POMODORO_DURATION.
     */
    private static final String PREF_POMODORO_DURATION = "prefPomodoroDuration";

    /**
     * The Constant PREF_REST_DURATION.
     */
    private static final String PREF_REST_DURATION = "prefRestDuration";

    /**
     * The Constant PREF_LONG_REST_DURATION.
     */
    private static final String PREF_LONG_REST_DURATION = "prefLongRestDuration";

    /**
     * The Constant PREF_LONG_REST_DURATION.
     */
    private static final String PREF_NUM_POMODOROS_UNTIL_LONG_REST = "prefNumPomodoroUntilLongRest";

    /**
     * The Constant PREF_VIBRATE.
     */
    public static final String PREF_VIBRATE = "prefVibrate";

    /**
     * The Constant PREF_RING.
     */
    public static final String PREF_RING = "prefRing";

    /**
     * The Constant PREF_TICTAC.
     */
    public static final String PREF_TICTAC = "prefTicTac";

    /**
     * The Constant PREF_TOODLEDO_EMAIL.
     */
    public static final String PREF_TOODLEDO_EMAIL = "prefToodledoEmail";

    /**
     * The Constant PREF_TOODLEDO_PASSWORD.
     */
    public static final String PREF_TOODLEDO_PASSWORD = "prefToodledoPassword";

    /*
      * (non-Javadoc)
      *
      * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
      */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        sp.registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * Check if it is necesary ring on finish time
     *
     * @param context the context
     * @return true if music
     */
    public static boolean isRing(Context context) {

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPrefs.getBoolean(PREF_RING, false);
    }

    public static boolean isTicTac(Context context) {

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPrefs.getBoolean(PREF_TICTAC, false);
    }

    /**
     * Checks if is vibrate.
     *
     * @param context the context
     * @return true, if is vibrate
     */
    public static boolean isVibrate(Context context) {

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPrefs.getBoolean(PREF_VIBRATE, false);
    }

    /**
     * Gets the toodledo username.
     *
     * @param context the context
     * @return the toodledo username
     * @throws SettingsConfigurationException the settings configuration exception
     */
    public static String getToodledoUsername(Context context)
            throws SettingsConfigurationException {

        String result;

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        result = sharedPrefs.getString(PREF_TOODLEDO_EMAIL, null);

        if (result == null)
            throw new SettingsConfigurationException("Username Not Set");

        return result;
    }

    /**
     * Gets the toodledo password.
     *
     * @param context the context
     * @return the toodledo password
     * @throws SettingsConfigurationException the settings configuration exception
     */
    public static String getToodledoPassword(Context context)
            throws SettingsConfigurationException {

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String result = sharedPrefs.getString(PREF_TOODLEDO_PASSWORD, null);

        if (result == null)
            throw new SettingsConfigurationException("Password Not Set");

        return result;
    }

    /**
     * Gets the pomodoro duration.
     *
     * @param context the context
     * @return the pomodoro duration
     */
    public static int getPomodoroDuration(Context context) {

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String result = sharedPrefs.getString(PREF_POMODORO_DURATION, "1");

        return Integer.parseInt(result);
    }

    /**
     * Gets the rest duration.
     *
     * @param context the context
     * @return the rest duration
     */
    public static int getRestDuration(Context context) {

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String result = sharedPrefs.getString(PREF_REST_DURATION, "1");

        return Integer.parseInt(result);
    }

    /**
     * Gets the long rest duration.
     *
     * @param context the context
     * @return the long rest duration
     */
    public static int getLongRestDuration(Context context) {

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String result = sharedPrefs.getString(PREF_LONG_REST_DURATION, "1");

        return Integer.parseInt(result);
    }

    /**
     * Gets the number of pomodoros until long rest.
     *
     * @param context the context
     * @return the long rest duration
     */
    public static int getNumPomodorosUntilLongRest(Context context) {

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String result = sharedPrefs.getString(PREF_NUM_POMODOROS_UNTIL_LONG_REST, "1");

        return Integer.parseInt(result);
    }

    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        if (key.equals(PREF_TOODLEDO_EMAIL)) {
            // Search for a valid mail pattern
            String pattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$";
            String value = sp.getString(key, null);
            if (!Pattern.matches(pattern, value)) {
                Toast.makeText(this, "Mail incorrect!", 2);
            }
        }
    }

}
