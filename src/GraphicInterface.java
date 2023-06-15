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
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import signalLogic.App;

public class GraphicInterface extends Application{
    private App myApp;

    @Override
    public void start(Stage stage) {
        // Label title = new Label("hello world");

        // create instance of sim to reference and run
        myApp = new App();

        // create ui variables
        Button toggleSimPause = new Button("Start");
        Group g = new Group();
        ArrayList<Rectangle> rectList = new ArrayList<Rectangle>(); // holds all track sections
        ArrayList<Circle> circleList = new ArrayList<Circle>(); // holds all single colour aspects
        ArrayList<Circle> dYellows = new ArrayList<Circle>(); // extra list holding circles for double yellow signal that are either transparent or yellow;
        ArrayList<Line> signalFrame = new ArrayList<Line>(); // holds all the "frames" of the signal (the upside down l shape)

        // populate ui variables
        for (int i = 0; i<myApp.get_tracks().size(); i++){
            // TODO: make this process a template which takes in number of tracks and draws accordingly
            // TODO: make the tracks a loop
            rectList.add(new Rectangle((20+60*i), 200, 50, 10)); // create track section rectangle with these dimensions
            rectList.get(i).setFill(Color.BLACK); // default to red
            g.getChildren().add(rectList.get(i)); // place into group to be displayed in gui
            circleList.add(new Circle(80+60*i, 180, 5)); // create signal lists
            circleList.get(i).setFill(Color.RED); // default to red
            g.getChildren().add(circleList.get(i)); // place into group to be displayed in gui
            dYellows.add(new Circle(80+60*i, 170, 5)); // do same for double yellow aspect
            dYellows.get(i).setFill(Color.TRANSPARENT);
            g.getChildren().add(dYellows.get(i));

            // overly complicated way of drawing the signal frame
            // NOTE: in the list, the horizontal line comes FIRST, then the corresponding vertical line is ADJACENT IN THE LIST
            // sure i could make it 2d, but these should just be rendered once then never touched again, so it doesn't really matter
            signalFrame.add(new Line((70+60*i), 180, (75+60*i), 180));
            signalFrame.add(new Line((70+60*i), 180, (70+60*i), 195));
            g.getChildren().add(signalFrame.get(i*2));
            g.getChildren().add(signalFrame.get(i*2+1));
            // i am actually super happy with how these came out
        }

        // set ui attributes
        toggleSimPause.setLayoutX(340);
        toggleSimPause.setLayoutY(250);
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
        // autoUpdate.play(); <- was used to immediately start running sim: not needed
        // myApp.run_sim(); <- was used to immediately start running sim

        // action event for toggleSimPause
        EventHandler<ActionEvent> togglePause = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                myApp.toggleSim();
                if ((toggleSimPause.getText().equals("Start")) || (toggleSimPause.getText().equals("Resume"))) {
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

    @Override
    public void stop() {
        myApp.stop_sim();
    }

    public static void main(String[] args) {
        GraphicInterface.launch();
    }
}
