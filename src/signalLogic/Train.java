package signalLogic;
public class Train {
    private int speed = 0;
    private int trainLength = 0;
    private String td;
    private Track currTrack; // if on two tracks, this will be the rear of the two tracks
    private int maxSpeed = 100;
    private int dYellowSpeed = 70;
    private int sYellowSpeed = 30;
    private boolean transitioning = false; // this flag indicates train is on two tracks: both the current track AND the last track it was on (track behind it)

    public Train(String description, int length, Track track) {
        this.td = description;
        this.trainLength = length;
        this.currTrack = track;
    }

    public int get_speed() {
        return this.speed;
    }

    public int get_length() {
        return this.trainLength;
    }

    public String get_td() {
        return this.td;
    }

    public Track get_pos() {
        return this.currTrack;
    }

    public boolean is_transitioning() {
        return this.transitioning;
    }

    public void set_transitioning(boolean value) {
        this.transitioning = value;
    }

    public void go_next_track() {
        this.currTrack = this.currTrack.get_next_track();
    }

    public void do_move(Signal sig) {
        String debug = "";
        if (this.is_transitioning()) { // if train is on two tracks, both tracks must be updated
            this.speed = calc_speed(this.get_speed(), this.maxSpeed, sig.get_aspect(), this.currTrack.get_next_track().get_train_front());
            debug += this.currTrack.get_next_track().move_train(this.speed); // this must be done first to avoid cleanup from prev track causing errors
            debug += this.currTrack.move_train(this.speed);
        } else {
            this.speed = calc_speed(this.get_speed(), this.maxSpeed, sig.get_aspect(), this.currTrack.get_train_front());
            debug += this.currTrack.move_train(this.speed);
        }
        System.out.println(debug);

        // possible improvement could be made here: get rid of this function and just have calcspeed?
        
        // should be finished here? below comments are thought processes; i might have spent more time on comments/ brainstorming
        // than actually coding, but it helps me find solutions and makes me feel cool if someone else ever reads this.

            //  CREATE A MAX SPEED?
            //  DECELERATE AND ACCELERATE FUNCTIONS?
            //  SCALABLE WITH LINE SPEED LIMITS?
            //  SCALABLE WITH DIFFERENT TRAIN TYPES
            //  CURRENT SOLUTION DOES NOT WORK: RED NEXT SIGNAL MEANS AS SOON
            //  AS TRAIN ENTERS TRACK IT STOPS; HOW TO RUN IT TO THE SIGNAL?
            //  FUNCTION TO DECELERATE PERFECTLY AT RED SIGNAL?
            //  PRIMATIVE ACCELERATE/DECELERATE FUNCTION DOESNT WORK EITHER:                <----- READ THIS; NEED SPECIAL FUNCTION.
            //  TRAIN NEVER ACCELERATES HERE, HOW DO DECELERATE PERFECTLY TO A RED SIGNAL??

            //  TO DO: SPEED LIMITS (TRAIN SENSITIVE?)  <-- done, but not train sensitive yet
            //         FUNCTION TO DECELERATE PERFECTLY (DIFFERENTIATION?) LINEAR ACCELERATION  <-- done (decel is somewhat primative)
            //         EASY TO ACCELERATE, NOT EASY TO DECELERATE;
            //         WHEN TO START DECELERATION? MUST BE ABLE TO CANCEL IN CASE SIGNAL GOES GREEN  <--- done by mixing accel and decel
        }


    private int calc_speed(int currSpeed, int maxSpeed, String situation, int trainPos) {         // this function is always called from do_move
        int decelMaxSpeed = 0;
        boolean finalDecel = false;
        // System.out.println(situation + " " + this.td);
        // System.out.println("current speed:");
        // System.out.println(currSpeed);
        // System.out.println("max speed");

        // this switch statement sucks. lots of checks: feels like more efficient solution should be posssible
        // possibly just use if statements instead of case? lots of if statements being used here as well as case anyway
        // possible create more flags as local variables?
        switch (situation) {

            case "red": 

                if ((this.currTrack.get_track_length() - trainPos) > 200) {  // checks is in final decel stage. 
                    decelMaxSpeed = this.sYellowSpeed;  // train is not yet slowing to a stop, carry on coast at single yellow speed
                    // System.out.println(this.sYellowSpeed);
                } else if (trainPos == this.get_pos().get_track_length()) {
                    return 0;  // train is at red signal: no movement

                } else if (currSpeed > 1) {decelMaxSpeed = 1; //System.out.println(decelMaxSpeed); <- printing line for debug, comment out for release
                    finalDecel = true;}   // decelerate to 1ups; should arrive at said speed before passing signal
                else {return 1;} // bloat or efficiency: a quick break for trains doing 1ups already and are not at signal
                
                // irl train issue / emulation issue here. how does a train decelerate? 
                // distance-time graph is a parabolica with linear deceleration

            
            
            case "green" : 
            // inefficieny here, not lagging yet tho.
                if (finalDecel) {
                    maxSpeed = 1;
                } else if (situation == "green") {
                    decelMaxSpeed = this.maxSpeed;
                }
        
            case "double yellow": 
                if (finalDecel) {decelMaxSpeed = 1;} // checking sig situation again due to java case statement semantics
                else if (situation == "double yellow") {
                    {decelMaxSpeed = this.dYellowSpeed;}
                }

            case "yellow": 
                if (finalDecel) { decelMaxSpeed = 1;} // will need testing if breaks out of if statement or case statement} 
                else if (situation == "yellow") {
                    {decelMaxSpeed = this.sYellowSpeed;} 
                }

            default: // technically failsafe lol, will eventually slow speed to stop if no above cases hit
                // System.out.println(decelMaxSpeed);
                
                if (currSpeed > decelMaxSpeed) {
                    return currSpeed-3;  // speed may be below max momentarily, fixed inside this case
                } else if (currSpeed < decelMaxSpeed) {
                    return currSpeed+1;
                } 
        }
            return currSpeed;

    }
}
            
            // LIKELY NOT A GOOD SOLUTION: MISSING SOMETHING AND I DONT KNOW WHAT AND ITS PISSING ME OFF  <- still true, feels like there is better solution somehwere
            // RECURSIVE FUNCTION COULD WORK?????  cant see this working: recursive but sleeping between calls? doesnt make sense

            // MAYBE THE FUNCTION CAN BE SIMPLE? PREDETERMINED DECEL POINT?  <-- this is solution used so far
            // HOW TO CALL THIS, MUST CHECK EVERY UPDATE?  <-- yes, if red signal is in place. can skip check if at proceed signal
            // BEST WAY TO CALL DECEL FUNCTION IS TO CHECK AT DECEL POIINT? hard code decel point for red signals? <-- seems best idea
            // HOW TO DECEL FOR YELLOWS: DECEL IMMEDIATELY?  <-- yes, this is implemented. decel to certain speed after passing signal

            // currently have two different functions for train movement:
            // do_move and calc_speed. do_move was intended to be called everytime the sim updates (default 1 second)
            // however calc_speed may be called more often: more accurate speed?  <- not nessecary: might provide more accuracy in the long run tho
            // maybe a recursive function could work after all?  <-- highly unlikely
            
            // MUST be careful train does not SPAD: should be encapsulated in red signal case  <-- current solution is brake before train needs to and coast last part at 1ups
            // if train SPADs, program will break



            // CURRENT MAJOR ISSUE: MIXING UP SPEED WITH DISTANCE/POSITION.
            // trains travel at a speed. emulation can only update the **position** periodically.
            // possibly under control, not got to testing stage yet
            // position and speed works as intended, might have some decel issues with track lengths. i believe the train should be
            // able to come to a stop from full speed to red signal, but not tested yet.

            // TESTING PLAN: with the way its coded, everything is relative: hard to have one signal on red without train there
            // maybe its best to introduce points now? would be easier to test red signals
            // maybe let user set routes to clear signals: semi automatic signals? would require lots more code on signal
            // likely will go with the latter: route setting will be needed anyway if points happen.
            // entry exit style route setting? how to verify route? interlocking? <-- trying to use jargon lol
            // signals have track berth but arent directly linked; maybe can verify through track berths?


            // SHOULD LOOK AT GRAPHICAL INTERFACES WITH JAVA: javafx possibly?
            // track layout represented in a circle internally so far.
            // could actually be made interactive with one line; get user to add a train to line: have a queue of trains waiting to enter area?
            // make different linespeeds, so on and so forth.
            // ultimately, i want the train moving and signal controlling to remain universal:
            // meaning any track layout should be supported: points have not been considered at all yet, but seem pretty simple
            // station emulation is new end goal: i am thinking very far ahead here, but that seems pretty cool to me:
            // having a station of a somewhat large stature and have ars to route trains: possibly scalable with universal track junction before station?
            // dont get ahead of yourself, but know this project can go a long way

            // thought for reconsideration at end/later on in the project:
            // maybe move the train within track class? make it more relative? <- would be harder to do transitions possibly