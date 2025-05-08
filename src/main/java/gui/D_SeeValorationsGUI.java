package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Review;
import domain.MoneyMovement;
import domain.Driver;
import domain.Traveler;
import domain.User;

import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.GridLayout;

public class D_SeeValorationsGUI extends JFrame {

	private JPanel contentPane;
	private JList<String> ReviewList;
	private JScrollPane scrollPane;
	private DefaultListModel<String> ReviewModel;
	private BLFacade facade;
	private Driver d;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					D_SeeValorationsGUI frame = new D_SeeValorationsGUI(null,null);
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
	public D_SeeValorationsGUI(Driver d,BLFacade facade) {
		this.facade= facade; 
		this.d = d;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		
		
		ReviewModel = new DefaultListModel<String>();
		ReviewModel.clear();
		for(Review b : facade.getReviews(d)) {
			ReviewModel.addElement(b.toString());
		}
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		
		
		ReviewList = new JList<String>(ReviewModel);
		scrollPane.setViewportView(ReviewList);
		ReviewList.setModel(ReviewModel);
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		contentPane.add(btnClose);
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		JFrame driverGUI = new DriverGUI(d, facade);
		driverGUI.setLocation(D_SeeValorationsGUI.this.getLocation());
		driverGUI.setVisible(true);
		this.setVisible(false);
	
	}
}
