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
        this.trainRear = pos - id.get_length(); // this value can and will be negative when new train enters
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
        
            // a different approach to this is to maybe remove train rear attribute altogether?
            // instead add a boolean flag to the train to say if its on 2 tracks at once
            // this would speed up the update function: would only need to check for rear once
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
            } else if ((this.trainFront+amount) > this.trackLength) { // train has crossed from one track to another
                this.get_train().set_transitioning(true);
                this.nextTrack.set_train(this.train, (this.trainFront + amount - this.get_track_length()));
                this.trainFront += amount;
                this.trainRear += amount;
                return this.train.get_td() + " transitioning response from front track";

            } else {
                this.trainFront += amount;
                this.trainRear += amount;
                return this.train.get_td() + " moved";
            }
        }
    }
}
