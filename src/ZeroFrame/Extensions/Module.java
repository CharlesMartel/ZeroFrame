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
	
	//Required Properties
	public abstract String getName();
	public abstract String getVersion();
	public abstract String getAuthor();
	
	public Module(){
		
	}
}
