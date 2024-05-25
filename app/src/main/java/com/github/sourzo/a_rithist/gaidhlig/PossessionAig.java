package com.github.sourzo.a_rithist.gaidhlig;

import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.Random;

public class PossessionAig extends ExerciseGenerator {
    GrammarGd gg;
    GrammarEn ge;
    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public PossessionAig(LessonOptions lo){
        super(lo);
        gg = new GrammarGd(lo.appRes);
        ge = new GrammarEn(lo.appRes);
    }
    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        int objectNum = new Random().nextInt(lo.sampleVocabList.size());
        int personNum = new Random().nextInt(7);

        //Construct sentences ----------------------------------------------------------------------
        String objIndef = ge.enIndefArticle(lo.sampleVocabList.get(objectNum, "english"));

        String sentenceEn = capitalise(ge.en.get(personNum, "en_subj")) + " " + ge.en.get(personNum, "have_pres").toLowerCase() + " " + objIndef.toLowerCase();
        String sentenceGd = "Tha " + lo.sampleVocabList.get(objectNum, "nom_sing").toLowerCase() + " " + gg.pp.get(personNum, "aig").toLowerCase();

        //PrePrompt --------------------------------------------------------------------------------
        e.setPrePrompt("Translate:");

        //Question ---------------------------------------------------------------------------------

        if (lo.translateFromGaelic){
            e.setQuestion(sentenceGd);
        } else {
            e.setQuestion(sentenceEn);
        }

        //EditText Prompt --------------------------------------------------------------------------
        if (lo.sentenceType.equals("blanks")){
            if (lo.translateFromGaelic){
                String etp = " " + objIndef;
                e.setEditTextPrompt(etp);
                e.setEditTextCursorPosition(0);
            } else {
                String etp = "Tha " + lo.sampleVocabList.get(objectNum, "nom_sing").toLowerCase() + " ";
                e.setEditTextPrompt(etp);
                e.setEditTextCursorPosition(etp.length());
            }
        }
        //Solutions --------------------------------------------------------------------------------
        if (lo.translateFromGaelic){
            e.addSolution(sentenceEn);
        } else {
            e.addSolution(sentenceGd);
        }
        //Output -----------------------------------------------------------------------------------
        return e;
    }
}
