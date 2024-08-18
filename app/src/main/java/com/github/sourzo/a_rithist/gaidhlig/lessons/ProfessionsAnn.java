package com.github.sourzo.a_rithist.gaidhlig.lessons;

import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.HashMap;
import java.util.Random;

public class ProfessionsAnn extends ExerciseGenerator {
    GrammarGd g;
    GrammarEn ge;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public ProfessionsAnn(LessonOptions lo){
        super(lo);
        g = new GrammarGd(lo.appRes);
        ge = new GrammarEn(lo.appRes);
        lo.vocabListName = "people_professions.csv";
    }

    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        //randomiser
        int personNum = new Random().nextInt(7);
        int professionNum = new Random().nextInt(lo.sampleVocabList.size());
        HashMap<String,String> profession = lo.sampleVocabList.data.get(professionNum);

        //Gaelic parts of sentence
        String ppAnn = g.pp.get(personNum,"ann an");

        String professionGd;
        if (personNum < 4){
            professionGd = profession.get("nom_sing");
        } else {
            professionGd = profession.get("nom_pl");
        }

        //English
        String pronounEn = g.pp.get(personNum,"en_subj");
        String beEn = ge.en.get(personNum,"be_pres");

        String professionEn = profession.get("english");
        if (personNum < 4){
            professionEn = ge.enIndefArticle(professionEn);
        } else {
            professionEn = ge.pluralise(professionEn);
        }

        //Sentences:
        String sentenceGd = "'S e " + professionGd.toLowerCase() + " a th' " + ppAnn.toLowerCase();
        String sentenceEn = capitalise(pronounEn) + " " + beEn.toLowerCase() + " " + professionEn.toLowerCase();

        //Prompt -----------------------------------------------------------------------------------
        e.setPrePrompt("Translate:");
        if (lo.sentenceType.equals("blanks")) {
            if (lo.translateFromGaelic) {
                e.setEditTextPrompt(" " + professionEn.toLowerCase());
                e.setEditTextCursorPosition(0);
            } else {
                e.setEditTextPrompt("'S e " + professionGd.toLowerCase() + " a th' ");
                e.setEditTextCursorPosition(("'S e " + professionGd.toLowerCase() + " a th' ").length());
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
        } else {
            e.addSolution(sentenceGd);
        }

        //Output -----------------------------------------------------------------------------------
        return e;
    }
}
