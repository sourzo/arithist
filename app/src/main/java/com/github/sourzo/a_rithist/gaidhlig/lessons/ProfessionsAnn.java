package com.github.sourzo.a_rithist.gaidhlig.lessons;

import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.gaidhlig.GrammaticalPerson;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.HashMap;
import java.util.Random;

public class ProfessionsAnn extends ExerciseGenerator {
    GrammarGd gg;
    GrammarEn ge;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public ProfessionsAnn(LessonOptions lo){
        super(lo);
        gg = new GrammarGd(lo.appRes);
        ge = new GrammarEn(lo.appRes);
        lo.vocabListName = "people_professions.csv";
    }

    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        //randomiser
        GrammaticalPerson person = GrammaticalPerson.random();
        int persNumEn = ge.en.getRow("en_subj", person.en_subj());
        int professionNum = new Random().nextInt(lo.sampleVocabList.size());
        HashMap<String,String> profession = lo.sampleVocabList.data.get(professionNum);

        //Gaelic parts of sentence
        String ppAnn = person.gd_ann();

        String professionGd;
        if (person.isPlural()){
            professionGd = profession.get("nom_pl");
        } else {
            professionGd = profession.get("nom_sing");
        }

        //English
        String pronounEn = person.en_subj();
        String beEn = ge.en.get(persNumEn,"be_pres");

        String professionEn = profession.get("english");
        if (person.isPlural()){
            professionEn = ge.pluralise(professionEn);
        } else {
            professionEn = ge.enIndefArticle(professionEn);
        }

        //Sentences:
        String sentenceGd = "'S e " + professionGd.toLowerCase() + " a th' " + ppAnn.toLowerCase();
        String sentenceEn = capitalise(pronounEn) + " " + beEn.toLowerCase() + " " + professionEn.toLowerCase();

        //Prompt -----------------------------------------------------------------------------------
        e.setPrePrompt("Translate:");
        if (lo.responseType == LessonOptions.ResponseType.BLANKS) {
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
