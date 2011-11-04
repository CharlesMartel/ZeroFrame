/**
 * 
 */
package ZeroFrame.Extensions;

/**
 * An abstract class that modules need to inherit.
 * 
 * @author Hammer
 *
 */
public abstract class Module {	
	//Events Management Object
	public ZeroFrame.Extensions.Events Events = new ZeroFrame.Extensions.Events();
	
	//Properties
    private String Name;
	public String Version;
	public String Author;
	
	public abstract String getName();
	public abstract String getVersion();
	public abstract String getAuthor();
}
