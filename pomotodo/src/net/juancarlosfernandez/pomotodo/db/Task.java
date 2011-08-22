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

import java.util.List;

import net.juancarlosfernandez.pomotodo.toodledo.data.Todo;
import net.juancarlosfernandez.pomotodo.toodledo.util.TdDate;
import net.juancarlosfernandez.pomotodo.toodledo.util.TdDateTime;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Task {

	private DataBaseHelper	dbHelper;

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
		values.put(TASK_TAG, todo.getTag());
		values.put(TASK_FOLDER, todo.getFolder());
		values.put(TASK_CONTEXT, todo.getContext());
		values.put(TASK_GOAL, todo.getGoal());
		values.put(TASK_LOCATION, todo.getLocation());
		values.put(TASK_PARENT, todo.getParent());
		values.put(TASK_CHILDREN, todo.getChildren());
		values.put(TASK_ORDER, todo.getOrder());
		values.put(TASK_DUEDATE, todo.getDueDate().toString());
		values.put(TASK_DUEDATEMOD, todo.getDueDateMod());
		values.put(TASK_STARTDATE, todo.getStartDate().toString());
		values.put(TASK_DUETIME, todo.getDueTime().toString());
		values.put(TASK_STARTTIME, todo.getStartTime().toString());
		values.put(TASK_REMIND, todo.getRemind());
		values.put(TASK_REPEAT, todo.getRepeat().getRepeatAsInteger());
		values.put(TASK_REPEATFROM, todo.getRepeatFrom());
		values.put(TASK_STATUS, todo.getStatus().getStatusAsInteger());
		values.put(TASK_LENGTH, todo.getLength());
		values.put(TASK_PRIORITY, todo.getPriority().getPriorityAsInt());
		values.put(TASK_STAR, todo.isStar());
		values.put(TASK_MODIFIED, todo.getModified().toString());
		values.put(TASK_COMPLETED, todo.getCompleted().toString());
		values.put(TASK_ADDED, todo.getAdded().toString());
		values.put(TASK_TIMER, todo.getTimer());
		values.put(TASK_TIMERON, todo.getTimerOn().toString());
		values.put(TASK_NOTE, todo.getNote());
		values.put(TASK_META, todo.getMeta());

		db.insertOrThrow(TABLE_TASK, null, values);
	}

	public Todo getTask(int id) {
		Todo task = null;

		SQLiteDatabase db;
		db = dbHelper.getReadableDatabase();
		Cursor cursor;

		cursor = db.query(TABLE_TASK, null, "id = " + id, null, null, null, null);

		cursor.moveToNext();

		if (cursor.getCount() == 1) {
			task = new Todo();
			task.setId(cursor.getInt(0));
			task.setTitle(cursor.getString(1));
			task.setTag(cursor.getString(2));
			task.setFolder(cursor.getInt(3));
			task.setContext(cursor.getInt(4));
			task.setGoal(cursor.getInt(5));
			task.setLocation(cursor.getInt(6));
			task.setParent(cursor.getInt(7));
			task.setChildren(cursor.getInt(8));
			task.setOrder(cursor.getInt(9));
			task.setDueDate(new TdDate(cursor.getString(10)));
			task.setDueDateMod(cursor.getInt(11));
			task.setStartDate(new TdDate(cursor.getString(12)));
			task.setDueTime(new TdDateTime(cursor.getString(13)));
			task.setStartTime(new TdDateTime(cursor.getString(14)));
			task.setRemind(cursor.getInt(15));
			// task.setRepeat(cursor.getInt(16));
			task.setRepeatFrom(cursor.getInt(17));
			// task.setStatus(new Status(cursor.getInt(18)));
			task.setLength(cursor.getInt(19));
			// task.setPriority(new Priority(cursor.getInt(20)));
			// task.setStar(cursor.getInt(cursor.getInt(21)));
			task.setModified(new TdDateTime(cursor.getString(22)));
			task.setCompleted(new TdDate(cursor.getString(23)));
			task.setAdded(new TdDate(cursor.getString(24)));
			task.setTimer(cursor.getInt(25));
			task.setTimerOn(new TdDateTime(cursor.getString(26)));
			task.setNote(cursor.getString(27));
			task.setMeta(cursor.getColumnName(28));
			// 0-values.put(TASK_ID_TOODLEDO, todo.getId());
			// 1-values.put(TASK_TITLE, todo.getTitle());
			// 2-values.put(TASK_TAG, todo.getTag());
			// 3-values.put(TASK_FOLDER, todo.getFolder());
			// 4-values.put(TASK_CONTEXT, todo.getContext());
			// 5-values.put(TASK_GOAL, todo.getGoal());
			// 6-values.put(TASK_LOCATION, todo.getLocation());
			// 7-values.put(TASK_PARENT, todo.getParent());
			// 8-values.put(TASK_CHILDREN, todo.getChildren());
			// 9-values.put(TASK_ORDER, todo.getOrder());
			// 10-values.put(TASK_DUEDATE, todo.getDueDate().toString());
			// 11-values.put(TASK_DUEDATEMOD, todo.getDueDateMod());
			// 12-values.put(TASK_STARTDATE, todo.getStartDate().toString());
			// 13-values.put(TASK_DUETIME, todo.getDueTime().toString());
			// 14-values.put(TASK_STARTTIME, todo.getStartTime().toString());
			// 15-values.put(TASK_REMIND, todo.getRemind());
			// 16-values.put(TASK_REPEAT,
			// todo.getRepeat().getRepeatAsInteger());
			// 17-values.put(TASK_REPEATFROM, todo.getRepeatFrom());
			// 18-values.put(TASK_STATUS,
			// todo.getStatus().getStatusAsInteger());
			// 19-values.put(TASK_LENGTH, todo.getLength());
			// 20-values.put(TASK_PRIORITY,
			// todo.getPriority().getPriorityAsInt());
			// 21-values.put(TASK_STAR, todo.isStar());
			// 22-values.put(TASK_MODIFIED, todo.getModified().toString());
			// 23-values.put(TASK_COMPLETED, todo.getCompleted().toString());
			// 24-values.put(TASK_ADDED, todo.getAdded().toString());
			// 25-values.put(TASK_TIMER, todo.getTimer());
			// 26-values.put(TASK_TIMERON, todo.getTimerOn().toString());
			// 27-values.put(TASK_NOTE, todo.getNote());
			// 28-values.put(TASK_META, todo.getMeta());
		}
		return task;
	}

	public List<Todo> getTasks() {

		return null;
	}

	public void clearTasks() {
		SQLiteDatabase db;
		db = dbHelper.getReadableDatabase();
		db.execSQL("DELETE FROM " + TABLE_TASK);
	}
}