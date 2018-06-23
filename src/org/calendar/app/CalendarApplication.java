package org.calendar.app;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * @author G. Vaidhyanathan
 * @version 1.0
 */
public class CalendarApplication {

	private final CalendarPanel calendarPanel;
	private Date currentDate; // hold the current date
	private boolean isWeekSelected = false; // this is to check whether a week
	// or a day is selected

	private final Date[] currentWeek = new Date[7];

	private final boolean isDateSelectionMode;
	private final Calendar cal = Calendar.getInstance();

	private final Vector<CalendarDateSelectionListener> dateSelectionListeners = new Vector<CalendarDateSelectionListener>();

	/**
	 * default constructor
	 */
	private CalendarApplication() {
		this(false);
	}

	/**
	 * constructor
	 * 
	 * @param isDateSelection
	 */
	private CalendarApplication(boolean isDateSelection) {
		isDateSelectionMode = isDateSelection;
		calendarPanel = new CalendarPanel(this);
	}

	/**
	 * 
	 * @return CalendarAppllication
	 */
	public static CalendarApplication getWeekSelectionInstance() {
		return new CalendarApplication();
	}

	/**
	 * 
	 * @return CalendarAppllication
	 */
	public static CalendarApplication getDateSelectionInstance() {
		return new CalendarApplication(true);
	}

	public CalendarPanel getCalendarPanel() {
		return calendarPanel;
	}

	private void initCurrentWeek() {
		for (int i = 0; i < 7; i++) {
			currentWeek[i] = null;
		}
	}

	public void initCalendarPanel() {
		calendarPanel.initComponents();
	}

	public void setDate(CalendarDay day, int month, int year) {
		if (day != null) {
			cal.set(Calendar.DATE, day.getDay());
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.YEAR, year);
			this.currentDate = cal.getTime();
		} else {
			this.currentDate = null;
		}
		notifyDateSelectionListeners();
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setWeek(CalendarWeek week, int month, int year) {
		int count = 0;
		initCurrentWeek();
		for (CalendarDay day : week.getDays()) {
			if (day.getDay() != -1) {
				cal.set(Calendar.DATE, day.getDay());
				cal.set(Calendar.MONTH, month);
				cal.set(Calendar.YEAR, year);
				currentWeek[count++] = cal.getTime();
			}
		}
		notifyWeekSelectionListeners();
	}

	public boolean isWeekSelected() {
		return isWeekSelected;
	}

	public void setWeekSelected(boolean flag) {
		isWeekSelected = flag;
	}

	public boolean isDateSelectionMode() {
		return isDateSelectionMode;
	}

	public void addDateSelectionListener(CalendarDateSelectionListener listener) {
		dateSelectionListeners.add(listener);
	}

	public void removeDateSelectionListener(CalendarDateSelectionListener listener) {
		dateSelectionListeners.remove(listener);
	}

	private void notifyDateSelectionListeners() {
		for (CalendarDateSelectionListener listener : dateSelectionListeners) {
			listener.dateSelected(getCurrentDate());
		}
	}

	private void notifyWeekSelectionListeners() {
		for (CalendarDateSelectionListener listener : dateSelectionListeners) {
			listener.weekSelected(currentWeek[0], findLastDate());
		}
	}

	private Date findLastDate() {
		Date lastDate = currentWeek[6];
		for (int i = 0; i < currentWeek.length; i++) {
			if (currentWeek[i] == null && i > 0) {
				lastDate = currentWeek[i - 1];
				break;
			}
		}
		return lastDate;
	}

	/**
	 * @return the currentWeek
	 */
	public Date[] getCurrentWeek() {
		return currentWeek;
	}

	/**
	 * @param currentDate
	 *            the currentDate to set
	 */
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
}
