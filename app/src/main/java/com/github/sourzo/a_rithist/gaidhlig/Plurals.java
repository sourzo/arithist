package com.github.sourzo.a_rithist.gaidhlig;

import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.LessonActivity;

import java.util.HashMap;
import java.util.Random;

public class Plurals extends ExerciseGenerator {
    public Plurals(LessonActivity la) {super(la);}

    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        int vocabNum = new Random().nextInt(la.sampleVocabList.size());
        HashMap<String,String> randomWord = la.sampleVocabList.data.get(vocabNum);

        //PrePrompt --------------------------------------------------------------------------------
        if (la.translateFromGaelic){
            e.setPrePrompt("Pluralise:");
        } else {
            e.setPrePrompt("Pluralise the Gaelic for:");
        }

        //EditText Prompt --------------------------------------------------------------------------

        //Question ---------------------------------------------------------------------------------
        if (la.translateFromGaelic){
            e.setQuestion(randomWord.get("nom_sing"));
        } else {
            e.setQuestion(randomWord.get("english"));
        }

        //Solutions --------------------------------------------------------------------------------
        e.addSolution(randomWord.get("nom_pl"));
        //Output -----------------------------------------------------------------------------------
        return e;
    }
}
