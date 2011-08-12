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
