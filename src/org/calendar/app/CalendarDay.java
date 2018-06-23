package org.calendar.app;

/**
 * Calendar Day class
 * <P>
 * Restricted to package level access only
 * 
 * @author gv
 * 
 */
class CalendarDay {
	public static final CalendarDay EMPTY_DAY = new CalendarDay(-1);
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	private int day = -1;
	private boolean isToday = false;
	private boolean isNotCurrentMonth = false;

	CalendarDay(int day) {
		this(day, false);
	}

	CalendarDay(int day, boolean isToday) {
		this.day = day;
		this.isToday = isToday;
	}

	CalendarDay(int day, boolean isToday, boolean isNotCurrentMonth) {
		this(day, isToday);
		this.isNotCurrentMonth = isNotCurrentMonth;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (day == -1) {
			return EMPTY_STRING;
		}
		return (day < 10) ? "0" + day : String.valueOf(day); //$NON-NLS-1$
	}

	public void setDay(int day) {
		this.day = day;
	}

	public static CalendarDay newInstance(int day) {
		return new CalendarDay(day);
	}

	public boolean isToday() {
		return isToday;
	}

	public boolean isNotCurrentMonth() {
		return isNotCurrentMonth;
	}

	public int getDay() {
		return day;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object that) {
		if (!(that instanceof CalendarDay)) {
			return false;
		}
		return ((CalendarDay) that).getDay() == this.getDay();
	}
}
