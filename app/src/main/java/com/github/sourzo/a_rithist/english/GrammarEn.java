package com.github.sourzo.a_rithist.english;

import android.content.Context;

import com.github.sourzo.a_rithist.LessonActivity;
import com.github.sourzo.a_rithist.general.VocabTable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GrammarEn {
    //Setup ----------------------------------------------------------------------------------------
    LessonActivity la;
    public VocabTable en;
    /**Creates a new English Grammar instance. Requires context to load vocab tables.*/
    public GrammarEn(LessonActivity la){
        this.la = la;
        en = new VocabTable(la, "grammar_english.csv");
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
