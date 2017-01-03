
public class ParseFuture {

	public static double ParseString(String futurevalue, String futureID) {
		
		//Basic string manipulation
		futurevalue = futurevalue.replace(",", ""); //Remove commas
		futurevalue = futurevalue.replace("s", ""); //Remove s
		futurevalue = futurevalue.replace("-", "."); //Remove - sign for ZB
		
		//Multiply the Japanese yen by 100. 
		if (futureID.equals("6J"))
			futurevalue = Double.toString(Double.parseDouble(futurevalue)/100.0);
					
		//If the string has dash
		//if(futurevalue.contains("-"))
		//{
		//	String[] twoparts = futurevalue.split("-");
		//	return Integer.parseInt(twoparts[0])*1.0 + Integer.parseInt(twoparts[1])/32.0;
		//}
		
		return Double.parseDouble(futurevalue);
	}

}
