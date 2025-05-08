package gui;

import javax.swing.JFrame;

import businessLogic.BLFacade;
import domain.Admin;
import domain.User;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JLabel;

public class AdminGUI extends JFrame{
	private Admin admin;
	private BLFacade facade;
	
	public AdminGUI(Admin type, BLFacade facade) {
		this.admin=type;
		this.facade=facade;
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JButton btnSeeUsersWithClaims = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.SeeUsersWithClaims"));
		getContentPane().add(btnSeeUsersWithClaims);
		btnSeeUsersWithClaims.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

				JFrame usersWithClaims = new A_SeeUsersWithClaims(admin,facade);
				usersWithClaims.setLocation(AdminGUI.this.getLocation());
				usersWithClaims.setVisible(true);
			}
		});
		
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		getContentPane().add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
}
	
}
