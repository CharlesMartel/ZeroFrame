package ZeroFrame.Events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Static class holding the event registrars for Networking events.l
 * 
 * @author Hammer
 * 
 */
public final class Networking {

	public static List<Method> ClientConnectedEventMethods = new ArrayList<Method>(0);
	public static List<Object> ClientConnectedEventObjects = new ArrayList<Object>(0);

	public static void registerClientConnectedEvent(Object caller, Method invokable) {
		ClientConnectedEventObjects.add(caller);
		ClientConnectedEventMethods.add(invokable);
	}

}
