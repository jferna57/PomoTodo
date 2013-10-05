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

import android.os.CountDownTimer;
import android.util.Log;

import net.juancarlosfernandez.pomotodo.view.Main;

public class JkTimer {

    private final String TAG = this.getClass().getName();

    private CountDownTimer timer;

    private Main main;

    // Singleton object
    private static JkTimer jkTimer;

    private boolean isRunning = false;

    private JkTimer() {

    }

    public static synchronized JkTimer getObject() {

        Log.d("JkTimer", "getJkTimer");

        if (jkTimer == null)
            jkTimer = new JkTimer();

        return jkTimer;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public void start(Main mainInstance, int minutes) {

        Log.d(TAG, "startTimer ");

        this.main = mainInstance;

        long miliseconds = TimeUtils.minutesToMiliseconds(minutes);

        if (timer == null) {

            Log.d(TAG, "timer == null");

            timer = new CountDownTimer(miliseconds, TimeUtils.MILISECONDS) {

                public void onTick(long millisUntilFinished) {

                    int secondsUntilFinished = TimeUtils
                            .milisecondsToSeconds(millisUntilFinished);

                    main.updateTimeAndProgressBar(secondsUntilFinished);
                }

                public void onFinish() {

                    Log.d(TAG, "JkTimer + onFinish ");
                    stop();
                    main.onTimeFinish();
                }
            };
            timer.start();

            isRunning = true;

        }
    }

    public void stop() {

        Log.d(TAG, "stop");

        if (timer != null) {
            Log.d(TAG, "stop - timer != null cancelling timer");
            timer.cancel();
            timer = null;
        }
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

}
