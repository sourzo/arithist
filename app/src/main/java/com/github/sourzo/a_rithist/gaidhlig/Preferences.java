package com.github.sourzo.a_rithist.gaidhlig;

import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

public class Preferences extends ExerciseGenerator {
    GrammarGd g;
    GrammarEn ge;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public Preferences(LessonOptions lo){
        super(lo);
        g = new GrammarGd(lo.appRes);
        ge = new GrammarEn(lo.appRes);
    }

    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        int object_num = new Random().nextInt(lo.sampleVocabList.size());
        HashMap<String,String> randomWord = lo.sampleVocabList.data.get(object_num);
        int subject_num = new Random().nextInt(7);
        int tense = new Random().nextInt(2); // 0 = present tense, 1 = future conditional
        boolean positive = new Random().nextBoolean(); // F = positive, T = negative
        int likepref = new Random().nextInt(2); // 0 = like, 1 = prefer

        //Parts of sentence --------------------------------------------------------
        String obj_indef = ge.enIndefArticle(lo.sampleVocabList.get(object_num,"english"));

        //English
        String like_prefer_en;
        if (tense == 0) {
            if (positive) {
                like_prefer_en = "";
            } else {
                like_prefer_en = ge.en.get(subject_num,"do_pres").toLowerCase() + "n't ";
            }
        } else {
            if (positive) {
                like_prefer_en = "would ";
            } else {
                like_prefer_en = "wouldn't ";
            }
        }
        if (likepref == 0) {
            like_prefer_en = like_prefer_en + "like";
        } else {
            like_prefer_en = like_prefer_en + "prefer";
        }
        if (tense == 0 && positive)
        {
            HashSet<String> hsn = new HashSet<>(Arrays.asList("he", "she", "name"));
            if (hsn.contains(ge.en.get(subject_num,"en_subj").toLowerCase()))
            {
                like_prefer_en = like_prefer_en + "s";
            }      
        }

    //Gaelic
        String like_prefer_gd;
        if (positive) {
            if (likepref == 0) {
                if (tense == 0) {
                    like_prefer_gd = "is toil";
                } else {
                    like_prefer_gd = "bu toil";
                }
            } else {
                if (tense == 0) {
                    like_prefer_gd = "is fheàrr";
                } else {
                    like_prefer_gd = "b' fheàrr";
                }
            }
        } else {
            if (likepref == 0) {
                if (tense == 0) {
                    like_prefer_gd = "cha toil";
                } else {
                    like_prefer_gd = "cha bu toil";
                }
            } else {
                if (tense == 0) {
                        like_prefer_gd = "chan fheàrr";
                } else {
                        like_prefer_gd = "cha b' fheàrr";
                }
            }
        }

        //Construct sentences ------------------------------------------------------
        String sentence_en = capitalise(ge.en.get(subject_num,"en_subj")) + " " + like_prefer_en.toLowerCase() + " " + obj_indef.toLowerCase();
        String sentence_gd = capitalise(like_prefer_gd) + " " + g.pp.get(subject_num,"le").toLowerCase() + " " + Objects.requireNonNull(randomWord.get("nom_sing")).toLowerCase();

        //Prompts ----------------------------------------------------------------------------------
        e.setPrePrompt("Translate:");

        if (Objects.equals(lo.sentenceType, "blanks")) {
            if (lo.translateFromGaelic) {
                String etp = " " + like_prefer_en.toLowerCase() + " " + obj_indef.toLowerCase();
                e.setEditTextPrompt(etp);
                e.setEditTextCursorPosition(0);
            } else {
                String etp =capitalise(like_prefer_gd) + "  " + Objects.requireNonNull(randomWord.get("nom_sing")).toLowerCase();
                e.setEditTextPrompt(etp);
                int cursor = like_prefer_gd.length() + 1;
                e.setEditTextCursorPosition(cursor);
            }
        }
        //Question ---------------------------------------------------------------------------------
        if (lo.translateFromGaelic) {
            e.setQuestion(sentence_gd);
        } else {
            e.setQuestion(sentence_en);
        }

        //Solutions --------------------------------------------------------------------------------
        if (lo.translateFromGaelic) {
            e.addSolution(sentence_en);
        } else {
            e.addSolution(sentence_gd);
        }

        //Output -----------------------------------------------------------------------------------
        return e;
    }
}
