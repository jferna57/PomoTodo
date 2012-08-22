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
import net.juancarlosfernandez.pomotodo.toodledo.data.Todo;
import net.juancarlosfernandez.pomotodo.toodledo.util.TdDate;
import net.juancarlosfernandez.pomotodo.toodledo.util.TdDateTime;

import java.util.ArrayList;
import java.util.List;

import static net.juancarlosfernandez.pomotodo.db.Constants.*;

public class Task {

    private DataBaseHelper dbHelper;

    public Task(DataBaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void addTasks(List<Todo> listTodo) {

        for (int i = 0; i < listTodo.size(); i++) {
            addTask(listTodo.get(i));
        }
    }

    public void addTask(Todo todo) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TASK_ID_TOODLEDO, todo.getId());
        values.put(TASK_TITLE, todo.getTitle());
        // values.put(TASK_TAG, todo.getTag());
        // values.put(TASK_FOLDER, todo.getFolder());
        // values.put(TASK_CONTEXT, todo.getContext());
        // values.put(TASK_GOAL, todo.getGoal());
        // values.put(TASK_LOCATION, todo.getLocation());
        // values.put(TASK_PARENT, todo.getParent());
        // values.put(TASK_CHILDREN, todo.getChildren());
        // values.put(TASK_ORDER, todo.getOrder());
        // values.put(TASK_DUEDATE, todo.getDueDate().toString());
        // values.put(TASK_DUEDATEMOD, todo.getDueDateMod());
        // values.put(TASK_STARTDATE, todo.getStartDate().toString());
        // values.put(TASK_DUETIME, todo.getDueTime().toString());
        // values.put(TASK_STARTTIME, todo.getStartTime().toString());
        // values.put(TASK_REMIND, todo.getRemind());
        // values.put(TASK_REPEAT, todo.getRepeat().getRepeatAsInteger());
        // values.put(TASK_REPEATFROM, todo.getRepeatFrom());
        // values.put(TASK_STATUS, todo.getStatus().getStatusAsInteger());
        // values.put(TASK_LENGTH, todo.getLength());
        // values.put(TASK_PRIORITY, todo.getPriority().getPriorityAsInt());
        // values.put(TASK_STAR, todo.isStar());
        // values.put(TASK_MODIFIED, todo.getModified().toString());
        // values.put(TASK_COMPLETED, todo.getCompleted().toString());
        // values.put(TASK_ADDED, todo.getAdded().toString());
        // values.put(TASK_TIMER, todo.getTimer());
        // values.put(TASK_TIMERON, todo.getTimerOn().toString());
        // values.put(TASK_NOTE, todo.getNote());
        // values.put(TASK_META, todo.getMeta());
        values.put(TASK_IS_SELECTED, 0);

        db.insertOrThrow(TABLE_TASK, null, values);
    }

    public Todo getTask(int id) {
        Todo task = null;

        SQLiteDatabase db;
        db = dbHelper.getReadableDatabase();
        Cursor cursor;

        cursor = db.query(TABLE_TASK, null, "id = " + id, null, null, null, null);

        if (cursor.moveToNext())
            task = getTask(cursor);

        return task;
    }

    public List<Todo> getTasks(boolean isSelected) {
        List<Todo> result = new ArrayList<Todo>();
        Cursor cursor;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if (isSelected)
            cursor = db.query(TABLE_TASK, null, "is_selected = 1", null, null, null, null);
        else
            cursor = db.query(TABLE_TASK, null, "is_selected = 0", null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getTask(cursor));
        }
        return result;

    }

    public List<Todo> getTasks() {

        List<Todo> result = new ArrayList<Todo>();

        SQLiteDatabase db;
        db = dbHelper.getReadableDatabase();
        Cursor cursor;

        cursor = db.query(TABLE_TASK, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getTask(cursor));
        }
        return result;
    }

    private Todo getTask(Cursor cursor) {

        Todo task = new Todo();

        task.setId(cursor.getInt(1));
        task.setTitle(cursor.getString(2));
        task.setTag(cursor.getString(3));
        task.setFolder(cursor.getInt(4));
        task.setContext(cursor.getInt(5));
        task.setGoal(cursor.getInt(6));
        task.setLocation(cursor.getInt(7));
        task.setParent(cursor.getInt(8));
        task.setChildren(cursor.getInt(9));
        task.setOrder(cursor.getInt(10));
        task.setDueDate(new TdDate(cursor.getString(11)));
        task.setDueDateMod(cursor.getInt(12));
        task.setStartDate(new TdDate(cursor.getString(13)));
        task.setDueTime(new TdDateTime(cursor.getString(14)));
        task.setStartTime(new TdDateTime(cursor.getString(15)));
        task.setRemind(cursor.getInt(16));
        // task.setRepeat(cursor.getInt(17));
        task.setRepeatFrom(cursor.getInt(18));
        // task.setStatus(new Status(cursor.getInt(19)));
        task.setLength(cursor.getInt(20));
        // task.setPriority(new Priority(cursor.getInt(21)));
        // task.setStar(cursor.getInt(cursor.getInt(22)));
        task.setModified(new TdDateTime(cursor.getString(23)));
        task.setCompleted(new TdDate(cursor.getString(24)));
        task.setAdded(new TdDate(cursor.getString(25)));
        task.setTimer(cursor.getInt(26));
        task.setTimerOn(new TdDateTime(cursor.getString(27)));
        task.setNote(cursor.getString(28));
        task.setMeta(cursor.getString(29));
        task.setIsSelected(cursor.getInt(30));

        return task;
    }

    public void clearTasks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TASK);
    }

    public void changeClickedTask(Todo task) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (task.getIsSelected() == 0) {
            db.execSQL("UPDATE " + TABLE_TASK + " SET " + TASK_IS_SELECTED + " = 1 WHERE " + TASK_ID_TOODLEDO + " = "
                    + task.getId() + ";");
            task.setIsSelected(1);
        } else {
            db.execSQL("UPDATE " + TABLE_TASK + " SET " + TASK_IS_SELECTED + " = 0 WHERE " + TASK_ID_TOODLEDO + " = "
                    + task.getId() + ";");
            task.setIsSelected(0);
        }
    }

    public void resetSelectedTasks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL("UPDATE " + TABLE_TASK + " SET " + TASK_IS_SELECTED + " = 0 ;");
    }

    public void removeSelectedTasks() {
        SQLiteDatabase db;
        db = dbHelper.getReadableDatabase();
        Cursor cursor;

        cursor = db.query(TABLE_TASK, null, TASK_IS_SELECTED + " = 1", null, null, null, null);

        while (cursor.moveToNext())
            db.execSQL("DELETE FROM " + TABLE_TASK + " WHERE " + TASK_ID_TOODLEDO + " = " + cursor.getInt(1) + ";");
    }

    public int getCount(boolean isSelected) {
        List<Todo> tasks = getTasks(isSelected);
        return tasks.size();
    }
}
