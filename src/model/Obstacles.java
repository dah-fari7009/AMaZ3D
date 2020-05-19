package model;

import java.awt.geom.Point2D;

public class Obstacles extends Divers{
    private String s;

    public Obstacles(Maze m, String type){
        super(m);
        s=type;
        put();
    }
    public Obstacles(Maze m, Point2D p, String type){
      super(m,p);
      s=type;
    }
    public String getShape(){
        return s;
    }

}
