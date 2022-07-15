import java.util.ArrayList;

public class App {

    public static void main(String[] args) throws Exception {
        int numTracks = 10;
        int numTrains = 3;
        ArrayList<Track> trackArray = Setup.get_tracks(numTracks);
        ArrayList<Signal> sigArray = Setup.get_sigs(trackArray);
        ArrayList<Train> trainArray = Setup.get_trains(numTrains, trackArray);
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
