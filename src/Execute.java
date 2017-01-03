public class Execute {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		if(GetDate.isRun()) //Run the process
		{
					
			String msg = GetMessage.GetMessage(); //This is the return message
			
			if (msg.length()>10) //Make sure there is a message 
				SendMessage.SendMessage(msg);

			if(GetDate.isCOB()) //Take the snapshot of the day
			{
				DeleteRecords.DeleteSpot(); //Delete the spot data				
				DeleteRecords.DeleteFutures(); //Delete the futures data for current day
				
				//Insert the spot data
				InsertSpot.InsertSpot(); 				
				
				//Insert new closing data for futures
				InsertFutures.InsertFutures(); 												
			}
			
		}

	}

}
