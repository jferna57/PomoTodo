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

import android.util.Log;

import net.juancarlosfernandez.pomotodo.exception.ToodledoConnectionException;
import net.juancarlosfernandez.pomotodo.toodledo.ToodledoApi;
import net.juancarlosfernandez.pomotodo.toodledo.ToodledoApiImpl;
import net.juancarlosfernandez.pomotodo.toodledo.data.AccountInfo;
import net.juancarlosfernandez.pomotodo.toodledo.data.Todo;
import net.juancarlosfernandez.pomotodo.toodledo.exception.IncorrectUserPasswordException;
import net.juancarlosfernandez.pomotodo.toodledo.exception.InvalidSessionKeyException;
import net.juancarlosfernandez.pomotodo.toodledo.exception.MissingPasswordException;
import net.juancarlosfernandez.pomotodo.toodledo.exception.ToodledoApiException;
import net.juancarlosfernandez.pomotodo.toodledo.util.AuthToken;
import net.juancarlosfernandez.pomotodo.toodledo.util.TdDateTime;

import java.util.List;

public class JkToodledo {

    private final String TAG = this.getClass().getName();

    private String email;
    private String password;
    private String userId;
    private String sessionToken;
    private AuthToken authToken;
    private static ToodledoApi tdApi = new ToodledoApiImpl();

    private boolean isConnected = false;

    // Singleton object
    private static JkToodledo jkToodledo;

    private JkToodledo(String email, String password, String sessionToken) {

        this.email = email;
        this.password = password;
        this.sessionToken = sessionToken;
    }

    public static synchronized JkToodledo getObject(String email, String password, String sessionToken) {

        if (jkToodledo == null) {
            jkToodledo = new JkToodledo(email, password, sessionToken);
        } else {
            // If toodledo settings changes
            if (!jkToodledo.email.equals(email) || !jkToodledo.password.equals(password))
                jkToodledo = new JkToodledo(email, password, null);
        }
        return jkToodledo;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public void connect() throws ToodledoConnectionException {
        Log.d(TAG, "connect with session Token " + sessionToken);
        try {
            userId = tdApi.getUserId(email, password);
            authToken = tdApi.initialize(userId, password, sessionToken);

            if (!isSessionTokenValid()) {
                Log.d(TAG, "----->>>>>> TOKEN not Valid");
                authToken = tdApi.initialize(userId, password, null);
                sessionToken = authToken.getSessionToken();
                Log.d(TAG, "----->>>>>> NEW TOKEN " + sessionToken);
            }

            this.sessionToken = authToken.getSessionToken();
            isConnected = true;

        } catch (ToodledoApiException e) {
            throw new ToodledoConnectionException(e.getMessage());
        } catch (IncorrectUserPasswordException e) {
            throw new ToodledoConnectionException(e.getMessage());
        } catch (MissingPasswordException e) {
            throw new ToodledoConnectionException(e.getMessage());
        }
    }

    private boolean isSessionTokenValid() {

        if (authToken == null) {
            Log.d(TAG, "----->>>>> authToken is null");
            return false;
        }

        try {
            AccountInfo accountInfo = tdApi.getAccountInfo(authToken);

            Log.d(TAG, "Acount Info " + accountInfo.getAlias());
            Log.d(TAG, "Acount Info " + accountInfo.getUserId());

        } catch (ToodledoApiException e) {
            Log.d(TAG, "----->>>>ToodledoApiException " + e.getMessage());
            return false;
        } catch (InvalidSessionKeyException e) {
            Log.d(TAG, "----->>>>InvalidSessionKeyException " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Gets the list of all todos and prints their id and their title.
     */
    public List<Todo> getTodos() {
        List<Todo> todoList = null;

        try {
            todoList = tdApi.getTodosList(authToken);
        } catch (ToodledoApiException e) {
            Log.d(TAG, "ERROR !! getTodos " + e.getMessage());
        }
        return todoList;
    }

    /**
     * Finish tasks selected.
     *
     * @param todos List of todos
     * @throws ToodledoApiException
     */
    public void finishSelectedTodos(List<Todo> todos) throws ToodledoApiException {

        if (todos != null) {

            for (Todo _tmp : todos) {
                _tmp.setDueTime(new TdDateTime());
                tdApi.finishTodo(authToken, _tmp);
            }
        }
    }

    public String getSessionToken() {
        if (authToken != null)
            return authToken.getSessionToken();
        else
            return null;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

}
