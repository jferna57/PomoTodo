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
package net.juancarlosfernandez.pomotodo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.provider.BaseColumns._ID;
import static net.juancarlosfernandez.pomotodo.db.Constants.DATE;
import static net.juancarlosfernandez.pomotodo.db.Constants.DAY;
import static net.juancarlosfernandez.pomotodo.db.Constants.DURATION;
import static net.juancarlosfernandez.pomotodo.db.Constants.MONTH;
import static net.juancarlosfernandez.pomotodo.db.Constants.TABLE_HISTORY;
import static net.juancarlosfernandez.pomotodo.db.Constants.TABLE_TASK;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_ADDED;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_CHILDREN;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_COMPLETED;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_CONTEXT;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_DUEDATE;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_DUEDATEMOD;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_DUETIME;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_FOLDER;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_GOAL;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_ID_TOODLEDO;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_IS_SELECTED;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_LENGTH;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_LOCATION;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_META;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_MODIFIED;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_NOTE;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_ORDER;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_PARENT;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_PRIORITY;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_REMIND;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_REPEAT;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_REPEATFROM;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_STAR;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_STARTDATE;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_STARTTIME;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_STATUS;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_TAG;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_TIMER;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_TIMERON;
import static net.juancarlosfernandez.pomotodo.db.Constants.TASK_TITLE;
import static net.juancarlosfernandez.pomotodo.db.Constants.WEEK;
import static net.juancarlosfernandez.pomotodo.db.Constants.YEAR;

public class DataBaseHelper extends SQLiteOpenHelper {

    private final String TAG = this.getClass().getName();
    private static final String DATABASE_NAME = "net.juancarlosfernandez.pomotodo.db";
    private static final int DATABASE_VERSION = 3;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createTableTask(db);
        createTableHistory(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUngrade- oldversion:" + oldVersion + " newVersion: " + newVersion);

        if (newVersion < 3) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
            createTableHistory(db);
            createTableTask(db);
        } else if (newVersion == 3) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
            createTableHistory(db);
        }
    }

    private void createTableTask(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_HISTORY + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE
                + " STRING," + DAY + " INTEGER," + MONTH + " INTEGER," + YEAR + " INTEGER," + WEEK + " INTEGER,"
                + DURATION + " INTEGER);");
    }

    private void createTableHistory(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_TASK + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK_ID_TOODLEDO
                + " INTEGER," + TASK_TITLE + " STRING," + TASK_TAG + " STRING," + TASK_FOLDER + " INTEGER,"
                + TASK_CONTEXT + " INTEGER," + TASK_GOAL + " INTEGER," + TASK_LOCATION + " INTEGER," + TASK_PARENT
                + " INTEGER," + TASK_CHILDREN + " INTEGER," + TASK_ORDER + " INTEGER," + TASK_DUEDATE + " STRING,"
                + TASK_DUEDATEMOD + " INTEGER," + TASK_STARTDATE + " STRING," + TASK_DUETIME + " STRING,"
                + TASK_STARTTIME + " STRING," + TASK_REMIND + " INTEGER," + TASK_REPEAT + " INTEGER," + TASK_REPEATFROM
                + " INTEGER," + TASK_STATUS + " INTEGER," + TASK_LENGTH + " INTEGER," + TASK_PRIORITY + " INTEGER,"
                + TASK_STAR + " INTEGER," + TASK_MODIFIED + " STRING," + TASK_COMPLETED + " INTEGER," + TASK_ADDED
                + " STRING," + TASK_TIMER + " INTEGER," + TASK_TIMERON + " STRING," + TASK_NOTE + " STRING,"
                + TASK_META + " STRING," + TASK_IS_SELECTED + " INTEGER" + ");");

    }
}
