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
package net.juancarlosfernandez.pomotodo.toodledo;

import net.juancarlosfernandez.pomotodo.toodledo.data.AccountInfo;
import net.juancarlosfernandez.pomotodo.toodledo.data.Todo;
import net.juancarlosfernandez.pomotodo.toodledo.exception.IncorrectUserPasswordException;
import net.juancarlosfernandez.pomotodo.toodledo.exception.InvalidSessionKeyException;
import net.juancarlosfernandez.pomotodo.toodledo.exception.MissingPasswordException;
import net.juancarlosfernandez.pomotodo.toodledo.exception.ToodledoApiException;
import net.juancarlosfernandez.pomotodo.toodledo.util.AuthToken;

import java.util.List;

/**
 * toodledo api implemented by this library.
 */
public interface ToodledoApi {


    AuthToken initialize(String username, String password, String key) throws ToodledoApiException;

    Todo getTodo(AuthToken auth, int id) throws ToodledoApiException;

    boolean finishTodo(AuthToken auth, Todo newOne) throws ToodledoApiException;

    public AccountInfo getAccountInfo(AuthToken auth) throws ToodledoApiException, InvalidSessionKeyException;

    List<Todo> getTodosList(AuthToken auth) throws ToodledoApiException;

    List<Todo> getTodosList(AuthToken auth, Todo filter) throws ToodledoApiException;

    String getUserId(String eMail, String password) throws ToodledoApiException,
            IncorrectUserPasswordException, MissingPasswordException;

}
