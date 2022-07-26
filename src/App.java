import java.util.ArrayList;

public class App {

    // what i am working on currently / next time i code:
    // check signal class

    public static void main(String[] args) throws Exception {
        int numTracks = 10;
        int numTrains = 1;
        ArrayList<Track> trackArray = Setup.get_tracks(numTracks);
        ArrayList<Signal> sigArray = Setup.get_sigs(trackArray);
        ArrayList<Train> trainArray = Setup.get_trains(numTrains, trackArray);

        // set up graphical interface here
        // currently not working on laptop -- need to install javafx, might work on desktop?


        // BIG ISSUE IS KEEPING TABS ON TRAIN POSITION. currently two attributes to store this info, but only one is updated
        // when updating, currently update based on train, maybe better to centralise track position within train class?
        // if kept withing train class, how to tell if track is occupied? another call on update function?
        // UPDATE: issue fixed, no issues found as of yet

        // actually it might be possible to keep as is, and update train position when train is cleared
        // because track will know a train is on it.  <-- going with this for now.
        // this fix works, but creates another issue. when train is passing signal, it reads the signal it just passed
        // until it clears the track. which is always red. how to fix? 
        // temporary fix of new attribute to train, set to use next track signal/signal it just saw when passing
        // when passing the signal.  <-- current fix works, no more decel when transitioning from green signals


        // this fix doesnt explain yellow signals when nothing is making it yellow. or does it?
        // update on this: seems just the first signal is yellow, rest are green. why? how does first sig yellow, then
        // when the train passes it, his next is green when theres no other train????????? possible signal linking issue?
        // confirmed signal linking issue, seems like first signal is bound the same way as the last signal
        // meaining whatever the last signal shows, the first signal shows also.


        // update loop here: move train first, then update signal.
        while (true){
            Thread.sleep(100);
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
