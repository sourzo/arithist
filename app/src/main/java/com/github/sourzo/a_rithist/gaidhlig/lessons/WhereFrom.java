package com.github.sourzo.a_rithist.gaidhlig.lessons;

import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.gaidhlig.GrammaticalPerson;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.HashMap;
import java.util.Random;

public class WhereFrom extends ExerciseGenerator {
    //TODO Add emphatic pronouns (mise etc) to this as a boolean option?
    GrammarGd gg;
    GrammarEn ge;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public WhereFrom(LessonOptions lo){
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

        //Parts of sentence --------------------------------------------------------
        //NB prepositional case - I decided not to slenderise
        String placeGd = place.get("place_gd");
        String fromGd;

        if (GrammarGd.startsWithArticle(placeGd)){
            fromGd = "às " + gg.articleStandard(GrammarGd.removeArticle(placeGd));
        } else {
            fromGd = "à " + placeGd;
        }

        GrammaticalPerson whoQuestion = person.getOpposite();

        String beEn = ge.en
                .filterMatches("en_subj",person.en_subj)
                .get("be_pres",0);

        //Construct sentence -------------------------------------------------------
        String sentenceEn = capitalise(person.en_subj) + " "
                + beEn + " from " + place.get("english");
        String sentenceGd = "Tha " + person.gd_subj + " " + fromGd;
        String sentenceGdAlt = "'S ann " + fromGd + " a tha " + person.gd_subj;

        //Prompts ----------------------------------------------------------------------------------
        if (lo.responseType == LessonOptions.ResponseType.Q_AND_A){
            e.setPrePrompt("Answer:");
        } else {
            e.setPrePrompt("Translate:");
        }

        if (lo.responseType == LessonOptions.ResponseType.BLANKS) {
            String etp = "Tha " + person.gd_subj + " ";
            e.setEditTextPrompt(etp);
            e.setEditTextCursorPosition(etp.length());
        }


        //Question ---------------------------------------------------------------------------------
        if (lo.responseType == LessonOptions.ResponseType.FULL_SENTENCE ||
                lo.responseType == LessonOptions.ResponseType.BLANKS) {
            //Translate
            e.setQuestion(sentenceEn);
        } else{
            //Answer question
            e.setQuestion("Cò às a tha " + whoQuestion.gd_subj + "? (" + place.get("english") + ")");
        }

        //Solutions --------------------------------------------------------------------------------
        e.addSolution(sentenceGd);
        e.addSolution(sentenceGdAlt);
        if (lo.responseType == LessonOptions.ResponseType.Q_AND_A) {
            if (person == GrammaticalPerson.FIRST_SINGULAR){
                e.addSolution("Tha sibh " + fromGd);
            } else if (person == GrammaticalPerson.SECOND_PLURAL){
                e.addSolution("Tha mi " + fromGd);
            }
        }


        //Output -----------------------------------------------------------------------------------

        return e;
    }
}
