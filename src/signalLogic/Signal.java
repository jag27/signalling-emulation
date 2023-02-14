package signalLogic;

import javafx.scene.paint.Color;
public class Signal {
    private Color color = Color.GREEN;
    private String aspect = "green";
    private boolean dYellow = false;
    private final Track trackBerth;

    public Signal(Track track) {
        // constructor for signal: must be linked to a track
        // note that the track passed will be the track that the signal indicates
        // i.e. in direction of travel, order is this signal, then the track passed
        this.trackBerth = track.get_next_track();
    }


    // setters and getters
    // get color of signal for javafx --> was thinking of overriding get_aspect, but double yellows cause problems
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

    // periodically called within App. updates the state of a signal object
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
