package org.example.amaz3d.controller;

import org.example.amaz3d.Constants;
import org.example.amaz3d.model.exceptions.FormatNotSupported;
import org.example.amaz3d.model.MazeFloors;
import org.example.amaz3d.model.Scores;

import java.io.File;
import java.io.IOException;

public class SoloVersion extends GameVersion {

	public SoloVersion(int length, int width, int nbFloors,int nbObstacles,int nbMonstres,int nbTeleport,int nbDoors,int nbBonus,int typeBonus,String name, Scores score) throws FormatNotSupported, IOException {
		super(length, width,nbFloors,nbObstacles,nbMonstres,nbTeleport,nbDoors,nbBonus,typeBonus, name, score);

	}

	public SoloVersion(MazeFloors maze, Player player, Scores scores) throws FormatNotSupported, IOException {
		super(maze, player, scores);

	}

	public SoloVersion(MazeFloors maze, String name, Scores scores) throws FormatNotSupported, IOException {
		super(maze, name, scores);

	}

	public SoloVersion(MazeFloors maze, Player player) throws FormatNotSupported, IOException {
		this(maze, player, new Scores(Constants.TEXT_RESOURCES_PATH + File.separator + "bestSolos.txt", MazeInterface.getDifficulty(maze.getFloor().getFirst().getHeight(),maze.getFloor().getFirst().getWidth())));

	}

	public SoloVersion(MazeFloors maze, String name) throws FormatNotSupported, IOException {
		this(maze, name, new Scores(Constants.TEXT_RESOURCES_PATH + File.separator + "bestSolos.txt", MazeInterface.getDifficulty(maze.getFloor().getFirst().getHeight(),maze.getFloor().getFirst().getWidth())));
	}

	@Override
	public String scoresFile() {
		return Constants.TEXT_RESOURCES_PATH + File.separator + "bestSolos.txt";
	}

}