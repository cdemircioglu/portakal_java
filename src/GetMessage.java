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

public class GetMessage {

	//Define the map object to hold futures
	private static Map<String, GetPage.Row> futuresmap = new TreeMap<String, GetPage.Row>();
	
	//Get the list of futures
	private static List<String> futurelist = new ArrayList<String>();

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static String GetMessage() {
		//Get the spot prices from the web
		try {
			futuresmap = GetPage.GetFuturePriceMap();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Error" + e1.getMessage());
		}								
		
		//Get the list of futures
		futurelist = GetBlob.GetBlob("futures.txt");	

		//Set the mysql variables
		String url = "jdbc:mysql://cemoptions.cloudapp.net:3306/myoptions";
		String user = "borsacanavari";
		String password = "opsiyoncanavari1";

		//Set the msg
		String msg = "";
		
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
					
			//Create the conversion factor
			while(rs.next())
				{ 
					//Get the predictions from the database
					String future = rs.getString("FUTURE");
					Double buy1 = rs.getDouble("BUY05");
					Double sell1 = rs.getDouble("SELL05");
					Double buy2 = rs.getDouble("BUY1");
					Double sell2 = rs.getDouble("SELL1");
					Integer period = rs.getInt("PERIOD");
				
					//Get the spot price from the list
					Double futurespot = GetSpotPrice(future);
					
					//Make sure we just include the spot price available futures.
					if (futurespot != 0.0)
					{										
						//Number of decimal places
						int numberdecimals = (futurespot.toString().split("\\."))[1].length();
						
						//Check buy conditions
						if (futurespot < buy2*1.002) { //Check the 2D condition first
							msg += "$$$ PERIOD:" + period + " 2D ALERT $$$ "+future+" buy signal at " +  round(buy2,numberdecimals) + " current price is: " + futurespot + ".|" ;  
						} else if (futurespot < buy1*1.002) { //Check the 1D condition second
							msg += "PERIOD:" + period + " "+future+" buy signal at " + round(buy1,numberdecimals) + " current price is: " + futurespot + ".|" ;
						}
						
						//Check sell conditions
						if (futurespot > sell2*0.998) { //Check the 2D condition first
							msg += "$$$ PERIOD:" + period + " 2D ALERT $$$ "+future+" sell signal at " + round(sell2,numberdecimals) + " current price is: " + futurespot + ".|" ;  
						} else if (futurespot > sell1*0.998) { //Check the 1D condition second
							msg += "PERIOD:" + period + " "+future+" sell signal at " + round(sell1,numberdecimals) + " current price is: " + futurespot + ".|" ;
						}	
					}																			
					System.out.println(msg);
				}


			//Close the connection
			stmt.close();
			conn.close();

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error" + e.getMessage());
		}
		
		//Return value
		return msg;
	}

	//This method returns the last price of a future
	public static double GetSpotPrice(String currentfuture) {
		
		//Loop through the future list
		for (String futurestring : futurelist)
		{
			String[] futurearray = futurestring.split(",");	
			String futurename = futurearray[4].replace("_", " "); //The name of the future
			String futureID = futurearray[0];
			
			//Loop on  futures values 			
			for ( GetPage.Row row : futuresmap.values() ) 
			{
				if (row.Future.equals(futurename) && futureID.equals(currentfuture))
				{
					return ParseFuture.ParseString(row.LastPrice,futureID);	
				}
			}			
		}
		return 0.0;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
