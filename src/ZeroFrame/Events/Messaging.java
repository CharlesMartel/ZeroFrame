package ZeroFrame.Events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Static class holding the event registrars for client messages
 * 
 * @author Hammer
 *
 */
public final class Messaging {
	
	public static List<Method> MessageReceivedEventMethods = new ArrayList<Method>(0);
	public static List<Object> MessageReceivedEventObjects = new ArrayList<Object>(0);	
	public static void registerMessageReceivedEvent(Object caller, Method invokable){
		MessageReceivedEventObjects.add(caller);
		MessageReceivedEventMethods.add(invokable);
	}
	
	public static List<Method> MessageReceivedParameterizedEventMethods = new ArrayList<Method>(0);
	public static List<Object> MessageReceivedParameterizedEventObjects = new ArrayList<Object>(0);
	public static List<String[]> MessageReceivedParameterizedEventParameters = new ArrayList<String[]>(0);	
	public static void registerMessageReceivedEvent(Object caller, Method invokable, String[] parameters){
		MessageReceivedParameterizedEventObjects.add(caller);
		MessageReceivedParameterizedEventMethods.add(invokable);
		MessageReceivedParameterizedEventParameters.add(parameters);
	}
	
	public static List<Method> MessageReceivedGrammaredEventMethods = new ArrayList<Method>(0);
	public static List<Object> MessageReceivedGrammaredEventObjects = new ArrayList<Object>(0);
	public static List<ZeroFrame.Extensions.GrammarMatrix> MessageReceivedGrammaredEventParameters = new ArrayList<ZeroFrame.Extensions.GrammarMatrix>(0);	
	public static void registerMessageReceivedEvent(Object caller, Method invokable, ZeroFrame.Extensions.GrammarMatrix grammarMatrix){
		MessageReceivedGrammaredEventObjects.add(caller);
		MessageReceivedGrammaredEventMethods.add(invokable);
		MessageReceivedGrammaredEventParameters.add(grammarMatrix);
	}
}
