package com.github.sourzo.a_rithist.gaidhlig;

import java.util.Random;

/**
 * Enumerated type: Grammatical Person (I, you, he, she, we, youse, they)
 */
public enum GrammaticalPerson {
    FIRST_SINGULAR,
    SECOND_SINGULAR,
    THIRD_SINGULAR_MALE,
    THIRD_SINGULAR_FEMALE,
    FIRST_PLURAL,
    SECOND_PLURAL,
    THIRD_PLURAL;

    final private static Random numGen = new Random();

    //Methods: General -----------------------------------------------------------------------------

    GrammaticalPerson() {
    }

    public boolean isPlural() {
        switch(this) {
            case THIRD_PLURAL:
            case FIRST_PLURAL:
            case SECOND_PLURAL:
                return true;
            default:
                return false;
        }
    }
    public GrammarGd.GrammaticalGender getGender(){
        switch (this){
            case THIRD_SINGULAR_FEMALE:
                return GrammarGd.GrammaticalGender.FEMININE;
            case THIRD_SINGULAR_MALE:
                return GrammarGd.GrammaticalGender.MASCULINE;
            default:
                return GrammarGd.GrammaticalGender.UNDETERMINED;
        }
    }

    public int getOrdinal(){
        switch (this){
            case FIRST_SINGULAR:
            case FIRST_PLURAL:
                return 1;
            case SECOND_SINGULAR:
            case SECOND_PLURAL:
                return 2;
            default:
                return 3;
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

    //English: Pronouns ----------------------------------------------------------------------------
    /**English: The pronoun as the object of the verb*/
    public String en_obj() {
        switch (this){
            case FIRST_SINGULAR:
                return "me";
            case SECOND_SINGULAR:
                return "you (sg)";
            case THIRD_SINGULAR_MALE:
                return "him";
            case THIRD_SINGULAR_FEMALE:
                return "her";
            case FIRST_PLURAL:
                return "us";
            case SECOND_PLURAL:
                return "you (pl)";
            case THIRD_PLURAL:
                return "them";
            default: return "error - gram.pers.en_obj";

        }
    }
    /**English: The pronoun as the subject of the verb*/
    public String en_subj() {
        switch (this){
            case FIRST_SINGULAR:
                return "I";
            case SECOND_SINGULAR:
                return "you (sg)";
            case THIRD_SINGULAR_MALE:
                return "he";
            case THIRD_SINGULAR_FEMALE:
                return "she";
            case FIRST_PLURAL:
                return "we";
            case SECOND_PLURAL:
                return "you (pl)";
            case THIRD_PLURAL:
                return "they";
            default: return "error - gram.pers.en_obj";

        }
    }

    /**English: The possessive pronoun */
    public String en_poss() {
        switch (this){
            case FIRST_SINGULAR:
                return "my";
            case SECOND_SINGULAR:
                return "your (sg)";
            case THIRD_SINGULAR_MALE:
                return "his";
            case THIRD_SINGULAR_FEMALE:
                return "her";
            case FIRST_PLURAL:
                return "our";
            case SECOND_PLURAL:
                return "your (pl)";
            case THIRD_PLURAL:
                return "their";
            default: return "error - gram.pers.en_obj";

        }
    }
    //English: Common irregular verbs --------------------------------------------------------------
    /**The verb "to be" */
    public String en_toBe(Tense tense) {
        switch (tense) {
            case PRESENT_VERBAL_NOUN:
                switch (this) {
                    case FIRST_SINGULAR:
                        return "am";
                    case THIRD_SINGULAR_MALE:
                    case THIRD_SINGULAR_FEMALE:
                        return "is";
                    default:
                        return "are";
                }
            case PAST:
            case PAST_VERBAL_NOUN:
            {
                switch (this) {
                    case FIRST_SINGULAR:
                    case THIRD_SINGULAR_MALE:
                    case THIRD_SINGULAR_FEMALE:
                        return "was";
                    default:
                        return "were";
                }
            }
            case FUTURE:
            case FUTURE_VERBAL_NOUN:
            {
                return "will be";
            }
            default:
                return "tense not recognised";
        }
    }

    //Gaelic: Pronouns -----------------------------------------------------------------------------
    /**Gaelic: The pronoun as the subject of the verb*/
    public String gd_subj() {
        switch (this){
            case FIRST_SINGULAR:
                return "mi";
            case SECOND_SINGULAR:
                return "thu";
            case THIRD_SINGULAR_MALE:
                return "e";
            case THIRD_SINGULAR_FEMALE:
                return "i";
            case FIRST_PLURAL:
                return "sinn";
            case SECOND_PLURAL:
                return "sibh";
            case THIRD_PLURAL:
                return "iad";
            default: return "error - gram.pers.gd_subj";

        }
    }


    /**The emphatic pronoun*/
    public String gd_emph() {
        switch (this) {
            case FIRST_SINGULAR:
                return "mise";
            case SECOND_SINGULAR:
                return "thusa";
            case THIRD_SINGULAR_MALE:
                return "esan";
            case THIRD_SINGULAR_FEMALE:
                return "ise";
            case FIRST_PLURAL:
                return "sinne";
            case SECOND_PLURAL:
                return "sibhse";
            case THIRD_PLURAL:
                return "iadsan";
            default:
                return "error - gram.pers.gd_bho";
        }
    }

    //Gaelic: Prepositional pronouns ---------------------------------------------------------------
    /**The prepositional pronoun "do" (to)*/
    public String gd_do() {
        switch (this) {
            case FIRST_SINGULAR:
                return "dhomh";
            case SECOND_SINGULAR:
                return "dhut";
            case THIRD_SINGULAR_MALE:
                return "dha";
            case THIRD_SINGULAR_FEMALE:
                return "dhi";
            case FIRST_PLURAL:
                return "dhuinn";
            case SECOND_PLURAL:
                return "dhuibh";
            case THIRD_PLURAL:
                return "dhaibh";
            default:
                return "error - gram.pers.gd_do";

        }
    }

    /**The prepositional pronoun "bho" (from)*/
    public String gd_bho() {
        switch (this) {
            case FIRST_SINGULAR:
                return "bhuam";
            case SECOND_SINGULAR:
                return "bhuat";
            case THIRD_SINGULAR_MALE:
                return "bhuaithe";
            case THIRD_SINGULAR_FEMALE:
                return "bhuaipe";
            case FIRST_PLURAL:
                return "bhuainn";
            case SECOND_PLURAL:
                return "bhuaibh";
            case THIRD_PLURAL:
                return "bhuapa";
            default:
                return "error - gram.pers.gd_bho";
        }
    }
    /**The prepositional pronoun "le" (with)*/
    public String gd_le() {
        switch (this) {
            case FIRST_SINGULAR:
                return "leum";
            case SECOND_SINGULAR:
                return "leat";
            case THIRD_SINGULAR_MALE:
                return "leis";
            case THIRD_SINGULAR_FEMALE:
                return "leatha";
            case FIRST_PLURAL:
                return "leinn";
            case SECOND_PLURAL:
                return "leibh";
            case THIRD_PLURAL:
                return "leotha";
            default:
                return "error - gram.pers.gd_le";
        }
    }
    /**The prepositional pronoun "aig" (have)*/
    public String gd_aig() {
        switch (this) {
            case FIRST_SINGULAR:
                return "agam";
            case SECOND_SINGULAR:
                return "agad";
            case THIRD_SINGULAR_MALE:
                return "aige";
            case THIRD_SINGULAR_FEMALE:
                return "aice";
            case FIRST_PLURAL:
                return "againn";
            case SECOND_PLURAL:
                return "agaibh";
            case THIRD_PLURAL:
                return "aca";
            default:
                return "error - gram.pers.gd_aig";
        }
    }
    /**The prepositional pronoun "ann"*/
    public String gd_ann() {
        switch (this) {
            case FIRST_SINGULAR:
                return "annam";
            case SECOND_SINGULAR:
                return "annad";
            case THIRD_SINGULAR_MALE:
                return "ann";
            case THIRD_SINGULAR_FEMALE:
                return "innte";
            case FIRST_PLURAL:
                return "annainn";
            case SECOND_PLURAL:
                return "annaibh";
            case THIRD_PLURAL:
                return "annta";
            default:
                return "error - gram.pers.gd_ann";
        }
    }



}
