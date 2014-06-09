package HelloWorld;
import Server.RemoteObjectReference;

public class HelloWorld implements HelloWorld_Interface{

	@Override
	public String hellowitharg(String name) {
		System.out.println("Say Hello with arg"+ name) ;
		return "say hello " + name;
	}

	@Override
	public String hellowithoutarg() {
		System.out.println("Say Hello without arg") ;
		return  "say hello";
	}

	@Override
	public RemoteObjectReference getror() {
		return null;
	}
	public HelloWorld ()
	{
		
	}
	@Override
	public void setror(RemoteObjectReference obj) {
		// TODO Auto-generated method stub
		
	}
	

}