package ZeroFrame.EventsManager;

import java.lang.reflect.InvocationTargetException;

import javax.sound.sampled.AudioInputStream;

import ZeroFrame.Extensions.ClientAdapter;

/**
 * Static class for raising Speech events.
 * 
 * @author Hammer
 * 
 */
public final class Speech {

	public static void raiseSpeechReceivedEvent(ZeroFrame.Networking.Client client, String parsedSpeech, AudioInputStream audioStream) {
		ClientAdapter cAdapter = new ClientAdapter(client);
		Object paramsObj[] = { cAdapter, parsedSpeech };

		// Generic Event
		int count = ZeroFrame.Events.Speech.SpeechReceivedEventObjects.size();
		for (int index = 0; index < count; index++) {
			try {
				ZeroFrame.Events.Speech.SpeechReceivedEventMethods.get(index).invoke(ZeroFrame.Events.Speech.SpeechReceivedEventObjects.get(index), paramsObj);
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

		// Parameterized Event
		count = ZeroFrame.Events.Speech.SpeechReceivedParameterizedEventObjects.size();
		for (int index = 0; index < count; index++) {
			if (ZeroFrame.Analysis.TextAnalyzer.parameterMatchCheck(ZeroFrame.Events.Speech.SpeechReceivedParameterizedEventParameters.get(index), parsedSpeech)) {
				try {
					ZeroFrame.Events.Speech.SpeechReceivedParameterizedEventMethods.get(index).invoke(ZeroFrame.Events.Speech.SpeechReceivedParameterizedEventObjects.get(index), paramsObj);
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
		
		// Grammared Event
		count = ZeroFrame.Events.Speech.SpeechReceivedGrammaredEventObjects.size();
		for (int index = 0; index < count; index++) {
			if (ZeroFrame.Analysis.TextAnalyzer.matrixMatchCheck(ZeroFrame.Events.Speech.SpeechReceivedGrammaredEventParameters.get(index), parsedSpeech)) {
				try {
					ZeroFrame.Events.Speech.SpeechReceivedGrammaredEventMethods.get(index).invoke(ZeroFrame.Events.Speech.SpeechReceivedGrammaredEventObjects.get(index), paramsObj);
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
