package org.calendar.app;

import java.util.Arrays;

/**
 * One week of a calendar
 * 
 * @author gv
 * 
 */
class CalendarWeek {
	private static final int NUM_DAYS = 7;
	private CalendarDay[] days = new CalendarDay[NUM_DAYS];

	CalendarWeek() {
		initializeEmptyDays();
	}

	void initializeEmptyDays() {
		for (int i = 0; i < NUM_DAYS; i++) {
			days[i] = CalendarDay.EMPTY_DAY;
		}
	}

	void initializePreviousMonth(int startDay, int date, int maxDays, int today, boolean flag) {
		for (int i = startDay; i > -1; i--) {
			days[i] = new CalendarDay(date, (date == today) && flag, true);
			date--;
		}

	}

	boolean notCurrMonthFlag = false;

	void initializeDays(int startDay, int date, int maxDays, int today, boolean flag) {
		initializeEmptyDays();
		for (int i = startDay; i < NUM_DAYS; i++) {
			if (date > maxDays) {
				date = 1;
				notCurrMonthFlag = true;
				break;
			}
			days[i] = new CalendarDay(date, (date == today) && flag, notCurrMonthFlag);
			date++;
		}
	}

	public CalendarDay[] getDays() {
		return this.days;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Arrays.toString(days);
	}

}
