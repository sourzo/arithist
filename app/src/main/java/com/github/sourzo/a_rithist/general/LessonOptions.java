package com.github.sourzo.a_rithist.general;

import com.github.sourzo.a_rithist.gaidhlig.LessonInfo;

import java.io.Serializable;

public class LessonOptions implements Serializable {
    public transient AndroidAppRes appRes;
    /**The ID of the chosen lesson*/
    public String lessonID;
    /**The name of the chosen vocabulary list*/
    public String vocabListName;
    /**A subset of the original vocab list: A random sample of words
     * @see #vocabListSize*/
    public VocabTable sampleVocabList;
    /**The size of the vocabulary list, as chosen by the user*/
    public int vocabListSize;
    /**Required prefix for a vocab list. Used to filter for things like "places"*/
    public String vocabListPrefix;
    /**For lessons involving numbers, this is the largest number to include*/
    public int largestNumber;
    /**If true: User will be presented with Gaelic. User's answer would usually be in English.
     * If false: User will be presented with English/digits, and must respond in Gaelic.*/
    public boolean translateFromGaelic;
    /**Whether the user must respond by filling in the blank(s), or by writing a full sentence,
     * or by answering a question (with the prompted response)*/
    public ResponseType responseType;
    /**Whether to test the user's knowledge of the gender of nouns by asking them to add an adjective*/
    public boolean genderAdj;
    /**Whether to test the user's knowledge of the gender of nouns by asking them to use the definite
     * article ("the") in the nominative case*/
    public boolean genderDefArtNom;
    /**Whether to include comparatives ("a fox is smarter than a fish")*/
    public boolean comparatives;
    /**Whether to include superlatives ("the smartest fox")*/
    public boolean superlatives;
    /**Whether to include the past tense*/
    public boolean past;
    /**Whether to include the present tense*/
    public boolean present;
    /**Whether to include the future tense*/
    public boolean future;
    /**Whether to include positive statements*/
    public boolean posStatements;
    /**Whether to include negative statements*/
    public boolean negStatements;
    /**Whether to include positive questions*/
    public boolean posQuestions;
    /**Whether to include negative questions*/
    public boolean negQuestions;
    /**Whether to use pronouns*/
    public boolean pronouns;
    /**Whether to use nouns*/
    public boolean nouns;
    /**Whether the user's response should be considered incorrect if they don't have the right accents*/
    public boolean checkAccents;

    public enum ResponseType {
        BLANKS,
        BLANKS_VERB,
        BLANKS_PP,
        FULL_SENTENCE,
        Q_AND_A
    }


}
