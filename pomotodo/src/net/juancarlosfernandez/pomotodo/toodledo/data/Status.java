package net.juancarlosfernandez.pomotodo.toodledo.data;

import java.util.HashMap;
import java.util.Map;

public enum Status {

	NONE(0), NEXT_ACTION(1), ACTIVE(2), PLANNING(3), DELEGATED(4), WAITING(5), HOLD(6), POSTPONED(7), SOMEDAY(8), CANCELED(
			9), REFERENCE(10);

	private final int number;

	Status(int number) {
		this.number = number;
	}

	public int getStatusAsInteger() {
		return this.number;
	}

	public static final Map<Integer, Status> ValueFromInt = new HashMap<Integer, Status>() {
		private static final long serialVersionUID = 1990783908808123714L;
		{
			put(0, NONE);
			put(1, NEXT_ACTION);
			put(2, ACTIVE);
			put(3, PLANNING);
			put(4, DELEGATED);
			put(5, WAITING);
			put(6, HOLD);
			put(7, POSTPONED);
			put(8, SOMEDAY);
			put(9, CANCELED);
			put(10, REFERENCE);
		}
	};
}
