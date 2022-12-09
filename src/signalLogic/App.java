    // what i am working on currently / next time i code:
    // configure UI to look nicer
    // add features to ui that allow user to customise sim options (number of trains, tracks, etcetera)

    // what i plan to deliver at some point later:
    // points / route setting for user
    // entry / exit points for trains (this means a full timetabler)
    // interlocking for points / routes
    // level crossings
    // different train attribute (accel, decel, specific power needs -> can only go on specific tracks)
    // bi directional working
    // ability for user to control train direction / remove trains directly from sim
    // route cancelling

package signalLogic;

import java.util.ArrayList;

public class App {

    // sim variables
    private static int numTracks = 10;
    private static int numTrains = 5;
    private static ArrayList<Track> trackArray = Setup.get_tracks(numTracks);
    private static ArrayList<Signal> sigArray = Setup.get_sigs(trackArray);
    private static ArrayList<Train> trainArray = Setup.get_trains(numTrains, trackArray);
    private static boolean simRunning = false;

    public class appThread implements Runnable {
        @Override
        public void run() {    
    
            // update loop here: move train first, then update signal.
            while (simRunning){
                    try {
                    Thread.sleep(1000);
                    for (Train train: trainArray) {
                        if (train.is_transitioning()) {
                            train.do_move(train.get_pos().get_next_track().get_track_sig()); 
                            // this should fix train reacting to previous signal while transitioning
                        } else {train.do_move(train.get_pos().get_track_sig());}
                    }
                    for (Signal sig: sigArray) {
                        sig.update();
                    }

                    // debug function to see track status
                    for (Track track: trackArray) {
                        if (track.has_train()) {
                            System.out.print("xxxxxx " + track.get_track_sig().get_aspect() + " ");
                        } else {
                            System.out.print("oooooo " + track.get_track_sig().get_aspect() + " ");
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Got interrupted");
                }
            }
        }
    }
    
    
    public void run_sim() {
        simRunning = true;
        new Thread(new appThread()).start();
    }

    public void toggleSim() {
        if (simRunning) {
            simRunning = false; // not an immediate pause to the sim here, instead it does one last cycle, loop in thread is false therefore thread finished and is dead
            // if immediate pause is wanted, could kill thread explicitly?
        } else {
            run_sim();
        }
    }

    // method to stop sim when application exits but sim is still running
    public void stop_sim() {
        simRunning = false;
    }

    // getters of main objects: very general, could refine to return more specific objects rather than big arraylists?
    public ArrayList<Track> get_tracks() {
        return trackArray;
    }

    public ArrayList<Train> get_trains() {
        return trainArray;
    }

    public ArrayList<Signal> get_signals() {
        return sigArray;
    }
}

