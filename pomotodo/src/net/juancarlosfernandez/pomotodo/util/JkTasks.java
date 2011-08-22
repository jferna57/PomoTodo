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
package net.juancarlosfernandez.pomotodo.util;

import java.util.ArrayList;
import java.util.List;

import net.juancarlosfernandez.pomotodo.toodledo.data.Todo;

public class JkTasks {

	private List<Todo>		allTodoList;
	private List<Boolean>	selectedList;

	// Singleton object
	private static JkTasks	todoTasks;

	private JkTasks() {
		initAllTasks();
	}

	public static synchronized JkTasks getObject() {

		if (todoTasks == null)
			todoTasks = new JkTasks();
		return todoTasks;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public void initAllTasks() {
		this.allTodoList = new ArrayList<Todo>();
		this.selectedList = new ArrayList<Boolean>();
	}

	public List<Todo> getAllTasks() {
		return allTodoList;
	}

	public void setAllTasks(List<Todo> todoList) {
		initAllTasks();

		this.allTodoList = todoList;
		setSelectedTasks(todoList);
	}

	private void setSelectedTasks(List<Todo> todoList) {
		selectedList.clear();
		for (int i = 0; i < todoList.size(); i++)
			this.selectedList.add(false);
	}

	public void changeClickedTask(int pos) {
		changeSelectedPosition(pos);
	}

	public void changeClickedTask(Todo clickedTask) {
		changeSelectedPosition(findTaskPosition(clickedTask));
	}

	private void changeSelectedPosition(int position) {
		selectedList.set(position, !selectedList.get(position));
	}

	private int findTaskPosition(Todo clickedTask) {
		int result = -1;
		boolean found = false;

		for (int i = 0; i < allTodoList.size() && !found; i++) {
			if (allTodoList.get(i).getId() == clickedTask.getId()) {
				result = i;
				found = true;
			}
		}
		return result;
	}

	public String[] getSelectedTasks() {
		List<String> tmpList = new ArrayList<String>();

		for (int i = 0; i < selectedList.size(); i++) {
			if (selectedList.get(i))
				tmpList.add(allTodoList.get(i).getTitle());
		}

		String[] result = new String[tmpList.size()];

		for (int i = 0; i < result.length; i++) {
			result[i] = tmpList.get(i);
		}
		return result;
	}

	public CharSequence[] getAllTasksNames() {

		CharSequence[] result = new CharSequence[allTodoList.size()];

		for (int i = 0; i < allTodoList.size(); i++) {
			result[i] = allTodoList.get(i).getTitle();
		}
		return result;
	}

	public List<Todo> getNotSelectedList() {
		List<Todo> result = new ArrayList<Todo>();

		for (int i = 0; i < selectedList.size(); i++) {
			if (!selectedList.get(i))
				result.add(allTodoList.get(i));
		}
		return result;
	}

	public List<Todo> getSelectedList() {
		List<Todo> result = new ArrayList<Todo>();

		for (int i = 0; i < selectedList.size(); i++) {
			if (selectedList.get(i))
				result.add(allTodoList.get(i));
		}
		return result;
	}

	public void removeItem(int position) {
		selectedList.remove(position);
		allTodoList.remove(position);
	}

	public void removeSelectedItems() {

		for (int i = 0; i < selectedList.size(); i++)
			if (selectedList.get(i))
				removeItem(i);
	}

	public static String[] todosToString(List<Todo> todoList) {

		String[] todoTitles = new String[todoList.size()];

		int i = 0;
		for (Todo _tmp : todoList) {
			todoTitles[i] = _tmp.getTitle();
			i++;
		}
		return todoTitles;
	}

	public boolean isAllTodoListEmpty() {
		if (allTodoList.isEmpty())
			return true;
		else
			return false;
	}

	public boolean isSelectedTaskEmpty() {
		if (selectedList.isEmpty())
			return true;
		else
			return false;
	}
}