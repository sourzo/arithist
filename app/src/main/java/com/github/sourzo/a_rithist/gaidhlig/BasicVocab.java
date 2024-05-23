package com.github.sourzo.a_rithist.gaidhlig;

import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.HashMap;
import java.util.Random;

public class BasicVocab extends ExerciseGenerator {
    //Setup ----------------------------------------------------------------------------------------
    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public BasicVocab(LessonOptions lo){super(lo);}

    //Generate questions----------------------------------------------------------------------------

    /**Generates an Exercise: A question, prompt, and solution set*/
    public Exercise generate(){
        //setup
        Exercise exercise = new Exercise();
        int vocabNum = new Random().nextInt(lo.sampleVocabList.size());
        HashMap<String,String> randomWord = lo.sampleVocabList.data.get(vocabNum);

        //PrePrompt
        exercise.setPrePrompt("Translate:");

        //question
        if (lo.translateFromGaelic){
            exercise.setQuestion(randomWord.get("nom_sing"));
        } else {
            exercise.setQuestion(randomWord.get("english"));
        }
        //EditText Prompt --------------------------------------------------------------------------


        //solution
        if (lo.translateFromGaelic){
            exercise.addSolution(randomWord.get("english"));
        } else {
            exercise.addSolution(randomWord.get("nom_sing"));
        }

        return exercise;
    }

}
