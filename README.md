**Flight Booking**

A flight booking system where administrators can make adjustments and changes to flight-related matters, and users can conveniently book flights.

Upon successful verification of admin login, administrators have the following management capabilities:
  - Add/Remove existing flights
  - Add/Remove passengers from designated flights
  - Add airport allowing for expansion

Upon account creation and login, users have the following capabilities:
  - Search for available flights based on specified parameters or generalized
  - Book flights
  - View/Change/Cancel booked flights
  - Access past flight history


**GETTING STARTED: HOW TO GET THE DATABASE WORKING**
1. Download MySQL Community Edition (https://dev.mysql.com/downloads/mysql/)
2. Download MySQLWorkbench (https://www.mysql.com/products/workbench/)
3. Remember what you make your password. Update your username and password in this [file](src/main/java/edu/usd/utils/DatabaseConnection.java). The server is most likely the same if you didn't change anything. 
4. Run the file [DatabaseTablesCreation](src/main/java/edu/usd/utils/DatabaseTablesCreation.java). This will create the database and necessary tables, as well as add a sample administrator with **username:** zartman and **password:** password. Just like before, ensure that the username, server, and password of the database are correct.


**Usage**

This flight system was created using IntelliJ and the gradle build system thus dependencies can be accessed within the gradle.build file. Unit tests have full coverage of functionality. They can be accessed and executed within the Test folder of the project and are similarly part of the edu.usd package.

Once all prerequisites are met and the database is working, the graphical user interface is available to be interacted with by running the Main.java file. To interact with the interface as a user, create an account then login with your provided credentials. To access administrator privileges, login with airline approved admin credentials (sample administrator with **username:** zartman and **password:** password)

You can find Airport latitude and longitude by googling the airport. When using
latitude and longitude, use negative numbers for South and West. Ex:
<pre><code>Airport Name: San Diego International Airport
Airport Code: SAN
Airport Latitude: 32.732346
Airport Longitude: -117.196053</code></pre>

**ENJOY!**
