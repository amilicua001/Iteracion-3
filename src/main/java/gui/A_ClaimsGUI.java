package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Complain;
import domain.User;

import java.awt.GridLayout;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class A_ClaimsGUI extends JFrame {
	private Admin admin;
	private BLFacade facade;
	private User user;

	private JPanel contentPane;
	private JList<Complain> ComplainsViewport =  new JList<Complain>();
	private Collection<Complain> ComplainsList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					A_ClaimsGUI frame = new A_ClaimsGUI(null, null, null);
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
	public A_ClaimsGUI(Admin admin, BLFacade facade, User user) {
		this.admin = admin;
		this.facade = facade;
		this.user = user;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(ComplainsViewport);
		updateScrollPane();
		getContentPane().add(scrollPane);
		
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnEliminateClaim = new JButton(
				ResourceBundle.getBundle("Etiquetas").getString("A_ClaimsGUI.EliminateClaim"));
		btnEliminateClaim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				facade.eliminateClaim(ComplainsViewport.getSelectedValue());
				updateScrollPane();
			}
		});
		panel.add(btnEliminateClaim);

		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame usersWithClaims = new A_SeeUsersWithClaims(admin, facade);
				usersWithClaims.setVisible(true);
				usersWithClaims.setLocation(A_ClaimsGUI.this.getLocation());
				jButtonClose_actionPerformed(e);
			}
		});
		panel.add(btnClose);
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	private void updateScrollPane() {
		ComplainsList = facade.getComplainsOfUser(user);
		DefaultListModel<Complain> model = new DefaultListModel<Complain>();
		for(Complain e: ComplainsList)
			model.addElement(e);
		
		ComplainsViewport.setModel(model);
	}
}
