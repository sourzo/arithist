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

public class Months extends ExerciseGenerator {
    GrammarGd gg;
    GrammarEn ge;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public Months(LessonOptions lo){
        super(lo);
        gg = new GrammarGd(lo.appRes);
        ge = new GrammarEn(lo.appRes);
    }

    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();

        //Step 1: Pick a random month
        int monthNum = new Random().nextInt(12);
        HashMap<String,String> month = monthsVocabList.data.get(monthNum);

        //Step 2: Choose whether to construct a sentence with the Prepositional case
        boolean usePrepositional = new Random().nextBoolean();

        //Step 3: If Prepositional, then need a holiday (or birthday)
        String holidayEn = "";
        String holidayGd = "";
        String monthPrep = "";
        if (usePrepositional) {
            VocabTable holiday = holidaysVocabList
                    .filterMatches("month_en", month.get("english"));
            if (holiday.size() == 0) {
                //No holidays: Use birthday instead
                GrammaticalPerson person = GrammaticalPerson.random();
                String whoseEn = capitalise(person.en_poss());
                String whoseGd = person.gd_aig();
                holidayEn = whoseEn +  " birthday";
                holidayGd = "an co-là breith " + whoseGd;
            } else {
                //Pick a random holiday for the month
                holiday = holiday.getRandomRows(1);
                holidayEn = holiday.get(0,"english");
                holidayGd = holiday.get(0,"nom_sing");
            }

            //also need the prepositional case for the month
            //(NB I decided not to slenderise months)
            monthPrep = gg.commonArticle(
                    month.get("nom_sing"),
                    "sg",
                    month.get("gender"),
                    GrammarGd.GrammaticalCase.PREPOSITIONAL);
        }

        //Construct sentence -------------------------------------------------------
        String sentenceEn;
        String sentenceGd;
        if (usePrepositional) {
            sentenceEn = holidayEn + " is in " + month.get("english");
            sentenceGd = "Tha " + holidayGd + " anns " + monthPrep;
        } else {
            sentenceEn = "It is " + month.get("english");
            sentenceGd = "'S e " + month.get("nom_sing") + " a th' ann";
        }

        //Prompt -----------------------------------------------------------------------------------
        e.setPrePrompt("Translate:");

        if (lo.responseType == LessonOptions.ResponseType.BLANKS) {
            if (usePrepositional) {
                String etp = "Tha " + holidayGd + " ";
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

        //Output -----------------------------------------------------------------------------------
        return e;
    }
}
