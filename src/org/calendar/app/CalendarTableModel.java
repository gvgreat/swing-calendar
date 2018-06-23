package org.calendar.app;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * Table Model for Calendar
 * 
 * @author gv
 * 
 */
@SuppressWarnings("serial")
public class CalendarTableModel extends DefaultTableModel {
	private CalendarMonth calendarMonth;

	public CalendarTableModel(CalendarMonth month, Object[] columns) {
		super();
		this.calendarMonth = month;
		Object[][] arr = convertMonth();
		setDataVector(arr, columns);
	}

	private Object[][] convertMonth() {
		CalendarWeek[] week = calendarMonth.getWeeks();
		Object[][] arr = new Object[week.length][columnIdentifiers.size()];
		for (int i = 0; i < arr.length; i++) {
			if (week[i] != null) arr[i] = week[i].getDays();
		}
		return arr;
	}

	public void setMonthAndYear(int month, int year) {
		calendarMonth.setMonth(month);
		calendarMonth.setYear(year);
		calendarMonth.initializeWeeks();
		dataVector = convertToVector(convertMonth());
		fireTableDataChanged();
	}

	/**
	 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/**
	 * @see javax.swing.table.DefaultTableModel#getColumnClass(int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Class getColumnClass(int columnIndex) {
		return CalendarDay.class;
	}

	public CalendarMonth getCalendarMonth() {
		return this.calendarMonth;
	}

	/**
	 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getValueAt(int row, int col) {
		Vector vector = (Vector) dataVector.get(row);
		return vector.get(col);
	}
}
