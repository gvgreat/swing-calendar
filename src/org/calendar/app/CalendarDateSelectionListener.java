package org.calendar.app;

import java.util.Date;

/**
 * @author G. Vaidhyanathan
 * @version 1.0
 */
public interface CalendarDateSelectionListener {
	void dateSelected(Date date);

	void weekSelected(Date startDate, Date endDate);
}
