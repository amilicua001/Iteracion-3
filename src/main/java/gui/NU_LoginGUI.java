package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Driver;
import domain.Traveler;
import domain.User;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NU_LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUser;
	private JPasswordField passwordField;
	private BLFacade facade;
	private String s1="";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NU_LoginGUI frame = new NU_LoginGUI(null);
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
	public NU_LoginGUI(BLFacade facade) {
		this.facade= facade; 
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.this.title")); //$NON-NLS-1$ //$NON-NLS-2$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 469, 397);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblUserName = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.UserName"));
		contentPane.add(lblUserName);
		
		textFieldUser = new JTextField();
		contentPane.add(textFieldUser);
		textFieldUser.setColumns(10);
		
		JLabel lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Password"));
		contentPane.add(lblPassword);
		
		JPasswordField passwordField = new JPasswordField();

		contentPane.add(passwordField);
		
		JLabel lblError = new JLabel(" "); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(lblError);
		
		JButton btnLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Login"));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = textFieldUser.getText();
				String password =new String(passwordField.getPassword());
			
				if (user.equals("Admin") && password.equals("bat")) {
					JFrame adminGUI = new AdminGUI(new Admin(), facade);
					adminGUI.setVisible(true);
					adminGUI.setLocation(NU_LoginGUI.this.getLocation());
					jButtonClose_actionPerformed(e);
					return;
				}
				
				Object type =facade.isLogin(user, password);
				
				if(type == null) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Error")); 
				}else if(type instanceof Traveler){
					JFrame travelerGUI = new TravelerGUI((User)type, facade);
	        		travelerGUI.setVisible(true);
	        		travelerGUI.setLocation(NU_LoginGUI.this.getLocation());
					jButtonClose_actionPerformed(e);

					
				}else if(type instanceof Driver){
					JFrame driverGUI = new DriverGUI((User)type,facade);
	        		driverGUI.setVisible(true);
	        		driverGUI.setLocation(NU_LoginGUI.this.getLocation());
					jButtonClose_actionPerformed(e);

				}
			    	
			}
		});
		contentPane.add(btnLogin);
		
		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		contentPane.add(jButtonClose);
		
		textFieldUser.setText(s1);
		//passwordField.setText("bat");
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
