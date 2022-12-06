package signalLogic;
import java.util.ArrayList;
import java.util.Random;

public class Setup {
    public static ArrayList<Signal> get_sigs(ArrayList<Track> trackList) {
        ArrayList<Signal> toReturn = new ArrayList<Signal>();
        for (Track track: trackList) {
            Signal sig = new Signal(track);
            toReturn.add(sig);
            track.add_signal(sig);
        }

        return toReturn;
    }

    public static ArrayList<Train> get_trains(int num, ArrayList<Track> trackList) {
        Random rand = new Random();
        ArrayList<Train> toReturn = new ArrayList<Train>();
        for (int i=0; i<num; i++) {
            int len = rand.nextInt(50);
            while (len < 10) {
                len = rand.nextInt(50);
            }
            toReturn.add(new Train("train id " + String.valueOf(i+1), len, trackList.get(i)));
            trackList.get(i).set_train(toReturn.get(i), 200);
        }
        return toReturn;
    }

    public static ArrayList<Track> get_tracks(int num) {
        ArrayList<Track> toReturn = new ArrayList<Track>();
        for (int i=0; i<=num; i++) {
            toReturn.add(new Track((int)Math.floor(Math.random()*(1000-500+1)+500)));
        }
        for (int i=0; i<=num; i++) {
            if (i==num) {toReturn.get(i).link_tracks(toReturn.get(0));}
            else {toReturn.get(i).link_tracks(toReturn.get(i+1));}
        }
        return toReturn;
    }
    
}
