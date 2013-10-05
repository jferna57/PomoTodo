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
package net.juancarlosfernandez.pomotodo.toodledo.request;

import android.util.Log;

import net.juancarlosfernandez.pomotodo.toodledo.data.Todo;
import net.juancarlosfernandez.pomotodo.toodledo.exception.ToodledoApiException;
import net.juancarlosfernandez.pomotodo.toodledo.response.Response;
import net.juancarlosfernandez.pomotodo.toodledo.util.AuthToken;

public class FinishTodoRequest extends Request {

    private static final String URL = "http://api.toodledo.com/2/tasks/edit.php?key=";

    private final String TAG = this.getClass().getName();

    /**
     * @param token
     * @param todo
     * @throws ToodledoApiException
     */
    public FinishTodoRequest(AuthToken token, Todo todo) throws ToodledoApiException {
        super();
        this.url = URL + token.getKey();
        StringBuffer buff = new StringBuffer();

        if (todo.getId() != -1) {
            // Add taskId
            buff.append(";tasks=%5B%7B");
            buff.append("%22id%22%3A%22" + todo.getId()).append("%22");

            // Add completed time
            long lnSystemTime = System.currentTimeMillis();
            buff.append("%2C%22completed%22%3A%22").append(lnSystemTime / 1000 + "%22");

            // Set xml format
            buff.append("%7D%5D;f=xml");

            this.url = this.url.concat(buff.toString());

            Log.d(TAG, this.url);
        } else {
            throw new ToodledoApiException("At least the todo must have the 'id' field.");
        }
    }

    @Override
    public Response getResponse() {
        this.exec();
        return null;
    }

}
