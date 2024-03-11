package common;

public class Reservation {
	private Integer ReservationID;
	private String userID;
	private String parkName;
	private String numOfVisitors;
	private String date;
	private String time;
	private String phoneNum;
	private String email;
	private boolean prepayment;
	private String type;
	public  float entryTicket = 100;
	public Reservation(int reservationID, String userID, String parkName, String numOfVisitors, String date,
			String time, String phoneNum, String email, boolean prepayment, String type) {

		ReservationID = reservationID;
		this.userID = userID;
		this.parkName = parkName;
		this.numOfVisitors = numOfVisitors;
		this.date = date;
		this.time = time;
		this.phoneNum = phoneNum;
		this.email = email;
		this.prepayment=prepayment;
		this.type=type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getReservationID() {
		return ReservationID;
	}
	public void setReservationID(Integer reservationID) {
		ReservationID = reservationID;
	}
	public boolean isPrepayment() {
		return prepayment;
	}
	public void setPrepayment(boolean prepayment) {
		this.prepayment = prepayment;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getNumOfVisitors() {
		return numOfVisitors;
	}
	public void setNumOfVisitors(String numOfVisitors) {
		this.numOfVisitors = numOfVisitors;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return ""+parkName.toLowerCase()+","+ReservationID+","+userID+","+numOfVisitors+","+date+","+time+","+phoneNum+","+email+","+prepayment+","+type;
	}
	
	public  String calculatePriceSinglePreorder() {
		Double price = (Float.parseFloat(numOfVisitors) * entryTicket) * 0.85;
		return price.toString();	
	}
	
	public  String calculatePriceSingle() {
		Float price = Float.parseFloat(numOfVisitors) * entryTicket;
		return price.toString();	
	}
	
	public  String calculatePriceGroupPreorder() {
		Double price = ((Float.parseFloat(numOfVisitors) - 1) * entryTicket) * 0.75; // -1 because the group guide not pay
		return price.toString();	
	}
	
	public  String calculateaAnotherDiscount(String price) {
		Double finalPrice = Float.parseFloat(price) * 0.88;
		return finalPrice.toString();
	}
	
	public  String calculatePriceGroup() {
		Double price = ((Float.parseFloat(numOfVisitors)) * entryTicket) * 0.9; 
		return price.toString();	
	}
	
}
