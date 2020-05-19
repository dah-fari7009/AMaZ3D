package controller;

import model.FormatNotSupported;
import model.MazeFloors;
import model.Scores;

import java.io.IOException;

public class TimeTrialVersion extends GameVersion {
	public final int timeLimit;


	public TimeTrialVersion(int length, int width,  int nbFloors,int nbObstacles,int nbMonstres,int nbTeleport,int nbDoors,int nbBonus,int typeBonus,String name, int time) throws FormatNotSupported,IOException{
		super(length,width,nbFloors,nbObstacles,nbMonstres,nbTeleport,nbDoors,nbBonus,typeBonus,name,new Scores("bestRaces.txt", MazeInterface.getDifficulty(length,width)));
		timeLimit=time;

	}

    public TimeTrialVersion(MazeFloors maze, Player player, int time) throws FormatNotSupported,IOException{
		super(maze,player,new Scores("txt/bestRaces.txt", MazeInterface.getDifficulty(maze.getFloor().getFirst().getHeight(),maze.getFloor().getFirst().getWidth())));
        timeLimit=time;

    }

    public TimeTrialVersion(MazeFloors maze, String name, int time) throws FormatNotSupported,IOException{
	    super(maze,name,new Scores("txt/bestRaces.txt", MazeInterface.getDifficulty(maze.getFloor().getFirst().getHeight(),maze.getFloor().getFirst().getWidth())));
	    timeLimit=time;

    }

    @Override
	public String scoresFile(){
		return "txt/bestRaces.txt";
	}





}

