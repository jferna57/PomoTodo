package net.juancarlosfernandez.pomotodo.toodledo.util;

import net.juancarlosfernandez.pomotodo.util.JkUtils;
import org.joda.time.DateTime;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class AuthToken {

    private static final String appToken = "api4d7a6373ef109";

    private String sessionToken;
    private String key;
    private DateTime date;

    public AuthToken(String password, String sessionToken) {
        this.sessionToken = sessionToken;

        // get the key
        String total;

        try {
            total = JkUtils.MD5(password) + appToken + sessionToken;
            this.key = JkUtils.MD5(total);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        this.date = new DateTime().plusHours(4);
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getKey() {
        return key;
    }

    public DateTime getDate() {
        return date;
    }

    public int getRemainingTime() {
        return Math.max(0, this.date.getSecondOfDay() - new DateTime().getSecondOfDay());
    }
}
