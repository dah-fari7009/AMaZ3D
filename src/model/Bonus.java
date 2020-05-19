package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import model.Divers;
import model.Maze;

import java.awt.geom.Point2D;
import java.io.IOException;

public abstract class Bonus extends Divers {
    protected String avantage;

    public Bonus(Maze m, String a){
        super(m);
        avantage=a;
        put();
    }
    public Bonus(Maze m, Point2D p, String a){
      super(m,p);
      avantage=a;
    }

    public String getAvantage(){return avantage;}

   public MeshView initBonus() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        PhongMaterial mat = new PhongMaterial();
        if(avantage.equals("model.Piece")) {
            fxmlLoader.setLocation(this.getClass().getResource("fxml/MegaCoin.fxml")); //mettre pieces ou bonus temps
            mat.setSpecularColor(Color.LIGHTGOLDENRODYELLOW);
            mat.setDiffuseColor(Color.YELLOW);
        }else{
            fxmlLoader.setLocation(this.getClass().getResource("fxml/sablier.fxml"));
            mat.setSpecularColor(Color.MAROON);
            mat.setDiffuseColor(Color.BROWN);
        }
        MeshView bonus = fxmlLoader.<MeshView>load();
        bonus.setMaterial(mat);
        bonus.setRotationAxis(Rotate.Z_AXIS);
        return bonus;
    }
}
