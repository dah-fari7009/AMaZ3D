package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.*;

public class Teleporteur extends Divers{
    private final Point2D end;

    public Teleporteur(Maze m){
        super(m);
        end=new Point2D.Double();
        put();
        put(end);
        while(end.equals(p)) {
            put(end);
        }
    }

    public Teleporteur(Maze m, Point2D p){
      super(m,p);
      end=null;
    }

    public Teleporteur(Maze m, Point2D s, Point2D e){
        super(m,e);
        end=s;
    }

    public Point2D getStart() {
        return p;
    }

    public Point2D getEnd() {
        return end;
    }
    private void put(Point2D point){
        Random rand=new Random();
        int i=0; int j=0;
        while(maze.getCase(i,j)!=Maze.WAY) {
            j = rand.nextInt(maze.getWidth());
            i = rand.nextInt(maze.getHeight());
        }
        double k=centrer(i);
        double l=centrer(j);
        point.setLocation(l,k);
    }

    public MeshView initTeleport() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("fxml/teleport.fxml"));
        PhongMaterial mat = new PhongMaterial();
        mat.setSpecularColor(Color.HOTPINK);
        mat.setDiffuseColor(Color.PURPLE);
        MeshView teleport = fxmlLoader.<MeshView>load();
        teleport.setMaterial(mat);
        Rotate r=new Rotate();
        r.setAxis(Rotate.X_AXIS);
        r.setAngle(r.getAngle()-90);
        teleport.getTransforms().add(r);
        return teleport;
    }
}
