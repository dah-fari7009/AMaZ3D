package org.example.amaz3d.controller;

import org.example.amaz3d.Constants;
import org.example.amaz3d.model.exceptions.FormatNotSupported;
import org.example.amaz3d.model.MazeFloors;
import org.example.amaz3d.model.Scores;

import java.io.File;
import java.io.IOException;

public class TimeTrialVersion extends GameVersion {
	public final int timeLimit;


	public TimeTrialVersion(int length, int width,  int nbFloors,int nbObstacles,int nbMonstres,int nbTeleport,int nbDoors,int nbBonus,int typeBonus,String name, int time) throws FormatNotSupported,IOException{
		super(length,width,nbFloors,nbObstacles,nbMonstres,nbTeleport,nbDoors,nbBonus,typeBonus,name,new Scores("bestRaces.txt", MazeInterface.getDifficulty(length,width)));
		timeLimit=time;

	}

    public TimeTrialVersion(MazeFloors maze, Player player, int time) throws FormatNotSupported,IOException{
		super(maze,player,new Scores(Constants.TEXT_RESOURCES_PATH + File.separator + "bestRaces.txt", MazeInterface.getDifficulty(maze.getFloor().getFirst().getHeight(),maze.getFloor().getFirst().getWidth())));
        timeLimit=time;

    }

    public TimeTrialVersion(MazeFloors maze, String name, int time) throws FormatNotSupported,IOException{
	    super(maze,name,new Scores(Constants.TEXT_RESOURCES_PATH + File.separator + "bestRaces.txt", MazeInterface.getDifficulty(maze.getFloor().getFirst().getHeight(),maze.getFloor().getFirst().getWidth())));
	    timeLimit=time;

    }

    @Override
	public String scoresFile(){
		return Constants.TEXT_RESOURCES_PATH + File.separator + "bestRaces.txt";
	}





}

