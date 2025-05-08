package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Traveler;
import domain.User;

import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class U_MoneyGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JTextField errorField = new JTextField();
	private JTextField Account;
	private JTextField changeText;
	private BLFacade facade;
	private User user;

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					U_MoneyGUI frame = new U_MoneyGUI(null,null);
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
	public U_MoneyGUI(User user,BLFacade facade) {
		this.facade= facade; 
		this.user= user;
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.this.title")); //$NON-NLS-1$ //$NON-NLS-2$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		Account = new JTextField();
		Account.setEditable(false);
		Account.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Balance")+user.getMoney()); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(Account);
		Account.setColumns(10);
		
		changeText = new JTextField();
		changeText.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(changeText);
		changeText.setColumns(10);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		
		JButton btnDeposit = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Deposit")); //$NON-NLS-1$ //$NON-NLS-2$
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try { 
					errorField.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
					int amount = Integer.parseInt(changeText.getText());
					
					if(amount <= 0) {
						
						errorField.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.NotNatural")); //$NON-NLS-1$ //$NON-NLS-2$
						
					}else {
						
						facade.addMoney(user, amount, ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Deposit"));
						
						Account.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Balance")+getNewBalance(user, facade));
						user.setMoney(getNewBalance(user, facade));
					}
				}catch (Exception err){
					
					errorField.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.NotNatural")); //$NON-NLS-1$ //$NON-NLS-2$
					
				}

				
			}
		});
		panel.add(btnDeposit);
		
		JButton btnWithdraw = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Withdraw")); //$NON-NLS-1$ //$NON-NLS-2$
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try { 
					errorField.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
					int amount = Integer.parseInt(changeText.getText());
					if(amount <= 0 || amount> getNewBalance(user,facade)) {
						errorField.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Big")); //$NON-NLS-1$ //$NON-NLS-2$
						
					}else {
						
						facade.withdrawMoney(user, amount, ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Withdraw"));
						
						Account.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Balance")+getNewBalance(user, facade)); 
						user.setMoney(getNewBalance(user, facade));
					}
				}catch (Exception err){
					errorField.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.NotNatural")); //$NON-NLS-1$ //$NON-NLS-2$
					
				}

				
			}
		});
		panel.add(btnWithdraw);
		
		errorField.setEditable(false);
		errorField.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(errorField);
		errorField.setColumns(10);
		
		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					jButtonClose_actionPerformed(e);
			}
		});
		contentPane.add(jButtonClose);
		
		JButton btnSeeMoneyMovements = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.btnSeeMoneyMovements")); //$NON-NLS-1$ //$NON-NLS-2$
		btnSeeMoneyMovements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame moneyMovementFrame = new U_MoneyMovementGUI(user, facade);
				moneyMovementFrame.setVisible(true);
				moneyMovementFrame.setLocation(U_MoneyGUI.this.getLocation());
				setVisible(false);
			}
		});
		contentPane.add(btnSeeMoneyMovements);
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		if(user.getClass().equals(Driver.class)) {
			JFrame driverGUI = new DriverGUI(user, facade);
			driverGUI.setVisible(true);
			driverGUI.setLocation(U_MoneyGUI.this.getLocation());
		}else if(user.getClass().equals(Traveler.class)) {
			JFrame travelerGUI = new TravelerGUI(user, facade);
			travelerGUI.setVisible(true);
			travelerGUI.setLocation(U_MoneyGUI.this.getLocation());
		}
		this.setVisible(false);
	}
	
	public float getNewBalance (User user, BLFacade facade) {
		return facade.getNewBalance(user);
	}
}

