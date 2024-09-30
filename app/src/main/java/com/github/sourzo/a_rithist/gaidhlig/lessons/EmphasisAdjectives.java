package com.github.sourzo.a_rithist.gaidhlig.lessons;

import android.util.Log;

import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.gaidhlig.GrammaticalPerson;
import com.github.sourzo.a_rithist.gaidhlig.Tense;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.HashMap;
import java.util.Random;

public class EmphasisAdjectives extends ExerciseGenerator {
    GrammarGd gg;
    GrammarEn ge;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public EmphasisAdjectives(LessonOptions lo){
        super(lo);
        gg = new GrammarGd(lo.appRes);
        ge = new GrammarEn(lo.appRes);
    }

    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        int adjNum = new Random().nextInt(lo.sampleVocabList.size());
        HashMap<String,String> randomAdjective = lo.sampleVocabList.data.get(adjNum);
        int adjModifierNum = new Random().nextInt(GrammarGd.adjModList.size());
        GrammaticalPerson person = GrammaticalPerson.random();

        //Parts of sentence ------------------------------------------------------------------------
        String pronoun_en = person.en_subj();

        String adjModEn = GrammarGd.adjModList.get(adjModifierNum);
        Log.i("info","adjModEn = " + adjModEn);
        String adjModGd = GrammarGd.adjectiveModifiers.get(adjModEn);
        Log.i("info","adjModGd = " + adjModGd);

        String adjectiveGd = randomAdjective.get("adj_gd");
        String adjective_en = randomAdjective.get("english");

        String beEn = person.en_toBe(Tense.PRESENT_VERBAL_NOUN);

        //Construct sentence -----------------------------------------------------------------------
        assert adjModGd != null;
        if (adjModGd.equals("ro ") || adjModGd.equals("glè ")){
            assert adjectiveGd != null;
            adjectiveGd = GrammarGd.lenite(adjectiveGd, false);
        }
        String sentenceGd = "Tha " + person.gd_emph() + " " + adjModGd + adjectiveGd;
        String sentenceEn = "*" + capitalise(pronoun_en) + "* " +
                beEn.toLowerCase() + " " + adjModEn + adjective_en;
        String sentenceEnWithoutAsterisks = capitalise(pronoun_en) +
                beEn.toLowerCase() + " " + adjModEn + adjective_en;

        //Prompt -----------------------------------------------------------------------------------
        e.setPrePrompt("Translate:");

        //Question ---------------------------------------------------------------------------------
        if (lo.translateFromGaelic) {
            e.setQuestion(sentenceGd);
        } else {
            e.setQuestion(sentenceEn);
        }
        //Solutions --------------------------------------------------------------------------------
        if (lo.translateFromGaelic){
            e.addSolution(sentenceEn);
            e.addSolution(sentenceEnWithoutAsterisks);
        } else {
            e.addSolution(sentenceGd);
        }

        //Output -----------------------------------------------------------------------------------
        return e;
    }
}
