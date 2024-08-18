package com.github.sourzo.a_rithist.gaidhlig.lessons;

import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.gaidhlig.GrammaticalPerson;
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

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public VerbTenses(LessonOptions lo){
        super(lo);
        gg = new GrammarGd(lo.appRes);
    }

    public Exercise generate() {
//    //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
//        int vocabNum = new Random().nextInt(lo.sampleVocabList.size());
//        HashMap<String,String> verb = lo.sampleVocabList.data.get(vocabNum);
//        GrammaticalPerson person = GrammaticalPerson.random();
//        int persNumPp = gg.pp.getRow("en_subj", person.en_subj);
//
//        //verb forms statement/question, positive/negative
//        ArrayList<GrammarGd.VerbForm> verbFormOptions = new ArrayList<>();
//        if (lo.posQuestions){verbFormOptions.add(GrammarGd.VerbForm.POS_QUESTION);}
//        if (lo.negQuestions){verbFormOptions.add(GrammarGd.VerbForm.NEG_QUESTION);}
//        if (lo.posStatements){verbFormOptions.add(GrammarGd.VerbForm.POS_STATEMENT);}
//        if (lo.negStatements){verbFormOptions.add(GrammarGd.VerbForm.NEG_STATEMENT);}
//        GrammarGd.VerbForm verbForm = verbFormOptions.get(new Random().nextInt(verbFormOptions.size()));
//
//        //Tense
//        ArrayList<String> tenseOptions = new ArrayList<>();
//        if (lo.past){tenseOptions.add("past");}
//        if (lo.past){tenseOptions.add("vn_past");}
//        if (lo.present){tenseOptions.add("present");}
//        if (lo.future){tenseOptions.add("future");}
//        if (lo.future){tenseOptions.add("vn_future");}
//        String chosen_tense = tenseOptions.get(new Random().nextInt(tenseOptions.size()));
//
//        String person_gd = gg.pp.get(persNumPp,"pronoun_gd");
//        String person_en = person.en_subj;
//
//        if (Objects.equals(chosen_tense, "past")
//                || Objects.equals(chosen_tense, "future"))
//        {
//            String v_root = verb.get("root");
//            String verb_gd = gg.transformVerb(
//                    v_root,
//                    chosen_tense,
//                    verbForm);
//            if (Objects.equals(person_gd, "thu") &&
//                    ((chosen_tense.equals("future") && verb_gd.endsWith("idh") && verb_gd !="bidh")
//                    || Arrays.asList("faca", "cuala", "chuala").contains(verb_gd))) {
//                person_gd = "tu";
//            }
//        } else {
//            String v_noun = verb.get("verbal_noun");
//            String sentence_gd = gg.verbal_noun(v_noun,
//                    person_gd,
//                    chosen_tense,
//                    verbForm);
//        }
//
//        sentence_gd = capitalise(sentence_gd);
//
//        String sentence_en = gg.en_verb(vocab_sample, verb_num,
//                person_en,
//                chosen_tense,
//                verbForm);
//        sentence_en = capitalise(sentence_en).replace(" i ", " I ");
//
//        //Prompt -----------------------------------------------------------------------------------
//        e.setPrePrompt("Translate:");
//
//        //Question ---------------------------------------------------------------------------------
//        String q = sentence_en;
//        if (q_s == True) {
//            q = q + "?";
//        }
//        e.setQuestion(q);
//        //Solutions --------------------------------------------------------------------------------
//        e.addSolution(capitalise(sentence_gd));
//
//    //Output -----------------------------------------------------------------------------------
        return e;
    }
}
