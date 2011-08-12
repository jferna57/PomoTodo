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

public class Pomodoro {

	private final String TAG = this.getClass().getName();

	private int pomodoroToday = 0;
	private int pomodoroTotal = 0;
	private int pomodoroRecord = 0;
	private String lastPomodoroDay = TimeUtils.RESET_DATE;
	private boolean isPomodoro = true;

	public Pomodoro(int numPomodorosToday, int numPomodorosTotal,
			int numPomodorsoRecord, String lastPomodoroDay) {

		this.pomodoroToday = numPomodorosToday;
		this.pomodoroTotal = numPomodorosTotal;
		this.pomodoroRecord = numPomodorsoRecord;
		this.lastPomodoroDay = lastPomodoroDay;

		if (!TimeUtils.isToday(lastPomodoroDay))
			pomodoroToday = 0;

		Log.d("lastPomodoroDay", lastPomodoroDay);
	}
	
	public boolean isPomodoro() {
		return isPomodoro;
	}

	public void setIsPomodoro(boolean isPomodoro) {
		this.isPomodoro = isPomodoro;
	}

	public int getPomodoroToday() {
		return pomodoroToday;
	}

	public void setPomodoroToday(int pomodoroToday) {
		this.pomodoroToday = pomodoroToday;
	}

	public int getPomodoroTotal() {
		return pomodoroTotal;
	}

	public void setPomodoroTotal(int pomodoroTotal) {
		this.pomodoroTotal = pomodoroTotal;
	}

	public String getLastPomodoroDay() {
		return lastPomodoroDay;
	}

	public void setLastPomodoroDay(String lastPomodoroDay) {
		this.lastPomodoroDay = lastPomodoroDay;
	}

	public void pomodoroFinish() {

		// If day change
		if (!lastPomodoroDay.equalsIgnoreCase(TimeUtils.getToday())) {
			Log.d(TAG, "Dia distinto");

			pomodoroToday = 0;
			lastPomodoroDay = TimeUtils.getToday();
		}

		pomodoroToday++;
		pomodoroTotal++;

		// If Record!
		if (pomodoroToday > pomodoroRecord)
			pomodoroRecord = pomodoroToday;
	}

	/**
	 * Check if you have working 4 times
	 * 
	 * @return true if you have 4 pomodoros false in another case
	 */
	public boolean isLongRest(int numPomodorosUntilLongRest) {

		Log.d(TAG, "PomodoroToday = " + pomodoroToday);
		
		return (pomodoroToday % numPomodorosUntilLongRest == 0);
	}

	public void resetHistory() {

		Log.d(TAG, "resetHistory");

		setPomodoroToday(0);
		setPomodoroTotal(0);
		setPomodoroRecord(0);

		setLastPomodoroDay(TimeUtils.RESET_DATE);
	}

	public int getPomodoroRecord() {
		return pomodoroRecord;
	}

	public void setPomodoroRecord(int pomodoroRecord) {
		this.pomodoroRecord = pomodoroRecord;
	}

}
