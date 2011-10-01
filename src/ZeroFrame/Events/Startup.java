package ZeroFrame.Events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Startup {
	
	public static List<Method> ExtensionsLoadedEventMethods = new ArrayList<Method>(0);
	public static List<Object> ExtensionsLoadedEventObjects = new ArrayList<Object>(0);	
	public static void registerExtensionsLoadedEvent(Object caller, Method invokable){
		ExtensionsLoadedEventObjects.add(caller);
		ExtensionsLoadedEventMethods.add(invokable);
	}
	
	public static List<Method> ApplicationReadyEventMethods = new ArrayList<Method>(0);
	public static List<Object> ApplicationReadyEventObjects = new ArrayList<Object>(0);	
	public static void registerApplicationReadyEvent(Object caller, Method invokable){
		ApplicationReadyEventObjects.add(caller);
		ApplicationReadyEventMethods.add(invokable);
	}
	
}
