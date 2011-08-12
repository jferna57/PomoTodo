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

import net.juancarlosfernandez.pomotodo.R;
import net.juancarlosfernandez.pomotodo.exception.SettingsConfigurationException;
import net.juancarlosfernandez.pomotodo.exception.ToodledoConnectionException;
import net.juancarlosfernandez.pomotodo.util.*;
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

	private final String TAG = this.getClass().getName();

	private final int NOTIFICATION_ID = 2;
	private NotificationManager notificationMgr;
	private JkTimer jkTimer = JkTimer.getObject();
	private PrivatePrefs privatePrefs;
	private Pomodoro pomodoro;

	private ImageButton actionButton;
	private TextView timeLeft;
	private ProgressBar myProgressBar;
	private TextView numPomodoroToday;

	private ListView todoList;
	
	/**
	 * The activity is being created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Initialize fields
		initButtons();
		initFields();
		initializeTimeToFinish();

		// Notification Manager
		notificationMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		privatePrefs = PrivatePrefs.getObject(getPreferences(MODE_PRIVATE));

		// Update History
		initHistory();
		setHistory();

        /* Populate todo List */
		populateTodoList();
	}
	
	/**
	 * The activity is about to become visible.
	 */
	@Override
	protected void onStart(){
		Log.d(TAG,"onStart");
		super.onStart();
	}
	/**
	 * Another activity is taking focus (this activity is about to be "paused")
	 */
	@Override
	protected void onPause() {
		Log.d(TAG, "onPause");

        MusicManager.getObject().silent();

		super.onPause();

		PrivatePrefs jkPrivatePrefs = PrivatePrefs.getObject(getPreferences(MODE_PRIVATE));
		jkPrivatePrefs.savePomodoro(pomodoro);
	}
	/**
	 * The activity has become visible (it is now "resumed").
	 */
	@Override
	protected void onResume() {
		Log.d(TAG, "onResume");

        MusicManager.getObject().resume();

		super.onResume();
		populateTodoList();
	}
	
	/**
	 * The activity is no longer visible (it is now "stopped")
	 */
	@Override
	protected void onStop() {
		Log.d(TAG, "onStop");
		super.onStop();

		//stopTimer();
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
		String[] selectedTasks = TodoTasks.getObject().getSelectedTasks();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.task_entry, selectedTasks);
		todoList.setAdapter(adapter);
	}

	private void initFields() {
		timeLeft = (TextView) findViewById(R.id.time_left);
		myProgressBar = (ProgressBar) findViewById(R.id.task_progress_bar);
		numPomodoroToday = (TextView) findViewById(R.id.num_pomodoro_today);
		todoList = (ListView) findViewById(R.id.todo_list);
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
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	public void onClick(View v) {
		Log.d(TAG, "onClick");

		switch (v.getId()) {
			case R.id.sync_button :
				askIfSync();
				break;
			case R.id.action_button :
				actionStartOrStop();
				break;
			case R.id.clear_selected_tasks_button :
				if ( todoList.getCount() > 0 )
					askIfClearSelectedTasks();
				else
					Toast.makeText(Main.this,"No tasks selected!", Toast.LENGTH_SHORT).show();
				break;
			case R.id.finish_tasks_button :
				if ( todoList.getCount() > 0 )
					askIfFinishSelectedTasks();
				else
					Toast.makeText(Main.this,"No tasks selected!", Toast.LENGTH_SHORT).show();
				
				break;
			case R.id.select_task_button :
				if (! TodoTasks.getObject().isAllTodoListEmpty()){
					Intent i = new Intent(this, TaskListView.class);
					startActivity(i);
				} else {
					Toast.makeText(Main.this, "No task!", Toast.LENGTH_SHORT).show();
				}
				break;
			default :
				break;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected");

		switch (item.getItemId()) {
			case R.id.settings :
				startActivity(new Intent(this, Preferences.class));
				return true;
			case R.id.about :
				Intent i = new Intent(this, About.class);
				startActivity(i);
				return true;
			case R.id.exit :
				finish();
				return true;
			case R.id.reset :
				askIfResetHistory();
				return true;
		}
		return false;
	}

	protected void sync() {
		Log.d(TAG, "sync");

		String email, password;

		resetTasks();

		try {
			email = Preferences.getToodledoUsername(getApplicationContext());

			password = Preferences.getToodledoPassword(getApplicationContext());

			String sessionToken = privatePrefs.getStringValue(PrivatePrefs.TOODLEDO_TOKEN, null);

			JkToodledo jkToodledo = JkToodledo.getObject(email, password, sessionToken);
			jkToodledo.connect();

			privatePrefs.savePrivatePref(PrivatePrefs.TOODLEDO_TOKEN, jkToodledo.getSessionToken());

			TodoTasks.getObject().setAllTodoTasks(jkToodledo.getTodos());

			Toast.makeText(Main.this, "Sync Done!", Toast.LENGTH_SHORT).show();
		} catch (SettingsConfigurationException e) {
			Toast.makeText(Main.this, "Settings Error!" + e.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (ToodledoConnectionException e) {
			Toast.makeText(Main.this, "Connection Error!" + e.getMessage(), Toast.LENGTH_SHORT).show();
		}

	}

	private void askIfFinishSelectedTasks() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this).setTitle(R.string.finish_tasks_question)
				.setMessage("Are you sure?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						finishSelectedTasks();
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	private void askIfClearSelectedTasks() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this).setTitle(R.string.clear_tasks_question)
				.setMessage("Are you sure?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						clearSelectedTasks();
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	private void askIfStopTimer() {
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this).setTitle(R.string.stop_timer_question)
				.setMessage("Are you sure?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						stopTimer();
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void askIfSync() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
				.setTitle(R.string.toodledo_sync_question).setMessage("Are you sure?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						sync();
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void askIfResetHistory() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
				.setTitle(R.string.reset_history_question).setMessage("Are you sure?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						resetPomodoros();
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void clearSelectedTasks() {
		TodoTasks.getObject().setAllTodoTasks(TodoTasks.getObject().getAllTodoTasks());
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
			jkToodledo.finishSelectedTodos(TodoTasks.getObject().getSelectedList());

			TodoTasks.getObject().removeSelectedItems();
			populateTodoList();

			Toast.makeText(Main.this, "Tasks finished!", Toast.LENGTH_SHORT).show();

		} catch (SettingsConfigurationException ex) {
			Toast.makeText(Main.this, "Settings Error " + ex.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (Exception ex) {
			Toast.makeText(Main.this, "Unhandled Error " + ex.getMessage(), Toast.LENGTH_SHORT).show();
		}

	}

	private void resetTasks() {
		TodoTasks.getObject().initAllTodoList();
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
			// iconAction.setImageResource(R.drawable.play);

		} else {
			if (pomodoro.isLongRest(Preferences.getNumPomodorosUntilLongRest(getApplicationContext()))) {
				minutes = Preferences.getLongRestDuration(getApplicationContext());
				// iconAction.setImageResource(R.drawable.long_break);
			} else {
				minutes = Preferences.getRestDuration(getApplicationContext());
				// iconAction.setImageResource(R.drawable.pause);
			}
        }

		initializeProgressBar(TimeUtils.minutesToSeconds(minutes));
		jkTimer.start(this, minutes);
		playTicTac();
	}

	private void playTicTac() {
		Log.d(TAG, "stopTimer");
		// TODO Auto-generated method stub

	}

	private void stopTimer() {
		Log.d(TAG, "stopTimer");

		// iconAction.setImageResource(R.drawable.stop);
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

	/**
	 * Set progress bar to 0
	 */
	private void resetProgressBar() {
		Log.d(TAG, "resetProgressBar");

		myProgressBar.setProgress(0);
	}

	/**
	 * Initialize progress bar values. Same values to max and progress
	 * 
	 * @param seconds
	 *            : Initial seconds value
	 */
	private void initializeProgressBar(int seconds) {
		Log.d(TAG, "initializeProgressBar");

		// Set progressBar
		myProgressBar.setMax(seconds);
		myProgressBar.setProgress(seconds);
	}

	/**
	 * Update progress
	 * 
	 * @param secondsUntilFinished
	 *            : New seconds value
	 */
	public void updateTimeAndProgressBar(int secondsUntilFinished) {
		// Log.d(TAG, "updateTimeAndProgressBar");
        if(Preferences.isTicTac(this))
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
			pomodoro.pomodoroFinish();
			setHistory();
			
			if (pomodoro.isLongRest(Preferences.getNumPomodorosUntilLongRest(getApplicationContext())))
				message = R.string.take_a_long_rest;
			else
				message = R.string.take_a_rest;
		} else
			message = R.string.another_pomodoro;

		showDialogOnEndTime(message);
	}

	private void showDialogOnEndTime(int message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this).setTitle(R.string.what_do_you_want)
				.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						pomodoro.setIsPomodoro(!pomodoro.isPomodoro());
						startTimer();
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
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
		numPomodoroToday.setText("" + pomodoro.getPomodoroToday());
	}

	private void initHistory() {

		int numPomodorosToday = privatePrefs.getIntValue(PrivatePrefs.POMODOROS_TODAY, 0);
		int numPomodorosTotal = privatePrefs.getIntValue(PrivatePrefs.POMODOROS_TOTAL, 0);
		int numPomodorosRecord = privatePrefs.getIntValue(PrivatePrefs.POMODOROS_RECORD, 0);
		String lastPomodoroDay = privatePrefs.getStringValue(PrivatePrefs.POMODOROS_LASTDAY, "0");

		pomodoro = new Pomodoro(numPomodorosToday, numPomodorosTotal, numPomodorosRecord, lastPomodoroDay);
	}

	private void resetPomodoros() {
		pomodoro.resetHistory();
		privatePrefs.resetPrivatePrefs();
		setHistory();
	}
}