package MyRemote;

import Server.RemoteObjectReference;
/*
 * My remote interface
 * */
public interface MyRemote {
	public RemoteObjectReference getror ();
	public void setror (RemoteObjectReference obj);
}
