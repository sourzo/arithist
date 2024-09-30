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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class VerbTenses extends ExerciseGenerator {
    GrammarGd gg;
    GrammarEn ge;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public VerbTenses(LessonOptions lo){
        super(lo);
        gg = new GrammarGd(lo.appRes);
        ge = new GrammarEn(lo.appRes);
    }

    public Exercise generate() {
    //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();

        //Random verb
        int verbNum = new Random().nextInt(lo.sampleVocabList.size());
        HashMap<String,String> verb = lo.sampleVocabList.data.get(verbNum);

        //Grammatical Person
        GrammaticalPerson person = GrammaticalPerson.random();
        String person_gd = person.gd_subj();

        //Verb forms statement/question, positive/negative
        ArrayList<SentenceType> sentenceTypeOptions = new ArrayList<>();
        if (lo.posQuestions){sentenceTypeOptions.add(SentenceType.POS_QUESTION);}
        if (lo.negQuestions){sentenceTypeOptions.add(SentenceType.NEG_QUESTION);}
        if (lo.posStatements){sentenceTypeOptions.add(SentenceType.POS_STATEMENT);}
        if (lo.negStatements){sentenceTypeOptions.add(SentenceType.NEG_STATEMENT);}
        SentenceType sentenceType = sentenceTypeOptions.get(new Random().nextInt(sentenceTypeOptions.size()));

        Tense tense = randomTense();

        //Build the sentence -----------------------------------------------------------------------
        String sentence_gd;

        if (tense.equals(Tense.PAST)) {
            String v_root = verb.get("root");
            String verb_gd = gg.verbSimplePast(v_root, sentenceType);
            if (person_gd.equals("thu") &&
                    Arrays.asList("faca", "cuala", "chuala").contains(verb_gd)) {
                person_gd = "tu";
            }
            sentence_gd = verb_gd + " " + person_gd;
        } else if (tense.equals(Tense.FUTURE)) {
            String v_root = verb.get("root");
            String verb_gd = gg.verbSimpleFuture(v_root, sentenceType);
            if (person_gd.equals("thu") &&
                    (verb_gd.endsWith("idh") && !verb_gd.equals("bidh"))) {
                person_gd = "tu";
            }
            sentence_gd = verb_gd + " " + person_gd;
        } else {
            sentence_gd = gg.verbalNoun(Objects.requireNonNull(verb.get("verbal_noun")),
                    person_gd,
                    tense,
                    sentenceType);
        }

        sentence_gd = capitalise(sentence_gd);

        String sentence_en = ge.transformVerb(verb,
                person,
                tense,
                sentenceType);
        sentence_en = capitalise(sentence_en).replace(" i ", " I ");

        //Prompt -----------------------------------------------------------------------------------
        e.setPrePrompt("Translate:");

        //Question ---------------------------------------------------------------------------------
        String q = sentence_en;
        if (sentenceType.isQuestion) {
            q = q + "?";
        }
        e.setQuestion(q);
        //Solutions --------------------------------------------------------------------------------
        e.addSolution(capitalise(sentence_gd));

    //Output -----------------------------------------------------------------------------------
        return e;
    }
}
