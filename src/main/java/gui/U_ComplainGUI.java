package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import businessLogic.BLFacade;
import domain.Booking;
import domain.Driver;
import domain.Traveler;
import domain.User;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class U_ComplainGUI extends JFrame {
	private BLFacade facade;
	private User from;
	private JTextField text;
	
	public U_ComplainGUI(Booking b, User from, User to, BLFacade facade) {
		this.facade= facade; 
		this.from = from;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		getContentPane().setLayout(new GridLayout(4, 0, 0, 0));
		
		JPanel panel1 = new JPanel();
		getContentPane().add(panel1);
		panel1.setLayout(new GridLayout(0, 2, 0, 0));
	
		JLabel lblFrom = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("U_ComplainGUI.lblFrom")); //$NON-NLS-1$ //$NON-NLS-2$
		panel1.add(lblFrom);
		
		JLabel lfblFromInfo = new JLabel(from.getName()); //$NON-NLS-1$ //$NON-NLS-2$
		panel1.add(lfblFromInfo);
		
		JLabel lblTo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("U_ComplainGUI.lblTo")); //$NON-NLS-1$ //$NON-NLS-2$
		panel1.add(lblTo);
		
		JLabel lblToInfo = new JLabel(to.getName()); //$NON-NLS-1$ //$NON-NLS-2$
		panel1.add(lblToInfo);
		
		JLabel lblText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("U_ComplainGUI.lblText")); //$NON-NLS-1$ //$NON-NLS-2$
		getContentPane().add(lblText);
		
		text = new JTextField();
		getContentPane().add(text);
		text.setColumns(10);
		
		JPanel panel2 = new JPanel();
		getContentPane().add(panel2);
		panel2.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		panel2.add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					jButtonClose_actionPerformed(e);
			}
		});
		
		JButton btnSendComplain = new JButton(ResourceBundle.getBundle("Etiquetas").getString("U_ComplainGUI.complain"));
		btnSendComplain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				facade.sendComplain(from, to, text.getText());				
			}
		});
		panel2.add(btnSendComplain);
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		if(from.getClass().equals(Driver.class)) {
			JFrame a = new DriverGUI(from, facade);
			a.setVisible(true);
			a.setLocation(U_ComplainGUI.this.getLocation());
		}else if(from.getClass().equals(Traveler.class)) {
			JFrame a = new T_ReservationStatusGUI((Traveler) from, facade);
			a.setVisible(true);
			a.setLocation(U_ComplainGUI.this.getLocation());
		}
		this.setVisible(false);

	
	}
}
