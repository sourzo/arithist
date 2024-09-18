package com.github.sourzo.a_rithist.gaidhlig.lessons;

import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.gaidhlig.GrammaticalPerson;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

public class Preferences extends ExerciseGenerator {
    GrammarGd gg;
    GrammarEn ge;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public Preferences(LessonOptions lo){
        super(lo);
        gg = new GrammarGd(lo.appRes);
        ge = new GrammarEn(lo.appRes);
    }

    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        int object_num = new Random().nextInt(lo.sampleVocabList.size());
        HashMap<String,String> randomWord = lo.sampleVocabList.data.get(object_num);
        GrammaticalPerson person = GrammaticalPerson.random();
        int persNumEn = ge.en.getRow("en_subj", person.en_subj);
        int persNumPp = gg.pp.getRow("en_subj", person.en_subj);
        int tense = new Random().nextInt(2); // 0 = present tense, 1 = future conditional
        boolean positive = new Random().nextBoolean(); // F = positive, T = negative
        int likePref = new Random().nextInt(2); // 0 = like, 1 = prefer

        //Parts of sentence --------------------------------------------------------
        String objIndef = ge.enIndefArticle(lo.sampleVocabList.get(object_num,"english"));

        //English
        String likePreferEn;
        if (tense == 0) {
            if (positive) {
                likePreferEn = "";
            } else {
                likePreferEn = ge.en.get(persNumEn,"do_pres").toLowerCase() + "n't ";
            }
        } else {
            if (positive) {
                likePreferEn = "would ";
            } else {
                likePreferEn = "wouldn't ";
            }
        }
        if (likePref == 0) {
            likePreferEn = likePreferEn + "like";
        } else {
            likePreferEn = likePreferEn + "prefer";
        }
        if (tense == 0 && positive)
        {
            HashSet<String> hsn = new HashSet<>(Arrays.asList("he", "she", "name"));
            if (hsn.contains(person.en_subj.toLowerCase()))
            {
                likePreferEn = likePreferEn + "s";
            }      
        }

    //Gaelic
        String likePreferGd;
        if (positive) {
            if (likePref == 0) {
                if (tense == 0) {
                    likePreferGd = "is toil";
                } else {
                    likePreferGd = "bu toil";
                }
            } else {
                if (tense == 0) {
                    likePreferGd = "is fheàrr";
                } else {
                    likePreferGd = "b' fheàrr";
                }
            }
        } else {
            if (likePref == 0) {
                if (tense == 0) {
                    likePreferGd = "cha toil";
                } else {
                    likePreferGd = "cha bu toil";
                }
            } else {
                if (tense == 0) {
                        likePreferGd = "chan fheàrr";
                } else {
                        likePreferGd = "cha b' fheàrr";
                }
            }
        }

        //Construct sentences ------------------------------------------------------
        String sentenceEn = capitalise(person.en_subj) + " " + likePreferEn.toLowerCase() + " " + objIndef.toLowerCase();
        String sentenceGd = capitalise(likePreferGd) + " " + gg.pp.get(persNumPp,"le").toLowerCase() + " " + Objects.requireNonNull(randomWord.get("nom_sing")).toLowerCase();

        //Prompts ----------------------------------------------------------------------------------
        e.setPrePrompt("Translate:");

        if (Objects.equals(lo.responseType, "blanks")) {
            if (lo.translateFromGaelic) {
                String etp = " " + likePreferEn.toLowerCase() + " " + objIndef.toLowerCase();
                e.setEditTextPrompt(etp);
                e.setEditTextCursorPosition(0);
            } else {
                String etp =capitalise(likePreferGd) + "  " + Objects.requireNonNull(randomWord.get("nom_sing")).toLowerCase();
                e.setEditTextPrompt(etp);
                int cursor = likePreferGd.length() + 1;
                e.setEditTextCursorPosition(cursor);
            }
        }
        //Question ---------------------------------------------------------------------------------
        if (lo.translateFromGaelic) {
            e.setQuestion(sentenceGd);
        } else {
            e.setQuestion(sentenceEn);
        }

        //Solutions --------------------------------------------------------------------------------
        if (lo.translateFromGaelic) {
            e.addSolution(sentenceEn);
        } else {
            e.addSolution(sentenceGd);
        }

        //Output -----------------------------------------------------------------------------------
        return e;
    }
}
