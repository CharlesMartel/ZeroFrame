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
			
		for(int index = 0; index < count; index++){
			if(ZeroFrame.Analysis.TextAnalyzer.parameterMatchCheck((String[])ZeroFrame.Events.Messaging.MessageReceivedParameterizedEventParameters.get(index), payload)){			
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
}
