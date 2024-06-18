# GoNature Park Management System

## Project Description

GoNature is a comprehensive park management system developed using Java. It leverages JavaFX for its graphical user interface and utilizes OCSF (Object Client-Server Framework) for effective communication between client and server. This system follows the MVC (Model-View-Controller) architecture, which aids in organizing code and maintaining the application. The project was developed as part of an academic course using the Eclipse IDE to meet the needs of park facility management and visitor services.

## System Architecture

**Package Structure:**

- **gui:** Manages the graphical user interface using JavaFX.
- **client:** Contains the client-side operations and handles communication with the server.
- **server:** Manages server-side operations, including connections, data processing, and business logic.
- **entities:** Defines the data models, such as users, bookings, and park details.

**Development Environment:** The project is developed and maintained using Eclipse IDE, which supports Java and JavaFX development efficiently.

## System Design and Planning

Before development, extensive planning and design phases were conducted to understand the system requirements fully and structure the project effectively. This included creating UML diagrams and detailed use case scenarios to visualize system architecture and user interactions. Key design components included:

- **Use Case Diagrams:** Illustrate user interactions with the system.
- **Class Diagrams:** Show the system structure, including classes, attributes, methods, and relationships.
- **Activity Diagrams:** Detail the flow of activities within the system processes.
- **Sequence Diagrams:** Describe the sequence of operations among system components.

## System Requirements

- **Java:** JDK 11 or later
- **JavaFX:** For the graphical user interface
- **MySQL:** Database installation
- **OCSF:** For client-server communication
- **Eclipse IDE:** Recommended for development and debugging

## Employee Roles

**Roles and Responsibilities:**

- **Park Manager:**
  - Update park settings such as capacity, visit duration, and reservation limits.
  - Generate operational reports.
- **Department Manager:**
  - Approve changes made by Park Managers.
  - Create monthly reports on visitor activities and cancellations.
- **Service Worker:**
  - Register new guides.
- **Park Employee:**
  - Manage visitor entry and exit.
  - Check availability and approve entry for visitors without prior reservations if space is available.

## Visitor Management

- **Existing Reservation Check:** Visitors with prior bookings can log in using their ID number to manage their reservations.
- **New Visitor Booking:** New visitors can directly access the booking page.
  - Availability checks for preferred dates and times.
  - Option to join a waiting list if the requested time slot is unavailable.
  - Notifications sent to waiting list customers when slots become available.

## Database Interaction

GoNature uses a MySQL database for all operational data, including user details, bookings, and administrative changes. Proper database configuration is crucial for the system's functionality.

## Installation

To install and run the GoNature system locally, follow these steps:

1. Clone the repository:
    ```bash
    git clone https://github.com/GadAzriel/GoNarure.git
    ```

2. Navigate to the project directory:
    ```bash
    cd GoNarure
    ```

3. Set up the database:
    - Create a MySQL database.
    - Run the SQL scripts in the `database` directory to set up the necessary tables and data.

4. Build the project:
    - Open the project in Eclipse.
    - Build the project to resolve dependencies and compile the code.

5. Run the application:
    - Start the server using provided scripts or Eclipse run configurations.
    - Access the application at `http://localhost:8080`.

## Usage

1. **Visitor Reservations:** Users can register and log in to book their visits.
2. **Access Control:** Park staff can verify visitor identities and manage entry.
3. **Real-Time Data Management:** Admins can view real-time data on visitor counts and park capacity.
4. **Membership Management:** Users can manage their memberships and enjoy benefits.
5. **Automated Notifications:** Users receive booking confirmations, reminders, and updates.
6. **Operational Reports:** Admins can generate detailed reports on park operations.

## Demonstration Video

[![Watch the video](https://img.youtube.com/vi/mMXF7E2Wjjo/0.jpg)](https://www.youtube.com/watch?v=mMXF7E2Wjjo)

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any questions or suggestions, please contact the project maintainer at [gadazriel7@gmail.com](mailto:gadazriel7@gmail.com).
