import com.windowsazure.messaging.Notification;
import com.windowsazure.messaging.NotificationHub;

import net.pushover.client.PushoverClient;
import net.pushover.client.PushoverException;
import net.pushover.client.PushoverMessage;
import net.pushover.client.PushoverRestClient;

public class SendMessage {

	
	public static void SendMessage(String msg) {
		
		//Remove the last character
		msg = msg.substring(0,msg.length()-1); //Remove last pipe
		msg = msg + "(" + GetDate.GetDate();
		msg = msg.substring(0,msg.length()-12) + ")"; // Remove the year/seconds
		SendAndroid(msg);
		SendIOS(msg);
		
	}

	public static void SendAndroid(String msg) {
		//This is the section to send a phone notification via Azure service
		NotificationHub hub = new NotificationHub("Endpoint=sb://goldfutures.servicebus.windows.net/;SharedAccessKeyName=DefaultFullSharedAccessSignature;SharedAccessKey=wKg1Pvy4D7fLTSfVL62pxjoMZ2U1YQLq79DnfTY0v4A=","goldfutures");
		
		//Define the pay load
		String payload = "{\"data\":{\"message\":\""+msg+"\"}}";
		
		System.out.println(payload);
		
		//Send the notification 
		Notification p = Notification.createGcmNotifiation(payload);
		hub.sendNotification(p);
	}
	
	public static void SendIOS(String msg) {
	
		try {
			PushoverClient client = new PushoverRestClient();        

			client.pushMessage(PushoverMessage.builderWithApiToken("adxge5yu38amh7ma4ecic3q4rcc38e")
			        .setUserId("uf2yd3iovd5cktn9zwqfosi651g5hh")
			        .setMessage(msg)
			        .build());
		} catch (PushoverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
}
