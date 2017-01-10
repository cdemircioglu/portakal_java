import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.*;



public class TestHarness {
	
	public static void main(String[] args) throws IOException, InterruptedException {
	
		//Set the SQL parameters ##CHANGE THEM###
		String url = "jdbc:mysql://localhost:3306/testdata";
		String user = "root";
		String password = "KaraburunCe2";
		
		//Get the list of futures
		List<String> futurelist = new ArrayList<String>();
		futurelist = GetBlob.GetBlob("futures.txt");
		futurelist.addAll(GetBlob.GetBlob("futures_ice.txt"));	
		
		//Delete the future historical records
		DeleteRecords.TruncateFutures();
		
		//Loop on the futures
		for(String futureline : futurelist)
		{
			//Sleep the thread for upto ten seconds. 
			Thread.sleep((int)(Math.random() * 10000));
			
			//Get the name and the address
			String future = futureline.split(",")[0];
			String futureaddress = futureline.split(",")[1];

			System.out.println(future);
			
			//Get the prices
			List<String> lines = DownloadCSV.DownloadCSV(futureaddress);
						
			try {
				//Get the driver
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				
				//Establish connection to MySQL
				Connection conn = DriverManager.getConnection(url, user, password);

				//Prepare the statement
				Statement stmt = conn.createStatement();

				//First line check variable
				int linenumber = 0;
				
				//Loop on the lines
				for(String line:lines)
				{			
					//Skip the first line
					if (linenumber > 1 && !Arrays.asList(line.split(",")).contains(""))
					{
						String query = "INSERT INTO futures VALUES('"+future+"','"+(line.split(","))[0]+"',"+(line.split(","))[1]+","+(line.split(","))[2]+","+(line.split(","))[3]+","+(line.split(","))[4]+","+(line.split(","))[5]+","+(line.split(","))[6]+","+(line.split(","))[7]+","+(line.split(","))[8]+")";
						stmt.execute(query);										
					}
					linenumber++;
						
				}

			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	

		
		

			
		
		
		
	}

	
}

	
	

