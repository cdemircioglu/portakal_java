import com.windowsazure.messaging.Notification;
import com.windowsazure.messaging.NotificationHub;

public class SendMessage {

	
	public static void SendMessage(String msg) {
		
		//This is the section to send a phone notification via Azure service
		NotificationHub hub = new NotificationHub("Endpoint=sb://goldfutures.servicebus.windows.net/;SharedAccessKeyName=DefaultFullSharedAccessSignature;SharedAccessKey=wKg1Pvy4D7fLTSfVL62pxjoMZ2U1YQLq79DnfTY0v4A=","goldfutures");
		
		//Define the payload
		String payload = "{\"data\":{\"message\":\""+msg+"\"}}";
		
		//Send the notification 
		Notification p = Notification.createGcmNotifiation(payload);
		hub.sendNotification(p);
		
		
	}
}
