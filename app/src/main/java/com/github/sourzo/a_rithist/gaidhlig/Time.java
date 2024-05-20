package com.github.sourzo.a_rithist.gaidhlig;



import com.github.sourzo.a_rithist.LessonActivity;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;

import java.util.Random;

public class Time extends ExerciseGenerator {
    GrammarGd g;
    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public Time(LessonActivity la){
        super(la);
        g = new GrammarGd(la);
    }
    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        int hrsNum = new Random().nextInt(24);
        int minsNum = new Random().nextInt(12)*5;
        String hrsDigStr = String.valueOf(hrsNum);
        if (hrsNum < 10){
            hrsDigStr = "0" + hrsDigStr;
        }
        String minsDigStr = String.valueOf(minsNum);
        if (minsNum < 10){
            minsDigStr = "0" + minsDigStr;
        }

        //PrePrompt --------------------------------------------------------------------------------
        if (la.translateFromGaelic){
            e.setPrePrompt("What time is it? (Digital hh:mm)");
        } else {
            e.setPrePrompt("Dè an uair a tha e?");
        }

        //Question ---------------------------------------------------------------------------------
        if (la.translateFromGaelic){
            e.setQuestion("Tha e " + getTime(hrsNum,minsNum));
        } else {
            e.setQuestion(hrsDigStr + ":" + minsDigStr);
            e.setEditTextPrompt("Tha e ");
            e.setEditTextCursorPosition(6);
        }
        //EditText Prompt --------------------------------------------------------------------------

        //Solutions --------------------------------------------------------------------------------
         if (la.translateFromGaelic) {
             //Gaelic to digits (need digits as strings)
             //include a.m. and p.m. times and leading/dropped zeros for hours
             String hrsAm = String.valueOf(getHrsAm(hrsNum));
             String hrsPm = String.valueOf(getHrsPm(hrsNum));

             if (hrsAm.length() == 1) {
                 e.addSolution("0" + hrsAm + ":" + minsDigStr);
                 e.addSolution("0" + hrsAm + ":" + minsDigStr + " or " + hrsPm + ":" + minsDigStr);
                 e.addSolution(hrsPm + ":" + minsDigStr + " or " + "0" + hrsAm + ":" + minsDigStr);
             }

             e.addSolution(hrsAm + ":" + minsDigStr);
             e.addSolution(hrsPm + ":" + minsDigStr);
             e.addSolution(hrsAm + ":" + minsDigStr + " or " + hrsPm + ":" + minsDigStr);
             e.addSolution(hrsPm + ":" + minsDigStr + " or " + hrsAm + ":" + minsDigStr);


        } else {
            //Digits to Gaelic
            e.addSolution("Tha e " + getTime(hrsNum, minsNum));

            if (minsNum == 0) {
                //midday/midnight
                if (hrsNum == 0) {
                    e.addSolution("Tha e meadhan oidhche");
                } else if (hrsNum == 12) {
                    e.addSolution("Tha e meadhan latha");
                }
            }

            if (hrsNum < 12) {
                // "in the morning"
                e.addSolution("Tha e " + getTime(hrsNum, minsNum) + " anns a' mhadainn");
            } else if (hrsNum > 12 ||  minsNum != 0) {
                // "in the afternoon/evening"
                if (hrsNum < 19) {
                    e.addSolution("Tha e " + getTime(hrsNum, minsNum) + " feasgar");
                }
                if (hrsNum > 15) {
                    e.addSolution("Tha e " + getTime(hrsNum, minsNum) + " as t-oidhche");
                }
            }
        }
        
        //Output -----------------------------------------------------------------------------------
        return e;
    }

    /**Convert the hour-part of 24-hour time to 12-hour time*/
    private int get12h(int hour){
        if (hour > 12) {return hour - 12;}
        else if (hour == 0) {return 12;}
        else {return hour;}
    }

    /**Convert hour to morning (24hr)
     * @return Hour between 0 and 11*/
    private int getHrsAm(int hour){
        if (hour >= 12) {return hour - 12;}
        else {return hour;}
    }

    /**Convert hour to afternoon (24hr)
     * @return Hour between 12 and 23*/
    private int getHrsPm(int hour){
        if (hour < 12) {return hour + 12;}
        else {return hour;}
    }

    /**Get the Gaelic for the hours part of the time*/
    private String getHrsGd(int hour){
        int hrs12 = get12h(hour);
        if (hrs12 == 1) {return "uair";}
        else if (hrs12 == 2) {return "dhà";}
        else if (hrs12 <= 10) {return g.numbers.filterMatches("number", String.valueOf(hrs12)).get("cardinal",0);}
        else if (hrs12 == 11) {return "aon uair deug";}
        else {return "dà uair dheug";}
    }

    /**Returns the time, in Gaelic, from the given hours/minutes
     * @param hrs Hours, 0-23
     * @param mins Minutes, 0-55 in 5-minute increments (I'm not sure on the Gaelic for smaller increments, in terms of pluralisation)
     * @return String of the Gaelic for the given time*/
    private String getTime(int hrs, int mins) {
        if (mins == 0) {
            // hrs o'clock
            int hrs12 = get12h(hrs);
            if (hrs12 == 2) {
                return ("dà uair");
            } else if (hrs12 >= 3 && hrs12 <= 10) {
                return (getHrsGd(hrs) + " uairean");
            } else {
                return (getHrsGd(hrs));
            }
        } else if (mins == 15) {
            // quarter past
            return ("cairteal an dèidh " + getHrsGd(hrs));

            // quarter to
        } else if (mins == 45) {
            return ("cairteal gu " + getHrsGd(hrs + 1));

            // half past
        } else if (mins == 30) {
            return "leth-uair an dèidh " + getHrsGd(hrs);
        }

        // other times
        else {
            //e.g. 40 past 12 = 20 to 1
            int minsToFrom = Math.min(mins, 60 - mins);
            String minsGd = g.digitsToGd(minsToFrom);

            if (minsToFrom == 20) {
                minsGd = minsGd + " mionaid";
            } else if (minsToFrom == 25) {
                minsGd = "còig mionaidean fichead";
            } else {
                minsGd = minsGd + " mionaidean";
            }

            if (minsToFrom == mins) {
                return (minsGd + " an dèidh " + getHrsGd(hrs));
            } else {
                return (minsGd + " gu " + getHrsGd(hrs + 1));
            }
        }
    }

}
