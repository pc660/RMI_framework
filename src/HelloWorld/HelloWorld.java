package HelloWorld;
import java.util.LinkedList;

import Server.RemoteObjectReference;

public class HelloWorld implements HelloWorld_Interface{

	int count; 
	@Override
	public String hellowitharg(String name) {
		System.out.println("Say Hello with arg"+ name) ;
		count ++;
		return "say hello " + name;
	}

	@Override
	public String hellowithoutarg() {
		System.out.println("Say Hello without arg") ;
		count ++ ; 
		return  "say hello";
	}

	@Override
	public RemoteObjectReference getror() {
		return null;
	}
	public HelloWorld ()
	{
		count = 0;
	}
	@Override
	public void setror(RemoteObjectReference obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String hellowithreference(LinkedList<Character> list) {
		Character a = list.getFirst();
		count ++;
		String name = "";
			for (Character c : list)
			{
				name = name + c;
			}
			return "say hello " + name;
	}

	@Override
	public String hellowithROR(HelloWorld_Interface a) {
		count ++;
		System.out.println(count);
		return "say hello "  + "reference" ;
	}
	

}