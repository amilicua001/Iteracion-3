package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Driver;
import domain.User;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class DriverGUI extends JFrame {
	
    private Driver driver;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton RideManagement = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnMoney;
	private JButton btnConfirm;
	private JButton btnAddCar;
	private JButton btnReviews;
	
	
	/**
	 * This is the default constructor
	 */
	public DriverGUI(User d,BLFacade facade) {
		super();
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.this.title")); //$NON-NLS-1$ //$NON-NLS-2$

		driver=(Driver)d;
		
		// this.setSize(271, 295);
		this.setSize(495, 290);
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		RideManagement = new JButton();
		RideManagement.setText(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.RideManagement"));
		RideManagement.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new D_RideManagementGUI(driver,facade);
				a.setVisible(true);
				a.setLocation(DriverGUI.this.getLocation());
				setVisible(false);
			}
		});
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(0, 1, 0, 0));
		jContentPane.add(jLabelSelectOption);
		jContentPane.add(RideManagement);
		
		btnMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UserGUI.Money")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
		btnMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new U_MoneyGUI(d, facade);
				a.setVisible(true);
				a.setLocation(DriverGUI.this.getLocation());
				setVisible(false);
			}
		});
		jContentPane.add(btnMoney);
		
		btnConfirm = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.Confirm")); //$NON-NLS-1$ //$NON-NLS-2$
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new D_AcceptGUI(driver, facade);
				a.setVisible(true);
				a.setLocation(DriverGUI.this.getLocation());
				setVisible(false);
			}
		});
		jContentPane.add(btnConfirm);
		
		btnAddCar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.AddCar")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAddCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new D_AddCarGUI(driver, facade);
				a.setVisible(true);
				a.setLocation(DriverGUI.this.getLocation());
				setVisible(false);
			}
		});
		jContentPane.add(btnAddCar);
		
		btnReviews = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.Reviews")); //$NON-NLS-1$ //$NON-NLS-2$
		btnReviews.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new D_SeeValorationsGUI(driver, facade);
				a.setVisible(true);
				a.setLocation(DriverGUI.this.getLocation());
				setVisible(false);
			}
		});
		jContentPane.add(btnReviews);
		
		jButtonClose.setBounds(new Rectangle(275, 263, 130, 30));
		jContentPane.add(jButtonClose);
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		
		setContentPane(jContentPane);
	
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
	

	private void jButtonClose_actionPerformed(ActionEvent e) {
			this.setVisible(false);
	}
	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		RideManagement.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.RideManagement"));
		btnMoney.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Login"));
		btnConfirm.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Register"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ " - driver :"+driver.getName());
	}
	
} // @jve:decl-index=0:visual-constraint="0,0"

