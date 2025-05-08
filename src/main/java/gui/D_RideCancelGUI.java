package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Booking;
import domain.Driver;
import domain.Ride;
import domain.Traveler;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class D_RideCancelGUI extends JFrame {

	private JPanel contentPane;
	private Ride SelectedRide;
	private BLFacade facade;
	private Driver driver;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					D_RideCancelGUI frame = new D_RideCancelGUI(null,null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public D_RideCancelGUI(Driver driver,BLFacade facade) {
		this.facade=facade;
		this.driver=driver;
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		JComboBox<Ride> comboBox = new JComboBox<Ride>();
		comboBoxUpdate(comboBox, facade, driver);
		contentPane.add(comboBox);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnCancel = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Driver_RideManagementGUI.btnCancel"));
		panel.add(btnCancel);
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		panel.add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SelectedRide = (Ride)comboBox.getSelectedItem();
				if(SelectedRide != null) {
					facade.cancelDriverRide(SelectedRide);
				}
				comboBoxUpdate(comboBox, facade, driver);
			}
		});
		
		
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		JFrame driverGUI = new D_RideManagementGUI(driver, facade);
		driverGUI.setLocation(D_RideCancelGUI.this.getLocation());
		driverGUI.setVisible(true);
		this.setVisible(false);
	}
	private void comboBoxUpdate (JComboBox<Ride> comboBox, BLFacade facade, Driver driver) {
		
		ArrayList<Ride> RidesList = (ArrayList<Ride>) facade.getDriverRides(driver);
		 
		DefaultComboBoxModel<Ride> comboBoxModel = new DefaultComboBoxModel(RidesList.toArray());
		
		comboBox.setModel(comboBoxModel);
		 
	}
}
