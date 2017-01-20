//Comment AABB
public class ConvertZB {
	
	public static double ConvertTo32(Double value) {
		
		//Get the number after the decimal
		double x = value - Math.floor(value);
		
		//Apply the conversion
		return (x*0.32) + Math.floor(value);
	}
	
	public static double ConvertTo100(Double value) {
		
		//Get the number after the decimal
		double x = value - Math.floor(value);
		
		//Apply the conversion		
		return (x/0.32) + Math.floor(value);
	}
	
}
