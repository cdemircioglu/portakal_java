import java.io.IOException;

public class Execute {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub

		if(GetDate.isRun()) //Run the process
		{
			
			int COB = 0; //To overwrite the schedule
			
			// Check how many arguments were passed in
		    if(args.length == 1)
		    	COB = Integer.parseInt(args[0]);
			
			String msg = GetMessage.GetMessage(); //This is the return message
			
			if (msg.length()>10) //Make sure there is a message 
				SendMessage.SendMessage(msg);

			if(GetDate.isCOB() || COB == 1) //Take the snapshot of the day
			{
				DeleteRecords.DeleteSpot(); //Delete the spot data				
				DeleteRecords.DeleteFutures(); //Delete the futures data for current day
				
				//Insert the spot data
				InsertSpot.InsertSpot(); 				
				
				//Insert new closing data for futures
				InsertFutures.InsertFutures(); 	
				
				//Insert ICE into future spot
				for (String futurestring : GetBlob.GetBlob("futures_ice.txt"))
				{
					String future = futurestring.split(",")[0];
					
					//Delete the future
					DeleteRecords.DeleteSpot(future);
					
					//Get the value
					Double[] futurevalues = GetICESpot.GetValues(futurestring.split(",")[2]);
					
					//Insert the future
					InsertSpot.InsertSpot(future, futurevalues[0].toString());
					System.out.println(future);
					
					//Insert new closing data for futures
					InsertFutures.InsertFutures(future, futurevalues); 	

				}
				
			}
			
		}

	}

}
