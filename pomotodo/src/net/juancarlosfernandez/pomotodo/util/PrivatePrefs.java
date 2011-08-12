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

import android.content.SharedPreferences;
import android.util.Log;

public class PrivatePrefs {

	private static final String TAG = PrivatePrefs.class.getName();

	// PRIVATE PREFERENCES
	public static final String POMODOROS_TODAY = "prefPomodorosToday";
	public static final String POMODOROS_TOTAL = "prefPomodorosTotal";
	public static final String POMODOROS_LASTDAY = "prefPomodoroLastDay";
	public static final String POMODOROS_RECORD = "prefPomodorosRecord";
	public static final String TOODLEDO_TOKEN = "prefToodledoToken";

	private SharedPreferences privatePrefs;

	private static PrivatePrefs jkPrivatePrefs;

	private PrivatePrefs(SharedPreferences privatePrefs) {
		this.privatePrefs = privatePrefs;
	}

	public static synchronized PrivatePrefs getObject(SharedPreferences privatePrefs) {

		if (jkPrivatePrefs == null)
			jkPrivatePrefs = new PrivatePrefs(privatePrefs);

		return jkPrivatePrefs;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public void savePrivatePref(String privatePref, String value) {
		privatePrefs.edit().putString(privatePref, value).commit();
	}

	public void savePomodoro(Pomodoro jkPomodoro) {
		Log.d(TAG, "savePomodoroHistory");

		savePrivatePref(POMODOROS_TODAY, "" + jkPomodoro.getPomodoroToday());
		savePrivatePref(POMODOROS_TOTAL, "" + jkPomodoro.getPomodoroTotal());
		savePrivatePref(POMODOROS_LASTDAY, "" + jkPomodoro.getLastPomodoroDay());
		savePrivatePref(POMODOROS_RECORD, "" + jkPomodoro.getPomodoroRecord());
	}

	public void saveToodledo(JkToodledo jkToodledo) {
		Log.d(TAG, "saveToodledo token " + jkToodledo.getSessionToken());
		savePrivatePref(TOODLEDO_TOKEN, jkToodledo.getSessionToken());
	}

	public String getStringValue(String pref, String defaultValue) {
		String key = privatePrefs.getString(pref, defaultValue);

		return key;
	}

	public int getIntValue(String preference, int defaultValue) {

		String stringValue = privatePrefs.getString(preference, "" + defaultValue);

		int result;

		Log.d(TAG, preference + " = " + stringValue);

		try {
			result = new Integer(stringValue);
		} catch (Exception e) {
			Log.e(TAG, "ERROR!!! " + preference + " value " + stringValue);
			result = 0;
		}
		return result;
	}

	public void resetPrivatePrefs() {
		savePrivatePref(POMODOROS_TODAY, null);
		savePrivatePref(POMODOROS_TOTAL, null);
		savePrivatePref(POMODOROS_LASTDAY, null);
		savePrivatePref(POMODOROS_RECORD, null);
		savePrivatePref(TOODLEDO_TOKEN, null);
	}
}
