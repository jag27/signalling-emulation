import java.util.ArrayList;

public class App {

    public static void main(String[] args) throws Exception {
        int numTracks = 20;
        int numTrains = 1;
        ArrayList<Track> trackArray = Setup.get_tracks(numTracks);
        ArrayList<Signal> sigArray = Setup.get_sigs(trackArray);
        ArrayList<Train> trainArray = Setup.get_trains(numTrains, trackArray);

        // set up graphical interface here
        // currently not working on laptop -- need to install javafx, might work on desktop?


        // BIG ISSUE IS KEEPING TABS ON TRAIN POSITION. currently two attributes to store this info, but only one is updated
        // when updating, currently update based on train, maybe better to centralise track position within train class?


        // update loop here: move train first, then update signal.
        while (true){
            Thread.sleep(500);
            for (Train train: trainArray) {
                train.do_move(train.get_pos().get_track_sig());
            }
            for (Signal sig: sigArray) {
                sig.update();
            }

        }
    }
}
