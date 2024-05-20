package com.github.sourzo.a_rithist.gaidhlig;

import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.Generatable;
import com.github.sourzo.a_rithist.LessonActivity;

import java.util.Random;

public class Numbers implements Generatable {
    LessonActivity lessonActivity;
    GrammarGd grammarGd;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public Numbers(LessonActivity lessonActivity){
        this.lessonActivity = lessonActivity;
        grammarGd = new GrammarGd(lessonActivity);
    }

    public Exercise generate() {
        //setup ------------------------------------------------------------------------------------
        Exercise exercise = new Exercise();
        int num = new Random().nextInt(lessonActivity.largestNumber)+1;
        String num_gd = grammarGd.digitsToGd(num);

        //Question ---------------------------------------------------------------------------------
        if (lessonActivity.translateFromGaelic){
            exercise.setQuestion(num_gd);
        } else {
            exercise.setQuestion(String.valueOf(num));
        }

        //Prompt -----------------------------------------------------------------------------------
        if (lessonActivity.translateFromGaelic){
            exercise.setPrompt("Number (in Gaelic):");
        } else {
            exercise.setPrompt("Number (in digits):");
        }

        //Solutions --------------------------------------------------------------------------------
        if (lessonActivity.translateFromGaelic){
            exercise.addSolution(String.valueOf(num));
        } else {
            exercise.addSolution(num_gd);
        }

        //Output -----------------------------------------------------------------------------------
        return exercise;
    }
}
