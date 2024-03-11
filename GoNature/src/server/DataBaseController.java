package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
//import common.Commands;
import common.Reservation;
import common.Login;

public class DataBaseController {

	public static Connection con;
	// Auxiliary variables to track the serial number of an order for each park
	public static Integer resevationIDDisneyLand; 
	public static Integer resevationIDUniversal;
	public static Integer resevationIDAfek;
	
	// Auxiliary variables to track the queue number of waiting list for each park
	public static Integer queueIDDisneyLand; 
	public static Integer queueIDUniversal;
	public static Integer queueIDAfek;

	// Create a DataBaseController object and connect to the Database.
	public DataBaseController() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root", "Aa123456");
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		Statement stmt1,stmt2,stmt3;
		String query1 = "SELECT LastIDOrder FROM parks_details WHERE ParkName = 'disneyland'";
		String query2 = "SELECT LastIDOrder FROM parks_details WHERE ParkName = 'universal'";
		String query3 = "SELECT LastIDOrder FROM parks_details WHERE ParkName = 'afek'";
		try {
			stmt1 = con.createStatement();
			PreparedStatement ps1 = con.prepareStatement(query1);
			ResultSet rs1 = ps1.executeQuery(query1);
			stmt2 = con.createStatement();
			PreparedStatement ps2 = con.prepareStatement(query2);
			ResultSet rs2 = ps2.executeQuery(query2);
			stmt3 = con.createStatement();
			PreparedStatement ps3 = con.prepareStatement(query3);
			ResultSet rs3 = ps3.executeQuery(query3);
			rs1.next();
			resevationIDDisneyLand = rs1.getInt(1);
			rs2.next();
			resevationIDUniversal=rs2.getInt(1);
			rs3.next();
			resevationIDAfek = rs3.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Statement stmt4,stmt5,stmt6;
		String query4 = "SELECT QueueID FROM parks_details WHERE ParkName = 'disneyland'";
		String query5 = "SELECT QueueID FROM parks_details WHERE ParkName = 'universal'";
		String query6 = "SELECT QueueID FROM parks_details WHERE ParkName = 'afek'";
		try {
			stmt4 = con.createStatement();
			PreparedStatement ps4 = con.prepareStatement(query4);
			ResultSet rs4 = ps4.executeQuery(query4);
			stmt5 = con.createStatement();
			PreparedStatement ps5 = con.prepareStatement(query5);
			ResultSet rs5 = ps5.executeQuery(query5);
			stmt6 = con.createStatement();
			PreparedStatement ps6 = con.prepareStatement(query6);
			ResultSet rs6 = ps6.executeQuery(query6);
			rs4.next();
			queueIDDisneyLand = rs4.getInt(1);
			rs5.next();
			queueIDUniversal=rs5.getInt(1);
			rs6.next();
			queueIDAfek = rs6.getInt(1);
			System.out.println("Dis:" + queueIDDisneyLand);
			System.out.println("Uni:" + queueIDUniversal );
			System.out.println("Afek: " + queueIDAfek);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	
//	Client: sent all data of wanted order- HashMap<enum="newSingleReservation", ArrayList<String>**>
//	**:"userid, parkName, numOfVisitors, visitDate, visitTime, phoneNum, email>- all are string 
//	Server: pay attention: to add details of user in user table 
//	Client: to show only existing parks, check validity

	public static void InsertNewReservation(Reservation order) {
		Statement stmt;
		Integer reservationID = 0;
		try {
			if (order.getParkName().equals("disneyland"))
				reservationID = ++resevationIDDisneyLand;
			if (order.getParkName().equals("universal"))
				reservationID = ++resevationIDUniversal;
			if (order.getParkName().equals("afek"))
				reservationID = ++resevationIDAfek;
			order.setReservationID(reservationID);
			stmt = con.createStatement();
			String query = "INSERT INTO " + order.getParkName()
					+ " (ReservationID, UserID, NumOfVisitors, VisitDay, VisitTime, PhoneNum, Email, ReservationStatus, VisitorsType, Prepayment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = con.prepareStatement(query);

			ps.setString(1, reservationID.toString());
			ps.setString(2, order.getUserID());
			ps.setString(3, order.getNumOfVisitors());
			ps.setString(4, order.getDate());
			ps.setString(5, order.getTime());
			ps.setString(6, order.getPhoneNum());
			ps.setString(7, order.getEmail());
			ps.setString(8, "Approve");
			ps.setString(9, order.getType());
			if(order.isPrepayment())
				ps.setString(10, "true");
			else
				ps.setString(10, "false");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = "UPDATE parks_details SET LastIDOrder = '"+ reservationID + "' WHERE ParkName = '" +order.getParkName() + "'";
		try {
			stmt = con.createStatement();
			PreparedStatement ps = con.prepareStatement(query);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
			
	}
	
	
	public String checkReservationID(Reservation order) {
		String result;
		String query = "SELECT ReservationID FROM " + order.getParkName();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String id = rs.getString("ReservationID");
				if (id.equals(order.getReservationID().toString())) {
					result = "yes";
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = "no";
		return result;
	}

	public void InsertNewSingleVisitor(Reservation order) {
		Statement stmt;
		try {
			stmt = con.createStatement();
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO users (UserID, UserType, UserPassword, PhoneNum, Email) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, order.getUserID());
			ps.setString(2, "Single");
			ps.setString(3, ""); // TODO
			ps.setString(4, order.getPhoneNum());
			ps.setString(5, order.getEmail());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean CheckFreeSlots(Reservation order) {
		String maxOfVisitors = null,timeOfStay = null,difference =null;
		boolean result;
		Integer amoutOfVisitors =0 ,checkSolot = 0 ;
		String query = "SELECT MaxOfVisitors, TimeOfStay, Difference FROM parks_details WHERE ParkName = '"+order.getParkName()+"'";
		Statement stmt;
		try {
			stmt = con.createStatement();
			PreparedStatement ps = con.prepareStatement(query);
			//ps.setString(1, "'"+order.getParkName()+"'");
			ResultSet rs = ps.executeQuery(query);
			
			while (rs.next()) {
				maxOfVisitors = rs.getString("MaxOfVisitors");
				timeOfStay = rs.getString("TimeOfStay");
				difference = rs.getString("Difference");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] timeStay = timeOfStay.split(":");
		String[] timeParts = order.getTime().split(":");
		Integer hours = Integer.parseInt(timeParts[0]);
		hours = hours + Integer.parseInt(timeStay[0]);
        if(hours > 21)
        	hours = 21;
        String until = hours.toString()+ ":00";
        hours = Integer.parseInt(timeParts[0]);
		hours = hours - Integer.parseInt(timeStay[0])+1; //We put +1 because we need only 3 hours later
        if(hours < 10)
        	hours = 10;
        String from = hours.toString()+ ":00";
    	String query2 = "SELECT NumOFVisitors FROM " + order.getParkName() + " WHERE VisitDay = '" + order.getDate()+ "' AND VisitTime BETWEEN '" + from + "' AND '"+ until + "'";
    	
		
    	try {
			stmt = con.createStatement();
			stmt =  con.prepareStatement(query2);
			ResultSet rs = stmt.executeQuery(query2);
			while (rs.next()) {
				String s = rs.getString("NumOFVisitors");
				amoutOfVisitors  = amoutOfVisitors + Integer.parseInt(s);
			}
			String[] amountDifference = difference.split(":");
			checkSolot = Integer.parseInt(maxOfVisitors) - Integer.parseInt(amountDifference[0]) - amoutOfVisitors -Integer.parseInt(order.getNumOfVisitors());
			if (checkSolot >= 0) {
				result = true;
				return result;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = false;
		return result;
	}
	
	
	public ArrayList<String> CheckFreeSlotsByDate(Reservation order) {
		ArrayList<String> hours = new ArrayList<>();
		Integer checkHour = 10; //10 = the time that the park open
		while(checkHour < 21) {
			String sendHour = checkHour.toString()+ ":00";
			order.setTime(sendHour);
			if(CheckFreeSlots(order))
				hours.add(sendHour);
			checkHour++;
		}
		return hours;
	}
	
	public void EnterToWaitingList(Reservation order) {
		Statement stmt;
		Integer queueID = 0;
		String tableName = "waiting_list_" + order.getParkName();
		try {
			if (order.getParkName().equals("disneyland")) 
				queueID = ++queueIDDisneyLand;
			if (order.getParkName().equals("universal"))
				queueID = ++queueIDUniversal;
			if (order.getParkName().equals("afek"))
				queueID = ++queueIDAfek;
			stmt = con.createStatement();
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO " + tableName + " (QueueID, UserID, NumOFVisitors, VisitDay, VisitTime, PhoneNum, Email, VisitorsType) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, queueID.toString());
			ps.setString(2, order.getUserID());
			ps.setString(3, order.getNumOfVisitors()); 
			ps.setString(4, order.getDate());
			ps.setString(5, order.getTime());
			ps.setString(6, order.getPhoneNum());
			ps.setString(7, order.getEmail());
			ps.setString(8, order.getType());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = "UPDATE parks_details SET QueueID = '"+ queueID + "' WHERE ParkName = '" +order.getParkName() + "'";
		try {
			stmt = con.createStatement();
			PreparedStatement ps = con.prepareStatement(query);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void ChangeStatustoPayment(Reservation order) {
		Statement stmt;
		String query = "UPDATE " + order.getParkName() + " SET ReservationStatus = 'Payment' WHERE ReservationID = '" +order.getReservationID().toString()+ "'";
		try {
			stmt = con.createStatement();
			PreparedStatement ps = con.prepareStatement(query);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Statement stmt1;
		String query1 = "UPDATE " + order.getParkName() + " SET Prepayment = 'true' WHERE ReservationID = '" +order.getReservationID().toString()+ "'";
		try {
			stmt1 = con.createStatement();
			PreparedStatement ps1 = con.prepareStatement(query1);
			ps1.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String Login(Login user) {
		Statement stmt;
		String userName="",password="",connectionStatus="",userType="";
		String query = "SELECT UserName, UserPassword,ConnectionStatus,UserType FROM users WHERE UserName = '"+user.getID()+"'";
		try {
			stmt = con.createStatement();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery(query);
			while (rs.next()) {
				userName = rs.getString("UserName");
				password = rs.getString("UserPassword");
				connectionStatus = rs.getString("ConnectionStatus");
				userType = rs.getString("UserType");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(userName.equals("")) {
			return "ID not registered";
		}
		if(!password.equals(user.getPassword())) {
			return "Password invalid";
		}
		if(connectionStatus.equals("Connected")) {
			return "connected";
		}
		String query1 = "UPDATE users SET ConnectionStatus = 'Connected' WHERE UserName = '" +user.getID()+ "'";
		try {
			stmt = con.createStatement();
			PreparedStatement ps = con.prepareStatement(query1);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return userType;	
	}

	public void Logout(String user) {
		Statement stmt;
		String query = "UPDATE users SET ConnectionStatus = 'Disconnected' WHERE UserName = '" +user+ "'";
		try {
			stmt = con.createStatement();
			PreparedStatement ps = con.prepareStatement(query);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	



}
