import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetICESpot {

	public static Double[] GetValues(String futureindex) throws IOException {
		Document document = Jsoup.connect("https://www.bloomberg.com/quote/" + futureindex).get();
				
		Elements contents = document.getAllElements();						
		String fline = contents.get(0).data();
		String[] flines = fline.substring(fline.indexOf("price")-1,fline.indexOf("lowPrice52Week")-2).replace("\"", "").split(",");
		
		Double[] values = new Double[4]; //Double last,high,low,previous; 			
		
		for (String line : flines)
		{				
			if (line.contains("price:"))
				values[0] = Double.parseDouble(line.split(":")[1]);
			if (line.contains("lowPrice:"))
				values[1] = Double.parseDouble(line.split(":")[1]);				
			if (line.contains("highPrice:"))
				values[2] = Double.parseDouble(line.split(":")[1]);									
			if (line.contains("previousClosingPriceOneTradingDayAgo:"))
				values[3] = Double.parseDouble(line.split(":")[1]);												
		}
		
		return values;
	}

}
