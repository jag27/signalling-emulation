import java.util.ArrayList;

public class App {

    // what i am working on currently / next time i code:
    // NEW CLASS FOR JAVAFX: lots of research still needed
    // route setting: entry exit style. then graphical interface to allow testing and implementation


    // IMPORTANT: multiple trains, trains still get lost somehow, debuggin rn
    // issue seems to be previous train is on the track the current train is on(?)
    // dont know why this is happening yet
    // might be easier to debug with graphical interface?

    // will make graphical interface first, then start full debugging.

    // need to do a LOT of research on javafx. seemed to be installed on desktop, not on my laptop (at least not correctly)
    // javafx extensions vs code?

    // JAVAFX ADDED TO LIBRARIES. import seems to work now on laptop.

    // IMPORTANT: FIND OUT WHERE REPOSITORY IS ON PC AND SET PATH_TO_FX VARIABLE otherwise i cant compile (i think)
    // libraries should all be there in libraries folder so ye :)
    

    public static void main(String[] args) throws Exception {
        int numTracks = 10;
        int numTrains = 3;
        ArrayList<Track> trackArray = Setup.get_tracks(numTracks);
        ArrayList<Signal> sigArray = Setup.get_sigs(trackArray);
        ArrayList<Train> trainArray = Setup.get_trains(numTrains, trackArray);

        // set up graphical interface here
        // ON SECOND THOUGHTS looks like graphical interface will need its own class (obviously lol)
        // hopefully this doesnt mean a whole new structure! im sure it will be fine
        // it kinda goes against what i know of java to have to redo everything, due to
        // the way java makes classes and stuff


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
