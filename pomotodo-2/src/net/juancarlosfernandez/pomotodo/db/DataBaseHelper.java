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
import static net.juancarlosfernandez.pomotodo.db.Constants.*;

public class DataBaseHelper extends SQLiteOpenHelper {

    private final String TAG = this.getClass().getName();
    private static final String DATABASE_NAME = "pomotodo.db";
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
