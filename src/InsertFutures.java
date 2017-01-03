import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InsertFutures {
	
	
	
	
	public static void InsertFutures() throws InterruptedException {
				
		//Get the current date
		String SQLDate = GetDate.GetDateMySQL();

		String url = "jdbc:mysql://cemoptions.cloudapp.net:3306/myoptions";
		String user = "borsacanavari";
		String password = "opsiyoncanavari1";
		
		try {
			//Truncate the futures spot table
			DeleteRecords.DeleteFutures();

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
						//Call the procedure to get the conversion factor
						String query = "CALL ConvertSpot('"+futureID+"',"+ParseFuture.ParseString(row.PreviousPrice,futureID)+")";
						System.out.println(query);
						ResultSet rs = stmt.executeQuery(query);
						
						//Create the conversion factor
			            Double conversionfactor = 0.0;
						while(rs.next())
							{ conversionfactor = rs.getDouble(1);}
					
						//The ZB case
						if (futureID.equals("ZB"))
						{ 
							Double PClose = conversionfactor*ParseFuture.ParseString(row.PreviousPrice,futureID);
							conversionfactor = PClose/ConvertZB.ConvertTo100(ParseFuture.ParseString(row.PreviousPrice,futureID)); //Set the conversion factor based on 100's.							
							query = "INSERT INTO futures (FUTURE,SNAPSHOTDATE,HIGH,LOW,LAST,SETTLE) VALUES('"+futureID+"','"+SQLDate+"',"+
									(ConvertZB.ConvertTo100(ParseFuture.ParseString(row.HighPrice,futureID))*conversionfactor)+","+
									(ConvertZB.ConvertTo100(ParseFuture.ParseString(row.LowPrice,futureID))*conversionfactor)+","+
									(ConvertZB.ConvertTo100(ParseFuture.ParseString(row.LastPrice,futureID))*conversionfactor)+","+
									(ConvertZB.ConvertTo100(ParseFuture.ParseString(row.LastPrice,futureID))*conversionfactor)+")";	
						}
						else 
						{
					        //System.out.println(futureID + " " + row.Future + " " + row.HighPrice  + " " + row.LowPrice + " " + row.LastPrice + " " + row.PreviousPrice);
							System.out.println(futureID + " " + ParseFuture.ParseString(row.LastPrice,futureID) + " " + row.LastPrice );						 
							query = "INSERT INTO futures (FUTURE,SNAPSHOTDATE,HIGH,LOW,LAST,SETTLE) VALUES('"+futureID+"','"+SQLDate+"',"+ParseFuture.ParseString(row.HighPrice,futureID)*conversionfactor+","+ParseFuture.ParseString(row.LowPrice,futureID)*conversionfactor+","+ParseFuture.ParseString(row.LastPrice,futureID)*conversionfactor+","+ParseFuture.ParseString(row.LastPrice,futureID)*conversionfactor+")";
						}
						
						stmt.execute(query); //Execute the query
					}
				 }			
			  }
			
			//Close the connection
			stmt.close();
			conn.close();
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}

}
