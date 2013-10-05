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

import java.util.HashMap;
import java.util.Map;

public enum Priority {
    NEGATIVE(-1), LOW(0), MEDIUM(1), HIGH(2), TOP(3);

    private final int number;

    Priority(int number) {
        this.number = number;
    }

    public int getPriorityAsInt() {
        return number;
    }

    public static final Map<Integer, Priority> ValueFromInt = new HashMap<Integer, Priority>() {

        private static final long serialVersionUID = 7422970606809824210L;

        {
            put(-1, NEGATIVE);
            put(0, LOW);
            put(1, MEDIUM);
            put(2, HIGH);
            put(3, TOP);
        }
    };
}
