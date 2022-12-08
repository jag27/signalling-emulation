import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import signalLogic.App;

public class GraphicInterface extends Application{

    @Override
    public void start(Stage stage) {
        // Label title = new Label("hello world");

        // create instance of sim to reference and run
        App myApp = new App();

        // create ui variables
        Button toggleSimPause = new Button("Start");
        Group g = new Group();
        ArrayList<Rectangle> rectList = new ArrayList<Rectangle>();
        ArrayList<Circle> circleList = new ArrayList<Circle>();
        ArrayList<Circle> dYellows = new ArrayList<Circle>(); // extra list holding circles for double yellow signal that are either transparent or yellow;

        // populate ui variables
        for (int i = 0; i<myApp.get_tracks().size(); i++){
            rectList.add(new Rectangle((20+60*i), 200, 50, 10));
            rectList.get(i).setFill(Color.RED);
            g.getChildren().add(rectList.get(i));
            circleList.add(new Circle(80+60*i, 180, 5));
            g.getChildren().add(circleList.get(i));
            dYellows.add(new Circle(80+60*i, 170, 5));
            g.getChildren().add(dYellows.get(i));
        }

        // set ui attributes
        g.getChildren().add(toggleSimPause);
        Scene scene = new Scene(g);
        stage.setMinHeight(480);
        stage.setMinWidth(480);
        // stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();

        // initialise ui updater
        Timeline autoUpdate = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {

            // some logic here since program started out as text based -> could be refined in future
            for (int i = 0; i < myApp.get_tracks().size(); i++) {
                if (myApp.get_tracks().get(i).has_train()) {
                    rectList.get(i).setFill(Color.RED);
                } else {
                    rectList.get(i).setFill(Color.BLACK);
                }
                circleList.get(i).setFill(myApp.get_signals().get(i).get_color());  // integrated color into Signal class for easier setting here
                if (myApp.get_signals().get(i).get_d_yellow()) {  // special double yellow case which needs logic
                    dYellows.get(i).setFill(myApp.get_signals().get(i).get_color()); // could technically straight set to yellow, set to sig color for debug purposes
                } else {
                    dYellows.get(i).setFill(Color.TRANSPARENT);
                }
            }
        }));
        autoUpdate.setCycleCount(Timeline.INDEFINITE);
        // autoUpdate.play(); <- was used to immediately start running sim
        // myApp.run_sim(); <- was used to immediately start running sim

        // action event for toggleSimPause
        EventHandler<ActionEvent> togglePause = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                myApp.toggleSim();
                if ((toggleSimPause.getText() == "Start") || (toggleSimPause.getText() == "Resume")) {
                    toggleSimPause.setText("Pause");
                    autoUpdate.play();
                } else {
                    toggleSimPause.setText("Resume");
                    autoUpdate.pause();
                }
            }
        };
        toggleSimPause.setOnAction(togglePause);

    }

    public static void main(String[] args) {
        GraphicInterface.launch();
    }
}
