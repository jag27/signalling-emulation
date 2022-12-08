package signalLogic;

import javafx.scene.paint.Color;
public class Signal {
    private Color color = Color.GREEN;
    private String aspect = "green";
    private boolean dYellow = false;
    private Track trackBerth;

    public Signal(Track track) {
        this.trackBerth = track.get_next_track();
    }


    // THOUGHT: DONT NEED TO LINK SIGNALS IF LINKED TO TRACK, JUST CHECK IF TRAIN ON TRACK, NOT NOT SIGNAL TO SET ASPECT
    // will be long to change, but might be neccessary. nothing is working rn
    // this seems like the way to go, will need a lot of updating but should fix a lot of errors.
    // also the doublely linked list of signals seems overkill anyway. track is single linked list, why should signals be different?


    // TO DO NEXT: remove signal linking, get signal aspect to depend DIRECTLY from the track, not other signals.  <-- done




    // DIRECTION IS IMPORTANT when updating signals. <-- is this true?

    public Color get_color() {
        return this.color;
    }

    public String get_aspect() {
        return this.aspect;
    }

    public boolean get_d_yellow() {
        return this.dYellow;
    }

    public Track get_track() { 
        return this.trackBerth;
    }

    public void update() {
        if (this.get_track().has_train()) {
            this.color = Color.RED;
            this.dYellow = false;
            this.aspect = "red";

        } else if (this.get_track().get_next_track().has_train()) {
            this.color = Color.YELLOW;
            this.dYellow = false;
            this.aspect = "yellow";

        } else if (this.get_track().get_next_track().get_next_track().has_train()) {
            this.color = Color.YELLOW;
            this.dYellow = true;
            this.aspect = "double yellow";
            
        } else {
            this.color = Color.SPRINGGREEN; 
            this.dYellow = false;
            this.aspect = "green";
        }
    }

}
