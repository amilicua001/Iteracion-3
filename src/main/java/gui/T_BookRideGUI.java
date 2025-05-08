package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.*;

import java.awt.GridLayout;
import java.awt.List;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class T_BookRideGUI extends JFrame {

	private JPanel BookingPanel;
	private JTextField NotesField;
	private JTextField NoteTitle;
	private JTextField PlaceTitle;
	private JComboBox<Integer> PlacesList;
	private JTextField Depart;
	private JTextField Arrival;
	private JTextField Data;
	private JTextField PriceTitle;
	private JTextField Price;
	private JButton jButtonClose;
	private JLabel lblError;
	private JPanel panel;
	private JPanel panel_1;
	private BLFacade facade;
	private Traveler traveler;
	private JPanel panel_2;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					T_BookRideGUI frame = new T_BookRideGUI(null, null,null);
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
	public T_BookRideGUI(Ride ride, User user,BLFacade facade) {
		this.facade= facade;
		this.traveler=(Traveler) user;
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.title")); //$NON-NLS-1$ //$NON-NLS-2$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 445, 339);
		BookingPanel = new JPanel();
		BookingPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(BookingPanel);
		BookingPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		Depart = new JTextField();
		Depart.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.Depart") + ": " + ride.getFrom());
		Depart.setEditable(false);
		BookingPanel.add(Depart);
		Depart.setColumns(10);
		
		Arrival = new JTextField();
		Arrival.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.Arrival") + ": " + ride.getTo());
		Arrival.setEditable(false);
		BookingPanel.add(Arrival);
		Arrival.setColumns(10);
		
		panel_1 = new JPanel();
		BookingPanel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		PriceTitle = new JTextField();
		panel_1.add(PriceTitle);
		PriceTitle.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.PriceTitle"));
		PriceTitle.setEditable(false);
		PriceTitle.setColumns(10);
		
		Price = new JTextField();
		panel_1.add(Price);
		Price.setText(String.valueOf(ride.getPrice()));
		Price.setEditable(false);
		Price.setColumns(10);
		
		
		ArrayList<Integer> nPlaces = new ArrayList<Integer>();
		for(int i=1; i<=(int)ride.getnPlaces(); i++) {
			nPlaces.add(i);
		}
		DefaultComboBoxModel<Integer> comboBoxModel = new DefaultComboBoxModel(nPlaces.toArray());
        
        Data = new JTextField();
        Data.setEditable(false);
        Data.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.Data") + ": " + ride.getDate());
        BookingPanel.add(Data);
        Data.setColumns(10);
        
        panel = new JPanel();
        BookingPanel.add(panel);
        panel.setLayout(new GridLayout(0, 2, 0, 0));
        
        
        
        PlaceTitle = new JTextField();
        panel.add(PlaceTitle);
        PlaceTitle.setEnabled(true);
        PlaceTitle.setEditable(false);
        PlaceTitle.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.PlacesTitle"));
        PlaceTitle.setColumns(10);
        PlacesList = new JComboBox<>(comboBoxModel);
        panel.add(PlacesList);
		
		jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					jButtonClose_actionPerformed(e);
			}
		});
		
		panel_2 = new JPanel();
		BookingPanel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		NoteTitle = new JTextField();
		NoteTitle.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.NoteTitle"));
		NoteTitle.setEditable(false);
		BookingPanel.add(NoteTitle);
		NoteTitle.setColumns(10);
		

		NotesField = new JTextField();
		NotesField.setText("");
		BookingPanel.add(NotesField);
		NotesField.setColumns(10);
		
		JButton btnBooking = new JButton();
		btnBooking.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.Booking"));
		btnBooking.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int nPlaces = (int) PlacesList.getSelectedItem();
				
				Booking booking = new Booking(ride.getDriver(), (Traveler) user, ride, (int)ride.getPrice() * nPlaces, nPlaces,NotesField.getText());
				
				Boolean saved = facade.saveBooking(booking);
				
				if(saved) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.Saved"));
					ride.setnPlaces(ride.getnPlaces()-nPlaces);
				}else {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.NotSaved"));
				}
			}
		});
		BookingPanel.add(btnBooking);
		
		lblError = new JLabel("");
		BookingPanel.add(lblError);
		BookingPanel.add(jButtonClose);
		

	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		JFrame tra = new TravelerGUI(traveler, facade);
		tra.setVisible(true);
		tra.setLocation(T_BookRideGUI.this.getLocation());
		this.setVisible(false);
	}
}
