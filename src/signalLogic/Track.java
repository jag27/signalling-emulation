package signalLogic;

public class Track {
    private Train train = null;
    private int trackLength = 0;
    private Track nextTrack;
    private int trainFront;
    private int trainRear;
    private Signal sig = null;


    public Track(int length) {
        // constructor that defines track length. other attributes are initialised in link tracks or during runtime
        this.trackLength = length;
    }

    public void link_tracks(Track next) {
        this.nextTrack = next;
    }

    // setters and getters for track attributes. 
    public boolean has_train() {
        if (this.train == null) {
            return false;
        } else { return true; }
    }

    public Train get_train() {
        return this.train;
    }

    public void set_train(Train id, int pos) {
        this.train = id;
        this.trainFront = pos;
        this.trainRear = pos - id.get_length(); // this value can be negative when new train enters
    }

    public void clear_train() {
        // remove train from track attributes
        this.train = null;
        this.trainFront = 0;
        this.trainRear = 0;
    }

    public int get_track_length() {
        return this.trackLength;
    }

    public int get_train_front() {
        return this.trainFront;
    }

    public int get_train_end() {
        return this.trainRear;
    }

    public Signal get_track_sig() {
        return this.sig;
    }

    public void add_signal(Signal sig) {
        this.sig = sig;
    }

    public Track get_next_track() {
        return this.nextTrack;
    }

    // Main logic for train movement is actaully done here in the track class
    public String move_train(int amount) {
        // *takes the distance that the train on this track moves*
        // currently returns string for debug purposes, will possibly move to boolean / void return value in a release

        if (this.has_train() == false) {
            return "no train"; // primative error catch -> raise exception here instead? Return false if no train on track?
        } else {
            if ((this.train.is_transitioning()) && (this.train.get_pos().nextTrack.get_train() == this.train)) { // double check: transitioning train AND track is prev track (not current track)
                this.trainFront += amount;
                this.trainRear += amount;
                if (this.trainRear >= this.trackLength) { // train has cleared track: do cleanup
                    this.get_train().set_transitioning(false);
                    this.train.go_next_track();
                    this.clear_train();
                    return this.nextTrack.get_train().get_td() + " cleared";
                } else { // train is still on track, nothing else to do
                    return this.train.get_td() + " transition response from rear track";
                }
            } else if ((this.trainFront+amount) >= this.trackLength) { // train has crossed from one track to another
                this.get_train().set_transitioning(true);
                this.nextTrack.set_train(this.train, (this.trainFront + amount - this.get_track_length()));
                this.trainFront += amount;
                this.trainRear += amount;
                return this.train.get_td() + " transitioning response from front track";

            } else { // no special case, simply move train internally
                this.trainFront += amount;
                this.trainRear += amount;
                return this.train.get_td() + " moved";
            }
        }
    }
}
