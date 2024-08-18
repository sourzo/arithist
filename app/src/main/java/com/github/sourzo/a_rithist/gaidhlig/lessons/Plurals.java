package com.github.sourzo.a_rithist.gaidhlig.lessons;

import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.HashMap;
import java.util.Random;

public class Plurals extends ExerciseGenerator {
    public Plurals(LessonOptions lo) {super(lo);}

    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        int vocabNum = new Random().nextInt(lo.sampleVocabList.size());
        HashMap<String,String> randomWord = lo.sampleVocabList.data.get(vocabNum);

        //PrePrompt --------------------------------------------------------------------------------
        if (lo.translateFromGaelic){
            e.setPrePrompt("Pluralise:");
        } else {
            e.setPrePrompt("Pluralise the Gaelic for:");
        }

        //EditText Prompt --------------------------------------------------------------------------

        //Question ---------------------------------------------------------------------------------
        if (lo.translateFromGaelic){
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
