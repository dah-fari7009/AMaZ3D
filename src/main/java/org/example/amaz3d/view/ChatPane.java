package org.example.amaz3d.view;

import org.example.amaz3d.controller.networking.Chat;
import javafx.scene.input.KeyCode;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.amaz3d.Constants;

import java.util.LinkedList;

public class ChatPane extends BorderPane{
	private Chat chat;
	private VBox messages;
	private TextField text;
	private Button send;

	public void close(){
		chat.close();
	}

	public ChatPane(){
		super();
		getStyleClass().add("root");
		messages=new VBox();
		text=new TextField();
		text.getStyleClass().add("tofill");
		send = new Button();
		send.setText("Send");
		chat = new Chat(this);
		send.setOnMouseClicked(e->{
			chat.sendMessage(text.getText());
			text.setText("");
		});
		HBox bas = new HBox();
		bas.getStyleClass().add("hbox");
		bas.getChildren().addAll(text,send);
		setBottom(bas);
		setTop(messages);
		this.setOnKeyPressed(event -> {
			if(event.getCode()== KeyCode.ENTER){
				chat.sendMessage(text.getText());
				text.setText("");
			}
		});
		getStylesheets().add(Constants.getCssResourcePath("chat.css"));
	}

	public void initHost(String name){
		chat.initHost(name);
	}

	public void initClient(String name, String addr){
		chat.initClient(name,addr);
	}


	public void addMessage(String str){
		Label lab = new Label(str);
		lab.getStyleClass().add("messages");
		messages.getChildren().add(lab);
	}

	public void styleMessage(String str){
		String debut="";
		String fin="";
		boolean flag = true;
		for(int i=0; i<str.length(); i++){
			if(flag){
				if(str.charAt(i)==':') flag=false;
				debut+=str.charAt(i)+"";
			}else fin+=str.charAt(i)+"";
		}
		Label deb=new Label(debut);
		deb.getStyleClass().add("deb");
		Label end=new Label(fin);
		end.getStyleClass().add("end");
		HBox align=new HBox();
		align.getChildren().addAll(deb,end);
		messages.getChildren().add(align);
	}

	public void miseAjour(){
		messages.getChildren().clear();
		LinkedList<String> m=chat.getMessages();
		for(int i=Math.max(0,m.size()-20);i<m.size();i++ ){
			styleMessage(m.get(i));
		}
	}
}