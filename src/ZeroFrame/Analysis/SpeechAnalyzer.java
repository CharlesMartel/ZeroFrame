package ZeroFrame.Analysis;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import ZeroFrame.Analysis.SpeechConfiguration.*;
import edu.cmu.sphinx.frontend.util.StreamDataSource;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

public class SpeechAnalyzer {

	GenericConfiguration config;
	Recognizer recognizer = new Recognizer();

	public SpeechAnalyzer() {
		try {
			config = new GenericConfiguration();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (config != null) {
			recognizer = config.getRecognizer();
			recognizer.allocate();
		}

	}

	public void analyzeUtterance(byte[] utteranceStream, ZeroFrame.Networking.Client sendingClient) {
		ByteArrayInputStream tempByteArrayInputStream = new ByteArrayInputStream(utteranceStream);
		AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
		AudioInputStream audioStream = null;
		audioStream = new AudioInputStream(tempByteArrayInputStream, format, utteranceStream.length);
		StreamDataSource recognizerStreamSource = config.getAudioStreamDataSource();
		recognizerStreamSource.setInputStream(audioStream, sendingClient.getClientName() + "-voicestream");
		Result result = recognizer.recognize();
		if (result != null) {
			String resultText = result.getBestFinalResultNoFiller();
			if (!resultText.trim().equalsIgnoreCase("")) {

				// For testing
				System.out.println("You said: " + resultText + '\n');

				// tokenize the result, fire the event
				ZeroFrame.EventsManager.Speech.raiseSpeechReceivedEvent(sendingClient, resultText, audioStream);
			} else {
				// there was voice data, but the recognizer had trouble
				// processing it... maybe respond back
				// "I cant hear you" or something?
			}

		} else {
			// Audio heard but likely not speech data, ignore and move on.
		}
	}
}
