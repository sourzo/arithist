package com.github.sourzo.a_rithist.gaidhlig.lessons;

import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.gaidhlig.GrammaticalPerson;
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
        GrammaticalPerson person = GrammaticalPerson.random();
        int persNumEn = ge.en.getRow("en_subj", person.en_subj);
        int persNumPp = gg.pp.getRow("en_subj", person.en_subj);

        //Construct sentences ----------------------------------------------------------------------
        String objIndef = ge.enIndefArticle(lo.sampleVocabList.get(objectNum, "english"));

        String sentenceEn = capitalise(person.en_subj) + " " + ge.en.get(persNumEn, "have_pres").toLowerCase() + " " + objIndef.toLowerCase();
        String sentenceGd = "Tha " + lo.sampleVocabList.get(objectNum, "nom_sing").toLowerCase() + " " + gg.pp.get(persNumPp, "aig").toLowerCase();

        //PrePrompt --------------------------------------------------------------------------------
        e.setPrePrompt("Translate:");

        //Question ---------------------------------------------------------------------------------

        if (lo.translateFromGaelic){
            e.setQuestion(sentenceGd);
        } else {
            e.setQuestion(sentenceEn);
        }

        //EditText Prompt --------------------------------------------------------------------------
        if (lo.responseType.equals("blanks")){
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
