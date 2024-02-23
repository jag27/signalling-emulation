import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import signalLogic.App;
import signalLogic.Track;

import javax.swing.text.StyledEditorKit;

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
        ArrayList<Text> trainIdUIDisplay = new ArrayList<Text>(); // holds labels of each train headcode

        // find best display method for a loop of tracks
        int numOfTracks = myApp.get_tracks().size();
        if (numOfTracks%4 == 0) {
            // TODO: find an appropriate way to display a loop of tracks
        }

        // populate track and signal layout
        for (int i = 0; i<numOfTracks; i++){
            // TODO: make this process a template which takes in number of tracks and draws accordingly
            // TODO: make the tracks a loop
//            if (i == Math.floorDiv(numOfTracks, 2)-1 && numOfTracks%2 == 0){
//                rectList.add(new Polygon(20+60*i, ))
//            }
            rectList.add(new Rectangle((20+60*i), 200, 50, 10)); // create track section rectangle with these dimensions

            rectList.get(i).setFill(Color.RED); // default to red
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
            signalFrame.get(i*2).setStroke(Color.WHITE);
            signalFrame.get(i*2+1).setStroke(Color.WHITE);
            g.getChildren().add(signalFrame.get(i*2));
            g.getChildren().add(signalFrame.get(i*2+1));
            // i am actually super happy with how these came out

            // assign each train headcode to a new label to display on UI
            trainIdUIDisplay.add(new Text(25+60*i, 225, ""));
            trainIdUIDisplay.get(i).setTextAlignment(TextAlignment.CENTER);
            trainIdUIDisplay.get(i).setFill(Color.TURQUOISE);
            trainIdUIDisplay.get(i).setFont(Font.font("Stencil", 18));
            g.getChildren().add(trainIdUIDisplay.get(i));
        }

        // set ui attributes
        toggleSimPause.setLayoutX(340);
        toggleSimPause.setLayoutY(250);
        g.getChildren().add(toggleSimPause);
        Scene scene = new Scene(g);
        scene.setFill(Color.BLACK);
        stage.setMinHeight(480);
        stage.setMinWidth(480);
        // stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();

        // initialise ui updater
        Timeline autoUpdate = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {

            // some logic here since program started out as text based -> could be refined in future
            for (int i = 0; i < numOfTracks; i++) {
                Track inspectedTrack = myApp.get_tracks().get(i);
                if (inspectedTrack.has_train()) {
                    // track occupied: set colour to red and display train id
                    rectList.get(i).setFill(Color.RED);
                    if (inspectedTrack.get_train().equals(inspectedTrack.get_next_track().get_train())) {
                        // same train is spanning two track berths: clear display of the rear track
                        // this way the headcode is only displayed at the front of the train
                        trainIdUIDisplay.get(i).setText("");
                    } else {
                        // train is not spanning two tracks. display headcode on current track berth
                        trainIdUIDisplay.get(i).setText(myApp.get_tracks().get(i).get_train().get_td());
                    }
                } else {
                    rectList.get(i).setFill(Color.GREY);
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
