package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import common.Reservation;
import common.Login;
import logic.Commands;

public class Main {
	public static void main(String[] args) {
		DataBaseController db = new DataBaseController();
		Reservation order = new Reservation(1, "123456789", "afek", "15", "12-12-2024", "11:00", "042752722",
				"ahfsdg", false, "Group");
		Login user = new Login("1111","");
	
		
		String userDetails="1111";
		// System.out.println(Helper.calculatePriceGroup(order));
		HashMap<Commands, Object> message = new HashMap<>();
		message.put(Commands.CHECK_ID_PASSWORD, user);
		HashMap<Commands, Object> answer = new HashMap<>();
		Set<Commands> commandSet = message.keySet();
		ArrayList<Commands> commandList = new ArrayList<>(commandSet);
		// Reservation order = (Reservation)(message.get(command);
		switch (commandList.get(0).toString()) {
		case "NEW_SINGLE_RESERVATION":
			try {
				if (db.CheckFreeSlots(order)) {
					db.InsertNewReservation(order);
					db.InsertNewSingleVisitor(order);
					answer.put(Commands.CONFIRMATION_SINGLE_RESERVATION, order.getReservationID().toString()); /// Send
																												/// only
																												/// command
																												/// &
																												/// ReservationID
				} else
					answer.put(Commands.CONFIRMATION_SINGLE_RESERVATION, "no");
				System.out.println(answer);
			} catch (Exception e) {
			}
			;
			break;
		case "ENTER_WAITING_LIST":
			try {
				db.EnterToWaitingList(order);
				answer.put(Commands.ENTER_WAITING_LIST, "yes");
				System.out.println(answer);
			} catch (Exception e) {
			}
			;
			break;
		case "FREE_SLOTS_DATA":
			try {
				ArrayList<String> freeSlots = db.CheckFreeSlotsByDate(order);
				answer.put(Commands.FREE_SLOTS_DATA, freeSlots);
				System.out.println(answer);
			} catch (Exception e) {
			}
			;
			break;
		case "NEW_GROUP_RESERVATION":
			try {
				if (db.CheckFreeSlots(order)) {
					db.InsertNewReservation(order);
					answer.put(Commands.CONFIRMATION_GROUP_RESERVATION, order.getReservationID().toString()); /// Send
																												/// only
																												/// command
																												/// &
																												/// ReservationID
				} else
					answer.put(Commands.CONFIRMATION_GROUP_RESERVATION, "no");
				System.out.println(answer);
			} catch (Exception e) {
			}
			;
			break;
		case "PREPAYMENT":
			try {
				String price = order.calculateaAnotherDiscount(order.calculatePriceGroupPreorder());
				answer.put(Commands.PREPAYMENT, price);
				db.ChangeStatustoPayment(order);
				System.out.println(answer);
			} catch (Exception e) {
			}
			;
			break;
		case "CHECK_ID_PASSWORD":
			try {
				String userPermission=db.Login(user);
				answer.put(Commands.CHECK_ID_PASSWORD, userPermission);
				System.out.println(answer);
			} catch (Exception e) {
			};
			break;
		case "Logout":
			db.Logout(userDetails);
				break;
		}

	}
}
