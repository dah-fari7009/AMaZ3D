package org.example.amaz3d.controller.networking;
import java.net.*;
import java.util.*;
import java.io.*;
import javafx.application.*;
import org.example.amaz3d.view.ChatPane;

public class Chat {
	private boolean hote=false;
	private LinkedList<Socket> sockets;
	private ServerSocket socServ;
	private Socket me;
	private String myName;
	private waitClients waitCli;
	private LinkedList<String> messages;
	private ChatPane pan;
	private boolean newMessage = false;
	private static int PORT = 1235;

	public Chat(ChatPane pan){
		this.pan=pan;
	}

	public void close(){
		if(hote){
			waitCli.interrupt(); //stop();
			for(Socket soc:sockets){
				netFunc.closeSocket(soc);
			}
		}
		netFunc.closeSocket(me);
	}

	public void initHost(String name){
		hote=true;
		sockets=new LinkedList<Socket>();
		try{
			socServ = new ServerSocket(PORT);
			sockets = new LinkedList<Socket>();
			initMe(name,"localHost");
			getClient();
		}catch(IOException e){e.printStackTrace();}
	}

	public void initClient(String name,String addr){
		initMe(name,addr);
	}

	public void getClient(){
		waitCli = new waitClients();
		waitCli.start();
	}

	public void initMe(String name, String addr){
		myName=name;
		messages=new LinkedList<String>();
		try{
			me=new Socket(addr, PORT);
			waitNewMessages wnm= new waitNewMessages(me,false);
			wnm.start();
		}catch(IOException e){}
	}

	public void sendMessage(String str){
			netFunc.sendString(me,myName+" : "+str);
	}

	private class waitClients extends Thread{
		private volatile boolean exit = false;

		public void run(){
			System.out.println("Attente de clients...");
			while(!exit){
				try{
					Socket tmp = socServ.accept();
					if(!exit){
						sockets.add(tmp);
						System.out.println("Un nouveau client a été ajouté.");
						waitNewMessages wnm= new waitNewMessages(tmp,true);
						wnm.start();
					}else
						tmp.close();
				}catch(Exception e){}
			}
		}
	}
	private class waitNewMessages extends Thread{
		private Socket soc;
		private boolean isHote;
		private waitNewMessages(Socket soc, boolean isHote){
			super();
			this.soc=soc;
			this.isHote=isHote;
		}
		public void run(){
			while(true){
				String str = netFunc.readString(soc);
				if(str==null) {
					netFunc.closeSocket(soc);
					sockets.remove(soc);
				}else{
				newMessage=true;
				System.out.println("J'ai recu un message.");
				if(isHote){
					System.out.println("Je suis hote et j'envoie un msg");
					for(Socket tmp:sockets){
							netFunc.sendString(tmp,str);

					}
				}else{
					System.out.println(str);
					messages.add(str);
					Platform.runLater(()-> pan.miseAjour());
				}
			}
		}
		}
	}

	public String getNewMessage(){
		if(newMessage){
			System.out.println("J'ai un newMess");
			newMessage=false;
			return messages.getLast();
		}
		return null;
	}


	public LinkedList<String> getMessages(){return messages;}

	/*public static void main(String[] args) {
		chat tmp = new chat();
		if(args.length<1){
			tmp.initHost("hote");
		}else{
			tmp.initClient("client", args[0]);
		}
		while(true){
			Scanner sc = new Scanner(System.in);
			String str = sc.nextLine();
			tmp.sendMessage(str);
		}
	}*/
}