package net.juancarlosfernandez.pomotodo.toodledo.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TdDate {
	
	protected DateTime dt; 
	
	public TdDate() {
		dt = new DateTime();
	}
	
	public TdDate(String date) {
	  try {
	    dt = new DateTime(date);
	  } catch (Exception e) { dt = null; }
	}
	
	public TdDate(int year, int month, int day) {
	  try {
	    dt = new DateTime(year, month, day, 0,0,0,0);
	  } catch (Exception e) { dt = null; }
	}
	
	public String toString() {
	  if (dt == null) return "";
		DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd");
		return dt.toString(fmt);
	}
}
