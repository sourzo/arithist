package com.github.sourzo.a_rithist.gaidhlig;

import com.github.sourzo.a_rithist.LessonActivity;
import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;

import java.util.HashMap;
import java.util.Random;

public class PossessionAig extends ExerciseGenerator {
    GrammarGd gg;
    GrammarEn ge;
    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public PossessionAig(LessonActivity la){
        super(la);
        gg = new GrammarGd(la);
        ge = new GrammarEn(la);
    }
    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        int objectNum = new Random().nextInt(la.sampleVocabList.size());
        int personNum = new Random().nextInt(7);

        //Construct sentences ----------------------------------------------------------------------
        String objIndef = ge.enIndefArticle(la.sampleVocabList.get(objectNum, "english"));

        String sentenceEn = capitalise(ge.en.get(personNum, "en_subj")) + " " + ge.en.get(personNum, "have_pres").toLowerCase() + " " + objIndef.toLowerCase();
        String sentenceGd = "Tha " + la.sampleVocabList.get(objectNum, "nom_sing").toLowerCase() + " " + gg.pp.get(personNum, "aig").toLowerCase();

        //PrePrompt --------------------------------------------------------------------------------
        e.setPrePrompt("Translate:");

        //Question ---------------------------------------------------------------------------------

        if (la.translateFromGaelic){
            e.setQuestion(sentenceGd);
        } else {
            e.setQuestion(sentenceEn);
        }

        //EditText Prompt --------------------------------------------------------------------------
        if (la.sentenceType.equals("blanks")){
            if (la.translateFromGaelic){
                String etp = " " + objIndef;
                e.setEditTextPrompt(etp);
                e.setEditTextCursorPosition(0);
            } else {
                String etp = "Tha " + la.sampleVocabList.get(objectNum, "nom_sing").toLowerCase() + " ";
                e.setEditTextPrompt(etp);
                e.setEditTextCursorPosition(etp.length());
            }
        }
        //Solutions --------------------------------------------------------------------------------
        if (la.translateFromGaelic){
            e.addSolution(sentenceEn);
        } else {
            e.addSolution(sentenceGd);
        }
        //Output -----------------------------------------------------------------------------------
        return e;
    }
}
