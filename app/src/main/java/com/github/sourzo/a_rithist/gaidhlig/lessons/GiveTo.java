package com.github.sourzo.a_rithist.gaidhlig.lessons;

import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.gaidhlig.GrammaticalPerson;
import com.github.sourzo.a_rithist.gaidhlig.SentenceType;
import com.github.sourzo.a_rithist.gaidhlig.Tense;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.HashMap;
import java.util.Random;

public class GiveTo extends ExerciseGenerator {
    GrammarGd gg;
    GrammarEn ge;
    static HashMap<String, String> giveEnMap = new HashMap<>();
    static{
        giveEnMap.put("en_vn", "giving");
        giveEnMap.put("english", "give");
        giveEnMap.put("en_past", "gave");
    }

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public GiveTo(LessonOptions lo){
        super(lo);
        gg = new GrammarGd(lo.appRes);
        ge = new GrammarEn(lo.appRes);
    }

    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        int giftNum = new Random().nextInt(lo.sampleVocabList.size());
        HashMap<String,String> gift = lo.sampleVocabList.data.get(giftNum);
        Tense tense = randomTense();

        boolean usePronouns = lo.pronouns;
        if (lo.pronouns && lo.nouns) {
            usePronouns = new Random().nextBoolean();
        }

        boolean useArticles = false;
        if (!usePronouns) {
            useArticles = new Random().nextBoolean();
        }
        GrammaticalPerson subject = GrammaticalPerson.random();

        //Parts of sentence ------------------------------------------------------------------------

        //Subject and object of the verb: Pronouns or names/professions; with/without the common article
        String subjectEn;
        String subjectGd;
        String objectEn;
        String objectGd;
        String objectGdAlt = "";
        if (usePronouns) {
            //subject of the verb
            subjectEn = subject.en_subj();
            subjectGd = subject.gd_subj();

            //object of the verb (including "to" / as the prepoisitional pronoun "do")
            GrammaticalPerson object = GrammaticalPerson.random();
            objectEn = object.en_obj();
            objectGd = object.gd_do();
        } else {
            subject = GrammaticalPerson.THIRD_SINGULAR_MALE;
            HashMap<String, String> subjectNoun;
            //subject of the verb
            if (useArticles) {
                subjectNoun = professionsVocabList.getRandomRow();
                subjectEn = "the " + subjectNoun.get("english");
                subjectGd = gg.commonArticle(subjectNoun.get("nom_sing"), "sg", "masc", GrammarGd.GrammaticalCase.NOMINAL);
            } else {
                subjectNoun = namesVocabList.getRandomRow();
                subjectEn = subjectNoun.get("english");
                subjectGd = subjectNoun.get("nom_sing");
            }

            //object of the verb
            HashMap<String, String> object;
            if (useArticles) {
                object = professionsVocabList.getRandomRow();
                objectEn = "the " + object.get("english");
                objectGd = gg.commonArticle(object.get("nom_sing"), "sg", "masc", GrammarGd.GrammaticalCase.NOMINAL);
                objectGd = GrammarGd.prepDo(objectGd, false);
                objectGdAlt = GrammarGd.prepDo(objectGd, true);
            } else {
                object = namesVocabList.getRandomRow();
                objectEn = object.get("english");
                objectGd = GrammarGd.prepDo(object.get("nom_sing"), false);
                objectGdAlt = GrammarGd.prepDo(object.get("nom_sing"), true);
            }
        }

        //The verb
        String giveGd;
        switch (tense) {
            case PAST:
                giveGd = gg.verbSimplePast("thig", SentenceType.POS_STATEMENT) + " " + subjectGd;
                break;
            case FUTURE:
                giveGd = gg.verbSimpleFuture("thig", SentenceType.POS_STATEMENT) + " " + subjectGd;
                break;
            default:
                giveGd = gg.verbalNoun("toirt", subjectGd, tense, SentenceType.POS_STATEMENT);
        }

        String giveEn = ge.transformVerb(giveEnMap, subject, tense, SentenceType.POS_STATEMENT);
        if (!usePronouns) {
            giveEn = giveEn.replaceAll("he ", subjectEn + " ");
        }

        String giftEn = ge.enIndefArticle(gift.get("english"));
        String giftGd = gift.get("nom_sing");


        //Construct sentences ----------------------------------------------------------------------
        String sentenceEn = capitalise(giveEn) + " " + giftEn + " to " + objectEn;
        String sentenceEnAlt = capitalise(giveEn) + " " + objectEn + " " + giftEn;
        String sentenceGd = capitalise(giveGd) + " " + giftGd + " " + objectGd;
        String sentenceGdAlt = "";
        if (!objectGdAlt.equals("")){
            sentenceGdAlt = capitalise(giveGd) + " " + giftGd + " " + objectGdAlt;
        }

        //Prompt -----------------------------------------------------------------------------------
        e.setPrePrompt("Translate:");

        if (lo.responseType == LessonOptions.ResponseType.BLANKS_PP) {
            String editTextPrompt;
            if (lo.translateFromGaelic){
                editTextPrompt = capitalise(giveEn) + " " + giftEn + " to ";
            } else {
                editTextPrompt = capitalise(giveGd) + " " + giftGd + " ";
            }
            e.setEditTextPrompt(editTextPrompt);
            e.setEditTextCursorPosition(editTextPrompt.length());
        } else if (lo.responseType == LessonOptions.ResponseType.BLANKS_VERB) {
            String editTextPrompt;
            if (lo.translateFromGaelic){
                editTextPrompt = capitalise(subjectEn) + "  " + giftEn + " to " + objectEn;
                e.setEditTextPrompt(editTextPrompt);
                e.setEditTextCursorPosition(subjectEn.length() + 1);
            } else {
                editTextPrompt = " " + giftGd + " " + objectGd;
                e.setEditTextPrompt(editTextPrompt);
                e.setEditTextCursorPosition(0);
            }
        }

        //Question ---------------------------------------------------------------------------------
        if (lo.translateFromGaelic){
            e.setQuestion(sentenceGd);
        } else {
            e.setQuestion(sentenceEn);
        }

        //Solutions --------------------------------------------------------------------------------
        if (lo.translateFromGaelic){
            e.addSolution(sentenceEn);
            e.addSolution(sentenceEnAlt);
        } else {
            e.addSolution(sentenceGd);
            if (!sentenceGdAlt.equals("")){
                e.addSolution(sentenceGdAlt);
            }
        }

        //Output -----------------------------------------------------------------------------------
        return e;
    }
}
