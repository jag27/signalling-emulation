import java.util.ArrayList;

public class App {

    public static void main(String[] args) throws Exception {
        int numTracks = 10;
        int numTrains = 3;
        ArrayList<Track> trackArray = Setup.get_tracks(numTracks);
        ArrayList<Signal> sigArray = Setup.get_sigs(trackArray);
        ArrayList<Train> trainArray = Setup.get_trains(numTrains, trackArray);

        // set up graphical interface here
        // currently not working on laptop -- need to install javafx, might work on desktop?
        javafx



        // update loop here: move train first, then update signal.
        while (true){
            Thread.sleep(1000);
            for (Train train: trainArray) {
                train.do_move(train.get_pos().get_track_sig());
            }
            for (Signal sig: sigArray) {
                sig.update();
            }

        }
    }
}
