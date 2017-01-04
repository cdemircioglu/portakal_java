import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InsertSpot {

	public static void InsertSpot() throws InterruptedException {
		//Get the current date
		String SQLDate = GetDate.GetDateMySQL();

		String url = "jdbc:mysql://cemoptions.cloudapp.net:3306/myoptions";
		String user = "borsacanavari";
		String password = "opsiyoncanavari1";
		
		// Load the Connector/J driver
		try {
			//Truncate the futures spot table
			DeleteRecords.DeleteSpot();
			
			//Get the driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			//Establish connection to MySQL
			Connection conn = DriverManager.getConnection(url, user, password);

			//Prepare the statement
			Statement stmt = conn.createStatement();
			
			//Define the map object to hold futures
			Map<String, GetPage.Row> futuresmap = new TreeMap<String, GetPage.Row>();
			futuresmap = GetPage.GetFuturePriceMap();								

			//Get the list of futures
			List<String> futurelist = new ArrayList<String>();
			futurelist = GetBlob.GetBlob("futures.txt");	
			
			//Loop through the future list
			for (String future : futurelist)
			{
				String[] futurearray = future.split(",");	
				String futurename = futurearray[4].replace("_", " "); //The name of the future
				String futureID = futurearray[0];
				
				//Loop on  futures values 			
				for ( GetPage.Row row : futuresmap.values() ) 
				{
					if (row.Future.equals(futurename))
					{
				        //System.out.println(futureID + " " + row.Future + " " + row.HighPrice  + " " + row.LowPrice + " " + row.LastPrice + " " + row.PreviousPrice);
						System.out.println(futureID + " " + ParseFuture.ParseString(row.LastPrice,futureID) + " " + row.LastPrice );
						String query = "INSERT INTO futuresspot (FUTURE,SETTLE) VALUES ('"+futureID+"',"+ParseFuture.ParseString(row.LastPrice,futureID)+")";
						stmt.execute(query);											
					}
				}			
			}
														
			//Close the connection
			stmt.close();
			conn.close();
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void InsertSpot(String future, String value) throws InterruptedException {
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
			
			//Insert into spot
			String query = "INSERT INTO futuresspot (FUTURE,SETTLE) VALUES ('"+future+"',"+ParseFuture.ParseString(value,future)+")";
			stmt.execute(query);
														
			//Close the connection
			stmt.close();
			conn.close();
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	

}
