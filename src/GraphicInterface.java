import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import signalLogic.App;

public class GraphicInterface extends Application{
    // this is where javafx whould be built
    // check out javafx hello world program to get started fast
    @Override
    public void start(Stage stage) {
        // Label title = new Label("hello world");
        App myApp = new App();
        Group g = new Group();
        ArrayList<Rectangle> rectList = new ArrayList<Rectangle>();
        ArrayList<Circle> circleList = new ArrayList<Circle>();
        ArrayList<Circle> dYellows = new ArrayList<Circle>();
        for (int i = 0; i<myApp.get_tracks().size(); i++){
            rectList.add(new Rectangle((20+60*i), 200, 50, 10));
            rectList.get(i).setFill(Color.RED);
            g.getChildren().add(rectList.get(i));
            circleList.add(new Circle(80+60*i, 180, 5));
            g.getChildren().add(circleList.get(i));
            dYellows.add(new Circle(80+60*i, 170, 5));
            g.getChildren().add(dYellows.get(i));
        }
        Scene scene = new Scene(g);
        stage.setMinHeight(480);
        stage.setMinWidth(480);
        // stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
        Timeline autoUpdate = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            for (int i = 0; i < myApp.get_tracks().size(); i++) {
                if (myApp.get_tracks().get(i).has_train()) {
                    rectList.get(i).setFill(Color.RED);
                } else {
                    rectList.get(i).setFill(Color.BLACK);
                }
                circleList.get(i).setFill(myApp.get_signals().get(i).get_color());
                if (myApp.get_signals().get(i).get_d_yellow()) {
                    dYellows.get(i).setFill(myApp.get_signals().get(i).get_color()); // could technically straight set to yellow, set to sig color for debug purposes
                } else {
                    dYellows.get(i).setFill(Color.TRANSPARENT);
                }
            }
        }));
        autoUpdate.setCycleCount(Timeline.INDEFINITE);
        autoUpdate.play();
        myApp.run_sim();


    }

    public static void main(String[] args) {
        GraphicInterface.launch();
    }
}
