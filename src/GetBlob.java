// Include the following imports to use blob APIs.
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

public class GetBlob {

	//Define the connection-string with your values
	public static final String storageConnectionString =
	 "DefaultEndpointsProtocol=http;" +
	 "AccountName=gakguk;" +
	 "AccountKey=pb4ole+R5XjXyOU9hlOMISPeXBJFmgUg9Gdwqr461cQ6wDOCtiGQRUtZkvaQUlTSta7ydxJF6unyzUJnAbU1PQ==";
	
	 public static List<String> GetBlob(String args) {

		 //List of futures			 
		 List<String> futurelist = new ArrayList<String>(); 
		 
		 try
		 {
			 //BlobName
			 String BlobName = args;
		
		     // Retrieve storage account from connection-string.
		     CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

		     // Create the blob client.
		     CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

		     // Retrieve reference to a previously created container.
		     CloudBlobContainer container = blobClient.getContainerReference("stock");

		     // Loop through each blob item in the container.
		     for (ListBlobItem blobItem : container.listBlobs()) {
		         // If the item is a blob, not a virtual directory.
		         if (blobItem instanceof CloudBlob) {		        	 
		             // Download the item and save it to a file with the same name.
		             CloudBlob blob = (CloudBlob) blobItem;
		          	
		             // Download the blob that is selected
		             if( blob.getName().equals(BlobName))
		             {
		             	blob.download(new FileOutputStream(blob.getName()));
		             	
		             	// Read the file
		             	File file = new File(BlobName);

		                try{
		                    // -read from filePooped with Scanner class
		                    Scanner inputStream = new Scanner(file);
		                    // hashNext() loops line-by-line
		                    while(inputStream.hasNext()){
		                        //read single line, put in string
		                        futurelist.add(inputStream.next());
		                    }
		                    // after loop, close scanner
		                    inputStream.close();


		                }catch (FileNotFoundException e){

		                    e.printStackTrace();
		                }
		             	
		             	
		             }
		             
		             
		             
		         }
		     }
		     
		 }
		 catch (Exception e)
		 {
		     // Output the stack trace.
		     e.printStackTrace();
		     System.out.println("Error" + e.getMessage());
		 }
		 
		 return futurelist;
	 }
		
}
