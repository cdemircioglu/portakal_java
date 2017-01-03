import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetPage {

	public static class Row
	  {
		//Row members
		public String Future;
	    public String LastPrice;
	    public String HighPrice;
	    public String LowPrice;
	    public String PreviousPrice;
	    
	    //Row definition
	    public Row(String Future, String LastPrice, String HighPrice, String LowPrice, String PreviousPrice)
	    {
	      this.Future = Future;
	      this.LastPrice = LastPrice;
	      this.HighPrice = HighPrice;
	      this.LowPrice = LowPrice;
	      this.PreviousPrice = PreviousPrice;
	    }
	    
	  }

	public static List<String> GetPage() {
		// TODO Auto-generated method stub
		try
		{
			 //Read the web site
			 Document doc = Jsoup.connect("https://rcgdirect.websol.barchart.com/").get();
			 
			 //Futures list
			 List<String> FutureList = new ArrayList<String>();
			 
			 //Loop through the sections
			 for(String myClass : "rowEven,rowOdd".split(","))
			 {				 
				 FutureList.addAll(GetFuture(doc, myClass));
			 }			 			 
			 
			 return FutureList;
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
		return null; 
	}

	public static List<String> GetFuture(Document doc, String myclass) {
		//Call the method to get the list
		 Elements contents = doc.getElementsByClass(myclass);
		 List<String> futureset = new ArrayList<String>();
		 
		 for(Element content : contents)
		 {
			 futureset.add(content.child(0).text()); //Name
			 futureset.add(content.child(3).text()); //Last
			 futureset.add(content.child(9).text()); //High
			 futureset.add(content.child(10).text()); //Low
			 futureset.add(content.child(8).text()); //Previous
			 
		 }
		 
		 return futureset;
	}
		
	public static Map<String, Row> GetFuturePriceMap() throws InterruptedException
	{
		
		//Get the spot prices of everything
		List<String> futurepricelist = new ArrayList<String>();
		
		//Check the future price list and make sure it is not null.
		while(true)
		{	
			try
			{
				futurepricelist = GetPage.GetPage();
				if (futurepricelist.size() != 0)
					break;
				Thread.sleep((int)(Math.random() * 10000));
			}
			catch (Exception ex)
			{
				//Continue to execute
			}
		}	
				
		//Define the return object
		Map<String, Row> map = new TreeMap<String, Row>();
		
		//Loop on the spot prices and put them to a map
		for(int i = 0; i < futurepricelist.size()/5;i++)
		{								
			Row r = new Row(futurepricelist.get(i*5),futurepricelist.get(i*5+1),futurepricelist.get(i*5+2),futurepricelist.get(i*5+3),futurepricelist.get(i*5+4));		
			map.put(futurepricelist.get(i*5), r);
		}						
		
		return map; 
		
	}

}
