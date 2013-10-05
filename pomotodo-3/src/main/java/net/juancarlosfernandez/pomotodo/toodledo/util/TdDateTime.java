package net.juancarlosfernandez.pomotodo.toodledo.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TdDateTime extends TdDate {

    public TdDateTime() {
        super();
    }

    public TdDateTime(String dateTime) {
        DateTimeFormatter parser = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        try {
            this.dt = parser.parseDateTime(dateTime);
        } catch (Exception e) {
            this.dt = null;
        }

        try {
            if (this.dt == null) {
                this.dt = new DateTime(dateTime);
            }
        } catch (Exception e) {
            this.dt = null;
        }
    }

    public TdDateTime(int year, int month, int day, int hour, int minute, int second) {
        try {
            this.dt = new DateTime(year, month, day, hour, minute, second, 0);
        } catch (Exception e) {
            this.dt = null;
        }
    }

    public String toString() {
        if (this.dt == null)
            return "";
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
        return dt.toString(fmt);
    }
}
