public class Track {
    private Train train = null;
    private int trackLength = 0;
    private Track nextTrack;
    private int trainFront;
    private int trainRear;
    private Signal sig = null;


    public Track(int length) {
        this.trackLength = length;
    }

    public void link_tracks(Track next) {
        this.nextTrack = next;
    }

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
        this.trainRear = pos - id.get_length();
    }

    public void clear_train() {
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

    public String move_train(int amount) {
        if (this.has_train() == false) {
            return "no train";
        } else {

            // trains being lost here currently, issue might be due to negative train rear placement <-- should be easy check for this
            // issue might also be cause by not setting train. currently inefficient by setting trains all the time but it works for now
            // actually, issue is likely due to trains spad currently, testing rn if trains spad in calcspeed function.
            if ((this.trainRear+amount) > this.trackLength) {
                this.nextTrack.set_train(this.get_train(), (this.get_train_front() + amount - this.get_track_length()));
                this.clear_train();
                return this.nextTrack.get_train().get_td() + " cleared";
            } else if ((this.trainFront+amount) > this.trackLength) {
                this.nextTrack.set_train(this.get_train(), (this.get_train_front() + amount - this.get_track_length()));
                this.trainFront += amount;
                this.trainRear += amount;
                return this.get_train().get_td() + " transitioning response from next track";

            } else {
                this.trainFront += amount;
                this.trainRear += amount;
                return this.get_train().get_td() + " moved";
            }
        }
    }
}
