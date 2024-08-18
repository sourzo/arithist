package com.github.sourzo.a_rithist.gaidhlig.lessons;

import static com.github.sourzo.a_rithist.gaidhlig.GrammarGd.lenite;

import android.util.Log;

import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class Gender extends ExerciseGenerator {
    GrammarGd g;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public Gender(LessonOptions lo){
        super(lo);
        g = new GrammarGd(lo.appRes);
    }

    public Exercise generate() {
        Log.i("ZoeNote","Generate Gender exercise: Start");
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        int vocabNum = new Random().nextInt(lo.sampleVocabList.size());
        HashMap<String,String> randomWord = lo.sampleVocabList.data.get(vocabNum);
        ArrayList<String> genderOptions = new ArrayList<>();
        if (lo.genderAdj){genderOptions.add("adj");}
        if (lo.genderDefArtNom){genderOptions.add("defArtNom");}
        String genderMode = genderOptions.get(new Random().nextInt(genderOptions.size()));
        String sentenceEn = "";
        String sentenceGd = "";
        //Construct sentence -----------------------------------------------------------------------
        switch (genderMode) {
            case "adj":
                String adjectiveEn = "small";
                String adjectiveGd = "beag";
                sentenceEn = "A " + adjectiveEn + " " + randomWord.get("english");
                if (Objects.equals(randomWord.get("gender"), "masc")) {
                    sentenceGd = randomWord.get("nom_sing") + " " + adjectiveGd;
                } else if (Objects.equals(randomWord.get("gender"), "fem")) {
                    sentenceGd = randomWord.get("nom_sing") + " " + lenite(adjectiveGd, false);
                }
                break;
            case "defArtNom":
                sentenceEn = "The " + randomWord.get("english") + " (nominative)";
                sentenceGd = g.commonArticle(randomWord.get("nom_sing"),
                        "sg",
                        randomWord.get("gender"),
                        GrammarGd.GrammaticalCase.NOMINAL);
                break;

            //warning - vocab lists don't have prep_sing at the moment
            case "defPrep":
                sentenceEn = "The " + randomWord.get("english") + " (prepositional)";

                sentenceGd = g.commonArticle(randomWord.get("prep_sing"),
                        "sg",
                        randomWord.get("gender"),
                        GrammarGd.GrammaticalCase.PREPOSITIONAL);
                break;

            //warning - vocab lists don't have poss_sing at the moment
            case "defPoss":
                sentenceEn = "The " + randomWord.get("english") + " (possessive)";

                sentenceGd = g.commonArticle(randomWord.get("poss_sing"),
                        "sg",
                        randomWord.get("gender"),
                        GrammarGd.GrammaticalCase.POSSESSIVE);
                break;
            default:
                sentenceEn="";
                sentenceGd="";
        }
        //Prompt -----------------------------------------------------------------------------------
        e.setPrePrompt("Translate:");

        //Question ---------------------------------------------------------------------------------
        if (!sentenceEn.equals("")){
            e.setQuestion(sentenceEn);
        } else {throw new RuntimeException("Question missing");}

        //Solutions --------------------------------------------------------------------------------
        if (!sentenceGd.equals("")){
            e.addSolution(sentenceGd);
        } else {throw new RuntimeException("Solution missing");}
        //Output -----------------------------------------------------------------------------------

        return e;
    }
}
