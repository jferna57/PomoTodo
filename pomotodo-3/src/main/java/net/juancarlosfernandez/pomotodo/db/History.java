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

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.juancarlosfernandez.pomotodo.util.TimeUtils;

import static net.juancarlosfernandez.pomotodo.db.Constants.DATE;
import static net.juancarlosfernandez.pomotodo.db.Constants.DAY;
import static net.juancarlosfernandez.pomotodo.db.Constants.DURATION;
import static net.juancarlosfernandez.pomotodo.db.Constants.MONTH;
import static net.juancarlosfernandez.pomotodo.db.Constants.TABLE_HISTORY;
import static net.juancarlosfernandez.pomotodo.db.Constants.WEEK;
import static net.juancarlosfernandez.pomotodo.db.Constants.YEAR;

public class History {

    private DataBaseHelper dbHelper;

    private static String CONDITION_DAY = "DAY = " + TimeUtils.getDay();
    private static String CONDITION_MONTH = "MONTH = " + TimeUtils.getMonth();
    private static String CONDITION_YEAR = "YEAR = " + TimeUtils.getYear();
    private static String CONDITION_WEEK = "WEEK = " + TimeUtils.getWeek();
    private static String AND = " AND ";

    private static final String[] MAX_POMODORO = {"max(pomodoros)"};
    private static final String FROM_MAX_POMODORO = "(select count(*) as pomodoros from history group by date)";

    public History(DataBaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void addPomodoro(int duration) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DAY, TimeUtils.getDay());
        values.put(MONTH, TimeUtils.getMonth());
        values.put(YEAR, TimeUtils.getYear());
        values.put(WEEK, TimeUtils.getWeek());
        values.put(DATE, TimeUtils.getDate());
        values.put(DURATION, duration);

        db.insertOrThrow(TABLE_HISTORY, null, values);
    }

    public void resetHistory() {
        SQLiteDatabase db;
        db = dbHelper.getReadableDatabase();
        db.execSQL("DELETE FROM " + TABLE_HISTORY);
    }

    public int getTotalToday() {

        SQLiteDatabase db;
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(TABLE_HISTORY, null, CONDITION_DAY + AND
                + CONDITION_MONTH + AND + CONDITION_YEAR, null, null, null,
                null);

        int total = cursor.getCount();
        cursor.close();

        return total;
    }

    public int getTotalMonth() {
        SQLiteDatabase db;
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(TABLE_HISTORY, null, CONDITION_MONTH + AND
                + CONDITION_YEAR, null, null, null, null);

        int total = cursor.getCount();
        cursor.close();

        return total;
    }

    public int getTotalYear() {
        SQLiteDatabase db;
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(TABLE_HISTORY, null, CONDITION_YEAR,
                null, null, null, null);

        int total = cursor.getCount();
        cursor.close();

        return total;
    }

    public int getTotalWeek() {
        SQLiteDatabase db;
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(TABLE_HISTORY, null, CONDITION_YEAR + AND + CONDITION_WEEK,
                null, null, null, null);

        int total = cursor.getCount();
        cursor.close();

        return total;

    }

    public int getTotal() {
        SQLiteDatabase db;
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(TABLE_HISTORY, null, null, null, null, null, null);

        int total = cursor.getCount();
        cursor.close();

        return total;
    }

    public int getRecord() {
        SQLiteDatabase db;
        db = dbHelper.getReadableDatabase();
        Cursor cursor;

        cursor = db.query(FROM_MAX_POMODORO, MAX_POMODORO, null, null, null, null, null);

        try {
            return cursor.moveToNext() ? cursor.getInt(0) : 0;
        } finally {
            cursor.close();
        }
    }
}
