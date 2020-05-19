package controller;

import controller.GameVersion;
import controller.Player;
import controller.SoloVersion;
import model.FormatNotSupported;
import model.MazeFloors;
import model.Scores;

import java.io.*;
import java.util.Stack;

public class MultiPlayerVersion implements Serializable {
    protected MazeFloors originalMaze;
    protected GameVersion game;
    protected Scores scores;
    protected Stack<Player> players;

    public MultiPlayerVersion(String[] names, MazeFloors maze) throws FormatNotSupported, IOException{
        players = new Stack<>();
        scores = new Scores();
        for (int i = names.length - 1; i >= 0; i--) players.push(new Player(names[i]));
        try{
        game = new SoloVersion(maze.clone(), players.pop(), scores);
            originalMaze=maze;
        }
        catch(CloneNotSupportedException e){

        }
    }

    public GameVersion getGame() {
        return game;
    }

    public void save(String file) throws IOException {
        File f=new File(file+".serM");
        if(!f.exists()) f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
    }

    public boolean gameOver() {
        return players.isEmpty();
    }

    public GameVersion next() throws IOException, FormatNotSupported,CloneNotSupportedException {
       game = new SoloVersion(originalMaze.clone(), players.pop(), scores);
       return game;
    }

    public Stack<Player> getPlayers() {
        return players;
    }
}