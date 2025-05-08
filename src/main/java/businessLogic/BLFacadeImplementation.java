package businessLogic;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.swing.JFrame;
import javax.swing.JLabel;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Ride;
import domain.Traveler;
import domain.User;
import domain.Alerta;
import domain.Review;
import domain.Booking;
import domain.Car;
import domain.MoneyMovement;
import domain.Driver;
import domain.Complain;
import exceptions.RideMustBeLaterThanTodayException;
import gui.DriverGUI;
import gui.NU_LoginGUI;
import gui.TravelerGUI;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		
		
		    dbManager=new DataAccess();
		    
		//dbManager.close();

		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		dbManager=da;		
	}
    
    
    /**
     * {@inheritDoc}
     */
    @WebMethod public List<String> getDepartCities(){
    	dbManager.open();	
		
		 List<String> departLocations=dbManager.getDepartCities();		

		dbManager.close();
		
		return departLocations;
    	
    }
    /**
     * {@inheritDoc}
     */
	@WebMethod public List<String> getDestinationCities(String from){
		dbManager.open();	
		
		 List<String> targetCities=dbManager.getArrivalCities(from);		

		dbManager.close();
		
		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String driverEmail, Car car ) throws RideMustBeLaterThanTodayException, RideAlreadyExistException{
	   
		dbManager.open();
		Ride ride=dbManager.createRide(from, to, date, nPlaces, price, driverEmail, car);		
		dbManager.close();
		return ride;
   };
	
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Ride> getRides(String from, String to, Date date){
		dbManager.open();
		List<Ride>  rides=dbManager.getRides(from, to, date);
		dbManager.close();
		return rides;
	}

	@WebMethod 
	public List<Date> getThisMonthDatesWithRidesWithSuperDriver(String from, String to, Date date){
		dbManager.open();
		List<Date>  dates=dbManager.getThisMonthDatesWithRidesWithSuperDriver(from, to, date);
		dbManager.close();
		return dates;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@WebMethod 
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		dbManager.open();
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		dbManager.close();
		return dates;
	}
	
	
	@WebMethod public void close() {
		DataAccess dB4oManager=new DataAccess();

		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}
    
    @WebMethod public Object isLogin(String name, String password) {
    	dbManager.open();  	
    	Object  type =dbManager.isLogin(name, password);
    	dbManager.close();
    	return type;
    }

    
    @WebMethod public void addMoney(User user, Integer cant, String explain) {
		dbManager.open();
		dbManager.addMoney(user, cant, explain);
		dbManager.close();
	
	}


    @WebMethod public float getNewBalance(User user) {
		dbManager.open();
		float balance = dbManager.getBalance(user);
		dbManager.close();
		return balance;
	}
	
    
    @WebMethod public void withdrawMoney(User user, Integer cant, String explain) {
		
		dbManager.open();
		dbManager.withdrawMoney(user, cant, explain);
		dbManager.close();
	}

	
	@WebMethod public boolean register(User u) {
		dbManager.open();
		boolean ema =dbManager.register(u);
		dbManager.close();
		return ema;
	}

	
	@WebMethod public boolean saveBooking(Booking booking) {
		if(booking.getTraveler().getMoney() < booking.getBlockedMoney()) {
			return false;
		}
		dbManager.open();
		boolean saved = dbManager.saveBooking(booking);
		dbManager.close();
		return saved;
	}

	
	@WebMethod public ArrayList<Booking> getBookingList(Driver driver) {
		dbManager.open();
		ArrayList<Booking> bookingList = dbManager.getBookingList(driver);
		dbManager.close();
		return bookingList;
	}


	@WebMethod public void accept(Booking booking) {
		dbManager.open();
		dbManager.Accept(booking);
		dbManager.close();
	}
	
	
	@WebMethod public void reject(Booking booking) {
		dbManager.open();
		dbManager.Reject(booking);
		dbManager.close();
	}

	
	@WebMethod public Collection<Booking> getBookings(Traveler traveler) {
		dbManager.open();
		Collection<Booking> bookingList =dbManager.getBookings(traveler);
		dbManager.close();	
		return bookingList;
	}

	@WebMethod 
	public Collection<Ride> getDriverRides(Driver driver) {
		dbManager.open();
		Collection<Ride> rideList =dbManager.getDriverRides(driver);
		dbManager.close();	
		return rideList;
	}

	@WebMethod 
	public void cancelDriverRide(Ride ride) {
		dbManager.open();
		dbManager.cancelDriverRide(ride);
		dbManager.close();
		
	}

	@WebMethod 
	public boolean addCar(Driver driver, Car car) {
		dbManager.open();
		boolean done = dbManager.addCar(driver, car);
		dbManager.close();
		return done;
	}

	@WebMethod 
	public void tripDone(Booking booking) {
		dbManager.open();
		dbManager.tripDone(booking);
		dbManager.close();
	}

	@WebMethod 
	public Collection<MoneyMovement> getMoneyMovement(User user) {
		dbManager.open();
		Collection<MoneyMovement> movements= dbManager.getMoneyMovement(user);
		dbManager.close();
		return movements;
		

	}

	@WebMethod 
	public void addValoration(Review review) {
		dbManager.open();
		dbManager.addValoration(review);
		dbManager.close();
		
	}

	@WebMethod 
	public Collection<Review> getReviews(Driver d) {
		dbManager.open();
		Collection<Review> movements= dbManager.getDriverValorations(d);
		dbManager.close();
		return movements;
	}

	@WebMethod 
	public void sendComplain(User from, User to, String lblText) {
		dbManager.open();
		
		dbManager.sendComplain(from, to, lblText);
		
		dbManager.close();
	}
	@WebMethod public Collection<User> getUsersWihComplains(){
		dbManager.open();
		
		Collection<User> userList = dbManager.getUsersWihComplains();
		
		dbManager.close();
		return userList;
	}
	@WebMethod public Collection<Complain> getComplainsOfUser(User user){
		dbManager.open();
		
		Collection<Complain> complainList = dbManager.getComplainsOfUser(user);
		
		dbManager.close();
		return complainList;
	}

	@WebMethod
	public void eliminateClaim(Complain claim) {
		dbManager.open();
		dbManager.eliminateClaim(claim);
		dbManager.close();
		
	}

	@WebMethod
	public void createAlert(String from, String to, Date date, int nPlaces, float priceMax, String TravelerEmail) throws RideMustBeLaterThanTodayException, RideAlreadyExistException {
		dbManager.open();
		dbManager.createAlert(from,to,date,nPlaces,priceMax,TravelerEmail);
		dbManager.close();
	}

	@WebMethod
	public List<Date> getDatesWithAlertCurrentMonth(Date date) {
		dbManager.open();
		List<Date> dates = dbManager.getThisMonthDatesWithAlerts(date);
		dbManager.close();
		return dates;
	}

	@WebMethod
	public List<Alerta> getAlertsFromDay(Date date) {
		dbManager.open();
		List<Alerta> dates = dbManager.getAlertsFromDay(date);
		dbManager.close();
		return dates;
	}

	
	@WebMethod 
	public int getComplainNumberOfUser(User user) {
		dbManager.open();
		int n= dbManager.getComplainNumberOfUser(user);
		dbManager.close();
		
		return n;
	}

	@Override
	public ArrayList<Car> getDriverCars(Driver driver) {
		dbManager.open();
        ArrayList<Car> cars = dbManager.getDriverCars(driver);
        dbManager.close();
        return cars;
	}
}

