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

import net.juancarlosfernandez.pomotodo.toodledo.data.Constants;
import net.juancarlosfernandez.pomotodo.toodledo.exception.ToodledoApiException;
import net.juancarlosfernandez.pomotodo.toodledo.response.GetUserIdResponse;
import net.juancarlosfernandez.pomotodo.toodledo.response.Response;
import net.juancarlosfernandez.pomotodo.util.JkUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class GetUserIdRequest extends Request {

    private final String TAG = this.getClass().getName();

    private static final String URL = "http://api.toodledo.com/2/account/lookup.php?f=xml;appid=";

    public GetUserIdRequest(String email, String password) throws ToodledoApiException {
        super();
        try {
            String sig = JkUtils.MD5(email + Constants.APP_TOKEN);
            this.url = URL + Constants.APP_ID + ";sig=" + sig + ";email=" + email + ";pass=" + password;

        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, "Problem!! NoSuchAlgorithException");
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "Problem!! UnsupportedEncodingException");
        }

    }

    @Override
    public Response getResponse() {
        this.exec();
        GetUserIdResponse response = new GetUserIdResponse(this.xmlResponse);
        return response;
    }

}
