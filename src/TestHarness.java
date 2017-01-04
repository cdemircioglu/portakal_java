import java.util.ArrayList;
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
		
		
		//Insert ICE into future spot
		for (String futurestring : GetBlob.GetBlob("futures_ice.txt"))
		{
			String future = futurestring.split(",")[0];
			String futureindex = futurestring.split(",")[2];
			
			//Delete the future
			DeleteRecords.DeleteSpot(future);
			
			//Get the value
			Double[] futurevalues = GetICESpot.GetValues(futurestring.split(",")[2]);
			
			//Insert the future
			InsertSpot.InsertSpot(future, futurevalues[0].toString());
			System.out.println(future);
		}
		//GetValues("aaa");
		
		
	}

	
}

	
	

