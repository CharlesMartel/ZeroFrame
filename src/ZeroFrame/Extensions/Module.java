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
    public String Name = "Name Not Set";
	public String Version = "Version Not Set";
	public String Creator = "Creator Not Set";

}
