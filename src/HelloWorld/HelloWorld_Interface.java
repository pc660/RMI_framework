package HelloWorld;
import MyRemote.MyRemote;
import java.util.*;

public interface HelloWorld_Interface extends MyRemote {
	public String hellowitharg( String name);
	public String hellowithoutarg();
	public String hellowithreference(LinkedList<Character> list);
	public String hellowithROR (HelloWorld_Interface a);
	public String hellowithtest( String name , LinkedList<Character> list);
}
