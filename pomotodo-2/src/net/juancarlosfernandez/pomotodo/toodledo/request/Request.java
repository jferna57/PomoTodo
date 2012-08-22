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
import net.juancarlosfernandez.pomotodo.toodledo.response.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class Request {

    private final String TAG = this.getClass().getName();
    private String authToken = null;
    protected String url = null;
    protected String xmlResponse = null;

    public abstract Response getResponse();

    public void exec() {

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        try {
            this.url = encode(this.url);
            response = httpclient.execute(new HttpGet(url));
            HttpEntity resEntityGet = response.getEntity();
            if (resEntityGet != null) {
                this.xmlResponse = EntityUtils.toString(resEntityGet, "UTF-8");
            }
        } catch (ClientProtocolException e) {
            Log.d(TAG, "Client Protocol Exception " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "IOException " + e.getMessage());
        }
    }

    private String encode(String url2) {

        String result = null;
        try {
            URI uri = new URI(url2);
            result = uri.toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
