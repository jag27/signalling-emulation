
public class Signal {
    private String aspect = "green";
    private Signal prevSignal;
    private Signal nextSignal;
    private Track trackBerth;

    public Signal(Track track) {
        this.trackBerth = track;
    }

    public void link_signals(Signal prev, Signal next) {
        this.prevSignal = prev;
        this.nextSignal = prev;
    }

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