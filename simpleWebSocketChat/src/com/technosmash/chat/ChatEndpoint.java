package com.technosmash.chat;

import javax.websocket.OnOpen;
import javax.websocket.OnError;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/chat")
public class ChatEndpoint {
	
	private static Set<Session> chatusers=new HashSet<Session>(); 
	
	@OnOpen
	public void OnOpen(Session session) {
		chatusers.add(session);
		System.out.println("New session created- "+session.getId()+"---current online users-"+chatusers.size());
	}
	
	@OnError
	public void OnError(Throwable e) {
		System.out.println("Erroe -----------");
	}
	
	@OnClose
	public void OnClose(Session session) {
		chatusers.remove(session);
		System.out.println("Session closed- "+session.getId());
	}
	
	@OnMessage
	public void OnMessage(Session session,String message) {
		System.out.println("Incoming Message- "+message);
		send_message_to_everyone(message,session);
	}
	
	
	
	public void send_message_to_everyone(String message, Session sender){
		Iterator<Session> ite=chatusers.iterator();
		
		while (ite.hasNext()) {
			Session session=ite.next();
			try {
				session.getBasicRemote().sendText(message+"~"+sender.getId());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	
	
	
	

}
