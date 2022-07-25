
public class Signal {
    private String aspect = "green";
    private Signal prevSignal;
    private Signal nextSignal;
    private Track trackBerth;

    public Signal(Track track) {
        this.trackBerth = track.get_next_track();
    }

    public void link_signals(Signal prev, Signal next) {
        this.prevSignal = prev;
        this.nextSignal = prev;
    }

    // THOUGHT: DONT NEED TO LINK SIGNALS IF LINKED TO TRACK, JUST CHECK IF TRAIN ON TRACK, NOT NOT SIGNAL TO SET ASPECT
    // will be long to change, but might be neccessary. nothing is working rn

    public String get_aspect() {
        return this.aspect;
    }

    public Signal get_prev_sig() {
        return this.prevSignal;
    }

    public Signal get_next_sig() {
        return this.nextSignal;
    }

    public Track get_track() { 
        return this.trackBerth;
    }

    public String update() {
        if (this.get_track().has_train()) {
            this.aspect = "red";
        } else {
            switch (this.nextSignal.get_aspect()) {
                case "red":
                    this.aspect = "yellow";
                    break;
                
                case "yellow":
                    this.aspect = "double yellow";
                    break;

                case "double yellow":
                    this.aspect = "green";
                    break;
                
                case "green":
                    this.aspect = "green";
            }
        }
        return this.aspect;
    }

}
