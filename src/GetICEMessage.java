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

	private static String msg = "";

	public static String GetICEMessage() {
		//Set the mysql variables
		String url = "jdbc:mysql://cemoptions.cloudapp.net:3306/myoptions";
		String user = "borsacanavari";
		String password = "opsiyoncanavari1";

		
		//Get the list of futures
		List<String> futurelist = GetBlob.GetBlob("futures_ice.txt");

		//Loop through the future list
		for (String futurestring : futurelist)
		{

			//Break the future list string into its components
			String[] futurearray = futurestring.split(",");	
			String future = futurearray[0]; 
			String futureaddress = futurearray[2];			
			System.out.println(futureaddress);
			
			//Loop the results until it succeeds. 
			Boolean CheckLoop = true; 
		
			//This is the loop to make sure we get the results. The system auto tries if it fails
			while(CheckLoop)				
				CheckLoop = GetMessage(url, user, password, future, futureaddress);		
			
		}
		
		return msg;
	}

	public static Boolean GetMessage(String url, String user, String password, String future, String futureaddress) {
		//Get the price array
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
				{
					//Number of decimal places
					int numberdecimals = (futurespot.toString().split("\\."))[1].length();

					//Check buy conditions
					if (futurespot < buy2*1.001) { //Check the 2D condition first
						msg += "$$$ PERIOD:" + period + " 2D ALERT $$$ "+future+" buy signal at " +  round(buy2,numberdecimals) + " current price is: " + futurespot + ".|" ;  
					} else if (futurespot < buy1*1.001) { //Check the 1D condition second
						msg += "PERIOD:" + period + " "+future+" buy signal at " + round(buy1,numberdecimals) + " current price is: " + futurespot + ".|" ;
					}
					
					//Check sell conditions
					if (futurespot > sell2*0.999) { //Check the 2D condition first
						msg += "$$$ PERIOD:" + period + " 2D ALERT $$$ "+future+" sell signal at " + round(sell2,numberdecimals) + " current price is: " + futurespot + ".|" ;  
					} else if (futurespot > sell1*0.999) { //Check the 1D condition second
						msg += "PERIOD:" + period + " "+future+" sell signal at " + round(sell1,numberdecimals) + " current price is: " + futurespot + ".|" ;
					}
				}
				
				
			}
			
			//Close the connection
			stmt.close();
			conn.close();

			return false;	
			
		} catch (IOException | SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			try {
				Thread.sleep((int)(Math.random() * 10000));
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
