package com.github.sourzo.a_rithist.general;

import com.github.sourzo.a_rithist.LessonActivity;
import com.github.sourzo.a_rithist.general.Generatable;

import java.util.function.Function;

public class Lesson {
    //Fields ---------------------------------------------------------------------------------------
    public String displayName;
    public String[] requiredColumns;
    public topicTags[] topics;
    public  lessonOptions[] options;
    public Function<LessonActivity, Generatable> getGenerator;


    //constructors----------------------------------------------------------------------------------
    public Lesson(String displayName,
                  String[] requiredColumns,
                  topicTags[] topics,
                  lessonOptions[] options,
                  Function<LessonActivity, Generatable> getGenerator){
        this.displayName = displayName;
        this.requiredColumns = requiredColumns;
        this.topics = topics;
        this.options = options;
        this.getGenerator = getGenerator;
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
        PREP_OBJECT
    }
}
