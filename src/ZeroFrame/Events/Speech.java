package ZeroFrame.Events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Static class holding the event registrars for Speech events.
 * 
 * @author Hammer
 *
 */
public final class Speech {

	public static List<Method> SpeechReceivedEventMethods = new ArrayList<Method>(0);
	public static List<Object> SpeechReceivedEventObjects = new ArrayList<Object>(0);	
	public static void registerSpeechReceivedEvent(Object caller, Method invokable){
		SpeechReceivedEventObjects.add(caller);
		SpeechReceivedEventMethods.add(invokable);
	}
	
	public static List<Method> SpeechReceivedParameterizedEventMethods = new ArrayList<Method>(0);
	public static List<Object> SpeechReceivedParameterizedEventObjects = new ArrayList<Object>(0);
	public static List<String[]> SpeechReceivedParameterizedEventParameters = new ArrayList<String[]>(0);	
	public static void registerMessageReceivedEvent(Object caller, Method invokable, String[] parameters){
		SpeechReceivedParameterizedEventObjects.add(caller);
		SpeechReceivedParameterizedEventMethods.add(invokable);
		SpeechReceivedParameterizedEventParameters.add(parameters);
	}
	
	public static List<Method> SpeechReceivedGrammaredEventMethods = new ArrayList<Method>(0);
	public static List<Object> SpeechReceivedGrammaredEventObjects = new ArrayList<Object>(0);
	public static List<ZeroFrame.Extensions.Toolbox.GrammarMatrix> SpeechReceivedGrammaredEventParameters = new ArrayList<ZeroFrame.Extensions.Toolbox.GrammarMatrix>(0);	
	public static void registerMessageReceivedEvent(Object caller, Method invokable, ZeroFrame.Extensions.Toolbox.GrammarMatrix grammarMatrix){
		SpeechReceivedGrammaredEventObjects.add(caller);
		SpeechReceivedGrammaredEventMethods.add(invokable);
		SpeechReceivedGrammaredEventParameters.add(grammarMatrix);
	}
	
	
}
