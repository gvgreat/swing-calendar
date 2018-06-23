package org.calendar.app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Table Cell Renderer for the Calendar
 * 
 * @author gv
 * 
 */
@SuppressWarnings("serial")
public class CalendarTableCellRenderer extends DefaultTableCellRenderer {

	private static Border todayBorder = new LineBorder(CalendarUtils.FOCUS_COLOR, 2);
	private static Border selectionBorder = new LineBorder(CalendarUtils.FOCUS_COLOR, 1);
	private static final Font font = new Font("Verdana", Font.PLAIN, 12); //$NON-NLS-1$
	private CalendarDay currentDay;

	public CalendarTableCellRenderer(CalendarDay currentDay) {
		super();
		this.currentDay = currentDay;
	}

	/**
	 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(JTable, Object, boolean,
	 *      boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
				column);
		if (value instanceof CalendarDay) {
			label.setFont(font);
			label.setHorizontalAlignment(JLabel.CENTER);

			if (((CalendarDay) value).isToday()) {
				label.setBorder(todayBorder);
			}

			if (((CalendarDay) value).isNotCurrentMonth()) {
				label.setForeground(Color.LIGHT_GRAY);
			} else {
				label.setForeground(Color.BLACK);
			}

			if (isSelected || hasFocus) {
				label.setForeground(Color.WHITE);
				label.setBackground(CalendarUtils.FOCUS_COLOR);
				label.setOpaque(true);
				table.setGridColor(CalendarUtils.FOCUS_COLOR);
				label.setBorder(selectionBorder);
				currentDay = null;
			} else if (value.equals(currentDay)) {
				label.setForeground(Color.WHITE);
				label.setBackground(CalendarUtils.FOCUS_COLOR);
				label.setOpaque(true);
				table.setGridColor(CalendarUtils.FOCUS_COLOR);
				label.setBorder(selectionBorder);
				table.changeSelection(row, column, true, false);
			} else {
				label.setOpaque(false);
				label.setBackground(CalendarUtils.TABLE_BG_COLOR);
				label.setForeground(CalendarUtils.FOREGROUND_COLOR);
				label.setBorder(null);
			}

		}

		return label;
	}
}
