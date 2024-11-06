package org.example.amaz3d.model;

import org.example.amaz3d.model.Maze;

import java.awt.geom.Point2D;
public class Piece extends Bonus {

    public Piece(Maze m){
        super(m,"Piece");
    }
    public Piece(Maze m,Point2D p){
      super(m,p,"Piece");
    }
}
