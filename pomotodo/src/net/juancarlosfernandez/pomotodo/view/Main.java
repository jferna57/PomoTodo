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
package net.juancarlosfernandez.pomotodo.view;

import java.util.List;

import net.juancarlosfernandez.pomotodo.R;
import net.juancarlosfernandez.pomotodo.db.DataBaseHelper;
import net.juancarlosfernandez.pomotodo.db.History;
import net.juancarlosfernandez.pomotodo.db.Task;
import net.juancarlosfernandez.pomotodo.exception.SettingsConfigurationException;
import net.juancarlosfernandez.pomotodo.exception.ToodledoConnectionException;
import net.juancarlosfernandez.pomotodo.toodledo.data.Todo;
import net.juancarlosfernandez.pomotodo.util.JkTasks;
import net.juancarlosfernandez.pomotodo.util.JkTimer;
import net.juancarlosfernandez.pomotodo.util.JkToodledo;
import net.juancarlosfernandez.pomotodo.util.MusicManager;
import net.juancarlosfernandez.pomotodo.util.Pomodoro;
import net.juancarlosfernandez.pomotodo.util.PrivatePrefs;
import net.juancarlosfernandez.pomotodo.util.TimeUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {

	private final String		TAG				= this.getClass().getName();

	private final int			NOTIFICATION_ID	= 2;
	private NotificationManager	notificationMgr;
	private JkTimer				jkTimer			= JkTimer.getObject();
	private PrivatePrefs		privatePrefs;
	private Pomodoro			pomodoro;

	private ImageButton			actionButton;
	private TextView			timeLeft;
	private ProgressBar			myProgressBar;

	// History Text
	private TextView			historyToday;
	private TextView			historyMonth;
	private TextView			historyYear;
	private TextView			historyTotal;
	private TextView			historyRecord;

	// DB
	private History				history;
	private Task				tasks;

	private ListView			todoList;

	/**
	 * The activity is being created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Initialize db
		DataBaseHelper db = new DataBaseHelper(this);
		history = new History(db);
		tasks = new Task(db);

		// Initialize fields
		initButtons();
		initFields();
		initializeTimeToFinish();

		// Notification Manager
		notificationMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		privatePrefs = PrivatePrefs.getObject(getPreferences(MODE_PRIVATE));

		// Update History
		setHistory();

		// Populate todo List
		populateTodoList();
	}

	/**
	 * The activity is about to become visible.
	 */
	@Override
	protected void onStart() {
		Log.d(TAG, "onStart");
		super.onStart();
	}

	/**
	 * Another activity is taking focus (this activity is about to be "paused")
	 */
	@Override
	protected void onPause() {

		super.onPause();

		Log.d(TAG, "onPause");

		MusicManager.getObject().silent();
	}

	/**
	 * The activity has become visible (it is now "resumed").
	 */
	@Override
	protected void onResume() {
		super.onResume();

		Log.d(TAG, "onResume");

		MusicManager.getObject().resume();

		populateTodoList();
	}

	/**
	 * The activity is no longer visible (it is now "stopped")
	 */
	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	/**
	 * The activity is about to be destroyed.
	 */
	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
		super.onDestroy();

		stopTimer();
	}

	/**
	 * Populate the todo list based on tasks selected on TodoTask
	 */
	private void populateTodoList() {
		String[] selectedTasks = JkTasks.getObject().getSelectedTasks();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.task_entry, selectedTasks);
		todoList.setAdapter(adapter);
	}

	private void initFields() {

		timeLeft = (TextView) findViewById(R.id.time_left);
		myProgressBar = (ProgressBar) findViewById(R.id.task_progress_bar);
		todoList = (ListView) findViewById(R.id.todo_list);

		// History fields
		historyToday = (TextView) findViewById(R.id.history_today);
		historyMonth = (TextView) findViewById(R.id.history_month);
		historyYear = (TextView) findViewById(R.id.history_year);
		historyTotal = (TextView) findViewById(R.id.history_total);
		historyRecord = (TextView) findViewById(R.id.history_record);
	}

	private void initButtons() {
		View actionViewButton = findViewById(R.id.action_button);
		actionViewButton.setOnClickListener(this);
		actionButton = (ImageButton) findViewById(R.id.action_button);

		View syncViewButton = findViewById(R.id.sync_button);
		syncViewButton.setOnClickListener(this);

		View selectTaskViewButton = findViewById(R.id.select_task_button);
		selectTaskViewButton.setOnClickListener(this);

		View clearTaskViewButton = findViewById(R.id.clear_selected_tasks_button);
		clearTaskViewButton.setOnClickListener(this);

		View finishTasksViewButton = findViewById(R.id.finish_tasks_button);
		finishTasksViewButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu, menu);
		return true;
	}

	public void onClick(View v) {
		Log.d(TAG, "onClick");

		switch (v.getId()) {
			case R.id.sync_button:
				askIfSync();
				break;
			case R.id.action_button:
				actionStartOrStop();
				break;
			case R.id.clear_selected_tasks_button:
				if (todoList.getCount() > 0)
					askIfClearSelectedTasks();
				else
					Toast.makeText(Main.this, R.string.no_task_selected, Toast.LENGTH_SHORT).show();
				break;
			case R.id.finish_tasks_button:
				if (todoList.getCount() > 0)
					askIfFinishSelectedTasks();
				else
					Toast.makeText(Main.this, R.string.no_task_selected_select_some, Toast.LENGTH_SHORT).show();

				break;
			case R.id.select_task_button:
				if (!JkTasks.getObject().isAllTodoListEmpty()) {
					Intent i = new Intent(this, TaskListView.class);
					startActivity(i);
				} else {
					Toast.makeText(Main.this, R.string.no_task_sync_toodledo, Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected");

		switch (item.getItemId()) {
			case R.id.settings:
				startActivity(new Intent(this, Preferences.class));
				return true;
			case R.id.about:
				Intent i = new Intent(this, About.class);
				startActivity(i);
				return true;
			case R.id.exit:
				finish();
				return true;
			case R.id.reset:
				askIfResetHistory();
				return true;
		}
		return false;
	}

	protected void connectAndSync() {
		Log.d(TAG, "sync");

		try {
			// Recover email, password and sessiontoken from preferences
			String email = Preferences.getToodledoUsername(getApplicationContext());
			String password = Preferences.getToodledoPassword(getApplicationContext());
			String sessionToken = privatePrefs.getStringValue(PrivatePrefs.TOODLEDO_TOKEN, null);

			// Connect with toodledo service
			JkToodledo jkToodledo = JkToodledo.getObject(email, password, sessionToken);
			jkToodledo.connect();

			// Save toodledo token in android private preferences
			privatePrefs.savePrivatePref(PrivatePrefs.TOODLEDO_TOKEN, jkToodledo.getSessionToken());

			// Get all tasks from toodledo service
			List<Todo> todos = jkToodledo.getTodos();
			resetTasks();
			JkTasks.getObject().setAllTasks(todos);

			// Remove old tasks and insert new tasks in database
			tasks.clearTasks();
			tasks.addTasks(todos);

			Toast.makeText(Main.this, R.string.sync_done, Toast.LENGTH_SHORT).show();

		} catch (SettingsConfigurationException e) {
			Toast.makeText(Main.this, R.string.settings_error + e.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (ToodledoConnectionException e) {
			Toast.makeText(Main.this, R.string.connection_error + e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	private void askIfFinishSelectedTasks() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this).setTitle(R.string.finish_tasks_question)
				.setMessage("Are you sure?").setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						finishSelectedTasks();
					}
				}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void askIfClearSelectedTasks() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this).setTitle(R.string.clear_tasks_question)
				.setMessage(R.string.are_you_sure)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						clearSelectedTasks();
					}
				}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void askIfStopTimer() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this).setTitle(R.string.stop_timer_question)
				.setMessage(R.string.are_you_sure)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						stopTimer();
					}
				}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void askIfSync() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
				.setTitle(R.string.toodledo_sync_question).setMessage(R.string.are_you_sure)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						connectAndSync();
					}
				}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void askIfResetHistory() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
				.setTitle(R.string.reset_history_question).setMessage(R.string.are_you_sure)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						resetHistory();
					}
				}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void clearSelectedTasks() {
		JkTasks.getObject().setAllTasks(JkTasks.getObject().getAllTasks());
		populateTodoList();
	}

	private void finishSelectedTasks() {
		Log.d(TAG, "finishSelectedTasks");

		String email, password;

		try {
			email = Preferences.getToodledoUsername(getApplicationContext());
			password = Preferences.getToodledoPassword(getApplicationContext());

			String sessionToken = privatePrefs.getStringValue(PrivatePrefs.TOODLEDO_TOKEN, null);

			JkToodledo jkToodledo = JkToodledo.getObject(email, password, sessionToken);
			jkToodledo.finishSelectedTodos(JkTasks.getObject().getSelectedList());

			JkTasks.getObject().removeSelectedItems();
			populateTodoList();

			Toast.makeText(Main.this, R.string.task_finish, Toast.LENGTH_SHORT).show();

		} catch (SettingsConfigurationException ex) {
			Toast.makeText(Main.this, R.string.settings_error + ex.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (Exception ex) {
			Toast.makeText(Main.this, R.string.unhandled_error + ex.getMessage(), Toast.LENGTH_SHORT).show();
		}

	}

	private void resetTasks() {
		Log.d(TAG, "resetTasks");
		JkTasks.getObject().initAllTasks();
		populateTodoList();
	}

	private void actionStartOrStop() {
		Log.d(TAG, "action");

		if (!jkTimer.isRunning()) {
			startTimer();
		} else {
			askIfStopTimer();
		}
	}

	private void startTimer() {
		Log.d(TAG, "startTimer " + pomodoro.isPomodoro());

		int minutes = 0;

		addNotification(true);
		actionButton.setImageResource(R.drawable.stop);

		if (pomodoro.isPomodoro()) {
			minutes = Preferences.getPomodoroDuration(getApplicationContext());
		} else {
			int numPomodorosUntilLongRest = Preferences.getNumPomodorosUntilLongRest(getApplicationContext());
			int pomodorosToday = history.getTotalToday();

			if (pomodoro.isLongRest(pomodorosToday, numPomodorosUntilLongRest))
				minutes = Preferences.getLongRestDuration(getApplicationContext());
			else
				minutes = Preferences.getRestDuration(getApplicationContext());
		}

		initializeProgressBar(TimeUtils.minutesToSeconds(minutes));
		jkTimer.start(this, minutes);
	}

	private void stopTimer() {
		Log.d(TAG, "stopTimer");

		actionButton.setImageResource(R.drawable.play);

		initializeTimeToFinish();
		resetProgressBar();
		clearAllNotification();

		// Initialize isPomodoro
		pomodoro.setIsPomodoro(true);
		jkTimer.stop();
	}

	private void addNotification(boolean start) {
		Log.d(TAG, "addNotification");

		String message1, message2;

		if (start) {
			message1 = "Start";
			message2 = "Starting!";
		} else {
			message1 = "Finish";
			message2 = "Time Finish!";
		}

		Notification notification = new Notification(R.drawable.pomotodo, message1, System.currentTimeMillis());

		Intent intent = new Intent(this, Main.class);

		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

		notification.flags = Notification.FLAG_ONGOING_EVENT;
		notification.setLatestEventInfo(this, "PomoTodo", message2, pendingIntent);

		notificationMgr.notify(NOTIFICATION_ID, notification);
	}

	private void clearAllNotification() {
		Log.d(TAG, "clearAllNotification");

		notificationMgr.cancelAll();
	}

	private void resetProgressBar() {
		Log.d(TAG, "resetProgressBar");

		myProgressBar.setProgress(0);
	}

	private void initializeProgressBar(int seconds) {
		Log.d(TAG, "initializeProgressBar");

		// Set progressBar
		myProgressBar.setMax(seconds);
		myProgressBar.setProgress(seconds);
	}

	public void updateTimeAndProgressBar(int secondsUntilFinished) {
		// Log.d(TAG, "updateTimeAndProgressBar");
		if (Preferences.isTicTac(this))
			MusicManager.getObject().play(this);
		timeLeft.setText(TimeUtils.secondsToMinutesAndSeconds(secondsUntilFinished));
		myProgressBar.setProgress(secondsUntilFinished);
	}

	private void initializeTimeToFinish() {
		Log.d(TAG, "initializeTimeToFinish");

		timeLeft.setText("" + Preferences.getPomodoroDuration(getApplicationContext()));
	}

	public void onTimeFinish() {
		Log.d(TAG, "onTimeFinish");

		int message;

		addNotification(false);

		playSoundAndVibrate();

		if (pomodoro.isPomodoro()) {

			history.addPomodoro(Preferences.getPomodoroDuration(getApplicationContext()));

			setHistory();

			int pomodorosUntilLongRest = Preferences.getNumPomodorosUntilLongRest(getApplicationContext());
			int pomodorosToday = history.getTotalToday();

			if (pomodoro.isLongRest(pomodorosToday, pomodorosUntilLongRest))
				message = R.string.take_a_long_rest;
			else
				message = R.string.take_a_rest;
		} else
			message = R.string.another_pomodoro;

		showDialogOnEndTime(message);
	}

	private void showDialogOnEndTime(int message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this).setTitle(R.string.what_do_you_want)
				.setMessage(message).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						pomodoro.setIsPomodoro(!pomodoro.isPomodoro());
						startTimer();
					}
				}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						stopTimer();
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void playSoundAndVibrate() {

		if (Preferences.isRing(getApplicationContext())) {
			// Create a new MediaPlayer to play this sound
			MediaPlayer mp = MediaPlayer.create(this, R.raw.ring);
			mp.start();
		}
		if (Preferences.isVibrate(getApplicationContext())) {
			Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(TimeUtils.MILISECONDS);
		}
	}

	private void setHistory() {
		pomodoro = new Pomodoro();

		historyToday.setText(new Integer(history.getTotalToday()).toString());
		historyMonth.setText(new Integer(history.getTotalMonth()).toString());
		historyYear.setText(new Integer(history.getTotalYear()).toString());
		historyTotal.setText(new Integer(history.getTotal()).toString());
		historyRecord.setText(new Integer(history.getRecord()).toString());
		// historyRecord.setText(new Integer(TimeUtils.getWeek()).toString());
	}

	private void resetHistory() {
		history.resetHistory();
		setHistory();
	}
}