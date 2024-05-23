package com.github.sourzo.a_rithist.english;

import com.github.sourzo.a_rithist.general.AndroidAppRes;
import com.github.sourzo.a_rithist.general.VocabTable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GrammarEn {
    //Setup ----------------------------------------------------------------------------------------
    AndroidAppRes appRes;
    public VocabTable en;
    /**Creates a new English Grammar instance. Requires context to load vocab tables.*/
    public GrammarEn(AndroidAppRes appRes){
        this.appRes = appRes;
        en = new VocabTable(appRes, "grammar_english.csv");
    }

    //Definitions ----------------------------------------------------------------------------------
    Set<Character> vowels = new HashSet<>(Arrays.asList('a','e','i','o','u'));

    //Methods --------------------------------------------------------------------------------------
    /**dd a/an to a word (using vowels) Note, a/an is applied in English by sound rather than
     * spelling, so this will be wrong sometimes (eg "an unicorn")*/
    public String enIndefArticle(String word) {
        if (vowels.contains(word.charAt(0))) {
            return "an " + word;
        } else {
            return "a " + word;
        }
    }



}
