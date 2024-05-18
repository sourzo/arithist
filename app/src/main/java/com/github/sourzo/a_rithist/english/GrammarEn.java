package com.github.sourzo.a_rithist.english;

import android.content.Context;

import com.github.sourzo.a_rithist.VocabTable;

public class GrammarEn {
    //Setup ----------------------------------------------------------------------------------------
    Context c;
    /**Creates a new English Grammar instance. Requires context to load vocab tables.*/
    public GrammarEn(Context c){
        this.c = c;
    }

    // Definitions ---------------------------------------------------------------------------------
    VocabTable en = new VocabTable(c, "grammar_english.csv");



}
