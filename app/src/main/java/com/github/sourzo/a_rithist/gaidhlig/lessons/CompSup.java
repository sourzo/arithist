package com.github.sourzo.a_rithist.gaidhlig.lessons;

import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.gaidhlig.GrammaticalPerson;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.HashMap;
import java.util.Random;

public class CompSup extends ExerciseGenerator {
    GrammarGd gg;
    GrammarEn ge;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public CompSup(LessonOptions lo){
        super(lo);
        gg = new GrammarGd(lo.appRes);
        ge = new GrammarEn(lo.appRes);
    }

    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        int subjNum = new Random().nextInt(lo.sampleVocabList.size());
        HashMap<String,String> subject = lo.sampleVocabList.data.get(subjNum);
        HashMap<String,String> adjective = gg.adjectives.getRandomRow();

        boolean useComparatives = true;
        if (lo.comparatives && lo.superlatives) {
            useComparatives = new Random().nextBoolean();
        }
        //Comparatives -----------------------------------------------------------------------------
        String subjectGd = subject.get("nom_sing");
        String sentenceGd;
        String sentenceEn;
        String sentenceEnAlt;

        if (useComparatives) {
            // Parts of sentence -------------------------------------------------------------------
            int objNum = new Random().nextInt(lo.sampleVocabList.size());
            HashMap<String,String> object = lo.sampleVocabList.data.get(objNum);
            String subjectEn = ge.enIndefArticle(subject.get("english"));
            String objectEn = ge.enIndefArticle(object.get("english"));
            String comparativeGd = "nas " + adjective.get("comp_sup");
            String comparativeEn = adjective.get("comp_en");
            String comparativeEnAlt = "more " + adjective.get("english");

            //Construct sentence -------------------------------------------------------------------
            sentenceGd = "A bheil " + subjectGd + " " + comparativeGd + " na " + object.get("nom_sing") + "?";
            sentenceEn = "Is " + subjectEn + " " +
                    comparativeEn + " than " + objectEn + "?";
            sentenceEnAlt = "Is " + subjectEn + " " +
                    comparativeEnAlt + " than " + objectEn + "?";

            //Prompt -----------------------------------------------------------------------------------
            e.setPrePrompt("Translate:");

            if (lo.responseType == LessonOptions.ResponseType.BLANKS) {
                String editTextPrompt;
                if (lo.translateFromGaelic){
                    editTextPrompt = "Is " + subjectEn + "  than " + objectEn + "?";
                    e.setEditTextPrompt(editTextPrompt);
                    e.setEditTextCursorPosition(4 + subjectEn.length());
                } else {
                    editTextPrompt = "A bheil " + subjectGd + "  " + object.get("nom_sing") +"?";
                    e.setEditTextPrompt(editTextPrompt);
                    e.setEditTextCursorPosition(9 + subjectGd.length());
                }
            }
        }

        //Superlatives -----------------------------------------------------------------------------
        else {
            // Parts of sentence -----------------------------------------------------------------------
            subjectGd = gg.commonArticle(subjectGd, "sg", subject.get("gender"), GrammarGd.GrammaticalCase.NOMINAL);
            String subjectEn = subject.get("english");

            //Construct sentence -------------------------------------------------------------------
            sentenceGd = capitalise(subjectGd) + " as " + adjective.get("comp_sup");
            sentenceEn = "The " + adjective.get("sup_en") + " " + subjectEn;
            sentenceEnAlt = "The most " + adjective.get("english") + " " + subjectEn;

            //Prompt -----------------------------------------------------------------------------------
            e.setPrePrompt("Translate:");

            if (lo.responseType == LessonOptions.ResponseType.BLANKS) {
                String editTextPrompt;
                if (lo.translateFromGaelic){
                    editTextPrompt = "The  " + subjectEn;
                    e.setEditTextPrompt(editTextPrompt);
                    e.setEditTextCursorPosition(4);
                } else {
                    editTextPrompt = capitalise(subjectGd) + " ";
                    e.setEditTextPrompt(editTextPrompt);
                    e.setEditTextCursorPosition(subjectGd.length() + 1);
                }
            }
        }

        //Question ---------------------------------------------------------------------------------
        if (lo.translateFromGaelic){
            e.setQuestion(sentenceGd);
        } else {
            e.setQuestion(sentenceEn);
        }

        //Solutions --------------------------------------------------------------------------------
        if (lo.translateFromGaelic){
            e.addSolution(sentenceEn);
            e.addSolution(sentenceEnAlt);
        } else {
            e.addSolution(sentenceGd);
        }

        //Output -----------------------------------------------------------------------------------
        return e;
    }
}
