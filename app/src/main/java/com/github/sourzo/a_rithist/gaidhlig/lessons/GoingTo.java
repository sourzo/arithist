package com.github.sourzo.a_rithist.gaidhlig.lessons;

import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.gaidhlig.GrammaticalPerson;
import com.github.sourzo.a_rithist.gaidhlig.SentenceType;
import com.github.sourzo.a_rithist.gaidhlig.Tense;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.HashMap;
import java.util.Random;

public class GoingTo extends ExerciseGenerator {
    GrammarGd gg;
    GrammarEn ge;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public GoingTo(LessonOptions lo){
        super(lo);
        gg = new GrammarGd(lo.appRes);
        ge = new GrammarEn(lo.appRes);
    }

    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();

        int vocabNum = new Random().nextInt(lo.sampleVocabList.size());
        HashMap<String,String> place = lo.sampleVocabList.data.get(vocabNum);
        GrammaticalPerson person = GrammaticalPerson.random();

        Tense tense = randomTense();

        //Parts of sentence ------------------------------------------------------------------------
        String persGoingGd;
        if (tense == Tense.PAST) {
            persGoingGd = gg.verbSimplePast("rach", SentenceType.POS_DECLARATION) +
                    " " + person.gd_subj();
        } else if (tense == Tense.FUTURE) {
            persGoingGd = gg.verbSimpleFuture("rach", SentenceType.POS_DECLARATION) +
                    " " + person.gd_subj();
        } else {
            persGoingGd = gg.verbalNoun("dol", person.gd_subj(), tense, SentenceType.POS_DECLARATION);
        }

        String toPlaceGd = GrammarGd.prepDo(place.get("place_gd"), true);
        String toPlaceGdAlt = GrammarGd.prepDo(place.get("place_gd"), false);

        String persGoingEn = ge.toGo(person, tense);

        //Construct sentences ----------------------------------------------------------------------
        String sentenceEn = capitalise(persGoingEn) + " to " + place.get("english");
        String sentenceGd = capitalise(persGoingGd) + " " + toPlaceGd;
        //Technically correct but not common: preposition is in the form "do" instead of "a":
        String sentenceGdAlt = capitalise(persGoingGd) + " " + toPlaceGdAlt;


        //Prompt -----------------------------------------------------------------------------------
        e.setPrePrompt("Translate:");

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
            e.addSolution(sentenceGdAlt);
        }

        //Output -----------------------------------------------------------------------------------
        return e;
    }
}
