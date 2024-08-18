package com.github.sourzo.a_rithist.gaidhlig.lessons;

import com.github.sourzo.a_rithist.english.GrammarEn;
import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;

import java.util.HashMap;
import java.util.Random;

public class PossessionMo extends ExerciseGenerator {
    GrammarGd gg;
    GrammarEn ge;

    /**Creates a new exercise generator. Requires context to load vocab tables.*/
    public PossessionMo(LessonOptions lo){
        super(lo);
        gg = new GrammarGd(lo.appRes);
        ge = new GrammarEn(lo.appRes);
    }

    public Exercise generate() {
        //Setup ------------------------------------------------------------------------------------
        Exercise e = new Exercise();
        int whatNum = new Random().nextInt(lo.sampleVocabList.size());
        int whereNum = new Random().nextInt(3);
        int whoseNum = new Random().nextInt(7);

        HashMap<String,String> randomWord = lo.sampleVocabList.data.get(whatNum);

        //Parts of sentence: Gaelic ----------------------------------------------------------------
        String gdWhat = "errorNoAssignment";
        if (whoseNum < 4){
            gdWhat = randomWord.get("nom_sing");
        } else {
            gdWhat = randomWord.get("nom_pl");
        }

        String gdWhere = "errorNoAssignment";
        switch (whereNum) {
            case 0:
                gdWhere = "seo";
                break;
            case 1:
                gdWhere = "sin";
                break;
            case 2:
                gdWhere = "siud";
                break;
            default:
                gdWhere = "error";
        }

        String gdWhoseWhat = "errorNoAssignment";

        //Special cases: Daughter and father don't use this form of possession, they use "aig".
        if (randomWord.get("possessive_compatible").equals("no")) {
            if (whoseNum < 4) {
                gdWhoseWhat = "an " + gdWhat + gg.pp.get(whoseNum,"aig");
            } else {
                gdWhoseWhat = "na " + gdWhat + gg.pp.get(whoseNum,"aig");
            }
        } else {
            //Not daughter/father
            gdWhoseWhat = gg.articlePossessive(gdWhat, whoseNum, true);
        }

        //Parts of sentence: English ---------------------------------------------------------------
        String enWhat = randomWord.get("english");

        String enBe = "errorNoAssignment";
        if (whoseNum >= 4){
            enWhat = ge.pluralise(enWhat);
            enBe = "are";
        } else {
            enBe = "is";
        }

        String enWhere = "errorNoAssignment";
        String enWhereAlt = "errorNoAssignment";
        switch (whereNum) {
            case 0:
                enWhere = "here";
                enWhereAlt = "this";
                break;
            case 1:
                enWhere = "there";
                enWhereAlt = "that";
                break;
            case 2:
                enWhere = "over there";
                enWhereAlt = "yonder";
                break;
            default:
                enWhere = "errorSwitchFail";
                enWhereAlt = "errorSwitchFail";
        }

        String enWhose = gg.pp.get(whoseNum, "en_poss");

        //Construct sentences ----------------------------------------------------------------------
        String sentenceEn = capitalise(enWhere) + " " + enBe + " " + enWhose + " " + enWhat;
        String sentenceGd = capitalise(gdWhere) + " " + gdWhoseWhat;

        //Prompt -----------------------------------------------------------------------------------
        e.setPrePrompt("Translate:");

        if (lo.sentenceType.equals("blanks")){
            if (lo.translateFromGaelic){
                String editTextPrompt = capitalise(enWhere) + " " + enBe + "  " + enWhat;
                e.setEditTextPrompt(editTextPrompt);
                e.setEditTextCursorPosition(enWhere.length() + enBe.length() + 2);
            } else {
                String editTextPrompt = capitalise(gdWhere) + " ";
                e.setEditTextPrompt(editTextPrompt);
                e.setEditTextCursorPosition(editTextPrompt.length());
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
        } else {
            e.addSolution(sentenceGd);
        }

        //Output -----------------------------------------------------------------------------------

        return e;
    }
}
