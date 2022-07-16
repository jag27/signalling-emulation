


public class Train {
    private int speed = 0;
    private int trainLength = 0;
    private String td;
    private Track currTrack;
    private int maxSpeed = 100;
    private int dYellowSpeed = 70;
    private int sYellowSpeed = 30;

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

    public void do_move(Signal sig) {
        this.speed = calc_speed(this.speed, this.maxSpeed, sig.get_aspect(), this.get_pos().get_train_front());
        String debug = this.get_pos().move_train(this.speed); // get status of train relative to track during track move for debug

        
            //  CREATE A MAX SPEED?
            //  DECELERATE AND ACCELERATE FUNCTIONS?
            //  SCALABLE WITH LINE SPEED LIMITS?
            //  SCALABLE WITH DIFFERENT TRAIN TYPES
            //  CURRENT SOLUTION DOES NOT WORK: RED NEXT SIGNAL MEANS AS SOON
            //  AS TRAIN ENTERS TRACK IT STOPS; HOW TO RUN IT TO THE SIGNAL?
            //  FUNCTION TO DECELERATE PERFECTLY AT RED SIGNAL?
            //  PRIMATIVE ACCELERATE/DECELERATE FUNCTION DOESNT WORK EITHER:                <----- READ THIS; NEED SPECIAL FUNCTION.
            //  TRAIN NEVER ACCELERATES HERE, HOW DO DECELERATE PERFECTLY TO A RED SIGNAL??

            //  TO DO: SPEED LIMITS (TRAIN SENSITIVE?)
            //         FUNCTION TO DECELERATE PERFECTLY (DIFFERENTIATION?) LINEAR ACCELERATION
            //         EASY TO ACCELERATE, NOT EASY TO DECELERATE;
            //         WHEN TO START DECELERATION? MUST BE ABLE TO CANCEL IN CASE SIGNAL GOES GREEN
        }

    }

    private int calc_speed(int currSpeed, int maxSpeed, String situation, int distance) {         // this function is always called from do_move
        int decelMaxSpeed = 0;
        switch (situation) {  

            case "red": 
                // ugly solution of getting train position and using function to measure speed: probably inefficient too

                // THIS IF STATEMENT NEEDS WORK. SPEED OF TRAIN MAY MEAN DECEL IS STARTED TOO LATE.
                // possibly fix by brute force: start decel at max delay between trainpos?
                // then just crawl up to signal at 1 u/s if there is gap between train front and signal?


                if ((distance - trainPos) > 100) {  // checks is in final decel stage. 
                    decelMaxSpeed = sYellowSpeed;
                } else if (currSpeed > 0) {return currSpeed - 1;}  
                else {return 0;}
                // irl train issue here / emulation issue. how does a train decelerate?
                // distance-time graph is a parabolica with linear deceleration

            
            
            case "green" : 
                if (maxSpeed == currSpeed) {
                    return currSpeed;
                } else { 
                    return currSpeed+1;
                }
        
            case "single yellow": decelMaxSpeed = sYellowSpeed;


            case "double yellow": 
                if (decelMaxSpeed == sYellowSpeed) { ;} // will need testing if breaks out of if statement or case statement} 
                else {decelMaxSpeed = dYellowSpeed;} 

            default: // technically failsafe lol, will eventually slow speed to stop if no above cases hit
            
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
            
            // LIKELY NOT A GOOD SOLUTION: MISSING SOMETHING AND I DONT KNOW WHAT AND ITS PISSING ME OFF
            // RECURSIVE FUNCTION COULD WORK?????
            // BASE CASE: FOR ACCELERATION: CURSPEED = MAX SPEED
            // STEP: CURRSPEED + 1
            // FOR DECELERATION:
            // BASE: ?????????
            // STEP: REDUCE SPEED? VARIABLE DECELERATION?????

            // MAYBE THE FUNCTION CAN BE SIMPLE? PREDETERMINED DECEL POINT?
            // HOW TO CALL THIS, MUST CHECK EVERY UPDATE?
            // BEST WAY TO CALL DECEL FUNCTION IS TO CHECK AT DECEL POIINT? hard code decel point for red signals? <-- seems best idea
            // HOW TO DECEL FOR YELLOWS: DECEL IMMEDIATELY?

            // currently have to different functions for train movement:
            // do_move and calc_speed. do_move was intended to be called everytime the sim updates (default 1 second)
            // however calc_speed may be called more often: more accurate speed?
            // maybe a recursive function could work after all?
            
            // MUST be careful train does not SPAD: should be encapsulated in red signal case
            //



            // CURRENT MAJOR ISSUE: MIXING UP SPEED WITH DISTANCE/POSITION.
            // trains travel at a speed. emulation can only update the **position** periodically.