package org.calendar.app;

import java.util.Calendar;

/**
 * Calendar Month class, which will be displayed as current month
 * 
 * @author gv
 */
class CalendarMonth {
	private int month, year;
	private static int NUM_WEEKS = 6;
	private CalendarWeek[] weeks = new CalendarWeek[NUM_WEEKS];
	private Calendar calendar = Calendar.getInstance();

	Calendar rightNow = Calendar.getInstance();
	private int today = rightNow.get(Calendar.DATE);
	private boolean isToday = false;

	CalendarMonth(int month, int year) {
		setMonth(month);
		calendar.set(Calendar.MONTH, month);
		setYear(year);
		calendar.set(Calendar.YEAR, year);
		initializeWeeks();
	}

	public void initializeWeeks() {
		calendar.set(Calendar.DATE, 1);
		int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int maxDays = calendar.getActualMaximum(Calendar.DATE);
		@SuppressWarnings("unused")
		int maxDaysInWeek = calendar.getMaximum(Calendar.DAY_OF_WEEK);

		int daysInFirstWeek = 8 - firstDayOfWeek;
		@SuppressWarnings("unused")
		int remainingDays = maxDays - daysInFirstWeek;
		for (int dayIndex = 0; dayIndex < NUM_WEEKS; dayIndex++) {
			int currentDate = calendar.get(Calendar.DATE);
			weeks[dayIndex] = new CalendarWeek();
			int incrementedMonth = calendar.get(Calendar.MONTH);
			boolean isJanuary = (getMonth() == Calendar.DECEMBER) && (incrementedMonth == Calendar.JANUARY);

			if ((incrementedMonth > getMonth()) || isJanuary) {
				break;
			}

			isToday = (rightNow.get(Calendar.MONTH) == getMonth())
					&& (rightNow.get(Calendar.YEAR) == getYear());

			weeks[dayIndex].initializeDays((dayIndex == 0) ? firstDayOfWeek - 1 : 0, currentDate, maxDays,
					today, isToday);

			calendar.add(Calendar.DATE, (dayIndex == 0) ? daysInFirstWeek : 7);

		}
	}

	public CalendarWeek[] getWeeks() {
		return weeks;
	}

	public int getMonth() {
		return this.month;
	}

	public void setMonth(int month) {
		this.month = month;
		calendar.set(Calendar.MONTH, month);

	}

	public void setYear(int year) {
		this.year = year;
		calendar.set(Calendar.YEAR, year);
	}

	public int getYear() {
		return this.year;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (CalendarWeek week : weeks) {
			buffer.append(week.toString() + "\n"); //$NON-NLS-1$
		}
		return buffer.toString();
	}

}
