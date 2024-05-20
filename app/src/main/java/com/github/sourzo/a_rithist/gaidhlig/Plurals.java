package com.github.sourzo.a_rithist.gaidhlig;

import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.Generatable;
import com.github.sourzo.a_rithist.LessonActivity;

import java.util.HashMap;
import java.util.Random;

public class Plurals implements Generatable {
    LessonActivity lessonActivity;
    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public Plurals(LessonActivity lessonActivity){
        this.lessonActivity = lessonActivity;
    }

    @Override
    public Exercise generate() {
        //setup ------------------------------------------------------------------------------------
        Exercise exercise = new Exercise();
        int vocabNum = new Random().nextInt(lessonActivity.sampleVocabList.size());
        HashMap<String,String> randomWord = lessonActivity.sampleVocabList.data.get(vocabNum);
        //Question ---------------------------------------------------------------------------------
        if (lessonActivity.translateFromGaelic){
            exercise.setQuestion(randomWord.get("nom_sing"));
        } else {
            exercise.setQuestion(randomWord.get("english"));
        }
        //Prompt -----------------------------------------------------------------------------------
        if (lessonActivity.translateFromGaelic){
            exercise.setPrompt("Pluralise:");
        } else {
            exercise.setPrompt("Pluralise the Gaelic for:");
        }
        //Solutions --------------------------------------------------------------------------------
        exercise.addSolution(randomWord.get("nom_pl"));
        //Output -----------------------------------------------------------------------------------
        return exercise;
    }
}
