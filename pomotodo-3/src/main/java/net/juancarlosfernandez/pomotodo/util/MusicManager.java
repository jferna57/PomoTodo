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

import android.content.Context;
import android.media.MediaPlayer;

import net.juancarlosfernandez.pomotodo.R;

public class MusicManager {

    //private static final String TAG = "MusicManager";

    private static boolean isSilent = false;

    private static MediaPlayer mp = null;

    // Singleton object
    private static MusicManager musicManager;

    private MusicManager() {

    }

    public static synchronized MusicManager getObject() {

        if (musicManager == null) {
            musicManager = new MusicManager();
        }
        return musicManager;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public void silent() {

        isSilent = true;

        if (isPlaying()) {
            mp.stop();
            mp.release();
        }
        mp = null;
    }


    private boolean isPlaying() {
        if (mp != null)
            if (mp.isPlaying())
                return true;
        return false;
    }

    public void play(Context context) {

        if (!isSilent) {

            if (mp != null) {
                if (!isPlaying())
                    mp.start();
            } else {
                mp = MediaPlayer.create(context, R.raw.onesecond);
                mp.start();
            }
        }
    }

    public void resume() {

        if (mp != null)
            mp.release();
        mp = null;
        isSilent = false;
    }
}
