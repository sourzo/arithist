package com.github.sourzo.a_rithist.gaidhlig.lessons;

import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.gaidhlig.GrammaticalPerson;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;
import com.github.sourzo.a_rithist.general.VocabTable;

import java.util.HashMap;
import java.util.Random;

public class Seasons extends ExerciseGenerator {
    GrammarGd gg;
    GrammarEn ge;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public Seasons(LessonOptions lo){
        super(lo);
        gg = new GrammarGd(lo.appRes);
        ge = new GrammarEn(lo.appRes);
    }

    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        int monthNum = new Random().nextInt(12);
        HashMap<String,String> month = monthsVocabList.data.get(monthNum);
        boolean usePrepositional = new Random().nextBoolean();
        //Parts of sentence ------------------------------------------------------------------------
        String monthEn = month.get("english");
        String monthGd = month.get("nom_sing");

        String seasonEn = month.get("season_en");
        String seasonGd = month.get("season_gd");

        VocabTable season = seasonsVocabList.filterMatches("english",seasonEn);

        //There are two ways to say the prepositional for seasons
        String seasonIn1 = season.get(0,"in_gd");
        String seasonIn2 = "anns " + gg.commonArticle(seasonGd, "sg", "masc", GrammarGd.GrammaticalCase.PREPOSITIONAL);

        //Construct sentence -----------------------------------------------------------------------
        String sentenceEn;
        String sentenceGd;
        String sentenceGd2;
        if (usePrepositional) {
            sentenceEn = monthEn + " is in " + seasonEn;
            sentenceGd = "Tha " + monthGd + " " + seasonIn1;
            sentenceGd2 = "Tha " + monthGd + " " + seasonIn2;
        }
        else {
            sentenceEn = "It is " + seasonEn;
            sentenceGd = "'S e " + seasonGd + " a th' ann";
            sentenceGd2 = "";
        }

        //Prompt -----------------------------------------------------------------------------------
        e.setPrePrompt("Translate:");

        if (lo.responseType == LessonOptions.ResponseType.BLANKS) {
            if (usePrepositional){
                String etp = "Tha " + monthGd + " ";
                e.setEditTextPrompt(etp);
                e.setEditTextCursorPosition(etp.length());
            } else {
                String etp = "'S e  a th' ann";
                e.setEditTextPrompt(etp);
                e.setEditTextCursorPosition(5);
            }
        }

        //Question ---------------------------------------------------------------------------------
        e.setQuestion(sentenceEn);

        //Solutions --------------------------------------------------------------------------------
        e.addSolution(sentenceGd);
        if (usePrepositional) {e.addSolution(sentenceGd2);}
        //Output -----------------------------------------------------------------------------------
        return e;
    }
}
