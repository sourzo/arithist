package com.github.sourzo.a_rithist.gaidhlig;

import java.util.Random;

/**
 * Enumerated type: Grammatical Person (I, you, he, she, we, youse, they)
 */
public enum GrammaticalPerson {
    FIRST_SINGULAR(1, false, GrammarGd.GrammaticalGender.UNDETERMINED),
    SECOND_SINGULAR(2, false, GrammarGd.GrammaticalGender.UNDETERMINED),
    THIRD_SINGULAR_MALE(3, false, GrammarGd.GrammaticalGender.MASCULINE),
    THIRD_SINGULAR_FEMALE(3, false, GrammarGd.GrammaticalGender.FEMININE),
    FIRST_PLURAL(1, true, GrammarGd.GrammaticalGender.UNDETERMINED),
    SECOND_PLURAL(2, true, GrammarGd.GrammaticalGender.UNDETERMINED),
    THIRD_PLURAL(3, true, GrammarGd.GrammaticalGender.UNDETERMINED);

    final public boolean isPlural;
    final public int ordinal;
    final public GrammarGd.GrammaticalGender gender;
    final private static Random PRNG = new Random();

    GrammaticalPerson(int ordinal, boolean isPlural, GrammarGd.GrammaticalGender gender) {
        this.ordinal = ordinal;
        this.isPlural = isPlural;
        this.gender = gender;
    }

    public static GrammaticalPerson random() {
        GrammaticalPerson[] gpArray = values();
        return gpArray[PRNG.nextInt(gpArray.length)];
    }

}
