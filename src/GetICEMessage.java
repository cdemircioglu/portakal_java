import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GetICEMessage {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Get the list of futures
		List<String> futurelist = GetBlob.GetBlob("futures_ice.txt");
		
		//Get the result set
		ResultSet rs = GetForecast();

		//Loop through the future list
		for (String futurestring : futurelist)
		{
			//Break the future list string into its components
			String[] futurearray = futurestring.split(",");	
			String future = futurearray[0]; 
			String futureaddress = futurearray[1];
			System.out.println(futureaddress);
			
			//Get the price array
			try {
				//Get the price array
				Double[] values = GetICESpot.GetValues(futureaddress);

				//Loop on forecast
				while(rs.next())
				{
					//Get the predictions from the database
					String futureforecast = rs.getString("FUTURE");
					Double buy1 = rs.getDouble("BUY05");
					Double sell1 = rs.getDouble("SELL05");
					Double buy2 = rs.getDouble("BUY1");
					Double sell2 = rs.getDouble("SELL1");
					Integer period = rs.getInt("PERIOD");
				
					//Get the spot price from the list
					Double futurespot = values[0];
				
					//Make sure the forecast side is equal to spot side
					if (future.equals(futureforecast))
						System.out.println(future);
				}
					
				
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			
		}
		
	}

	public static ResultSet GetForecast() {
		
		//Set the mysql variables
		String url = "jdbc:mysql://cemoptions.cloudapp.net:3306/myoptions";
		String user = "borsacanavari";
		String password = "opsiyoncanavari1";
		
		try {
		
		//Get the driver
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		//Establish connection to MySQL
		Connection conn = DriverManager.getConnection(url, user, password);

		//Prepare the statement
		Statement stmt = conn.createStatement();
		
		//Call the procedure to get the predictions
		String query = "SELECT FUTURE,BUY05,SELL05,BUY1,SELL1,PERIOD FROM futurespredict WHERE SNAPSHOTDATE = (SELECT MAX(SNAPSHOTDATE) FROM futurespredict)";
		ResultSet rs = stmt.executeQuery(query);													
		
		//Close the connection
		stmt.close();
		conn.close();
		
		return rs;
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		}
		return null;
	}

}
