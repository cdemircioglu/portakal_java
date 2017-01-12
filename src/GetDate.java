import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class GetDate {

	public static void main(String[] args) {
		
		isRun(); 
			
		
		
		
	}
	
	public static boolean isCOB() {
		//Get the date
		Date myDate = GetDate();
		Calendar myCalendar = toCalendar(GetDate());
		int dow = myCalendar.get (Calendar.DAY_OF_WEEK);
		
		boolean isCOB = false;
		
		if (
				myCalendar.get(Calendar.HOUR_OF_DAY) == 16 &&  //This is 4 pm
				myCalendar.get(Calendar.MINUTE) > 40 && //45 minutes or later
				((dow >= Calendar.MONDAY) && (dow <= Calendar.FRIDAY)) //Should be a week day
		   )
				
		{
			isCOB = true;
		}
		
		return isCOB;
	}
	
	public static boolean isRun() {
		//Get the date
		Date myDate = GetDate();
		Calendar myCalendar = toCalendar(GetDate());
		int dow = myCalendar.get (Calendar.DAY_OF_WEEK);
		
		boolean isRun = true;
		
		//IS IT TEN TO 5PM?
		//System.out.println(myCalendar.getTime());			
		//System.out.println(myCalendar.get(Calendar.HOUR_OF_DAY));
		//System.out.println(myCalendar.get(Calendar.MINUTE));
		
		if (
				myCalendar.get(Calendar.HOUR_OF_DAY) == 17 ||//This is 5 pm
				(myCalendar.get(Calendar.HOUR_OF_DAY) > 17 && myCalendar.get (Calendar.DAY_OF_WEEK) == 6) || //This is Friday 6PM
				(myCalendar.get(Calendar.HOUR_OF_DAY) < 18 && myCalendar.get (Calendar.DAY_OF_WEEK) == 1) || //This is Sunday
				myCalendar.get (Calendar.DAY_OF_WEEK) == 7 //This is Saturday
		   )
		{
			isRun = false; //Do not run the system. 				
		}
		
		return isRun;
	}

	public static Calendar toCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
	}

	public static String GetDateMySQL() {		
		long d = GetDate().getTime();
		java.sql.Date sqlDate = new java.sql.Date(d);
        return sqlDate.toString();
	}

	public static String GetDateMySQLDateTime() {		
		long d = GetDate().getTime();
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(d);
        return sqlDate.toString();
	}

	
	public static Date GetDate() {
		//Eastern time
		TimeZone.setDefault(TimeZone.getTimeZone("EST"));
        return new Date();
	}

}
