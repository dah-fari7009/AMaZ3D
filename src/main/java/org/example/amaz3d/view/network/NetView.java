package org.example.amaz3d.view.network;
import org.example.amaz3d.controller.GameVersion;
import org.example.amaz3d.controller.MazeInterface;
import org.example.amaz3d.controller.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.MeshView;
import javafx.util.Duration;
import org.example.amaz3d.model.Scores;
import org.example.amaz3d.view.View;
import org.example.amaz3d.controller.networking.*;

import java.io.IOException;

import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class NetView extends View {
    ArrayList<Player> players=new ArrayList<Player>();
    private static boolean debug = true;
    private DoubleProperty[][]t;
    private boolean finish=false;

    public NetView(GameVersion game, Socket me) throws IOException{
        super(new StackPane(), game);
        control=new NetControl(me);
    }

    protected class NetControl extends GameControl{
        private Socket me;
        private sendPos sp;
        private getPos gp;
        public NetControl(Socket me) throws IOException{
            super(true);
            this.me=me;
            sp = new sendPos();
            sp.start();
            gp=new getPos();
            gp.start();
        }

        protected class EndPane extends TimePane {
            public EndPane() {
                super(0);
                timeLine.setCycleCount(Timeline.INDEFINITE);
                timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(0.2), (event) -> {
                    game.convertBonus();
                    timePane.setTimeSeconds(game.getElapsed());
                    if (game.player().getBonus().size() == 0 && !MazeInterface.sounds(5).isPlaying()) {
                        Scores sc = game.scores();
                        game.addToScoresList();
                        netFunc.sendObject(me, sc);
                        if (debug)
                            System.out.println("J'envoie le score");
                        timeLine.stop();
                    }
                }));
            }
        }

        public void whenIsFinished(){
            if(!finish) {
                MazeInterface.sounds(5).play();
                finish = true;
                timePane.stop();
                sp.arret();
                NetView.this.setOnKeyPressed(null);
                new EndPane().play();

            }
        }

        public void isDead(){
            if(!finish){
                finish=true;
                timePane.stop();
                sp.arret();
                NetView.this.setOnKeyPressed(null);
                Scores sc = new Scores();
                sc.addToScoresList(game.player().getName(),(24*3600)-1);
                if(debug)
                    System.out.println("Envoie du score apres mort.");
                
                netFunc.sendObject(me,sc);
            }
        }

        private class sendPos extends Thread{
            private volatile boolean end;
            public sendPos(){
                super();
                end=false;
            }

            public void run(){
                if(debug)
                    System.out.println("Je peux aller la");
                while(!end){
                    try{
                        netFunc.sendObject(me,game.player());
                        if(debug)
                            System.out.println("Voici ma position");
                        Thread.sleep(200);
                    }catch(Exception e){
                        e.printStackTrace();
                        if(debug)
                            System.out.println("Je peux pas envoyer ma pos");
                    }
                }
            }

            public void arret(){
                end =true;
            }
        }

        public class getPos extends Thread{
            private volatile boolean end;
            public getPos(){
                super();
                end=false;
            }

            public void run(){
                boolean premierTour=true;
                while(!end){
                    try{
                        Object tmp=netFunc.readObject(me);
                        if(tmp instanceof ArrayList){
                            if(((ArrayList)tmp).get(0) instanceof Player){
                                if(debug)
                                    //System.out.println("Je recois une list de pos.");
                                    players = (ArrayList<Player>)tmp;
                                if(premierTour){
                                    t=new DoubleProperty[players.size()][3];
                                    Platform.runLater(() -> drawPlayer(mazePane)); //à changer
                                    premierTour=false;
                                }
                                else  Platform.runLater(() -> updatePlayer());
                            }
                        }else if(tmp instanceof Scores) {
                            if(debug)
                                System.out.println("Je recois le score");
                            
                            Scores sc = (Scores) tmp;
                            Scanner scan = new Scanner(game.scores().toString());
                            if(!scan.hasNextLine()) {
                                Scores sc2 = new Scores();
                                sc2.addToScoresList(game.player().getName(),(24*3600)-1);
                                scan=new Scanner(sc2.toString());
                                }
                                sc.setCurrent(scan.nextLine());
                            Platform.runLater(() -> displayScores(sc));
                            if(debug)
                                System.out.println("J'ai affiché le score");
                        }
                    }catch(Exception e){if(debug) e.printStackTrace();}
                }
            }

            public void arret(){
                end=true;
            }
        }
    }


    private void printPlayer(ArrayList<String> list){
        System.out.println("Les joueurs ayant fini sont :");
        for(String str:list)
            System.out.println("  - "+str);
    }

    public void drawPlayer(Group root){
        //System.out.println("Je suis dans drawPlayer() et il y a "+players.size());
        for(int i=0; i<players.size(); i++) {
            try {
                //System.out.println("Je dessine le joueur numéro " + i);
                Player p = players.get(i);
                MeshView player = p.initPlayer();
                int g = p.getMazeIndex();
                //System.out.println("Je suis au sol");
                t[i][0] = new SimpleDoubleProperty((p.getPosition().getX() + mazePane.getCoordSwitch()[g].x()) * mazePane.getSIZE_BOX() - mazePane.getSIZE_BOX() /2);
                t[i][1] = new SimpleDoubleProperty((p.getPosition().getY() + mazePane.getCoordSwitch()[g].z()) * mazePane.getSIZE_BOX() - mazePane.getSIZE_BOX() /2);
                t[i][2] = new SimpleDoubleProperty(-p.getY() * mazePane.getSIZE_BOX() + mazePane.getSIZE_BOX()/4);
            /*x=new SimpleDoubleProperty(p.getPosition().getX()+mazePane.coordSwitch[g].x()*mazePane.SIZE_BOX-mazePane.SIZE_BOX/2);
            z=new SimpleDoubleProperty(p.getPosition().getY()+mazePane.coordSwitch[g].z()*mazePane.SIZE_BOX-mazePane.SIZE_BOX/2);
            y=new SimpleDoubleProperty(-p.getY()*mazePane.SIZE_BOX);*/
                //System.out.println("Je suis entre les deux");
                player.translateXProperty().bind(t[i][0]);
                player.translateYProperty().bind(t[i][2]);
                player.translateZProperty().bind(t[i][1]);
            /*player.setTranslateX(p.getPosition().getX()+mazePane.coordSwitch[g].x()*mazePane.SIZE_BOX-mazePane.SIZE_BOX/2);
            player.setTranslateZ(p.getPosition().getY()+mazePane.coordSwitch[g].z()*mazePane.SIZE_BOX-mazePane.SIZE_BOX/2);
            player.setTranslateY(-p.getY()*mazePane.SIZE_BOX);*/
                player.setScaleX(player.getScaleX()* mazePane.getSIZE_BOX()/20);
                player.setScaleZ(player.getScaleZ()* mazePane.getSIZE_BOX()/20);
                player.setScaleY(player.getScaleY()* mazePane.getSIZE_BOX()/10);
                //System.out.println("J'ai fait les modifs");
                root.getChildren().add(player);
            }catch(IOException e){
                System.out.println("Impossible de créer le joueur "+i);
            }
        }
    }

    public void updatePlayer(){
        for(int i=0; i<players.size(); i++) {
            //System.out.println("J'ai un tab de tille "+players.size());
            //System.out.println("Je met à jour le joueur numéro " + i);
            Player p = players.get(i);
            int g = p.getMazeIndex();
            t[i][0].set((p.getPosition().getX() + mazePane.getCoordSwitch()[g].x()) * mazePane.getSIZE_BOX() - mazePane.getSIZE_BOX() /2);
            t[i][1].set((p.getPosition().getY() + mazePane.getCoordSwitch()[g].z()) * mazePane.getSIZE_BOX() - mazePane.getSIZE_BOX() /2);
            t[i][2].set(-p.getY() * mazePane.getSIZE_BOX() + mazePane.getSIZE_BOX()/4);
            //System.out.println(t[i][0].get()+" "+t[i][1].get()+" "+t[i][2].get());
            //System.out.println("Je suis encore au "+i);
        }
    }
}