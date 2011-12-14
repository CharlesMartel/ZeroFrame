/**
 * 
 */
package ZeroFrame.Extensions;

import java.lang.reflect.Method;

import ZeroFrame.Extensions.Toolbox.GrammarMatrix;

/**
 * Static class for registering to ZeroFrame events.
 * 
 * @author Hammer
 *
 */
public final class Events {
	
	public static void registerExtensionsLoadedEvent(String methodName, Object object) {
		Class<?> currentClass = object.getClass();
		Method[] invokables = currentClass.getDeclaredMethods();
		Method invokable = null;
		for(Method tbd: invokables){
			if(tbd.getName().equals(methodName)){
				invokable = tbd;
				break;
			}
		}
		if(invokable == null){
			//TODO: Log an instance where the method could not be found in the class
		}else{
			ZeroFrame.Events.Startup.ExtensionsLoadedEventMethods.add(invokable);
			ZeroFrame.Events.Startup.ExtensionsLoadedEventObjects.add(object);
		}
	}
	
	public static void registerApplicationReadyEvent(String methodName, Object object) {
		Class<?> currentClass = object.getClass();
		Method[] invokables = currentClass.getDeclaredMethods();
		Method invokable = null;
		for(Method tbd: invokables){
			if(tbd.getName().equals(methodName)){
				invokable = tbd;
				break;
			}
		}
		if(invokable == null){
			//TODO: Log an instance where the method could not be found in the class
		}else{
			ZeroFrame.Events.Startup.ApplicationReadyEventMethods.add(invokable);
			ZeroFrame.Events.Startup.ApplicationReadyEventObjects.add(object);
		}
	}
	
	public static void registerClientConnectedEvent(String methodName, Object object) {
		Class<?> currentClass = object.getClass();
		Method[] invokables = currentClass.getDeclaredMethods();
		Method invokable = null;
		for(Method tbd: invokables){
			if(tbd.getName().equals(methodName)){
				invokable = tbd;
				break;
			}
		}
		if(invokable == null){
			//TODO: Log an instance where the method could not be found in the class
		}else{
			ZeroFrame.Events.Networking.ClientConnectedEventMethods.add(invokable);
			ZeroFrame.Events.Networking.ClientConnectedEventObjects.add(object);
		}
	}
	
	public static void registerMessageReceivedEvent(String methodName, Object object) {
		Class<?> currentClass = object.getClass();
		Method[] invokables = currentClass.getDeclaredMethods();
		Method invokable = null;
		for(Method tbd: invokables){
			if(tbd.getName().equals(methodName)){
				invokable = tbd;
				break;
			}
		}
		if(invokable == null){
			//TODO: Log an instance where the method could not be found in the class
		}else{
			ZeroFrame.Events.Messaging.MessageReceivedEventMethods.add(invokable);
			ZeroFrame.Events.Messaging.MessageReceivedEventObjects.add(object);
		}
	}
	
	public static void registerMessageReceivedEvent(String methodName, Object object, String[] parameters) {
		Class<?> currentClass = object.getClass();
		Method[] invokables = currentClass.getDeclaredMethods();
		Method invokable = null;
		for(Method tbd: invokables){
			if(tbd.getName().equals(methodName)){
				invokable = tbd;
				break;
			}
		}
		if(invokable == null){
			//TODO: Log an instance where the method could not be found in the class
		}else{
			ZeroFrame.Events.Messaging.MessageReceivedParameterizedEventMethods.add(invokable);
			ZeroFrame.Events.Messaging.MessageReceivedParameterizedEventObjects.add(object);
			ZeroFrame.Events.Messaging.MessageReceivedParameterizedEventParameters.add(parameters);
		}
	}
	
	public static void registerMessageReceivedEvent(String methodName, Object object, GrammarMatrix grammarMatrix) {
		Class<?> currentClass = object.getClass();
		Method[] invokables = currentClass.getDeclaredMethods();
		Method invokable = null;
		for(Method tbd: invokables){
			if(tbd.getName().equals(methodName)){
				invokable = tbd;
				break;
			}
		}
		if(invokable == null){
			//TODO: Log an instance where the method could not be found in the class
		}else{
			ZeroFrame.Events.Messaging.MessageReceivedGrammaredEventMethods.add(invokable);
			ZeroFrame.Events.Messaging.MessageReceivedGrammaredEventObjects.add(object);
			ZeroFrame.Events.Messaging.MessageReceivedGrammaredEventParameters.add(grammarMatrix);
			ZeroFrame.Analysis.SpeechConfiguration.Grammar.addGrammarMatrix(grammarMatrix);
		}
	}
	
	public static void registerSpeechReceivedEvent(String methodName, Object object){
		Class<?> currentClass = object.getClass();
		Method[] invokables = currentClass.getDeclaredMethods();
		Method invokable = null;
		for(Method tbd: invokables){
			if(tbd.getName().equals(methodName)){
				invokable = tbd;
				break;
			}
		}
		if(invokable == null){
			//TODO: Log an instance where the method could not be found in the class
		}else{
			ZeroFrame.Events.Speech.SpeechReceivedEventMethods.add(invokable);
			ZeroFrame.Events.Speech.SpeechReceivedEventObjects.add(object);
		}
	}
	
	public static void registerSpeechReceivedEvent(String methodName, Object object, String[] parameters){
		Class<?> currentClass = object.getClass();
		Method[] invokables = currentClass.getDeclaredMethods();
		Method invokable = null;
		for(Method tbd: invokables){
			if(tbd.getName().equals(methodName)){
				invokable = tbd;
				break;
			}
		}
		if(invokable == null){
			//TODO: Log an instance where the method could not be found in the class
		}else{
			ZeroFrame.Events.Speech.SpeechReceivedParameterizedEventMethods.add(invokable);
			ZeroFrame.Events.Speech.SpeechReceivedParameterizedEventObjects.add(object);
			ZeroFrame.Events.Speech.SpeechReceivedParameterizedEventParameters.add(parameters);
		}
	}
	
	public static void registerSpeechReceivedEvent(String methodName, Object object, GrammarMatrix grammarMatrix) {
		Class<?> currentClass = object.getClass();
		Method[] invokables = currentClass.getDeclaredMethods();
		Method invokable = null;
		for(Method tbd: invokables){
			if(tbd.getName().equals(methodName)){
				invokable = tbd;
				break;
			}
		}
		if(invokable == null){
			//TODO: Log an instance where the method could not be found in the class
		}else{
			ZeroFrame.Events.Speech.SpeechReceivedGrammaredEventMethods.add(invokable);
			ZeroFrame.Events.Speech.SpeechReceivedGrammaredEventObjects.add(object);
			ZeroFrame.Events.Speech.SpeechReceivedGrammaredEventParameters.add(grammarMatrix);
			ZeroFrame.Analysis.SpeechConfiguration.Grammar.addGrammarMatrix(grammarMatrix);
		}
	}

}
