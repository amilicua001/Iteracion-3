package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Booking;
import domain.User;
import domain.UserComplain;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class A_SeeUsersWithClaims extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					A_SeeUsersWithClaims frame = new A_SeeUsersWithClaims(null, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Admin admin;
	private BLFacade facade;
	private JList<UserComplain> userWithComplainViewport = new JList<UserComplain>();
	private Collection<User> userWithComplainsList;

	/**
	 * Create the frame.
	 * 
	 * @param facade
	 * @param admin
	 */

	public A_SeeUsersWithClaims(Admin admin, BLFacade facade) {
		this.admin = admin;
		this.facade = facade;
		this.userWithComplainsList = facade.getUsersWihComplains();
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JScrollPane scrollPane = new JScrollPane();
	
		scrollPane.setViewportView(userWithComplainViewport);
		updateScrollPane();
		getContentPane().add(scrollPane);
		getContentPane().add(panel);

		JButton btnSeeClaims = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.SeeClaims"));
		btnSeeClaims.setEnabled(false);

		btnSeeClaims.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a_ClaimsGUI = new A_ClaimsGUI(admin, facade,userWithComplainViewport.getSelectedValue().getUser());
				a_ClaimsGUI.setLocation(A_SeeUsersWithClaims.this.getLocation());
				a_ClaimsGUI.setVisible(true);
				setVisible(false);

			}
		});
		panel.add(btnSeeClaims);
		
		userWithComplainViewport.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(userWithComplainViewport.getSelectedValue() != null){
					btnSeeClaims.setEnabled(true);
				}else {
					btnSeeClaims.setEnabled(false);
				}
			}
		});
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame adminGUI = new AdminGUI(admin, facade);
				adminGUI.setLocation(A_SeeUsersWithClaims.this.getLocation());
				adminGUI.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		panel.add(btnClose);
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	private void updateScrollPane() {
		
		userWithComplainsList = facade.getUsersWihComplains();
		
		DefaultListModel<UserComplain> model = new DefaultListModel<UserComplain>();
		
		for(User u: userWithComplainsList) {
			
			UserComplain uCompl = new UserComplain(u,facade.getComplainNumberOfUser(u));
			
			model.addElement(uCompl);
	}
		
		userWithComplainViewport.setModel(sortModel(model));
		
	}
	
	private DefaultListModel<UserComplain> sortModel(DefaultListModel<UserComplain> notSortedModel) {
		DefaultListModel<UserComplain> sortedModel= new DefaultListModel<UserComplain>();
		
		
		ArrayList<UserComplain> elementList = Collections.list(notSortedModel.elements());

		 Collections.sort(elementList, new Comparator<UserComplain>() {
		        @Override
		        public int compare(UserComplain uc1, UserComplain uc2) {
		            return Integer.compare(uc2.getComplainNumber(), uc1.getComplainNumber());
		        }
		    });
		 
		 for (UserComplain uc : elementList) {
	            sortedModel.addElement(uc);
	        }
		 
		return sortedModel;
	}

}

