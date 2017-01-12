import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertNotification {

	public static void InsertNotification(String msg) {		

		//Database settings
		String url = "jdbc:mysql://cemoptions.cloudapp.net:3306/myoptions";
		String user = "borsacanavari";
		String password = "opsiyoncanavari1";
		
		//Get the current date time, UTC
		String SQLDate = GetDate.GetDateMySQLDateTime();
		
		String[] msgs = msg.split("\\|"); //Split into individual messages
		
		//Loop on records
		for (String row : msgs)
		{
			int sd = 1;
			int period = 0;
			String action = "buy";
			String future = "";
			int rsi = 0;
			int mfi = 0;
			double signal = 0;
			double current = 0;
			
			//Get the SD
			String rowSD = row.replace("$$$ ", "").replace("2D ALERT ", "");
			if (!rowSD.equals(rowSD)) 
					sd = 2;
			
			//Get the action sell or buy
			if (rowSD.contains("sell"))
					action = "sell";
						
			//Get the period
			period = Integer.parseInt(rowSD.split(" ")[0].split(":")[1]);

			//Get the rsi
			rsi = Integer.parseInt(rowSD.split(" ")[2].split(",")[0].split(":")[1].replace("(", "").replace(")", ""));

			//Get the mfi
			mfi = Integer.parseInt(rowSD.split(" ")[2].split(",")[1].split(":")[1].replace("(", "").replace(")", ""));
			
			//Get the signal
			signal = Double.parseDouble(rowSD.split(" ")[6]);
			
			//Get the current
			rowSD = rowSD.substring(0,rowSD.length()-1);
			current = Double.parseDouble(rowSD.split(" ")[10]);
			
			//Get the future
			future = rowSD.split(" ")[1];
			
			//System.out.println(SQLDate + " ACT:" + action + " FUT:" + future + " SD:" + sd + " PRD:" + period + " RSI:" + rsi + " MFI:" + mfi + " SIG:" + signal + " CUR:" + current);
															
			try {
				//Get the driver
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				
				//Establish connection to MySQL
				Connection conn = DriverManager.getConnection(url, user, password);

				//Prepare the statement
				Statement stmt = conn.createStatement();
								
				//Execute the query
				String query = "INSERT INTO futuresnotify VALUES ('"+SQLDate+"','"+future+"','"+action+"',"+sd+","+period+","+rsi+","+mfi+","+signal+","+current+")";
				stmt.executeQuery(query);
				
				//Close the connection
				stmt.close();
				conn.close();

			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
}
