/**
 * 
 */
package ZeroFrame.EventsManager;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * @author Hammer
 *
 */
public class Messaging {
	
	public static void raiseClientConnectedEvent(){
		Object paramsObj[] = {};
		int count = ZeroFrame.Events.Messaging.ClientConnectedEventObjects.size();		
		for(int index = 0; index < count; index++){
			try {
				ZeroFrame.Events.Messaging.ClientConnectedEventMethods.get(index).invoke(ZeroFrame.Events.Messaging.ClientConnectedEventObjects.get(index), paramsObj);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	public static void raiseMessageReceivedEvent(String code, String payload){
		Object paramsObj[] = {code, payload};
		int count = ZeroFrame.Events.Messaging.MessageReceivedEventObjects.size();		
		for(int index = 0; index < count; index++){			
			try {
				ZeroFrame.Events.Messaging.MessageReceivedEventMethods.get(index).invoke(ZeroFrame.Events.Messaging.MessageReceivedEventObjects.get(index), paramsObj);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
			}
		}	
	}
	
	public static void raiseMessageReceivedParameterizedEvent(String code, String payload){
		Object paramsObj[] = {code, payload};		
		
		int count = ZeroFrame.Events.Messaging.MessageReceivedParameterizedEventObjects.size();
		
		//convert payload to array
		String[] payloadArray = payload.split(" ");
		
			
		for(int index = 0; index < count; index++){
			if(parameterCheck((String[])ZeroFrame.Events.Messaging.MessageReceivedParameterizedEventParameters.get(index), payloadArray)){			
				try {
					ZeroFrame.Events.Messaging.MessageReceivedParameterizedEventMethods.get(index).invoke(ZeroFrame.Events.Messaging.MessageReceivedParameterizedEventObjects.get(index), paramsObj);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					System.out.println(e.toString());
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					System.out.println(e.toString());
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					System.out.println(e.toString());
				}
			}
		}
	}

	
	
	//-----------------------------------INTERNAL----------------------------------------------------------//
	
	private static Boolean parameterCheck(String[] arrayToCheckBy, String[] arrayToCheckIn){
		int countOfWords = arrayToCheckBy.length;
		int counter = 0;
		
		//convert everything in the array to lowercase
		for(int x=0; x < arrayToCheckIn.length; x++){
			arrayToCheckIn[x] = arrayToCheckIn[x].toLowerCase();
		}
		
		for(String test : arrayToCheckBy){
			if(Arrays.asList(arrayToCheckIn).contains(test.toLowerCase())){
				counter +=1;
			}
		}
		
		if(countOfWords == counter){
			return true;
		}else{
			return false;
		}
	}
}
