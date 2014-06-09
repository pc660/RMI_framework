package HelloWorld;
import MyRemote.MyRemote;
public interface HelloWorld_Interface extends MyRemote {
	public String hellowitharg( String name );
	public String hellowithoutarg();
}
