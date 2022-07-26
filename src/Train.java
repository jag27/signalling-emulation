


public class Train {
    private int speed = 0;
    private int trainLength = 0;
    private String td;
    private Track currTrack;
    private int maxSpeed = 100;
    private int dYellowSpeed = 70;
    private int sYellowSpeed = 30;
    private boolean transitioning = false;

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

    public boolean get_transitioning() {
        return this.transitioning;
    }

    public void set_transitioning(boolean value) {
        this.transitioning = value;
    }

    public void go_next_track() {
        this.currTrack = this.currTrack.get_next_track();
    }

    public void do_move(Signal sig) {
        this.speed = calc_speed(this.get_speed(), this.maxSpeed, sig.get_aspect(), this.get_pos().get_train_front());
        String debug = this.get_pos().move_train(this.speed); // get status of train relative to track during track move for debug

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
        System.out.println(situation + this.get_td());
        System.out.println("current speed:");
        System.out.println(currSpeed);
        System.out.println("max speed");
        switch (situation) {

            case "red": 
                // ugly solution of getting train position and using function to measure speed: probably inefficient too

                // THIS IF STATEMENT NEEDS WORK. SPEED OF TRAIN MAY MEAN DECEL IS STARTED TOO LATE.  <- fixed as suggested below
                // possibly fix by brute force: start decel at max delay between trainpos?
                // then just crawl up to signal at 1 u/s if there is gap between train front and signal?

                if ((this.get_pos().get_track_length() - trainPos) > 200) {  // checks is in final decel stage. 
                    decelMaxSpeed = this.sYellowSpeed;  // train is not yet slowing to a stop, carry on coast at single yellow speed
                    System.out.println(this.sYellowSpeed);
                } else if (trainPos == this.get_pos().get_track_length()) {
                    return 0;  // train is at red signal: no movement

                } else if (currSpeed > 1) {decelMaxSpeed = 1; System.out.println(decelMaxSpeed);}   // decelerate to 1ups; should arrive at said speed before passing signal
                else {return 1;} // bloat or efficiency: a quick break for trains doing 1ups already and are not at signal
                
                
                // irl train issue here / emulation issue. how does a train decelerate?
                // distance-time graph is a parabolica with linear deceleration

            
            
            case "green" : 
            // inefficieny here, not lagging yet tho.
                if (this.maxSpeed == currSpeed) {
                    if (decelMaxSpeed == 0) {
                        return currSpeed;
                    }
                } else { 
                    if (decelMaxSpeed == 0) {
                        return currSpeed+1;
                    }
                }
        
            case "yellow": decelMaxSpeed = this.sYellowSpeed;


            case "double yellow": 
                if (decelMaxSpeed == this.sYellowSpeed) { System.out.println("");} // will need testing if breaks out of if statement or case statement} 
                else {decelMaxSpeed = this.dYellowSpeed;} 

            default: // technically failsafe lol, will eventually slow speed to stop if no above cases hit
            System.out.println(decelMaxSpeed);
            
            if (currSpeed > decelMaxSpeed) {
                return currSpeed-2;  // speed may be below max momentarily, fixed inside this case
            } else if (currSpeed < decelMaxSpeed) {
                return currSpeed+1;
            } else {
                return currSpeed;
            }

        }
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