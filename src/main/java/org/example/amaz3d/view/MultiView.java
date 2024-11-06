package org.example.amaz3d.view;

import org.example.amaz3d.controller.MultiPlayerVersion;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.example.amaz3d.model.exceptions.FormatNotSupported;

import java.io.IOException;
import java.time.LocalDate;

public class MultiView extends View {
    protected MultiPlayerVersion multi;

    public MultiView(MultiPlayerVersion multi) throws IOException, FormatNotSupported {
        super(new StackPane(), multi.getGame());
        this.multi = multi;
        control = new MultiControl();

    }

    protected class MultiControl extends GameControl {
        protected IntegerProperty timeToBeat;
        protected Label nameLabel;
        protected HBox toBeat = new HBox();
        protected Label label;

        public MultiControl() throws IOException, FormatNotSupported {
            super(false);
            label = new Label();
            nameLabel = new Label(game.player().getName());
            timeToBeat = new TimeProperty(0);
            label.textProperty().bind(timeToBeat.asString());
            label.setStyle("-fx-alignment:center;-fx-text-fill:crimson;-fx-font-size:50pt");
            nameLabel.setStyle("-fx-alignment:center;-fx-text-fill:white;-fx-font-size:25pt");
            toBeat.getChildren().addAll(new Label("Time to beat : "), label);
            save.setOnMouseClicked(e -> {
                LocalDate now = LocalDate.now();
                String date[] = now.toString().split("-");
                try {
                    multi.save("src/main/resources/org/example/amaz3d/savings/" +game.player().getName()+"--"+date[2] + "-" + date[1] + "-" + date[0].charAt(2) +date[0].charAt(3));
                } catch (Exception exc) {
                    if(debug)exc.printStackTrace();
                }
            });
            tool.getItems().add(nameLabel);
        }

        protected class EndPane extends TimePane {
            public EndPane() {
                super(0);
                timeLine.setCycleCount(Timeline.INDEFINITE);
                timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(0.2), (event) -> {
                    game.convertBonus();
                    timePane.timeSeconds.set(game.getElapsed());
                    if (game.player().getBonus().size() == 0) {
                        game.addToScoresList();
                        timeToBeat.set(game.scores().get(0).getValue());
                        if (!multi.gameOver()) {
                            try {
                                game = multi.next();
                                mazePane.getChildren().clear();
                                handleAction();
                                timePane.getChildren().add(toBeat);
                                nameLabel.setText(game.player().getName());
                            } catch (IOException e1) {
                                if(debug) e1.printStackTrace();
                            } catch (FormatNotSupported formatNotSupported) {
                                if(debug)formatNotSupported.printStackTrace();
                            } catch (CloneNotSupportedException e) {
                                if(debug)e.printStackTrace();
                            }
                        } else {
                            displayScores(game.scores());

                        }
                        timeLine.stop();
                    }
                }));
            }
        }

        @Override
        public void whenIsFinished() {
            timePane.stop();
            gameTimer.stop();
            MultiView.this.setOnKeyPressed(null);
            new EndPane().play();

        }


    }
}
