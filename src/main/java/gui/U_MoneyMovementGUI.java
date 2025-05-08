package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
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

public class U_MoneyMovementGUI extends JFrame {

	private JPanel contentPane;
	private JList<String> moneyMovementLIst;
	private JScrollPane scrollPane;
	private DefaultListModel<String> moneyMovementModel;
	private BLFacade facade;
	private User user;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					U_MoneyMovementGUI frame = new U_MoneyMovementGUI(null,null);
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
	public U_MoneyMovementGUI(User user,BLFacade facade) {
		this.facade= facade; 
		this.user = user;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("User_MoneyMovementGUI.title"));
		
		setContentPane(contentPane);
		
		
		moneyMovementModel = new DefaultListModel<String>();
		moneyMovementModel.clear();
		for(MoneyMovement moneyMovement : facade.getMoneyMovement(user)) {
			moneyMovementModel.addElement(moneyMovement.toString());
		}
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		
		
		moneyMovementLIst = new JList<String>(moneyMovementModel);
		scrollPane.setViewportView(moneyMovementLIst);
		moneyMovementLIst.setModel(moneyMovementModel);
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		contentPane.add(btnClose);
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
			JFrame u_MoneyGUI = new U_MoneyGUI(user, facade);
			u_MoneyGUI.setVisible(true);
			u_MoneyGUI.setLocation(U_MoneyMovementGUI.this.getLocation());
			setVisible(false);
	
	}
}
