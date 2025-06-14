package com.github.sourzo.a_rithist.gaidhlig;

import com.github.sourzo.a_rithist.gaidhlig.lessons.BasicVocab;
import com.github.sourzo.a_rithist.gaidhlig.lessons.CompSup;
import com.github.sourzo.a_rithist.gaidhlig.lessons.GetFrom;
import com.github.sourzo.a_rithist.gaidhlig.lessons.GiveTo;
import com.github.sourzo.a_rithist.gaidhlig.lessons.Months;
import com.github.sourzo.a_rithist.gaidhlig.lessons.SayingsComparisons;
import com.github.sourzo.a_rithist.gaidhlig.lessons.EmphasisAdjectives;
import com.github.sourzo.a_rithist.gaidhlig.lessons.Gender;
import com.github.sourzo.a_rithist.gaidhlig.lessons.GoingTo;
import com.github.sourzo.a_rithist.gaidhlig.lessons.Numbers;
import com.github.sourzo.a_rithist.gaidhlig.lessons.Plurals;
import com.github.sourzo.a_rithist.gaidhlig.lessons.PossessionAig;
import com.github.sourzo.a_rithist.gaidhlig.lessons.PossessionMo;
import com.github.sourzo.a_rithist.gaidhlig.lessons.Preferences;
import com.github.sourzo.a_rithist.gaidhlig.lessons.ProfessionsAnn;
import com.github.sourzo.a_rithist.gaidhlig.lessons.Seasons;
import com.github.sourzo.a_rithist.gaidhlig.lessons.Time;
import com.github.sourzo.a_rithist.gaidhlig.lessons.VerbTenses;
import com.github.sourzo.a_rithist.gaidhlig.lessons.WhereFrom;
import com.github.sourzo.a_rithist.gaidhlig.lessons.WhereIn;
import com.github.sourzo.a_rithist.general.Lesson;

import java.util.TreeMap;

public class LessonInfo {
    public static TreeMap<String, Lesson> lessonSet = new TreeMap<>();
    static {
        lessonSet.put("basic_vocab",
                new Lesson("Basic vocabulary",
                        new String[] {"english", "nom_sing"},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.VOCABULARY},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.TRANSLATE_WORDS, Lesson.LessonOptions.TRANSLATE, Lesson.LessonOptions.VOCABULARY},
                        BasicVocab::new));
        lessonSet.put("give_to",
                new Lesson("Giving To ('thig', 'do')",
                        new String[] {"english", "nom_sing"},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.PREPOSITIONS, Lesson.TopicTag.VERBS},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.CHOSEN_TENSE, Lesson.LessonOptions.PREP_OBJECT, Lesson.LessonOptions.RESPONSE_BLANKS_VERB, Lesson.LessonOptions.RESPONSE_BLANKS_PP, Lesson.LessonOptions.TRANSLATE_WORDS, Lesson.LessonOptions.TRANSLATE, Lesson.LessonOptions.VOCABULARY},
                        GiveTo::new));
        lessonSet.put("get_from",
                new Lesson("Getting From ('faigh', 'bho')",
                        new String[] {"english", "nom_sing"},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.PREPOSITIONS, Lesson.TopicTag.VERBS},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.CHOSEN_TENSE, Lesson.LessonOptions.PREP_OBJECT, Lesson.LessonOptions.RESPONSE_BLANKS_VERB, Lesson.LessonOptions.RESPONSE_BLANKS_PP, Lesson.LessonOptions.TRANSLATE_WORDS, Lesson.LessonOptions.TRANSLATE, Lesson.LessonOptions.VOCABULARY},
                        GetFrom::new));
        lessonSet.put("possession_aig",
                new Lesson("Possession ('aig')",
                        new String[] {"english", "nom_sing"},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.PREPOSITIONS},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.TRANSLATE_WORDS, Lesson.LessonOptions.TRANSLATE, Lesson.LessonOptions.RESPONSE_BLANKS, Lesson.LessonOptions.VOCABULARY},
                        PossessionAig::new));
        lessonSet.put("gender",
                new Lesson("Gender of nouns",
                        new String[] {"english", "nom_sing", "gender"},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.VOCABULARY, Lesson.TopicTag.ADJECTIVES, Lesson.TopicTag.ARTICLES},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.GENDER_MODE, Lesson.LessonOptions.VOCABULARY},
                        Gender::new));
        lessonSet.put("numbers",
                new Lesson("Cardinal numbers",
                        new String[] {},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.NUMBERS, Lesson.TopicTag.VOCABULARY},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.TRANSLATE_NUMBERS, Lesson.LessonOptions.TRANSLATE, Lesson.LessonOptions.MAX_NUM},
                        Numbers::new));
        lessonSet.put("plurals",
                new Lesson("Plurals",
                        new String[] {"english", "nom_sing", "nom_pl"},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.VOCABULARY},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.TRANSLATE_GENERIC, Lesson.LessonOptions.TRANSLATE, Lesson.LessonOptions.VOCABULARY},
                        Plurals::new));
        lessonSet.put("preferences",
                new Lesson("Preferences ('le')",
                        new String[] {"english", "nom_sing"},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.PREPOSITIONS},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.TRANSLATE_WORDS, Lesson.LessonOptions.TRANSLATE, Lesson.LessonOptions.RESPONSE_BLANKS, Lesson.LessonOptions.VOCABULARY},
                        Preferences::new));
        lessonSet.put("verb_tenses",
                new Lesson("Verb tenses",
                        new String[] {"english", "en_past", "en_vn", "root", "verbal_noun"},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.VERBS, Lesson.TopicTag.VOCABULARY},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.CHOSEN_TENSE, Lesson.LessonOptions.SENTENCE_TYPE, Lesson.LessonOptions.VOCABULARY},
                        VerbTenses::new));
        lessonSet.put("professions_annan",
                new Lesson("Professions: ('ann an')",
                        new String[] {},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.PREPOSITIONS},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.TRANSLATE_WORDS, Lesson.LessonOptions.TRANSLATE, Lesson.LessonOptions.RESPONSE_BLANKS, Lesson.LessonOptions.VOCABULARY},
                        ProfessionsAnn::new));
        lessonSet.put("emphasis_adjectives",
                new Lesson("Emphatic pronouns and adjectives",
                        new String[] {"english", "adj_gd"},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.ADJECTIVES},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.TRANSLATE_WORDS, Lesson.LessonOptions.TRANSLATE, Lesson.LessonOptions.VOCABULARY},
                        EmphasisAdjectives::new));
        lessonSet.put("possession_mo",
                new Lesson("Possession ('mo', 'do', etc)",
                        new String[] {"english", "nom_sing", "nom_pl", "possessive_compatible"},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.ARTICLES},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.TRANSLATE_WORDS, Lesson.LessonOptions.TRANSLATE, Lesson.LessonOptions.RESPONSE_BLANKS, Lesson.LessonOptions.VOCABULARY},
                        PossessionMo::new));
        lessonSet.put("where_from",
                new Lesson("Where are they from? ('à'/'às')'",
                        new String[] {"english", "place_gd", "gender"},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.PREPOSITIONS, Lesson.TopicTag.PLACE},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.RESPONSE_QA, Lesson.LessonOptions.VOCABULARY},
                        WhereFrom::new));
        lessonSet.put("where_in",
                new Lesson("Where are they? ('ann an')",
                        new String[] {"english", "nom_sing", "gender"},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.PREPOSITIONS, Lesson.TopicTag.PLACE},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.RESPONSE_QA, Lesson.LessonOptions.VOCABULARY},
                        WhereIn::new));
        lessonSet.put("comparisons",
                new Lesson("Sayings: Comparisons",
                        new String[] {"english","simile_en","adj_gd","simile_gd"},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.ADJECTIVES},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.TRANSLATE_WORDS, Lesson.LessonOptions.TRANSLATE, Lesson.LessonOptions.VOCABULARY},
                        SayingsComparisons::new));
        lessonSet.put("comparatives_superlatives",
                new Lesson("Comparatives and superlatives",
                        new String[] {"english", "nom_sing", "gender"},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.ADJECTIVES},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.VOCABULARY, Lesson.LessonOptions.COMP_SUP, Lesson.LessonOptions.RESPONSE_BLANKS, Lesson.LessonOptions.TRANSLATE_WORDS, Lesson.LessonOptions.TRANSLATE},
                        CompSup::new));
        lessonSet.put("time",
                new Lesson("Time",
                        new String[] {},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.DATETIME},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.TRANSLATE_NUMBERS, Lesson.LessonOptions.TRANSLATE},
                        Time::new));
        lessonSet.put("which_season",
                new Lesson("Which season?",
                        new String[] {},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.PREPOSITIONS, Lesson.TopicTag.DATETIME},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.RESPONSE_BLANKS},
                        Seasons::new));
        lessonSet.put("which_month",
                new Lesson("Which month?",
                        new String[] {},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.PREPOSITIONS, Lesson.TopicTag.DATETIME},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.RESPONSE_BLANKS},
                        Months::new));
        lessonSet.put("going_to",
                new Lesson("Going to ('rach', 'do')",
                        new String[] {"english", "place_gd"},
                        new Lesson.TopicTag[] {Lesson.TopicTag.ALL, Lesson.TopicTag.PREPOSITIONS, Lesson.TopicTag.VERBS, Lesson.TopicTag.PLACE},
                        new Lesson.LessonOptions[] {Lesson.LessonOptions.CHOSEN_TENSE, Lesson.LessonOptions.TRANSLATE_WORDS, Lesson.LessonOptions.TRANSLATE, Lesson.LessonOptions.VOCABULARY},
                        GoingTo::new));
     }
}
