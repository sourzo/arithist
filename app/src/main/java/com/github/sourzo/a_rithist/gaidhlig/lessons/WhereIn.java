package com.github.sourzo.a_rithist.gaidhlig.lessons;

import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.gaidhlig.GrammaticalPerson;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.HashMap;
import java.util.Random;

public class WhereIn extends ExerciseGenerator {
    GrammarGd gg;
    GrammarEn ge;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public WhereIn(LessonOptions lo){
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
        GrammaticalPerson whoQuestion = person.getOpposite();
        boolean useCommonArticle = new Random().nextBoolean();

        //Build parts of sentence ------------------------------------------------------------------
        String whereGd;
        String whereEn;

        if (useCommonArticle) {
            whereGd = "anns " + gg.articleStandard(GrammarGd.removeArticle(place.get("nom_sing")));
            whereEn = "the " + place.get("english");
        } else {
            whereGd = "ann " + GrammarGd.anm(place.get("nom_sing"));
            whereEn = ge.enIndefArticle(place.get("english"));
        }

        //Construct sentence -----------------------------------------------------------------------
        String sentenceEn = capitalise(person.en_subj) + " " +
                ge.en.filterMatches("en_subj", person.en_subj).get(0,"be_pres") +
                " in " + whereEn;
        String sentenceGd = "Tha " + person.gd_subj + " " + whereGd;

        //Prompt -----------------------------------------------------------------------------------
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
            e.setQuestion("Càite a bheil " + whoQuestion.gd_subj + "? (" + whereEn + ")");
        }

        //Solutions --------------------------------------------------------------------------------
        e.addSolution(sentenceGd);
        if (person == GrammaticalPerson.FIRST_SINGULAR){
            e.addSolution("Tha sibh " + whereGd);
        } else if (person == GrammaticalPerson.SECOND_PLURAL){
            e.addSolution("Tha mi " + whereGd);
        }

        //Output -----------------------------------------------------------------------------------
        return e;
    }
}
