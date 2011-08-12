package net.juancarlosfernandez.pomotodo.toodledo.data;

import java.util.HashMap;
import java.util.Map;

public enum Repeat {
	NO_REPEAT(0), 
	WEEKLY(1), 
	MONTHLY(2), 
	YEARLY(3), 
	DAILY(4), 
	BIWEEKLY(5), 
	BIMONTHLY(6), 
	SEMIANNUALLY(7), 
	QUARTERLY(8), 
	WITH_PARENT(9);

	private final int number;
	
	Repeat(int number) {
		this.number = number;
	}
	public int getRepeatAsInteger() {
		return this.number;
	}

	public static Map<Integer, Repeat> ValueFromInt = new HashMap<Integer, Repeat>() {
		private static final long serialVersionUID = -8227627631185685414L;
		{
			put(0, NO_REPEAT);
			put(1, WEEKLY);
			put(2, MONTHLY);
			put(3, YEARLY);
			put(4, DAILY);
			put(5, BIWEEKLY);
			put(6, BIMONTHLY);
			put(7, SEMIANNUALLY);
			put(8, QUARTERLY);
			put(9, WITH_PARENT);
		}
	};

}
