/**
 * 
 */
package ZeroFrame.Constants;

/**
 * @author Hammer
 *
 */
public final class MessageCodes {

	//Connection state business is put into the 0000-0009 range
	public final static String WELCOME = "0000";
	public final static String CLOSE_CONNECTION = "0001";
	public final static String ACCEPTING_REQUESTS = "0002";
	public final static String REQUEST_ACKNOWLEDGED = "0003";
	
	//Client Server business is put into the 0010-0020 range
	public final static String IDENTIFY_CLIENT = "0010";
	public final static String IDENTITY_NOT_KNOWN = "0011";
	
	//Audio requests are in the 0100 - 0199 range
	public final static String REQUEST_AUDIO_SOCKET = "0100";
	public final static String AUDIO_SOCKET_PARAM = "0101";
	
	//Video requests are in the 0200 - 0299 range
	
	//0500 will be a generic message
	public final static String GENERIC_MESSAGE = "0500";
	
	
	//Error Codes are in the 9000 to 9999 range
	public final static String BAD_MESSAGE = "9999";
}
