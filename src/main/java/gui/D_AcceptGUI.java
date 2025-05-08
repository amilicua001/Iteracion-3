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

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.GridLayout;

public class D_AcceptGUI extends JFrame {

	private JPanel contentPane;
	private Booking SelectedBooking;
	private BLFacade facade;
	private Driver driver;

	private JButton btnReject = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AcceptGUI.Reject"));
	private JButton btnAccept = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AcceptGUI.Accept"));
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					D_AcceptGUI frame = new D_AcceptGUI(null, null);
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
	public D_AcceptGUI(Driver driver, BLFacade facade) {
		this.facade = facade;
		this.driver = driver;
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("AcceptGUI.title")); //$NON-NLS-1$ //$NON-NLS-2$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JComboBox comboBox = new JComboBox();
		comboBoxUpdate(comboBox, facade, driver);
		SelectedBooking = (Booking) comboBox.getSelectedItem();

		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		contentPane.add(comboBox);

		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		
		panel.add(btnReject);

		btnReject.setEnabled(false);
		
		panel.add(btnAccept);

		btnAccept.setEnabled(false);
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					facade.accept(SelectedBooking);
					comboBoxUpdate(comboBox, facade, driver);
			}
		});
		btnReject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					facade.reject(SelectedBooking);
					comboBoxUpdate(comboBox, facade, driver);
			}
		});

		

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == 1) { 
					SelectedBooking = (Booking) comboBox.getSelectedItem();
					if(SelectedBooking.getState().equals("Pending")) {
						btnAccept.setEnabled(true);
						btnReject.setEnabled(true);
					}else {
						btnAccept.setEnabled(false);
						btnReject.setEnabled(false);
					}
					
				}

			}
		});
		
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		contentPane.add(btnClose);
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		JFrame driverGUI = new DriverGUI(driver, facade);
		driverGUI.setLocation(D_AcceptGUI.this.getLocation());
		driverGUI.setVisible(true);
		this.setVisible(false);
	}

	private void comboBoxUpdate(JComboBox comboBox, BLFacade facade, Driver driver) {
		ArrayList<Booking> BookingList = facade.getBookingList(driver);
		DefaultComboBoxModel<Booking> comboBoxModel = new DefaultComboBoxModel(BookingList.toArray());
		comboBox.setModel(comboBoxModel);
		
		SelectedBooking = (Booking) comboBox.getSelectedItem();
		if(SelectedBooking.getState().equals("Pending")) {
			btnAccept.setEnabled(true);
			btnReject.setEnabled(true);
		}else {
			btnAccept.setEnabled(false);
			btnReject.setEnabled(false);
		}
	}
}
