import java.util.ArrayList;

public class App {

    // what i am working on currently / next time i code:
    // check signal class

    public static void main(String[] args) throws Exception {
        int numTracks = 10;
        int numTrains = 3;
        ArrayList<Track> trackArray = Setup.get_tracks(numTracks);
        ArrayList<Signal> sigArray = Setup.get_sigs(trackArray);
        ArrayList<Train> trainArray = Setup.get_trains(numTrains, trackArray);

        // set up graphical interface here
        // currently not working on laptop -- need to install javafx, might work on desktop?



        // update loop here: move train first, then update signal.
        while (true){
            Thread.sleep(1000);
            for (Train train: trainArray) {
                if (train.get_transitioning()) {
                    train.do_move(train.get_pos().get_next_track().get_track_sig()); 
                    // this should fix train reacting to previous signal while transitioning
                } else {train.do_move(train.get_pos().get_track_sig());}
            }
            for (Signal sig: sigArray) {
                sig.update();
            }

        }
    }
}
