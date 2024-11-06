package org.example.amaz3d.model.exceptions;

import org.example.amaz3d.controller.MazeInterface;

public class FormatNotSupported extends Exception{
	public FormatNotSupported(){
		super();
	}

	public FormatNotSupported(String msg){
		super(msg);
		if(!msg.equals("")) MazeInterface.warning(msg);
	}
}