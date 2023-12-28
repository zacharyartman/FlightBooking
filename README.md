# Flight Booking System

## Introduction
The Flight Booking System is a comprehensive solution for managing flight operations and user bookings. It offers distinct functionalities for administrators to handle flight-related activities and for users to book flights conveniently.

## Administrator Features
- **Flight Management**: Ability to add or remove flights.
- **Passenger Management**: Add or remove passengers from flights.
- **Airport Expansion**: Facilitate expansion by adding new airports.

## User Features
- **Flight Search**: Search for flights using specific or general criteria.
- **Flight Booking**: Book available flights.
- **Manage Bookings**: View, change, or cancel booked flights.
- **Flight History**: Access past flight history.

## Getting Started: Database Setup
1. Download MySQL Community Edition: [MySQL Community Edition](https://dev.mysql.com/downloads/mysql/)
2. Download MySQL Workbench: [MySQL Workbench](https://www.mysql.com/products/workbench/)
3. Update your username and password in [DatabaseConnection.java](src/main/java/edu/usd/utils/DatabaseConnection.java).
4. Run [DatabaseTablesCreation](src/main/java/edu/usd/utils/DatabaseTablesCreation.java) to set up the database and tables.
    - Default admin credentials: **username:** zartman, **password:** password
    - Ensure the database username, server, and password are correct.

## Usage
- Built with IntelliJ and Gradle. Dependencies in `gradle.build`.
- Full coverage unit tests in the Test folder, under the com.zachartman package.
- Launch the GUI by running `Main.java`.
- User access: Create an account and log in.
- Admin access: Log in with airline-approved credentials (e.g., zartman/password).

## Additional Information
- Find airport latitude and longitude online. Use negative numbers for South and West coordinates.

**Example:**
<pre><code>Airport Name: San Diego International Airport
Airport Code: SAN
Airport Latitude: 32.732346
Airport Longitude: -117.196053</code></pre>

**ENJOY!**
