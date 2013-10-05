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
package net.juancarlosfernandez.pomotodo.toodledo.data;

/**
 * The first thing you should do each time you sync with Toodledo is to request
 * the user's account information. This will give you useful information about
 * the user, including various timestamps that you can use to determine if any
 * further action is necessary. If the timestamps have changed, then the user's
 * account hasn't changed and you do not need to sync antything.
 * <p/>
 * For more information : http://api.toodledo.com/2/account/doc_info.php
 *
 * @author juancarlosf
 */
public class AccountInfo {

    private String userId;
    private String alias;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
