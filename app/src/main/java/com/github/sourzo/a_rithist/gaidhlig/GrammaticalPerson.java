package com.github.sourzo.a_rithist.gaidhlig;

import com.github.sourzo.a_rithist.general.VocabTable;

import java.util.Random;

/**
 * Enumerated type: Grammatical Person (I, you, he, she, we, youse, they)
 */
public enum GrammaticalPerson {
    FIRST_SINGULAR(1, false, GrammarGd.GrammaticalGender.UNDETERMINED, "I"),
    SECOND_SINGULAR(2, false, GrammarGd.GrammaticalGender.UNDETERMINED, "you (sg)"),
    THIRD_SINGULAR_MALE(3, false, GrammarGd.GrammaticalGender.MASCULINE, "he"),
    THIRD_SINGULAR_FEMALE(3, false, GrammarGd.GrammaticalGender.FEMININE, "she"),
    FIRST_PLURAL(1, true, GrammarGd.GrammaticalGender.UNDETERMINED, "we"),
    SECOND_PLURAL(2, true, GrammarGd.GrammaticalGender.UNDETERMINED, "you (pl)"),
    THIRD_PLURAL(3, true, GrammarGd.GrammaticalGender.UNDETERMINED, "they");

    final public boolean isPlural;
    final public int ordinalNum;
    final public GrammarGd.GrammaticalGender gender;
    final public String en_subj;
    final private static Random PRNG = new Random();

    GrammaticalPerson(int ordinalNum, boolean isPlural, GrammarGd.GrammaticalGender gender, String en_subj) {
        this.ordinalNum = ordinalNum;
        this.isPlural = isPlural;
        this.gender = gender;
        this.en_subj = en_subj;
    }

    public static GrammaticalPerson random() {
        GrammaticalPerson[] gpArray = values();
        return gpArray[PRNG.nextInt(gpArray.length)];
    }

}
