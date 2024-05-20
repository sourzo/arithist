package com.github.sourzo.a_rithist.gaidhlig;

import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.LessonActivity;

import java.util.Random;

public class Numbers extends ExerciseGenerator {
    GrammarGd g;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public Numbers(LessonActivity la){
        super(la);
        g = new GrammarGd(la);
    }

    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        int num = new Random().nextInt(la.largestNumber)+1;
        String num_gd = g.digitsToGd(num);

        //PrePrompt --------------------------------------------------------------------------------
        if (la.translateFromGaelic){
            e.setPrePrompt("Number (in Gaelic):");
        } else {
            e.setPrePrompt("Number (in digits):");
        }

        //Question ---------------------------------------------------------------------------------
        if (la.translateFromGaelic){
            e.setQuestion(num_gd);
        } else {
            e.setQuestion(String.valueOf(num));
        }

        //EditText Prompt --------------------------------------------------------------------------

        //Solutions --------------------------------------------------------------------------------
        if (la.translateFromGaelic){
            e.addSolution(String.valueOf(num));
        } else {
            e.addSolution(num_gd);
        }

        //Output -----------------------------------------------------------------------------------
        return e;
    }
}
