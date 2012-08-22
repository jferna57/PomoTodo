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

import net.juancarlosfernandez.pomotodo.toodledo.data.Todo;

import java.util.List;

public class StringUtils {

    public static String[] todosToString(List<Todo> todoList) {

        String[] todoTitles = new String[todoList.size()];

        int i = 0;
        for (Todo _tmp : todoList) {
            todoTitles[i] = _tmp.getTitle();
            i++;
        }
        return todoTitles;
    }

}
