package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.*;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

public class T_ReservationStatusGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton jButtonClose;
	private JList<Booking> ReservationsList;
	private DefaultListModel<Booking> lista = new DefaultListModel<Booking>();
	private JScrollPane reservations;
	private Collection<Booking> bookings;
	private JPanel panel;
	private JButton btnDone;
	private BLFacade facade;
	private Traveler traveler;
	private JPanel panel_1;
	private JButton btnComplain;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					T_ReservationStatusGUI frame = new T_ReservationStatusGUI(null,null);
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
	public T_ReservationStatusGUI(Traveler traveler,BLFacade facade) {
		this.traveler=traveler;
		this.facade= facade; 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		bookings=facade.getBookings(traveler);
		lista.clear();
		for(Booking b: bookings)
			lista.addElement(b);
		
		
		ReservationsList = new JList<Booking>();
		ReservationsList.setModel(lista);
		
		reservations = new JScrollPane();
		reservations.setViewportView(ReservationsList);
		contentPane.add(reservations);
		
          
		panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		panel.add(jButtonClose);
		
		panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnDone = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReservationStatusGUI.Done")); //$NON-NLS-1$ //$NON-NLS-2$
		panel_1.add(btnDone);
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Date today = new Date(); 
				
				if(ReservationsList.getSelectedValue()!=null && ReservationsList.getSelectedValue().getState().equals("Accepted") && today.compareTo(ReservationsList.getSelectedValue().getRide().getDate()) > 0) { 
					facade.tripDone((Booking) ReservationsList.getSelectedValue());
					JFrame a = new T_ValorationGUI((Booking) ReservationsList.getSelectedValue(), facade);
					a.setVisible(true);
					a.setLocation(T_ReservationStatusGUI.this.getLocation());
					setVisible(false);
				}
				reservationsListUpdate(facade, traveler);
			}
		});
		
		btnComplain = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReservationStatusGUI.Complain")); //$NON-NLS-1$ //$NON-NLS-2$
		btnComplain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ReservationsList.getSelectedValue()!=null && ReservationsList.getSelectedValue().getState().equals("TripDone") /*&& gaur.compareTo(ReservationsList.getSelectedValue().getRide().getDate()) > 0 */) { //gaur.compareTo(bidaiaEguna) > 0 ---> oindik ez da bidaia Eguna 
					Booking b=(Booking) ReservationsList.getSelectedValue();
					JFrame a = new U_ComplainGUI(b,traveler,b.getDriver(), facade);
					a.setVisible(true);
					a.setLocation(T_ReservationStatusGUI.this.getLocation());
					setVisible(false);
				}
			}
		});
		panel_1.add(btnComplain);
		
		
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		JFrame tra = new TravelerGUI(traveler, facade);
		tra.setVisible(true);
		tra.setLocation(T_ReservationStatusGUI.this.getLocation());
		this.setVisible(false);
	}
	private void reservationsListUpdate (BLFacade facade, Traveler traveler) {
		bookings=facade.getBookings(traveler);
		lista.clear();
		for(Booking b: bookings)
			lista.addElement(b);
		ReservationsList.setModel(lista);
	}
	

}
