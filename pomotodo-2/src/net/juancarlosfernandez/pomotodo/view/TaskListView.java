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

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import net.juancarlosfernandez.pomotodo.R;
import net.juancarlosfernandez.pomotodo.db.DataBaseHelper;
import net.juancarlosfernandez.pomotodo.db.Task;
import net.juancarlosfernandez.pomotodo.toodledo.data.Todo;
import net.juancarlosfernandez.pomotodo.util.StringUtils;

import java.util.List;

/**
 * The Class TaskListView.
 */
public class TaskListView extends ListActivity {

    private Task task;
    List<Todo> notSelectedTasksList;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.task_list);

        task = new Task(new DataBaseHelper(this));

        notSelectedTasksList = task.getTasks(false);

        if (notSelectedTasksList != null) {
            String[] allTodoTitles = StringUtils.todosToString(notSelectedTasksList);
            setListAdapter(new ArrayAdapter<String>(this, R.layout.my_simple_list_item_multiple_choice, allTodoTitles));
        }
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        Todo clickedTask = notSelectedTasksList.get(position);
        task.changeClickedTask(clickedTask);
    }
}
