package ZeroFrame.EventsManager;

import java.lang.reflect.InvocationTargetException;

public class Startup {
	
	public static void raiseExtensionsLoadedEvent(){
		Object paramsObj[] = {};
		int count = ZeroFrame.Events.Startup.ExtensionsLoadedEventObjects.size();		
		for(int index = 0; index < count; index++){
			try {
				ZeroFrame.Events.Startup.ExtensionsLoadedEventMethods.get(index).invoke(ZeroFrame.Events.Startup.ExtensionsLoadedEventObjects.get(index), paramsObj);
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
	
	public static void raiseApplicationReadyEvent(){
		Object paramsObj[] = {};
		int count = ZeroFrame.Events.Startup.ApplicationReadyEventObjects.size();		
		for(int index = 0; index < count; index++){
			try {
				ZeroFrame.Events.Startup.ApplicationReadyEventMethods.get(index).invoke(ZeroFrame.Events.Startup.ApplicationReadyEventObjects.get(index), paramsObj);
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

}
