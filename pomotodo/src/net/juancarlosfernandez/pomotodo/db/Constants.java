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

import android.provider.BaseColumns;

public interface Constants extends BaseColumns {

	// Table history
	public static final String	TABLE_HISTORY		= "history";

	// Columns in history table
	public static final String	YEAR				= "year";
	public static final String	MONTH				= "month";
	public static final String	DAY					= "day";
	public static final String	WEEK				= "week";
	public static final String	DATE				= "date";
	public static final String	DURATION			= "duration";

	// Table task
	public static final String	TABLE_TASK			= "task";

	// Columns in task table
	public static final String	TASK_ID_TOODLEDO	= "id_toodledo";
	public static final String	TASK_TITLE			= "description";
	public static final String	TASK_TAG			= "tag";
	public static final String	TASK_FOLDER			= "folder";
	public static final String	TASK_CONTEXT		= "context";
	public static final String	TASK_GOAL			= "goal";
	public static final String	TASK_LOCATION		= "location";
	public static final String	TASK_PARENT			= "parent";
	public static final String	TASK_CHILDREN		= "children";
	public static final String	TASK_ORDER			= "_order";
	public static final String	TASK_DUEDATE		= "dueDate";
	public static final String	TASK_DUEDATEMOD		= "dueDateMod";
	public static final String	TASK_STARTDATE		= "startDate";
	public static final String	TASK_DUETIME		= "dueTime";
	public static final String	TASK_STARTTIME		= "startTime";
	public static final String	TASK_REMIND			= "remind";
	public static final String	TASK_REPEAT			= "repeat";
	public static final String	TASK_REPEATFROM		= "repeatFrom";
	public static final String	TASK_STATUS			= "status";
	public static final String	TASK_LENGTH			= "length";
	public static final String	TASK_PRIORITY		= "priority";
	public static final String	TASK_STAR			= "star";
	public static final String	TASK_MODIFIED		= "modified";
	public static final String	TASK_COMPLETED		= "completed";
	public static final String	TASK_ADDED			= "added";
	public static final String	TASK_TIMER			= "timer";
	public static final String	TASK_TIMERON		= "timerOn";
	public static final String	TASK_NOTE			= "note";
	public static final String	TASK_META			= "meta";

}
