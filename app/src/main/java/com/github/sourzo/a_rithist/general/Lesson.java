package com.github.sourzo.a_rithist.general;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Lesson {
    //Fields ---------------------------------------------------------------------------------------
    public String displayName;
    public String[] requiredColumns;
    public TopicTag[] topics;
    public  LessonOptions[] options;
    public Function<com.github.sourzo.a_rithist.general.LessonOptions, ExerciseGenerator> getGenerator;


    //constructors----------------------------------------------------------------------------------
    public Lesson(String displayName,
                  String[] requiredColumns,
                  TopicTag[] topics,
                  LessonOptions[] options,
                  Function<com.github.sourzo.a_rithist.general.LessonOptions, ExerciseGenerator> getGenerator){
        this.displayName = displayName;
        this.requiredColumns = requiredColumns;
        this.topics = topics;
        this.options = options;
        this.getGenerator = getGenerator;
    }
    //nested classes--------------------------------------------------------------------------------
    public enum TopicTag {
        ALL("All lessons"),
        VOCABULARY("Vocabulary"),
        NUMBERS("Numbers"),
        DATETIME("Date & Time"),
        PREPOSITIONS("Prepositions"),
        ARTICLES("Articles"),
        VERBS("Verbs"),
        PLACE("Place"),
        ADJECTIVES("Adjectives");

        public final String label;

        private TopicTag(String label) {
            this.label = label;
        }

        private static final Map<String, TopicTag> BY_LABEL = new HashMap<>();
        static {
            for (TopicTag t: values()) {
                BY_LABEL.put(t.label, t);
            }
        }
        public static TopicTag valueOfLabel(String label) {
            return BY_LABEL.get(label);
        }

    }
    public enum LessonOptions {
        VOCABULARY,
        MAX_NUM,
        GENDER_MODE,
        COMP_SUP,
        CHOSEN_TENSE,
        SENTENCE_TYPE,
        TRANSLATE,
        TRANSLATE_WORDS,
        TRANSLATE_NUMBERS,
        TRANSLATE_GENERIC,
        RESPONSE_BLANKS,
        RESPONSE_BLANKS_VERB,
        RESPONSE_BLANKS_PP,
        RESPONSE_QA,
        PREP_OBJECT
    }
}
