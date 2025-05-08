package gui;

import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Alerta;
import domain.Car;
import domain.Driver;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class D_CreateRideGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField fieldOrigin = new JTextField();
	private JTextField fieldDestination = new JTextField();

	private JLabel jLabelOrigin = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"));
	private JLabel jLabelSeats = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.NumberOfSeats"));
	private JLabel jLabRideDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));
	private JLabel jLabelPrice = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Price"));

	private JComboBox<Integer> SeatsComboBox = new JComboBox<Integer>();
	private JTextField jTextFieldPrice = new JTextField();

	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneAlerts = new JScrollPane();

	private JButton jButtonCreate = new JButton(
			ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.CreateRide"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();

	private List<Date> datesWithAlertCurrentMonth;
	private JComboBox<Car> CarComboBox = null;

	private BLFacade facade;
	private Driver driver;

	private JTable tableAlerts = new JTable();
	private DefaultTableModel tableModelAlerts;
	private String[] zutabeIzenak = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateRidesGUI.From"), 
			ResourceBundle.getBundle("Etiquetas").getString("CreateRidesGUI.To"), 
			ResourceBundle.getBundle("Etiquetas").getString("CreateRidesGUI.PriceMax"),
			ResourceBundle.getBundle("Etiquetas").getString("CreateRidesGUI.nPlaces")
	};
	public D_CreateRideGUI(Driver driver, BLFacade facade) {
		
		this.facade=facade;
		this.driver=driver;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.CreateRide"));

		jLabelOrigin.setBounds(new Rectangle(6, 56, 92, 20));
		jLabelSeats.setBounds(new Rectangle(6, 119, 173, 20));
		SeatsComboBox.setBounds(new Rectangle(139, 119, 60, 20));
		
		jLabelPrice.setBounds(new Rectangle(6, 159, 173, 20));
		jTextFieldPrice.setBounds(new Rectangle(139, 159, 60, 20));

		jCalendar.setBounds(new Rectangle(300, 50, 225, 150));
		

		datesWithAlertCurrentMonth=facade.getDatesWithAlertCurrentMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar,datesWithAlertCurrentMonth,Color.RED);
		
		
		
		jButtonCreate.setBounds(new Rectangle(100, 275, 130, 30));

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(new Rectangle(274, 275, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jLabelMsg.setBounds(new Rectangle(6, 233, 275, 20));
		jLabelMsg.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		

		this.getContentPane().add(SeatsComboBox, null);

		this.getContentPane().add(jLabelSeats, null);
		this.getContentPane().add(jLabelOrigin, null);
		

		

		this.getContentPane().add(jCalendar, null);
		
		this.getContentPane().add(jLabelPrice, null);
		this.getContentPane().add(jTextFieldPrice, null);
		
		jLabRideDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabRideDate.setBounds(298, 16, 140, 25);
		getContentPane().add(jLabRideDate);
		
		jLabelDestination.setBounds(6, 81, 61, 16);
		getContentPane().add(jLabelDestination);
		
		
		fieldOrigin.setBounds(100, 53, 130, 26);
		getContentPane().add(fieldOrigin);
		fieldOrigin.setColumns(10);
		
		
		fieldDestination.setBounds(104, 81, 123, 26);
		getContentPane().add(fieldDestination);
		fieldDestination.setColumns(10);
		

		CarComboBox = new JComboBox<Car>();
		CarComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) { 
				SeatComboBoxUpdate();
			}
		});
		CarComboBox.setBounds(77, 191, 204, 20);
		
		ArrayList<Car> DriverCarList =facade.getDriverCars(driver);
		DefaultComboBoxModel <Car> carListModel = new DefaultComboBoxModel <Car>() ;
		for(Car car:DriverCarList) carListModel.addElement(car);
		CarComboBox.setModel(carListModel);
		
		getContentPane().add(CarComboBox);
		
		
		if(CarComboBox.getSelectedItem() != null) { 
			SeatComboBoxUpdate(); 
		}

		
		
		JLabel lblCar = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.lblCar")); //$NON-NLS-1$ //$NON-NLS-2$
		lblCar.setBounds(10, 198, 45, 13);
		getContentPane().add(lblCar);
	
		
		
	

		
		 //Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//			
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolverá 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar.setCalendar(calendarAct);						
	
					}
					jCalendar.setCalendar(calendarAct);
					int offset = jCalendar.getCalendar().get(Calendar.DAY_OF_WEEK);
					
						if (Locale.getDefault().equals(new Locale("es")))
							offset += 4;
						else
							offset += 5;
						
						datesWithAlertCurrentMonth = facade.getDatesWithAlertCurrentMonth(jCalendar.getDate());
						paintDaysWithEvents(jCalendar, datesWithAlertCurrentMonth, Color.RED);

				
				
				///ScrollPane 
				List<Alerta> dayAlertList = facade.getAlertsFromDay(UtilDate.trim(jCalendar.getDate()));
				tableModelAlerts.setRowCount(0);
				for(Alerta alerta : dayAlertList) {
					Vector<Object> row = new Vector<Object>();
					row.add(alerta.getFrom());
					row.add(alerta.getTo());
					row.add(alerta.getPriceMax());
					row.add(alerta.getnPlaces());
					
					tableModelAlerts.addRow(row);
				}
				}	
			}	
		});
		
		scrollPaneAlerts.setBounds(293, 201, 266, 74);
		
		scrollPaneAlerts.setViewportView(tableAlerts);
		tableModelAlerts = new DefaultTableModel(null, zutabeIzenak);
		tableAlerts.setModel(tableModelAlerts);
		
		
		tableModelAlerts.setDataVector(null, zutabeIzenak);
		tableModelAlerts.setColumnCount(4);
		
		tableAlerts.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableAlerts.getColumnModel().getColumn(1).setPreferredWidth(50);
		tableAlerts.getColumnModel().getColumn(2).setPreferredWidth(50);
		tableAlerts.getColumnModel().getColumn(3).setPreferredWidth(50);
		getContentPane().add(scrollPaneAlerts);
		
		tableAlerts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                try {
					if (!e.getValueIsAdjusting()) {
					    int selectedRow = tableAlerts.getSelectedRow();
					    if (selectedRow != -1) {
					    	fieldOrigin.setText((String) tableAlerts.getValueAt(selectedRow, 0));
					    	fieldDestination.setText((String) tableAlerts.getValueAt(selectedRow, 1));
					    	jTextFieldPrice.setText(tableAlerts.getValueAt(selectedRow, 2).toString());
					    	int kop= Math.min(((Car) CarComboBox.getSelectedItem()).getNPlaces()-1,  (int) tableAlerts.getValueAt(selectedRow, 3))-1;
					    	SeatsComboBox.setSelectedIndex(kop);
					    }
					}
				} catch (Exception e1) {

				}
            }
        });
	}
	

			
	private void jButtonCreate_actionPerformed(ActionEvent e) {
		jLabelMsg.setText("");
		String error = field_Errors();
		if (error != null)
			jLabelMsg.setText(error);
		else
			try {
				int inputSeats = (int) SeatsComboBox.getSelectedItem();
				float price = Float.parseFloat(jTextFieldPrice.getText());

				facade.createRide(fieldOrigin.getText(), fieldDestination.getText(),
						UtilDate.trim(jCalendar.getDate()), inputSeats, price, driver.getEmail(),
						(Car) CarComboBox.getSelectedItem());
				jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideCreated"));


			} catch (RideMustBeLaterThanTodayException e1) {
				// TODO Auto-generated catch block
				jLabelMsg.setText(e1.getMessage());
			} catch (RideAlreadyExistException e1) {
				// TODO Auto-generated catch block
				jLabelMsg.setText(e1.getMessage());
			}

	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		JFrame driverGUI = new D_RideManagementGUI(driver, facade);
		driverGUI.setLocation(D_CreateRideGUI.this.getLocation());
		driverGUI.setVisible(true);
		this.setVisible(false);

	}

	private String field_Errors() {

		try {
			if ((fieldOrigin.getText().length() == 0) || (fieldDestination.getText().length() == 0)
					|| ((int) SeatsComboBox.getSelectedItem() == 0) || (jTextFieldPrice.getText().length() == 0))
				return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorQuery");
			else {

				// trigger an exception if the introduced string is not a number
				int inputSeats = (int) (SeatsComboBox.getSelectedItem());

				if (inputSeats <= 0) {
					return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.SeatsMustBeGreaterThan0");
				} else {
					float price = Float.parseFloat(jTextFieldPrice.getText());
					if (price <= 0)
						return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.PriceMustBeGreaterThan0");

					else
						return null;

				}

			}
		} catch (java.lang.NumberFormatException e1) {

			return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorNumber");
		} catch (Exception e1) {

			e1.printStackTrace();
			return null;

		}
	}

	private void SeatComboBoxUpdate() {
		ArrayList<Integer> nPlaces = new ArrayList<Integer>();
		for (int i = 1; i < (int) ((Car) CarComboBox.getSelectedItem()).getNPlaces(); i++) {
			nPlaces.add(i);
		}
		DefaultComboBoxModel<Integer> comboBoxModelEserlekuKop = new DefaultComboBoxModel( nPlaces.toArray());
		SeatsComboBox.setModel(comboBoxModelEserlekuKop);
	}

	public static void paintDaysWithEvents(JCalendar jCalendar, List<Date> datesWithEventsCurrentMonth, Color color) {
		Calendar calendar = jCalendar.getCalendar();
		int month = calendar.get(Calendar.MONTH);
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);
		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;

		for (Date d : datesWithEventsCurrentMonth) {
			calendar.setTime(d);
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(color);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
	}
}
