import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteRecords {

	public static void DeleteFutures() {
		//Get the current date
		String SQLDate = GetDate.GetDateMySQL();

		String url = "jdbc:mysql://cemoptions.cloudapp.net:3306/myoptions";
		String user = "borsacanavari";
		String password = "opsiyoncanavari1";

		// Load the Connector/J driver
		try {
			//Get the driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			//Establish connection to MySQL
			Connection conn = DriverManager.getConnection(url, user, password);
			
			//Prepare the statement
			Statement stmt = conn.createStatement();
			stmt.execute("DELETE FROM futures WHERE SNAPSHOTDATE = '" + SQLDate +"'");
								
			//Close the connection
			stmt.close();
			conn.close();
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void DeleteSpot(String future) {
		//Get the current date
		String SQLDate = GetDate.GetDateMySQL();

		String url = "jdbc:mysql://cemoptions.cloudapp.net:3306/myoptions";
		String user = "borsacanavari";
		String password = "opsiyoncanavari1";

		// Load the Connector/J driver
		try {
			//Get the driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			//Establish connection to MySQL
			Connection conn = DriverManager.getConnection(url, user, password);
			
			//Prepare the statement
			Statement stmt = conn.createStatement();
			stmt.execute("DELETE FROM futuresspot WHERE FUTURE ='" + future + "';");
								
			//Close the connection
			stmt.close();
			conn.close();
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void DeleteFutures(String future) {
		//Get the current date
		String SQLDate = GetDate.GetDateMySQL();

		String url = "jdbc:mysql://cemoptions.cloudapp.net:3306/myoptions";
		String user = "borsacanavari";
		String password = "opsiyoncanavari1";

		// Load the Connector/J driver
		try {
			//Get the driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			//Establish connection to MySQL
			Connection conn = DriverManager.getConnection(url, user, password);
			
			//Prepare the statement
			Statement stmt = conn.createStatement();						
			stmt.execute("DELETE FROM futuresspot WHERE FUTURE ='" + future + "';");
								
			//Close the connection
			stmt.close();
			conn.close();
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	
	
	public static void DeleteSpot() {
		//Get the current date
		String SQLDate = GetDate.GetDateMySQL();

		String url = "jdbc:mysql://cemoptions.cloudapp.net:3306/myoptions";
		String user = "borsacanavari";
		String password = "opsiyoncanavari1";

		// Load the Connector/J driver
		try {
			//Get the driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			//Establish connection to MySQL
			Connection conn = DriverManager.getConnection(url, user, password);
			
			//Prepare the statement
			Statement stmt = conn.createStatement();
			stmt.execute("TRUNCATE TABLE futuresspot;");
								
			//Close the connection
			stmt.close();
			conn.close();
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
