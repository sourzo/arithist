package com.github.sourzo.a_rithist.gaidhlig;

import java.util.Random;

/**
 * Enumerated type: Grammatical Person (I, you, he, she, we, youse, they)
 */
public enum GrammaticalPerson {
    FIRST_SINGULAR(1, false, GrammarGd.GrammaticalGender.UNDETERMINED, "I", "mi"),
    SECOND_SINGULAR(2, false, GrammarGd.GrammaticalGender.UNDETERMINED, "you (sg)", "thu"),
    THIRD_SINGULAR_MALE(3, false, GrammarGd.GrammaticalGender.MASCULINE, "he", "e"),
    THIRD_SINGULAR_FEMALE(3, false, GrammarGd.GrammaticalGender.FEMININE, "she", "i"),
    FIRST_PLURAL(1, true, GrammarGd.GrammaticalGender.UNDETERMINED, "we", "sinn"),
    SECOND_PLURAL(2, true, GrammarGd.GrammaticalGender.UNDETERMINED, "you (pl)", "sibh"),
    THIRD_PLURAL(3, true, GrammarGd.GrammaticalGender.UNDETERMINED, "they", "iad");

    final public boolean isPlural;
    final public int ordinalNum;
    final public GrammarGd.GrammaticalGender gender;
    final public String en_subj;
    final public String gd_subj;
    final private static Random numGen = new Random();

    GrammaticalPerson(int ordinalNum, boolean isPlural, GrammarGd.GrammaticalGender gender, String en_subj, String gd_subj) {
        this.ordinalNum = ordinalNum;
        this.isPlural = isPlural;
        this.gender = gender;
        this.en_subj = en_subj;
        this.gd_subj = gd_subj;
    }

    public String en_obj() {
        switch (this){
            case FIRST_SINGULAR:
                return "me";
            case SECOND_SINGULAR:
                return "you (sg)";
            case THIRD_SINGULAR_FEMALE:
                return "her";
            case THIRD_SINGULAR_MALE:
                return "him";
            case THIRD_PLURAL:
                return "them";
            case FIRST_PLURAL:
                return "us";
            case SECOND_PLURAL:
                return "you (pl)";
            default: return "error - gram.pers.en_obj";

        }
    }

    public GrammaticalPerson getOpposite(){
        switch (this){
            case FIRST_SINGULAR:
                return SECOND_SINGULAR;
            case SECOND_SINGULAR:
                return FIRST_SINGULAR;
            case THIRD_SINGULAR_FEMALE:
            case THIRD_SINGULAR_MALE:
            case THIRD_PLURAL:
                return this;
            case FIRST_PLURAL:
                return SECOND_PLURAL;
            case SECOND_PLURAL:
                return FIRST_PLURAL;
        }
        return FIRST_PLURAL; //Won't happen
    }
    public static GrammaticalPerson random() {
        GrammaticalPerson[] gpArray = values();
        return gpArray[numGen.nextInt(gpArray.length)];
    }

}
