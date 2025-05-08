package dataAccess;

import java.io.File;

import java.net.NoRouteToHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JLabel;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.Alerta;
import domain.Review;
import domain.Booking;
import domain.Car;
import domain.MoneyMovement;
import domain.Driver;
import domain.Complain;
import domain.Ride;
import domain.Traveler;
import domain.User;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

import domain.Driver;
/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	private  EntityManager  db;
	private  EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

     public DataAccess()  {
		if (c.isDatabaseInitialized()) {
			String fileName=c.getDbFilename();

			File fileToDelete= new File(fileName);
			if(fileToDelete.delete()){
				File fileToDeleteTemp= new File(fileName+"$");
				fileToDeleteTemp.delete();

				  System.out.println("File deleted");
				} else {
				  System.out.println("Operation failed");
				}
		}
		open();
		if  (c.isDatabaseInitialized())initializeDB();
		
		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}
     
    public DataAccess(EntityManager db) {
    	this.db=db;
    }

	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();

		try {

		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=1; year+=1;}  
	    
		    Admin admin1=new Admin("admin1@gmail.com","Admin", "123", "bat");
		    //Create drivers 
			Driver driver1=new Driver("driver1@gmail.com","Aitor Fernandez", "123", "bat");
			Driver driver2=new Driver("driver2@gmail.com","Ane Gaztañaga", "123", "bat");
			Driver driver3=new Driver("driver3@gmail.com","Test driver", "123", "bat");
			Driver driver4=new Driver("driver4@gmail.com","gidari", "123", "bat");
			driver4.setPuntuazioa(5);
			
			//Create Travelers
			Traveler traveler1=new Traveler("traveler1@gmail.com","bidaiari", "123", "bat");
			traveler1.setMoney(100);
			//Create Cars
			Car car1 = new Car("Car1", driver1, "txuria", "Toyota", "Avensis", 5);
			driver1.getCars().add(car1);
			Car car2a = new Car("Car2a", driver2, "urdina", "Toyota", "Avensis", 5);
			driver2.getCars().add(car2a);
			Car car2b = new Car("Car2b", driver2, "beltza", "Toyota", "Avensis", 5);
			driver2.getCars().add(car2b);
			Car car3 = new Car("Car3", driver3, "grisa", "Toyota", "Avensis", 5);
			driver3.getCars().add(car3);
			Car kotxe1 = new Car("4678XXX", driver4, "beltza", "Toyota", "Avensis", 5);
			driver4.getCars().add(kotxe1);
			Car kotxe2 = new Car("1234XXX", driver4, "gorria", "Seat", "Ibiza", 5);
			driver4.getCars().add(kotxe2);
			
		
			driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7, car1);
			driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year,month,6), 4, 8,car1);
			driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 4, 4,car1);
			driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year,month,7), 4, 8,car1);
			driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 3, 3, car2a);
			driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 2, 5, car2a);
			driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year,month,6), 2, 5, car2b);
			driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,28), 2, 5, car2b);

			
			Ride ride1 = new Ride("Donosti", "Bilbo", UtilDate.newDate(year,month,6), 2, 3,driver4, kotxe1);
			Ride ride2 = new Ride("Bilbo", "Donostia", UtilDate.newDate(year,month,6), 2, 3,driver4, kotxe2);

			//Create bookings
			Booking booking1 = new Booking(driver4, traveler1, ride1, (int) ride1.getPrice(),ride1.getnPlaces() , "nota1");
			booking1.setState("Accepted");
			
			Booking booking2 = new Booking(driver4, traveler1, ride2, (int) ride2.getPrice(),ride2.getnPlaces() , "nota2");
			booking2.setState("Pending");
			
			//Create complains
			Complain claim1 = new Complain("1", traveler1, driver3, "Casi me tiro por la ventana, tenia puesto Melendi en bucle");
			Complain claim2 = new Complain("2", traveler1, driver3, "Me ha dejado a las afueras de Bilbo, en festivo, con huelga de buses. He tenido que ir andando hasta mi casa, 2 horas andando.");
			Complain claim3 = new Complain("3", traveler1, driver4, "A ido a 70 en una autopista de 120 durante 40 minutos");
			Complain claim4 = new Complain("4", traveler1, driver4, "Casi atropella un ciclista");
			Complain claim5 = new Complain("5", traveler1, driver4, "Se ha saltado un semaforo en rojo");
			Complain claim6 = new Complain("6", traveler1, driver2, "Ha conducido 3 km sin darse cuenta de que tenia los dos retrovisores cerrados");
			
			//Create alerts
			Alerta alert1 = new Alerta("Arbizu", "Legazpi", 4, UtilDate.newDate(year,month,28), 5, traveler1);
			Alerta alert2 = new Alerta("Zornotza", "Donosti", 4, UtilDate.newDate(year,month,16), 5, traveler1);
			
			
			//persist objects
			db.persist(admin1);
			db.persist(ride1);db.persist(ride2);
			db.persist(booking1); 
			db.persist(booking2);
			db.persist(driver1); db.persist(driver2); db.persist(driver3); db.persist(driver4);
			db.persist(traveler1);
			db.persist(claim1); db.persist(claim2); db.persist(claim3); db.persist(claim4); db.persist(claim5);db.persist(claim6);
			db.persist(alert1); db.persist(alert2);
			db.persist(car1); db.persist(car2a); db.persist(car2b); db.persist(car3); db.persist(kotxe1);db.persist(kotxe2);
			
			
			
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	public List<String> getDepartCities(){
			TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from", String.class);
			List<String> cities = query.getResultList();
			return cities;
		
	}
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from){
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from=?1 ORDER BY r.to",String.class);
		query.setParameter(1, from);
		List<String> arrivingCities = query.getResultList(); 
		return arrivingCities;
		
	}
	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail, Car car) throws  RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= "+from+" to= "+to+" driver="+driverEmail+" date "+date);
		try {
			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			db.getTransaction().begin();
			
			Driver driver = db.find(Driver.class, driverEmail);
			if (driver.doesRideExists(from, to, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			
			Ride ride = driver.addRide(from, to, date, nPlaces, price, car);
			//next instruction can be obviated
			db.persist(driver); 
			db.getTransaction().commit();

			db.getTransaction().begin();
			try {
				TypedQuery<Alerta> query = db.createQuery("SELECT DISTINCT a FROM Alerta a WHERE a.date= ?1",Alerta.class);
				query.setParameter(1, ride.getDate());
				Alerta alerta = query.getSingleResult();
				if(alerta != null 
						&& ride.getFrom().equals(alerta.getFrom()) 
						&& ride.getTo().equals(alerta.getTo()) 
						&& ride.getPrice() <= alerta.getPriceMax()
						&& ride.getnPlaces() >= alerta.getnPlaces()
						) { 
					if(alerta.getTraveler().getMoney()>=nPlaces * ride.getPrice()) {
						Booking booking = new Booking(driver, alerta.getTraveler(), ride, nPlaces * ride.getPrice(), nPlaces, "Automatic reserve");
					db.persist(booking);
					db.remove(alerta);
					alerta.getTraveler().getAlertList().remove(alerta);

					}
					
				}
			} catch (Exception e) {

			}
			db.getTransaction().commit();
			
			
			return ride;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
		
		
	}
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= "+from+" to= "+to+" date "+date);

		List<Ride> res = new ArrayList<>();	
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class);   
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, date);
		List<Ride> rides = query.getResultList();
	 	 for (Ride ride:rides){
		   res.add(ride);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRidesWithSuperDriver(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
	 	
		TypedQuery<Date> querySuper = db.createQuery("SELECT DISTINCT r.date FROM Ride r INNER JOIN r.driver d WHERE r.from = ?1 AND r.to = ?2 AND r.date BETWEEN ?3 AND ?4 AND d.puntuazioa >= 4", Date.class);
		querySuper.setParameter(1, from);
		querySuper.setParameter(2, to);
		querySuper.setParameter(3, firstDayMonthDate);
		querySuper.setParameter(4, lastDayMonthDate);
		List<Date> dates = querySuper.getResultList();
	 	 for (Date d:dates){
		   res.add(d);
		  }
	 	return res;
	 			
	}
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4",Date.class);
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
		   res.add(d);
		  }
	 	return res;
	 }
	

public void open(){
		
		String fileName=c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);
			  db = emf.createEntityManager();
    	   }
		System.out.println("DataAccess opened => isDatabaseLocal: "+c.isDatabaseLocal());

		
	}

	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}
	
	public Object isLogin (String name, String password) {
		
		TypedQuery<User> userQuery = db.createQuery("SELECT u FROM User u WHERE u.name=?1 AND u.password=?2", User.class);   
		userQuery.setParameter(1, name);
		userQuery.setParameter(2, password);
		
		try{
	        User user = userQuery.getSingleResult(); 
	        return user;
		}catch(Exception e){
			System.out.println(e.getLocalizedMessage());
		};
		
		return null;
	}
	
	public void addMoney(User user, float cant, String explain) {
		db.getTransaction().begin();
		
		
		User dbUser = db.find(user.getClass(), user.getEmail());
		
		
		MoneyMovement movement = new MoneyMovement(cant, explain , user);
		
		dbUser.setMoney(dbUser.getMoney() + cant);
		
		dbUser.getMoneyMovement().add(movement);
		
		db.getTransaction().commit();
		
	}
	
	public void withdrawMoney(User user, float cant, String explain) {
		db.getTransaction().begin();
		User dbUser = db.find(user.getClass(), user.getEmail());
		
		MoneyMovement movement = new MoneyMovement(cant, explain , user);
		
		dbUser.setMoney(dbUser.getMoney() - cant);
		
		dbUser.getMoneyMovement().add(movement);
	
		db.getTransaction().commit();
		
	}
	public float getBalance(User user) {
		db.getTransaction().begin();
		User dbUser = db.find(user.getClass(), user.getEmail());
		float balance = dbUser.getMoney();
		db.getTransaction().commit();
		return balance;
	}

	public boolean register(User u) {
		try {
			db.getTransaction().begin();
			db.persist(u);
			db.getTransaction().commit();			
		}
		catch(Exception e) {
			return false;
		}
		
		return true;
	}

	public boolean saveBooking(Booking booking) {
		try {
			db.getTransaction().begin();
			db.persist(booking);
			db.getTransaction().commit();
			
			Traveler dbTraveler = db.find(booking.getTraveler().getClass(), booking.getTraveler());
			
			float blockedMoney=booking.getBlockedMoney();
			this.withdrawMoney(dbTraveler,blockedMoney , "locked money" + " | " + booking.toString());
			
			db.getTransaction().begin();
			Ride dbRide = db.find(booking.getRide().getClass(), booking.getRide());
			dbRide.setnPlaces(dbRide.getnPlaces() - booking.getNPlaces());
			db.getTransaction().commit();
			
			booking.getTraveler().setMoney(dbTraveler.getMoney());
			
			
			return true;
		}catch(Exception e) {
			
			return false;
		}
	}

	public ArrayList<Booking> getBookingList(Driver driver) {
		TypedQuery<Booking> query = db.createQuery("SELECT b FROM Booking b WHERE b.driver=?1", Booking.class);   
		query.setParameter(1, driver);
		
		ArrayList<Booking> bookingList = (ArrayList<Booking>) query.getResultList();
		return bookingList;
	}

	public void Accept(Booking booking) {

		db.getTransaction().begin();
		
		Booking dbBooking = db.find(booking.getClass(), booking);
		dbBooking.setState("Accepted");
		
		db.getTransaction().commit();
		

	}
	
	public void Reject(Booking booking) {
		
		Traveler dbTraveler = db.find(booking.getTraveler().getClass(), booking.getTraveler());
		
		float blockedMoney=booking.getBlockedMoney();
		this.addMoney(dbTraveler, blockedMoney, "Returned money");
		
		Ride dbRide = db.find(booking.getRide().getClass(), booking.getRide());
		dbRide.setnPlaces(dbRide.getnPlaces() + booking.getNPlaces());
		
		
		db.getTransaction().begin();
		
		Booking dbBooking = db.find(booking.getClass(), booking);
		dbBooking.setState("Rejected");
		
		db.getTransaction().commit();

	}
 
	public Collection<Booking> getBookings(Traveler traveler) {
		TypedQuery<Booking> query = db.createQuery("SELECT b FROM Booking b WHERE b.traveler=?1", Booking.class);   
		query.setParameter(1, traveler);
		
		ArrayList<Booking> bookingList = (ArrayList<Booking>) query.getResultList();
		return bookingList;		
		
	}

	public Collection<Ride> getDriverRides(Driver driver) {
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.driver=?1", Ride.class);   
		query.setParameter(1, driver);
		
		ArrayList<Ride> rideList = (ArrayList<Ride>) query.getResultList();
		return rideList;	
	}

	public void cancelDriverRide(Ride ride) {
		
			Ride dbRide = db.find(ride.getClass(), ride);
			Driver driver = ride.getDriver();
			
			TypedQuery<Booking> query = db.createQuery("SELECT b FROM Booking b WHERE  b.ride = ?1", Booking.class); 
			query.setParameter(1, ride);
			ArrayList<Booking> bookingList = (ArrayList<Booking>) query.getResultList();
			for(Booking booking : bookingList) {
				Reject(booking); 
				db.getTransaction().begin();
				db.remove(booking);
				db.getTransaction().commit();
			}
			db.getTransaction().begin();
			
			db.remove(dbRide);
			
			Driver dbDriver = db.find(driver.getClass(), driver);
			dbDriver.getRides().remove(dbRide);
			
			db.getTransaction().commit();
	}

	public boolean addCar(Driver driver, Car car) {
		boolean done=true;
		try {
			db.getTransaction().begin();
			Driver dbDriver=db.find(driver.getClass(), driver);
			dbDriver.getCars().add(car);
			db.getTransaction().commit();
		} catch (Exception e) {
			done=false;
		}
		return done;	
	}

	public void tripDone(Booking booking) {
		db.getTransaction().begin();
		Booking dbBooking=db.find(booking.getClass(), booking);
		dbBooking.setState("TripDone");
		Driver dbDriver=db.find(booking.getDriver().getClass(), booking.getDriver());
		db.getTransaction().commit();
		
		float blockedMoney=booking.getBlockedMoney();
		addMoney(dbDriver, booking.getBlockedMoney(),"Done");
		db.getTransaction().begin();
		dbBooking.setBlockedMoney(0);
		db.getTransaction().commit();
		
	}

	public Collection<MoneyMovement> getMoneyMovement(User user) {
		TypedQuery<MoneyMovement> query = db.createQuery("SELECT m FROM MoneyMovement m WHERE m.user=?1", MoneyMovement.class);   
		query.setParameter(1, user);
		
		ArrayList<MoneyMovement> rideList = (ArrayList<MoneyMovement>) query.getResultList();
		return rideList;		
	}

	public void addValoration(Review review) {
		try {
			db.getTransaction().begin();
			db.persist(review);
			
			Driver dbDriver = db.find(Driver.class, review.getD());
			dbDriver.setReviewCop(dbDriver.getReviewCop()+1);
			
			float newPuntuazioa=review.getPuntuazioa();
			if(dbDriver.getPuntuazioa()!=-1) {
				newPuntuazioa= ( dbDriver.getPuntuazioa() + review.getPuntuazioa() )/2;
			}
			dbDriver.setPuntuazioa(newPuntuazioa);
			
			db.getTransaction().commit();
			
			
		} catch (Exception e) {
			
		}
		
	}
	
	public Collection<Review> getDriverValorations(Driver driver) {
		TypedQuery<Review> query = db.createQuery("SELECT bal FROM Review bal WHERE bal.d=?1", Review.class);   
		query.setParameter(1, driver);
		
		ArrayList<Review> reviewList = (ArrayList<Review>) query.getResultList();
		return reviewList;		
	}

	public void sendComplain(User from, User to, String text) {
		try {
			db.getTransaction().begin();
			String id = "id"+from.getEmail()+text;
			Complain complain = new Complain(id, from, to, text);
			
			db.persist(complain);
			
			db.getTransaction().commit();
			
			
		} catch (Exception e) {
		}

	}
	
	public Collection<User> getUsersWihComplains() {
		try {
			TypedQuery<User> query = db.createQuery("SELECT DISTINCT e.to FROM Complain e", User.class);  
			
			Collection<User> userList = query.getResultList();
			return userList;
		} catch (Exception e) {
			return new ArrayList<User>();
		}		
	}

	public Collection<Complain> getComplainsOfUser(User user) {
		try {
			System.out.println("USEER  "+ user.toString());
			
			TypedQuery<Complain> kontsulta = db.createQuery("SELECT e FROM Complain e WHERE e.to=?1", Complain.class);  
			kontsulta.setParameter(1, user);
			
			ArrayList<Complain> complainList = (ArrayList<Complain>) kontsulta.getResultList();
			System.out.println(complainList.toString());
			return complainList;
		} catch (Exception e) {
			return new ArrayList<Complain>();
		}			
	}

	public void eliminateClaim(Complain claim) {
		db.getTransaction().begin();
		try {
		Complain dbClaim = db.find(Complain.class, claim);
		db.remove(dbClaim);
		}catch(Exception e) {}
		db.getTransaction().commit();
		
	}

	public void createAlert(String from, String to, Date date, int nPlaces, float priceMax, String travelerEmail) throws RideMustBeLaterThanTodayException, RideAlreadyExistException {

			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			db.getTransaction().begin();
			
			Traveler dbTraveler = db.find(Traveler.class, travelerEmail);
			
			if (dbTraveler.doesAlertExists(from, to, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			
			Alerta alerta = new Alerta(from, to, nPlaces, date, priceMax, dbTraveler);
			
			dbTraveler.getAlertList().add(alerta);
			
			db.persist(alerta);
			db.getTransaction().commit();

		
	}
	public List<Date> getThisMonthDatesWithAlerts(Date date) {
		List<Date> res = new ArrayList<>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT a.date FROM Alerta a WHERE a.date BETWEEN ?1 and ?2",Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
		   res.add(d);
		  }
	 	return res;
	 }

	public List<Alerta> getAlertsFromDay(Date date) {
		TypedQuery<Alerta> query = db.createQuery("SELECT a FROM Alerta a WHERE a.date = ?1" , Alerta.class);
		query.setParameter(1, date);
		List<Alerta> alertList = query.getResultList();

		return alertList;
	}

	public int getComplainNumberOfUser(User user) {
		try {
			return this.getComplainsOfUser(user).size();
		} catch (Exception e) {
			return 0;
		}			
	}

	public ArrayList<Car> getDriverCars(Driver driver) {
		TypedQuery<Car> query = db.createQuery("SELECT c FROM Car c WHERE c.driver=?1", Car.class);   
        query.setParameter(1, driver);       
        ArrayList<Car> carList = (ArrayList<Car>) query.getResultList();
        return carList;
               
	}
}
