/**
 * 
 */
package ZeroFrame.Extensions;

import java.lang.reflect.Method;

/**
 * @author Hammer
 *
 */
public class Events {
	
	public void registerExtensionsLoadedEvent(String methodName, Object object) throws SecurityException, NoSuchMethodException{
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
	
	public void registerApplicationReadyEvent(String methodName, Object object) throws SecurityException, NoSuchMethodException{
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
	
	public void registerClientConnectedEvent(String methodName, Object object) throws SecurityException, NoSuchMethodException{
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
			ZeroFrame.Events.Messaging.ClientConnectedEventMethods.add(invokable);
			ZeroFrame.Events.Messaging.ClientConnectedEventObjects.add(object);
		}
	}
	
	public void registerMessageReceivedEvent(String methodName, Object object) throws SecurityException, NoSuchMethodException{
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
	
	public void registerMessageReceivedParameterizedEvent(String methodName, Object object, String[] parameters) throws SecurityException, NoSuchMethodException{
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

}
