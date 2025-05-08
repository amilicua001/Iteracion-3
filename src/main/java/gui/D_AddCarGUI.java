package gui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Car;
import domain.Driver;
import java.net.URL;

public class D_AddCarGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textRegistration;
	private JTextField textColour;
	private JLabel lblBrand;
	private JTextField textBrand;
	private JLabel lblModel;
	private JTextField textModel;
	private JLabel lblNPlaces;
	private JTextField textNPlaces;
	private JButton btnClose;
	private JButton btnAdd;
	private JLabel lblMsg;
	private JLabel lblNewLabel;
	private BLFacade facade;
	private Driver driver;
	private JComboBox<ImageIcon> carTypeComboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					D_AddCarGUI frame = new D_AddCarGUI(null,null);
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
	public D_AddCarGUI(Driver driver, BLFacade facade) {
		this.facade=facade;
		this.driver=driver;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblRegistration = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.Registration")); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(lblRegistration);
		
		textRegistration = new JTextField();
		contentPane.add(textRegistration);
		textRegistration.setColumns(10);
		
		JLabel lblColour = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.Colour")); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(lblColour);
		
		textColour = new JTextField();
		contentPane.add(textColour);
		textColour.setColumns(10);
		
		lblBrand = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.Brand")); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(lblBrand);
		
		textBrand = new JTextField();
		contentPane.add(textBrand);
		textBrand.setColumns(10);
		
		lblModel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.Model")); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(lblModel);
		
		textModel = new JTextField();
		contentPane.add(textModel);
		textModel.setColumns(10);
		
		lblNPlaces = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.NPlaces")); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(lblNPlaces);
		
		textNPlaces = new JTextField();
		contentPane.add(textNPlaces);
		textNPlaces.setColumns(10);

		// ComboBox de imagenes
		JLabel lblCarType = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.Tipo"));
		contentPane.add(lblCarType);

		carTypeComboBox = new JComboBox<>();
		carTypeComboBox.addItem(loadScaledIcon("/images/sedan.png", 100, 60));
		carTypeComboBox.addItem(loadScaledIcon("/images/suv.png", 100, 60));
		carTypeComboBox.addItem(loadScaledIcon("/images/pickup.png", 100, 60));
		contentPane.add(carTypeComboBox);
		
		btnAdd = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.Add")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String registration=textRegistration.getText();
				String colour=textColour.getText();
				String brand=textBrand.getText();
				String model=textModel.getText();
				int nPlaces=Integer.parseInt(textNPlaces.getText());
				
				Car car= new Car(registration, driver, colour, brand, model, nPlaces);
				Boolean done = facade.addCar(driver,car);
				
				if(done){
					driver.getCars().add(car);
					lblMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.Well"));
				}
				else
					lblMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.Error"));
					

				
			}
		});
		
		lblNewLabel=new JLabel(); //$NON-NLS-1$ //$NON-NLS-2
		contentPane.add(lblNewLabel);
		
		lblMsg = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(lblMsg);
		contentPane.add(btnAdd);
		
		btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setBounds(160, 212, 125, 41);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		contentPane.add(btnClose);
		
		
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		JFrame driverGUI = new DriverGUI(driver, facade);
		driverGUI.setLocation(D_AddCarGUI.this.getLocation());
		driverGUI.setVisible(true);
		this.setVisible(false);
	}
	private ImageIcon loadScaledIcon(String path, int width, int height) {
		ImageIcon icon = new ImageIcon(getClass().getResource(path));
		Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImage);
		}

	

}
