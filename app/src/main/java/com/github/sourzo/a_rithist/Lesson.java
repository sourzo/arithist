package com.github.sourzo.a_rithist;

import android.content.Context;

import java.util.function.Function;

public class Lesson {
    //Fields ---------------------------------------------------------------------------------------
    String displayName;
    String[] requiredColumns;
    topicTags[] topics;
    lessonOptions[] options;
    Function<Context, Generatable> generator;


    //constructors----------------------------------------------------------------------------------
    public Lesson(String displayName,
                  String[] requiredColumns,
                  topicTags[] topics,
                  lessonOptions[] options,
                  Function<Context, Generatable> generator){
        this.displayName = displayName;
        this.requiredColumns = requiredColumns;
        this.topics = topics;
        this.options = options;
        this.generator = generator;
    }
    //nested classes--------------------------------------------------------------------------------
    public enum topicTags{
        VOCABULARY,
        NUMBERS,
        DATETIME,
        TIME,
        PREPOSITIONS,
        ARTICLES,
        VERBS,
        PLACE,
        ADJECTIVES
        ;
    }
    public enum lessonOptions{
        VOCABULARY,
        MAX_NUM,
        GENDER_MODE,
        COMP_SUP,
        CHOSEN_TENSE,
        VERB_FORM,
        TRANSLATE,
        TRANSLATE_WORDS,
        TRANSLATE_NUMBERS,
        TRANSLATE_GENERIC,
        SENTENCE,
        SENTENCE_QA,
        PREP_OBJECT;
    }
}
