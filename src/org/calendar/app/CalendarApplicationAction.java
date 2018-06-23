package org.calendar.app;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JTextField;

import org.i18n.swing.util.DialogUtil;
import org.i18n.swing.util.Utils;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 * @author G. Vaidhyanathan
 * @version 1.0
 */
/**
 * Action class for the calendar component
 */
@SuppressWarnings("serial")
public class CalendarApplicationAction extends AbstractAction implements CalendarDateSelectionListener {
	protected final JTextField field;
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");//$NON-NLS-1$//the format for the date
	private CalendarApplication application;

	/**
	 * a parameterized constructor
	 * 
	 * @param field
	 *            JTextField
	 */
	protected CalendarApplicationAction(JTextField field) {
		super("..."); //$NON-NLS-1$
		this.field = field;
	}

	/**
	 * returns the instance of the calendar action
	 * 
	 * @param field
	 *            JTextField
	 * @return CalendarApplicationAction
	 */
	public static CalendarApplicationAction getInstance(JTextField field) {
		return new CalendarApplicationAction(field);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		application = CalendarApplication.getDateSelectionInstance();
		application.setCurrentDate(Utils.getParsedDate(field.getText()));
		application.initCalendarPanel();
		application.addDateSelectionListener(this);

		Point location = Utils.getLocationRelativeToComponent((JButton) e.getSource());
		DialogUtil.createAndShowDialog("Date Picker", application.getCalendarPanel(), location); //$NON-NLS-1$
	}

	/**
	 * set the selected date
	 * 
	 * @see com.tsi.mis.calendar.CalendarDateSelectionListener#dateSelected(java.util.Date)
	 */
	public void dateSelected(Date date) {
		if (date != null) field.setText(sdf.format(date));
		DialogUtil.disposeDialog();
	}

	/**
	 * it will return the calendar panel
	 * 
	 * @return Component
	 */
	public Component getView() {
		return application.getCalendarPanel();
	}

	/**
	 * @see com.tsi.mis.calendar.CalendarDateSelectionListener#weekSelected(java.util.Date, java.util.Date)
	 */
	public void weekSelected(Date startDate, Date endDate) {
		// TODO Auto-generated method stub

	}
}
