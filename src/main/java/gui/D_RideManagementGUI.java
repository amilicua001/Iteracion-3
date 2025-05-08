package gui;

import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

public class D_RideManagementGUI extends JFrame {

	private JPanel contentPane;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private final JPanel panel = new JPanel();
	private BLFacade facade;
	private Driver driver;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					D_RideManagementGUI frame = new D_RideManagementGUI(null,null);
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
	public D_RideManagementGUI(Driver driver,BLFacade facade) {
		this.facade=facade;
		this.driver=driver;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnCancel = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Driver_RideManagementGUI.btnCancel"));
		panel.add(btnCancel);
		
		jButtonCreateQuery = new JButton();
		panel.add(jButtonCreateQuery);
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRide"));
		jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new D_CreateRideGUI(driver,facade);
				a.setVisible(true);
				a.setLocation(D_RideManagementGUI.this.getLocation());
				setVisible(false);
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame c = new D_RideCancelGUI(driver, facade);
				c.setVisible(true);
				c.setLocation(D_RideManagementGUI.this.getLocation());
				setVisible(false);
			}
		});
		contentPane.add(jButtonClose);
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});;
		
		
		
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		JFrame driverGUI = new DriverGUI(driver, facade);
		driverGUI.setVisible(true);
		driverGUI.setLocation(D_RideManagementGUI.this.getLocation());
		this.setVisible(false);
	}

}
