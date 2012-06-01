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

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import net.juancarlosfernandez.pomotodo.toodledo.data.Constants;
import net.juancarlosfernandez.pomotodo.toodledo.response.AuthorizeResponse;
import net.juancarlosfernandez.pomotodo.toodledo.response.Response;
import net.juancarlosfernandez.pomotodo.util.JkUtils;

public class AuthorizeRequest extends Request {

	private static final String URL = "https://api.toodledo.com/2/account/token.php?userid=";

	public AuthorizeRequest(String userId) {
		super();
		url = URL + userId;

		url = url + ";appid=" + Constants.APP_ID;
		url = url + ";device=" + Constants.APP_DEVICE;

		try {
			url = url + ";sig=" + JkUtils.MD5(userId + Constants.APP_TOKEN) + ";f=xml";
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Response getResponse() {
		this.exec();
		AuthorizeResponse response = new AuthorizeResponse(this.xmlResponse);
		return response;
	}

}
