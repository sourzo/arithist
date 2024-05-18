package com.github.sourzo.a_rithist.gaidhlig;

import android.content.Context;

import com.github.sourzo.a_rithist.Exercise;
import com.github.sourzo.a_rithist.Generatable;
import com.github.sourzo.a_rithist.LessonActivity;

import java.util.HashMap;
import java.util.Random;

public class BasicVocab implements Generatable {
    //Setup ----------------------------------------------------------------------------------------
    Context c;
    /**Creates a new Exercise Generator instance. Requires context to load vocab tables.*/
    public BasicVocab(Context c){
        this.c = c;
    }



    //Generate questions----------------------------------------------------------------------------

    /**Generates an Exercise: A question, prompt, and solution set*/
    public Exercise generate(LessonActivity lessonActivity){
        //setup
        Exercise exercise = new Exercise();
        int vocabNum = new Random().nextInt(lessonActivity.sampleVocabList.size());
        HashMap<String,String> randomWord = lessonActivity.sampleVocabList.data.get(vocabNum);

        //question
        if (lessonActivity.translateFromGaelic){
            exercise.setQuestion(randomWord.get("nom_sing"));
        } else {
            exercise.setQuestion(randomWord.get("english"));
        }

        //prompt
        exercise.setPrompt("Translate:");

        //solution
        if (lessonActivity.translateFromGaelic){
            exercise.addSolution(randomWord.get("english"));
        } else {
            exercise.addSolution(randomWord.get("nom_sing"));
        }

        return exercise;
    }

}
