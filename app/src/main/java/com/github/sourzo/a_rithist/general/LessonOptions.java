package com.github.sourzo.a_rithist.general;

import java.io.Serializable;

public class LessonOptions implements Serializable {
    public transient AndroidAppRes androidAppRes;
    public String lessonID;
    public String vocabListName;
    public VocabTable sampleVocabList;
    public int vocabListSize;
    public int largestNumber;
    public boolean translateFromGaelic;
    public String sentenceType;
    public String genderType;
    public boolean comparatives;
    public boolean superlatives;
    public boolean past;
    public boolean present;
    public boolean future;
    public boolean posStatements;
    public boolean negStatements;
    public boolean posQuestions;
    public boolean negQuestions;
    public boolean pronouns;
    public boolean nouns;
    public boolean checkAccents;

}
