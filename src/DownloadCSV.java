import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DownloadCSV {

	public static List<String> DownloadCSV() {
		//DELETE THIS METHOD.. DO NOT USE IT OTHER THAN TESTING PURPOSES..
		//List of futures			 
		List<String> futurelist = new ArrayList<String>(); 

        // -File class needed to turn stringName to actual file
        File file = new File("C:\\Temp\\CHRIS-CME_CL1.csv");
        
        try{
            Scanner inputStream = new Scanner(file);
            
            while(inputStream.hasNext())
			    futurelist.add(inputStream.next());
            
            inputStream.close();
        }catch (FileNotFoundException e){

            e.printStackTrace();
        }
		
		return futurelist;
	}

	
	public static List<String> DownloadCSV(String URLString) {
		
		//Set the URL with the new address
		URLString = "https://www.quandl.com/api/v3/datasets/CHRIS/aaa.csv?api_key=zK6coAV1K5eyxuaPvWJm".replace("aaa", URLString);
		
		//List of futures			 
		List<String> futurelist = new ArrayList<String>(); 

		//Read the URL
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new InputStreamReader((new URL(URLString)).openStream()));
			String line = reader.readLine();
		
			while(line!=null)
			{
			    //read single line, put in string
			    futurelist.add(line);
			    line = reader.readLine();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return futurelist;
    
	}
	
}
