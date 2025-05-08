package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Review;
import domain.Booking;
import domain.Driver;
import domain.Traveler;

import javax.swing.JProgressBar;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JSlider;
import java.awt.Scrollbar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class T_ValorationGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JSlider slider;
	private JButton btnReview;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					T_ValorationGUI frame = new T_ValorationGUI(null,null);
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
	public T_ValorationGUI(Booking b,BLFacade facade) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		slider = new JSlider(0, 5, 2); 
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1); 
        
        contentPane.add(slider);
		
		textField = new JTextField();
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnReview = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.SendReview"));
		btnReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Driver d =b.getDriver();
				Traveler t= b.getTraveler();
				String id= d.toString()+t.toString()+b.getRide().getRideNumber();
				Review review= new Review(id, d, t, slider.getValue(), textField.getText());
				
				facade.addValoration(review);
				
				setVisible(false);
				JFrame tra = new TravelerGUI(b.getTraveler(), facade);
				tra.setVisible(true);
				tra.setLocation(T_ValorationGUI.this.getLocation());
			}
		});
		contentPane.add(btnReview);
		
	}

}
