package Communcation;
import java.util.HashMap;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.io.ObjectOutputStream;

import Message.InvokeMessage;
public class SocketCache {
	private class DoubleLinkedListNode
	{
		String key;
		public Socket socket;
		public ObjectOutputStream output;
		public ObjectInputStream input;
		public DoubleLinkedListNode pre;
		public DoubleLinkedListNode next;
		public DoubleLinkedListNode(String key, SocketInfo s) {
			this.key  = key;
			socket = s.socket;
			output = s.output;
			input = s.input;
		}
		
		
	}
	
	public HashMap<String, DoubleLinkedListNode> cache_hash ; 
	public  DoubleLinkedListNode head;
	public DoubleLinkedListNode end;
	int len;
	int capacity;
	public boolean containsKey(String key)
	{
		if (cache_hash.containsKey(key))
			return true;
		else return false;
	}
	public void removeNode(DoubleLinkedListNode node) {
		DoubleLinkedListNode cur = node;
		DoubleLinkedListNode pre = cur.pre;
		DoubleLinkedListNode post = cur.next;
 
		if (pre != null) {
			pre.next = post;
		} else {
			head = post;
		}
 
		if (post != null) {
			post.pre = pre;
		} else {
			end = pre;
		}
	}
	public void setHead(DoubleLinkedListNode node) {
		node.next = head;
		node.pre = null;
		if (head != null) {
			head.pre = node;
		}
 
		head = node;
		if (end == null) {
			end = node;
		}
	}
	
	public SocketInfo get(String key) {
		if (cache_hash.containsKey(key)) {
			DoubleLinkedListNode latest = cache_hash.get(key);
			removeNode(latest);
			setHead(latest);
			SocketInfo info = new SocketInfo(latest.socket, latest.output, latest.input);
			return info;
		} else {
			return null;
		}
	}
	
	
	

	public SocketCache (int size)
	{
		cache_hash = new HashMap<String, DoubleLinkedListNode> ();
		this.capacity = size;
		this.len = 0;
	}
	
	public void set(String  key, SocketInfo value) {
		if (cache_hash.containsKey(key)) {
			DoubleLinkedListNode oldNode = cache_hash.get(key);
			Socket socket = oldNode.socket;
			ObjectOutputStream output = oldNode.output;
			oldNode.socket = value.socket;
			oldNode.input = value.input; 
			oldNode.output = value.output;
			InvokeMessage msg = new InvokeMessage ();
			msg.method_name = null;
			removeNode(oldNode);
			setHead(oldNode);
		} else {
			
			DoubleLinkedListNode newNode = new DoubleLinkedListNode(key, value);

			if (len < capacity) {
				setHead(newNode);
				cache_hash.put(key, newNode);
				len++;
			} else {
				cache_hash.remove(end.key);
				end = end.pre;
				if (end != null) {
					end.next = null;
				}
 
				setHead(newNode);
				cache_hash.put(key, newNode);
			}
		}
	}
}
