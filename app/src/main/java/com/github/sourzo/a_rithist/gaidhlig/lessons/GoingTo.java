package com.github.sourzo.a_rithist.gaidhlig.lessons;

import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.gaidhlig.GrammaticalPerson;
import com.github.sourzo.a_rithist.gaidhlig.SentenceType;
import com.github.sourzo.a_rithist.gaidhlig.Tense;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.ArrayList;
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

        //Tense
        ArrayList<Tense> tenseOptions = new ArrayList<>();
        if (lo.past){
            tenseOptions.add(Tense.PAST);
            tenseOptions.add(Tense.PAST_VERBAL_NOUN);
        }
        if (lo.present){
            tenseOptions.add(Tense.PRESENT_VERBAL_NOUN);
        }
        if (lo.future){
            tenseOptions.add(Tense.FUTURE);
            tenseOptions.add(Tense.FUTURE_VERBAL_NOUN);
        }
        Tense tense = tenseOptions.get(new Random().nextInt(tenseOptions.size()));

        //Parts of sentence ------------------------------------------------------------------------
        String persGoingGd;
        if (tense == Tense.PAST || tense == Tense.FUTURE) {
            persGoingGd = gg.transformVerb("rach", tense, SentenceType.POS_STATEMENT) +
                    " " + person.gd_subj;
        } else {
            persGoingGd = gg.verbal_noun("dol", person.gd_subj, tense, SentenceType.POS_STATEMENT);
        }

        String toPlaceGd = GrammarGd.addDo(place.get("place_gd"));

        String persGoingEn = ge.toGo(person, tense);

        //Construct sentences ----------------------------------------------------------------------
        String sentenceEn = capitalise(persGoingEn) + " to " + place.get("english");
        String sentenceGd = capitalise(persGoingGd) + " " + toPlaceGd;

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
        }

        //Output -----------------------------------------------------------------------------------
        return e;
    }
}
