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

import net.juancarlosfernandez.pomotodo.toodledo.data.Todo;
import net.juancarlosfernandez.pomotodo.toodledo.exception.ToodledoApiException;
import net.juancarlosfernandez.pomotodo.toodledo.response.GetTodosResponse;
import net.juancarlosfernandez.pomotodo.toodledo.response.Response;
import net.juancarlosfernandez.pomotodo.toodledo.util.AuthToken;

public class GetTodosRequest extends Request {
	
	private static final String URL = "http://api.toodledo.com/2/tasks/get.php?key=";

	public GetTodosRequest(AuthToken token, Todo filter) throws ToodledoApiException {
		super();
		this.url = URL + token.getKey() + ";comp=0;f=xml";
		// TODO: Add filter options
		if (filter != null) {
			
		}
	}

	@Override
	public Response getResponse() {
		this.exec();
		GetTodosResponse response = new GetTodosResponse(this.xmlResponse);
		return response;
	}
	
}
