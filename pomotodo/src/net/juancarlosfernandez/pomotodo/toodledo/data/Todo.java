package net.juancarlosfernandez.pomotodo.toodledo.data;

import net.juancarlosfernandez.pomotodo.toodledo.util.TdDate;
import net.juancarlosfernandez.pomotodo.toodledo.util.TdDateTime;

/**
 * This class maps all the components of a toodledo task.
 * 
 * For more info go to http://api.toodledo.com/2/tasks/index.php
 * 
 * @author juancarlosf
 */
public class Todo {
	/**
	 * The server id number for this task. It is guaranteed to be unique per
	 * account, but two different accounts may have two different tasks with the
	 * same id number.
	 */
	private int id = -1;
	/**
	 * A string for the name of the task. Up to 255 characters. When setting,
	 * please encode the & character as %26 and the ; character as %3B.
	 */
	private String title;
	/**
	 * A comma separated string listing the tags assigned to this task. Up to 64
	 * characters. When setting, please encode the & character as %26 and the ;
	 * character as %3B.
	 */
	private String tag;
	/**
	 * The id number of the folder. Omit this field or set it to 0 to leave the
	 * task unassigned to a folder.
	 */
	private int folder;
	/**
	 * The id number of the context. Omit this field or set it to 0 to leave the
	 * task unassigned to a context.
	 */
	private int context;
	/**
	 * The id number of the goal. Omit this field or set it to 0 to leave the
	 * task unassigned to a goal.
	 */
	private int goal;
	/**
	 * The id number of the location. Omit this field or set it to 0 to leave
	 * the task unassigned to a location.
	 */
	private int location;
	/**
	 * This is used on Pro accounts that have access to subtasks. To create a
	 * subtask, set this to the id number of the parent task. The default is 0,
	 * which creates a normal task.
	 */
	private int parent;
	/**
	 * This is used on Pro accounts that have access to subtasks. This will
	 * indicate the number of child tasks that this task has. This will be 0 for
	 * subtasks or for regular tasks without subtasks.
	 */
	private int children;
	/**
	 * This is used on Pro accounts that have access to subtasks. This is an
	 * integer that indicates the manual order of subtasks within the parent
	 * task. Currently this is read-only.
	 */
	private int order;
	/**
	 * A GMT unix timestamp for when the task is due. The time component of this
	 * timestamp will always be noon.
	 */
	private TdDate dueDate;

	/**
	 * An integer representing the due date modifier.
	 * 
	 * 0 = Due By 1 = Due On (=) 2 = Due After (>) 3 = Optionally (?)
	 */
	private int dueDateMod;

	/**
	 * A GMT unix timestamp for when the task starts. The time component of this
	 * timestamp will always be noon.
	 */
	private TdDate startDate;

	/**
	 * A GMT unix timestamp for when the task is due. If the task does not have
	 * a time set, then this will be 0. If the task has a duetime without a
	 * duedate set, then the date component of this timestamp will be Jan 1,
	 * 1970.
	 */
	private TdDateTime dueTime;

	/**
	 * A GMT unix timestamp for when the task starts. If the task does not have
	 * a time set, then this will be 0. If the task has a starttime without a
	 * startdate set, then the date component of this timestamp will be Jan 1,
	 * 1970.
	 */
	private TdDateTime startTime;

	/**
	 * An integer that represents the number of minutes prior to the
	 * duedate/time that a reminder will be sent. Set it to 0 for no reminder.
	 * Values will be constrained to this list of valid numbers (0, 1, 15, 30,
	 * 45, 60, 90, 120, 180, 240, 1440, 2880, 4320, 5760, 7200, 8640, 10080,
	 * 20160, 43200). Additionally, if the user does not have a Pro account, the
	 * only valid numbers are 0,60. If you submit an invalid number, it will be
	 * rounded up or down to a valid non zero value.
	 */
	private int remind;

	/**
	 * A string indicating how the task repeats. For example: "Every 1 Week" or
	 * "On the 2nd Friday". Please read our "repeat format faq for details about
	 * valid values. When a task is rescheduled, it is moved forward to the new
	 * date. For record keeping purposes, a completed copy of this task will be
	 * added to the user's list. It will have a new ID number and will be
	 * already completed. To unset this value, set it to an empty string.
	 */
	private Repeat repeat;

	/**
	 * Indicates how tasks repeat. It will be 0 to repeat from the due-date or 1
	 * to repeat from the completion date.
	 */
	private int repeatFrom;

	/**
	 * An integer that represents the status of the task.
	 * 
	 * 0 = None 1 = Next Action 2 = Active 3 = Planning 4 = Delegated 5 =
	 * Waiting 6 = Hold 7 = Postponed 8 = Someday 9 = Canceled 10 = Reference
	 */
	private Status status;

	/**
	 * An integer representing the number of minutes that the task will take to
	 * complete.
	 */
	private int length;

	/**
	 * An integer that represents the priority.
	 * 
	 * -1 = Negative 0 = Low 1 = Medium 2 = High 3 = Top
	 */
	private Priority priority;

	/**
	 * A boolean (0 or 1) that indicates if the task has a star or not.
	 */
	private boolean star;

	/**
	 * A GMT unix timestamp for when the task was last modified.
	 */
	private TdDateTime modified;

	/**
	 * A GMT unix timestamp for when the task was completed. If the task is not
	 * completed, the value will be 0. Toodledo does not track the time that a
	 * task was completed, so tasks will always appear to be completed at noon.
	 */
	private TdDate completed;

	/**
	 * A GMT unix timestamp for when the task was added. Toodledo does not track
	 * the time that a task was added, so tasks will always appear to be added
	 * at noon.
	 */
	private TdDate added;

	/**
	 * The value in the timer field indicates the number of seconds that have
	 * elapsed for the timer not including the current session.
	 */
	private int timer;

	/**
	 * If the timer is currently on, this will contain a unix timestamp
	 * indicating the last time that the timer was started. Therefore, if the
	 * timer is currently on, you will need to calculate the elapsed time when
	 * you present it to the user. This calculation is: Total
	 * Time=timer+(now-timeron). Where "now" is a unix timestamp for the current
	 * time.
	 */
	private TdDateTime timerOn;

	/**
	 * A text string up to 32,000 bytes long. When setting, please encode the &
	 * character as %26 and the ; character as %3B.
	 */
	private String note;

	/**
	 * A text string up to 255 bytes long for storing metadata about the task.
	 * This is useful for syncing data that cannot otherwise be synced to
	 * Toodledo. This data is unique per task ID. When a task is rescheduled, a
	 * new This data is private to your App ID. Neither the user, nor other App
	 * IDs can see the data that you put in here. Because of an implementation
	 * detail, using the meta field introduces extra latency to each API call,
	 * so you should only use this field when necessary.
	 */
	private String meta;

	public Todo() {
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title for the task.
	 * 
	 * @param title
	 *            the title to set. If the title is longer than 255 charts it
	 *            will be cropped.
	 */
	public void setTitle(String title) {
		if (title.length() > 255)
			this.title = title.substring(0, 255);
		else
			this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getFolder() {
		return folder;
	}

	public void setFolder(int folder) {
		this.folder = folder;
	}

	public int getContext() {
		return context;
	}

	public void setContext(int context) {
		this.context = context;
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public TdDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(TdDate dueDate) {
		this.dueDate = dueDate;
	}

	public int getDueDateMod() {
		return dueDateMod;
	}

	public void setDueDateMod(int dueDateMod) {
		this.dueDateMod = dueDateMod;
	}

	public TdDate getStartDate() {
		return startDate;
	}

	public void setStartDate(TdDate startDate) {
		this.startDate = startDate;
	}

	public TdDateTime getDueTime() {
		return dueTime;
	}

	public void setDueTime(TdDateTime dueTime) {
		this.dueTime = dueTime;
	}

	public TdDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(TdDateTime startTime) {
		this.startTime = startTime;
	}

	public int getRemind() {
		return remind;
	}

	public void setRemind(int remind) {
		this.remind = remind;
	}

	public Repeat getRepeat() {
		return repeat;
	}

	public void setRepeat(Repeat repeat) {
		this.repeat = repeat;
	}

	public int getRepeatFrom() {
		return repeatFrom;
	}

	public void setRepeatFrom(int repeatFrom) {
		this.repeatFrom = repeatFrom;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public boolean isStar() {
		return star;
	}

	public void setStar(boolean star) {
		this.star = star;
	}

	public TdDateTime getModified() {
		return modified;
	}

	public void setModified(TdDateTime modified) {
		this.modified = modified;
	}

	public TdDate getCompleted() {
		return completed;
	}

	public void setCompleted(TdDate completed) {
		this.completed = completed;
	}

	public TdDate getAdded() {
		return added;
	}

	public void setAdded(TdDate added) {
		this.added = added;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public TdDateTime getTimerOn() {
		return timerOn;
	}

	public void setTimerOn(TdDateTime timerOn) {
		this.timerOn = timerOn;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}
}
