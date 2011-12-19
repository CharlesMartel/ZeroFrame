package ZeroFrame.EventsManager;

import java.lang.reflect.InvocationTargetException;

/**
 * Static class for raising Networking events
 * 
 * @author Hammer
 * 
 */
public final class Networking {

	public static void raiseClientConnectedEvent() {
		Object paramsObj[] = {};
		int count = ZeroFrame.Events.Networking.ClientConnectedEventObjects.size();
		for (int index = 0; index < count; index++) {
			try {
				ZeroFrame.Events.Networking.ClientConnectedEventMethods.get(index).invoke(ZeroFrame.Events.Networking.ClientConnectedEventObjects.get(index), paramsObj);
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
