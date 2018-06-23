package org.calendar.app;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class CalendarPanel extends JPanel implements ActionListener {
	private static final int NUM_ROWS = 6;

	private static final String[] months = new String[] {
			Messages.getString("CalendarPanel.JAN"), Messages.getString("CalendarPanel.FEB"), Messages.getString("CalendarPanel.MAR"), Messages.getString("CalendarPanel.APR"), Messages.getString("CalendarPanel.MAY"), Messages.getString("CalendarPanel.JUNE"), Messages.getString("CalendarPanel.JUL"), Messages.getString("CalendarPanel.AUG"), Messages.getString("CalendarPanel.SEP"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
			Messages.getString("CalendarPanel.OCT"), Messages.getString("CalendarPanel.NOV"), Messages.getString("CalendarPanel.DEC") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private static final String[] weekdays = new String[] {
			Messages.getString("CalendarPanel.SUN"), Messages.getString("CalendarPanel.MON"), Messages.getString("CalendarPanel.TUE"), Messages.getString("CalendarPanel.WED"), Messages.getString("CalendarPanel.THR"), Messages.getString("CalendarPanel.FRI"), Messages.getString("CalendarPanel.SAT"), }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$

	private Dimension dim;
	private SpinnerDateModel spinnerModel;
	private JPanel topPanel;
	private final Calendar cal = Calendar.getInstance();
	private final CalendarMonth month = new CalendarMonth(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
	private JTable table;
	private CalendarTableModel model;
	private JComboBox combo;
	private int currentYear = cal.get(Calendar.YEAR);

	private CalendarDay currentDay;

	private CalendarApplication application;

	public CalendarPanel(CalendarApplication application) {
		super();
		this.application = application;
	}

	public void initComponents() {
		if (application.getCurrentDate() != null) {
			Calendar call = Calendar.getInstance();
			call.setTime(application.getCurrentDate());
			currentDay = new CalendarDay(call.get(Calendar.DATE));
		}
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200, 140));
		createTopPanel();
		createCalendarPanel();
		if (this.application.isDateSelectionMode()) {
			createActionPanel();
			setPreferredSize(new Dimension(200, 170));
		}
		if (application.getCurrentDate() != null) {
			setCurrentDate(application.getCurrentDate());
		}
	}

	/**
	 * @param date
	 */
	private void setCurrentDate(Date date) {
		Calendar call = Calendar.getInstance();
		call.setTime(date);
		spinnerModel.setValue(date);
		combo.setSelectedIndex(call.get(Calendar.MONTH));
	}

	private void createTopPanel() {
		topPanel = new JPanel(new BorderLayout());

		spinnerModel = new SpinnerDateModel(cal.getTime(), null, null, Calendar.YEAR);

		final JSpinner spinner = new JSpinner(spinnerModel);
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, Messages
				.getString("CalendarPanel.YYYY")); //$NON-NLS-1$
		spinner.setEditor(dateEditor);
		JTextField field = dateEditor.getTextField();
		field.setEditable(false);

		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ce) {
				if (model != null) {
					Object value = spinner.getValue();
					cal.setTime((Date) value);
					currentYear = cal.get(Calendar.YEAR);
					int selectedIndex = combo.getSelectedIndex();
					model.setMonthAndYear(selectedIndex, currentYear);
				}
			}
		});

		topPanel.add(spinner, BorderLayout.WEST);

		combo = new JComboBox(months);
		combo.setSelectedIndex(cal.get(Calendar.MONTH));
		topPanel.add(combo, BorderLayout.EAST);

		combo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				if (model != null) {
					int selectedIndex = combo.getSelectedIndex();
					model.setMonthAndYear(selectedIndex, currentYear);
				}
			}
		});

		add(topPanel, BorderLayout.NORTH);
	}

	private void createCalendarPanel() {
		JPanel calendarPanel = new JPanel(new BorderLayout());

		model = new CalendarTableModel(month, weekdays);

		table = new JTable(model);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		calendarPanel.add(new JScrollPane(table), BorderLayout.CENTER);

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				boolean flag = table.getSelectedRowCount() > 0;
				application.setWeekSelected(flag);
			}
		});
		table.setToolTipText(Messages.getString("CalendarPanel.TOOLTIP"));
		table.setShowGrid(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		table.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

		table.setCellSelectionEnabled(application.isDateSelectionMode());
		table.setRowSelectionAllowed(!application.isDateSelectionMode());
		table.setColumnSelectionAllowed(false);

		CalendarTableCellRenderer renderer = new CalendarTableCellRenderer(currentDay);
		table.setDefaultRenderer(CalendarDay.class, renderer);
		table.setFocusable(true);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) {
					doDateSelectionOperation();
				}
			}
		});
		add(calendarPanel, BorderLayout.CENTER);
	}

	/**
   * 
   */
	protected void doDateSelectionOperation() {
		int selectedRow = table.getSelectedRow();
		CalendarMonth mm = model.getCalendarMonth();
		if (selectedRow < 0) {
			JOptionPane.showMessageDialog(this,
					Messages.getString("CalendarPanel.SELECTDATE"), "Error", JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			return;
		}

		if (application.isDateSelectionMode()) {
			int col = table.getSelectedColumn();
			CalendarDay value = (CalendarDay) model.getValueAt(selectedRow, col);
			application.setDate(value, mm.getMonth(), mm.getYear());
		} else {
			application.setWeek(mm.getWeeks()[selectedRow], mm.getMonth(), mm.getYear());
		}
	}

	private void createActionPanel() {
		JPanel actionPanel = new JPanel(new BorderLayout());

		JPanel tmp = new JPanel();
		JButton actionOK = new JButton(Messages.getString("CalendarPanel.OK")); //$NON-NLS-1$
		JButton actionCancel = new JButton(Messages.getString("CalendarPanel.CANCEL")); //$NON-NLS-1$
		tmp.add(actionOK);
		tmp.add(actionCancel);

		actionOK.addActionListener(this);
		actionCancel.addActionListener(this);

		actionPanel.add(tmp, BorderLayout.EAST);

		add(actionPanel, BorderLayout.SOUTH);
	}

	private int getRowHeight(int frameHeight) {
		Dimension dimension = topPanel.getPreferredSize();
		int height = frameHeight - dimension.height;
		int modulo = height % NUM_ROWS;
		height -= modulo;
		return height;
	}

	@SuppressWarnings("unused")
	private void addSizeTrimmings() {
		if (dim != null) {
			table.setPreferredScrollableViewportSize(dim);
			table.setRowHeight(getRowHeight(dim.height) / NUM_ROWS);
			table.addHierarchyBoundsListener(new HierarchyBoundsAdapter() {
				@Override
				public void ancestorResized(HierarchyEvent e) {
					Component c = e.getChangedParent();
					if (c == null || !(c instanceof JInternalFrame)) {
						return;
					}
					int hh = getRowHeight(c.getBounds().height);
					if (hh > 1) {
						table.setRowHeight(hh / NUM_ROWS);
					}
				}
			});
		}
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals(Messages.getString("CalendarPanel.OK"))) { //$NON-NLS-1$
			doDateSelectionOperation();
		} else if (cmd.equals(Messages.getString("CalendarPanel.CANCEL"))) { //$NON-NLS-1$
			application.setDate(null, -1, -1);
		}

	}
}
