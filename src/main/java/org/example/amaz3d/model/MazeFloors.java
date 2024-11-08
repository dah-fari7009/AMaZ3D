package org.example.amaz3d.model;

import org.example.amaz3d.model.exceptions.FormatNotSupported;

import java.io.*;
import java.util.*;

public class MazeFloors implements Serializable,Cloneable{
    private LinkedList<Maze> mazes;
    private int nbFloors;

    public MazeFloors(int L, int l, int nb)throws FormatNotSupported {
        this(L,l,nb,0,0,0,0,0,0);
    }

    public MazeFloors(Maze m){
        mazes=new LinkedList<Maze>();
        mazes.add(m);
        nbFloors=0;
    }

    public LinkedList<Maze>getFloor(){
      return mazes;
    }
    public MazeFloors(LinkedList<Maze> m){
        mazes=m;
        nbFloors=m.size();
    }
    public MazeFloors(int L, int l, int nb, int nbObstacles, int nbMonstres,int nbTeleport,int nbDoors,int nbBonus, int typeBonus) throws FormatNotSupported {
        if(nb==0) throw new FormatNotSupported("You must put a number of floors, min 1");
        mazes=new LinkedList<Maze>();
        nbFloors=nb;
        while(nb!=0){
            mazes.add(new Maze(L,l,nbObstacles,nbMonstres,nbTeleport,nbDoors,nbBonus,typeBonus)); //problèmes dans getcase pour teleporteur et porte
            nb--;
        }
        if(nbFloors!=1) {
            for (int i = 0; i < mazes.size(); i++) {
                Maze m = mazes.get(i);
                if (i == 0) m.changeEnding();
                else if (i == mazes.size() - 1) m.changeBeginning();
                else {
                    m.changeBeginning();
                    m.changeEnding();
                }
            }
        }
    }

    public MazeFloors clone() throws CloneNotSupportedException{
      LinkedList<Maze> m=new LinkedList<>();
      for(Maze ma:this.mazes) m.add(ma.clone());
      return new MazeFloors(m);
    }

    public void print(){ //affiche le labyrinthe dans le terminal
        for(int i=0; i<mazes.size(); i++){
            mazes.get(i).print();
            System.out.println("Next Floor!!");
        }
    }

    public static void main(String []args) throws FormatNotSupported {
        MazeFloors mf=new MazeFloors(20,10,3,0,0,0,0,3,0);
        mf.print();
    }
}
