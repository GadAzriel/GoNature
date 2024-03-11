//// This file contains material supporting section 3.7 of the textbook:
//// "Object Oriented Software Engineering" and is issued under the open-source
//// license found at www.lloseng.com 
//package server;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Set;
//
//import common.Reservation;
//import common.Login;
//import ocsf.server.*;
//import logic.Commands;
//
///**
// * This class overrides some of the methods in the abstract superclass in order
// * to give more functionality to the server.
// *
// * @author Dr Timothy C. Lethbridge
// * @author Dr Robert Lagani&egrave;re
// * @author Fran&ccedil;ois B&eacute;langer
// * @author Paul Holden
// * @version July 2000
// */
//public class EchoServer extends AbstractServer {
//	// Class variables *************************************************
//
//	/**
//	 * The default port to listen on.
//	 */
//	final public static int DEFAULT_PORT = 5555;
//	DataBaseController db = new DataBaseController();
//	// public static ArrayList<ConnectedClient> clientConnections;
//	// Constructors ****************************************************
//
//	/**
//	 * Constructs an instance of the echo server.
//	 *
//	 * @param port The port number to connect on.
//	 */
//	public EchoServer(int port) {
//		super(port);
//		clientConnections = new ArrayList<>();
//	}
//
//	// Instance methods ************************************************
//
//	/**
//	 * This method handles any messages received from the client.
//	 *
//	 * @param msg    The message received from the client.
//	 * @param client The connection from which the message originated.
//	 * @throws IOException
//	 */
//	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
//		if (msg == null)
//			try {
//				client.sendToClient(null);
//			} catch (Exception e) {
//			}
//		;
//		HashMap<Commands, Object> answer = new HashMap<>();
//		if (msg instanceof HashMap<?, ?>) {
//			HashMap<Commands, Object> message = (HashMap<Commands, Object>) msg;
//			Set<Commands> commandSet = message.keySet();
//			ArrayList<Commands> commandList = new ArrayList<>(commandSet);
//			Reservation order;
//			Login user;
//			String userDetails;
//			if((message.get(commandList.get(0))) instanceof Reservation) {
//				order = (Reservation) (message.get(commandList.get(0)));
//			}
//			if((message.get(commandList.get(0))) instanceof Login) {
//				 user = (Login) (message.get(commandList.get(0)));
//			}
//			if((message.get(commandList.get(0))) instanceof String) {
//				userDetails = (String) (message.get(commandList.get(0)));
//			}
//			switch (commandList.get(0).toString()) {
//			case "NEW_SINGLE_RESERVATION":
//				try {
//					if (db.CheckFreeSlots(order)) {
//						db.InsertNewReservation(order);
//						db.InsertNewSingleVisitor(order);
//						answer.put(Commands.CONFIRMATION_SINGLE_RESERVATION, order.getReservationID().toString()); /// Send
//																													/// only
//																													/// command
//																													/// &
//																													/// ReservationID
//					} else
//						answer.put(Commands.CONFIRMATION_SINGLE_RESERVATION, "no");
//					client.sendToClient(answer);
//				} catch (Exception e) {
//				}
//				;
//				break;
//			case "ENTER_WAITING_LIST":
//				try {
//					db.EnterToWaitingList(order);
//					answer.put(Commands.ENTER_WAITING_LIST, "yes");
//					client.sendToClient(answer);
//				} catch (Exception e) {
//				}
//				;
//				break;
//			case "FREE_SLOTS_DATA":
//				try {
//					ArrayList<String> freeSlots = db.CheckFreeSlotsByDate(order);
//					answer.put(Commands.FREE_SLOTS_DATA, freeSlots);
//					client.sendToClient(answer);
//				} catch (Exception e) {
//				}
//				;
//				break;
//			case "NEW_GROUP_RESERVATION":
//				try {
//					if (db.CheckFreeSlots(order)) {
//						db.InsertNewReservation(order);
//						answer.put(Commands.CONFIRMATION_GROUP_RESERVATION, order.getReservationID().toString()); /// Send
//																													/// only
//																													/// command
//																													/// &
//																													/// ReservationID
//					} else
//						answer.put(Commands.CONFIRMATION_GROUP_RESERVATION, "no");
//					client.sendToClient(answer);
//				} catch (Exception e) {
//				}
//				;
//				break;
//			case "PREPAYMENT":
//				try {
//					String price = order.calculateaAnotherDiscount(order.calculatePriceGroupPreorder());
//					answer.put(Commands.PREPAYMENT, price);
//					db.ChangeStatustoPayment(order);
//					client.sendToClient(answer);
//				} catch (Exception e) {
//				}
//				;
//				break;
//			case "CHECK_ID_PASSWORD":
//				try {
//					String userPermission=db.Login(user);
//					answer.put(Commands.CHECK_ID_PASSWORD, userPermission);
//					client.sendToClient(answer);
//				} catch (Exception e) {
//				};
//				break;
//			case "Logout":
//				db.Logout(userDetails);
//				break;
//			}
//		}
//	}
//
//	/**
//	 * This method overrides the one in the superclass. Called when the server
//	 * starts listening for connections.
//	 */
//	protected void serverStarted() {
//		System.out.println("Server listening for connections on port " + getPort());
//	}
//
//	/**
//	 * This method overrides the one in the superclass. Called when the server stops
//	 * listening for connections.
//	 */
//	protected void serverStopped() {
//		System.out.println("Server has stopped listening for connections.");
//	}
//
//	// Class methods ***************************************************
//
//	/**
//	 * This method is responsible for the creation of the server instance (there is
//	 * no UI in this phase).
//	 *
//	 * @param args[0] The port number to listen on. Defaults to 5555 if no argument
//	 *                is entered.
//	 */
//	public static void main(String[] args) {
//		int port = 0; // Port to listen on
//
//		try {
//			port = Integer.parseInt(args[0]); // Get port from command line
//		} catch (Throwable t) {
//			port = DEFAULT_PORT; // Set port to 5555
//		}
//
//		EchoServer sv = new EchoServer(port);
//
//		try {
//			sv.listen(); // Start listening for connections
//		} catch (Exception ex) {
//			System.out.println("ERROR - Could not listen for clients!");
//		}
//	}
//
//////////////////////////////////////////////////////////////////////
//	@Override
//	protected void clientConnected(ConnectionToClient client) {
//		String clientHost = client.getInetAddress().getHostName();
//		String clientIP = client.getInetAddress().getHostAddress();
//
//		ConnectedClient connectedClient = new ConnectedClient(client, clientHost, clientIP);
//		clientConnections.add(connectedClient);
//
//		System.out.println("Client connected: " + clientHost + " (" + clientIP + ")");
//	}
//
//}
////End of EchoServer class
