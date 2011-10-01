/**
 * 
 */
package ZeroFrame.Events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hammer
 *
 */
public class Messaging {

	public static List<Method> ClientConnectedEventMethods = new ArrayList<Method>(0);
	public static List<Object> ClientConnectedEventObjects = new ArrayList<Object>(0);	
	public static void registerClientConnectedEvent(Object caller, Method invokable){
		ClientConnectedEventObjects.add(caller);
		ClientConnectedEventMethods.add(invokable);
	}
	
	public static List<Method> MessageReceivedEventMethods = new ArrayList<Method>(0);
	public static List<Object> MessageReceivedEventObjects = new ArrayList<Object>(0);	
	public static void registerMessageReceivedEvent(Object caller, Method invokable){
		MessageReceivedEventObjects.add(caller);
		MessageReceivedEventMethods.add(invokable);
	}
	
	public static List<Method> MessageReceivedParameterizedEventMethods = new ArrayList<Method>(0);
	public static List<Object> MessageReceivedParameterizedEventObjects = new ArrayList<Object>(0);
	public static List<String[]> MessageReceivedParameterizedEventParameters = new ArrayList<String[]>(0);	
	public static void registerMessageReceivedParameterizedEvent(Object caller, Method invokable, String[] parameters){
		MessageReceivedParameterizedEventObjects.add(caller);
		MessageReceivedParameterizedEventMethods.add(invokable);
		MessageReceivedParameterizedEventParameters.add(parameters);
	}
}
