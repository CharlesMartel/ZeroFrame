/**
 * 
 */
package ZeroFrame.Constants;

/**
 * @author Hammer
 * 
 */
public final class MessageCodes {

	// Connection state business is put into the 0000-0009 range
	public final static String WELCOME = "0000";
	public final static String CLOSE_CONNECTION = "0001";
	public final static String ACCEPTING_REQUESTS = "0002";
	public final static String REQUEST_ACKNOWLEDGED = "0003";

	// Client Server business is put into the 0010-0020 range
	public final static String IDENTIFY_CLIENT = "0010";
	public final static String IDENTITY_NOT_KNOWN = "0011";

	// Client configuration business is put into the 0050 - 0099 range

	// Audio requests are in the 0100 - 0299 range
	public final static String REQUEST_AUDIO_SOCKET = "0100";
	public final static String AUDIO_SOCKET_PARAM = "0101";
	public final static String AUDIO_TRANSFER_NOTIFICATION = "0102";

	// Video requests are in the 0300 - 0499 range

	// Speech requests are in the 0500 - 0599 range
	public final static String SPEAK_PHRASE = "0500";

	// 0700 - 0710 will be a generic messages
	public final static String GENERIC_MESSAGE = "0700";
	public final static String NOTIFY_PLUGIN = "0701";

}
