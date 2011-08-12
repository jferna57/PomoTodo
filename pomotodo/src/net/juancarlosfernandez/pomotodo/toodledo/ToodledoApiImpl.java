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

import java.util.List;

import android.util.Log;

import net.juancarlosfernandez.pomotodo.toodledo.data.AccountInfo;
import net.juancarlosfernandez.pomotodo.toodledo.data.Todo;
import net.juancarlosfernandez.pomotodo.toodledo.exception.IncorrectUserPasswordException;
import net.juancarlosfernandez.pomotodo.toodledo.exception.InvalidSessionKeyException;
import net.juancarlosfernandez.pomotodo.toodledo.exception.MissingPasswordException;
import net.juancarlosfernandez.pomotodo.toodledo.exception.ToodledoApiException;
import net.juancarlosfernandez.pomotodo.toodledo.request.AuthorizeRequest;
import net.juancarlosfernandez.pomotodo.toodledo.request.FinishTodoRequest;
import net.juancarlosfernandez.pomotodo.toodledo.request.GetAccountInfoRequest;
import net.juancarlosfernandez.pomotodo.toodledo.request.GetTodosRequest;
import net.juancarlosfernandez.pomotodo.toodledo.request.GetUserIdRequest;
import net.juancarlosfernandez.pomotodo.toodledo.request.Request;
import net.juancarlosfernandez.pomotodo.toodledo.response.AuthorizeResponse;
import net.juancarlosfernandez.pomotodo.toodledo.response.GetAccountInfoResponse;
import net.juancarlosfernandez.pomotodo.toodledo.response.GetTodosResponse;
import net.juancarlosfernandez.pomotodo.toodledo.response.GetUserIdResponse;
import net.juancarlosfernandez.pomotodo.toodledo.util.AuthToken;
import net.juancarlosfernandez.pomotodo.toodledo.xml.AccountInfoParser;
import net.juancarlosfernandez.pomotodo.toodledo.xml.AuthorizeParser;
import net.juancarlosfernandez.pomotodo.toodledo.xml.GetTodosParser;
import net.juancarlosfernandez.pomotodo.toodledo.xml.GetUserIdParser;

public class ToodledoApiImpl implements ToodledoApi {
	
	private final String TAG = this.getClass().getName();

	public Todo getTodo(AuthToken auth, int id) throws ToodledoApiException {
		Todo filter = new Todo();
		filter.setId(id);
		List<Todo> res = getTodosList(auth, filter);
		if (res != null && res.size() > 0) {
			return res.get(0);
		} else {
			return null;
		}
	}

	public List<Todo> getTodosList(AuthToken auth) throws ToodledoApiException {
		return getTodosList(auth, null);
	}

	public List<Todo> getTodosList(AuthToken auth, Todo filter) throws ToodledoApiException {
		Request getTodosRequest = new GetTodosRequest(auth, filter);
		GetTodosResponse response = (GetTodosResponse) getTodosRequest.getResponse();

		return new GetTodosParser(response.getXmlResponseContent()).getTodos();
	}

	public AuthToken initialize(String username, String password, String sessionToken) throws ToodledoApiException {
		Log.d(TAG, "initialize");
		
		AuthToken authToken = null;

		if (sessionToken == null) {
			Log.d(TAG, "---> NEW token");
			Request initReq = new AuthorizeRequest(username);
			// response gives back the token, now create the AuthToken
			AuthorizeResponse response = (AuthorizeResponse) initReq.getResponse();
			sessionToken = new AuthorizeParser(response.getXmlResponseContent()).getToken();
		} else {
			Log.d(TAG, "---> TOKEN Reused " + sessionToken);
		}

		authToken = new AuthToken(password, sessionToken);
		
		return authToken;
	}

	public boolean finishTodo(AuthToken auth, Todo newOne) throws ToodledoApiException {
		FinishTodoRequest modifyRequest = new FinishTodoRequest(auth, newOne);
		modifyRequest.getResponse();
		return true;
	}

	public AccountInfo getAccountInfo(AuthToken auth) throws ToodledoApiException, InvalidSessionKeyException {

		GetAccountInfoRequest request = new GetAccountInfoRequest(auth);
		GetAccountInfoResponse resp = (GetAccountInfoResponse) request.getResponse();

		return new AccountInfoParser(resp.getXmlResponseContent()).getAccountInfo();
	}

	public String getUserId(String mail, String password) throws ToodledoApiException, IncorrectUserPasswordException,
			MissingPasswordException {

		GetUserIdRequest request = new GetUserIdRequest(mail, password);
		GetUserIdResponse response = (GetUserIdResponse) request.getResponse();
		
		GetUserIdParser parser = new GetUserIdParser(response.getXmlResponseContent());
		return parser.getUserId();
	}

}
