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
        lessonSet.put("a1_basic_vocab",
                new Lesson("Basic vocabulary",
                        new String[] {"english", "nom_sing"},
                        new Lesson.topicTags[] {Lesson.topicTags.VOCABULARY},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.TRANSLATE_WORDS, Lesson.lessonOptions.TRANSLATE, Lesson.lessonOptions.VOCABULARY},
                        BasicVocab::new));
        lessonSet.put("give_to",
                new Lesson("Giving To: the irregular verb 'thig' and the preposition / prepositional pronoun 'do'",
                        new String[] {"english", "nom_sing"},
                        new Lesson.topicTags[] {Lesson.topicTags.PREPOSITIONS, Lesson.topicTags.VERBS},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.CHOSEN_TENSE, Lesson.lessonOptions.PREP_OBJECT, Lesson.lessonOptions.RESPONSE_BLANKS_VERB, Lesson.lessonOptions.RESPONSE_BLANKS_PP, Lesson.lessonOptions.TRANSLATE_WORDS, Lesson.lessonOptions.TRANSLATE, Lesson.lessonOptions.VOCABULARY},
                        GiveTo::new));
        lessonSet.put("get_from",
                new Lesson("Getting From: the irregular verb 'faigh' and the preposition / prepositional pronoun 'bho'",
                        new String[] {"english", "nom_sing"},
                        new Lesson.topicTags[] {Lesson.topicTags.PREPOSITIONS, Lesson.topicTags.VERBS},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.CHOSEN_TENSE, Lesson.lessonOptions.PREP_OBJECT, Lesson.lessonOptions.RESPONSE_BLANKS_VERB, Lesson.lessonOptions.RESPONSE_BLANKS_PP, Lesson.lessonOptions.TRANSLATE_WORDS, Lesson.lessonOptions.TRANSLATE, Lesson.lessonOptions.VOCABULARY},
                        GetFrom::new));
        lessonSet.put("possession_aig",
                new Lesson("Possession using the prepositional pronoun 'aig'",
                        new String[] {"english", "nom_sing"},
                        new Lesson.topicTags[] {Lesson.topicTags.PREPOSITIONS},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.TRANSLATE_WORDS, Lesson.lessonOptions.TRANSLATE, Lesson.lessonOptions.RESPONSE_BLANKS, Lesson.lessonOptions.VOCABULARY},
                        PossessionAig::new));
        lessonSet.put("gender",
                new Lesson("Gender of nouns (using adjectives/articles)",
                        new String[] {"english", "nom_sing", "gender"},
                        new Lesson.topicTags[] {Lesson.topicTags.VOCABULARY, Lesson.topicTags.ADJECTIVES, Lesson.topicTags.ARTICLES},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.GENDER_MODE, Lesson.lessonOptions.VOCABULARY},
                        Gender::new));
        lessonSet.put("a4_numbers",
                new Lesson("Numbers",
                        new String[] {},
                        new Lesson.topicTags[] {Lesson.topicTags.NUMBERS, Lesson.topicTags.VOCABULARY},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.TRANSLATE_NUMBERS, Lesson.lessonOptions.TRANSLATE, Lesson.lessonOptions.MAX_NUM},
                        Numbers::new));
        lessonSet.put("a2_plurals",
                new Lesson("Plurals",
                        new String[] {"english", "nom_sing", "nom_pl"},
                        new Lesson.topicTags[] {Lesson.topicTags.VOCABULARY},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.TRANSLATE_GENERIC, Lesson.lessonOptions.TRANSLATE, Lesson.lessonOptions.VOCABULARY},
                        Plurals::new));
        lessonSet.put("preferences",
                new Lesson("Preferences (I would like/prefer etc) using the prepositional pronoun 'le'",
                        new String[] {"english", "nom_sing"},
                        new Lesson.topicTags[] {Lesson.topicTags.PREPOSITIONS},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.TRANSLATE_WORDS, Lesson.lessonOptions.TRANSLATE, Lesson.lessonOptions.RESPONSE_BLANKS, Lesson.lessonOptions.VOCABULARY},
                        Preferences::new));
        lessonSet.put("a3_verb_tenses",
                new Lesson("Verb tenses",
                        new String[] {"english", "en_past", "en_vn", "root", "verbal_noun"},
                        new Lesson.topicTags[] {Lesson.topicTags.VERBS, Lesson.topicTags.VOCABULARY},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.CHOSEN_TENSE, Lesson.lessonOptions.SENTENCE_TYPE, Lesson.lessonOptions.VOCABULARY},
                        VerbTenses::new));
        lessonSet.put("professions_annan",
                new Lesson("Professions: the prepositional pronoun 'ann an'",
                        new String[] {},
                        new Lesson.topicTags[] {Lesson.topicTags.PREPOSITIONS},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.TRANSLATE_WORDS, Lesson.lessonOptions.TRANSLATE, Lesson.lessonOptions.RESPONSE_BLANKS, Lesson.lessonOptions.VOCABULARY},
                        ProfessionsAnn::new));
        lessonSet.put("a8_emphasis_adjectives",
                new Lesson("Emphatic pronouns and adjectives",
                        new String[] {"english", "adj_gd"},
                        new Lesson.topicTags[] {Lesson.topicTags.ADJECTIVES},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.TRANSLATE_WORDS, Lesson.lessonOptions.TRANSLATE, Lesson.lessonOptions.VOCABULARY},
                        EmphasisAdjectives::new));
        lessonSet.put("possession_mo",
                new Lesson("Possession using the possessive articles 'mo', 'do', etc",
                        new String[] {"english", "nom_sing", "nom_pl", "possessive_compatible"},
                        new Lesson.topicTags[] {Lesson.topicTags.ARTICLES},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.TRANSLATE_WORDS, Lesson.lessonOptions.TRANSLATE, Lesson.lessonOptions.RESPONSE_BLANKS, Lesson.lessonOptions.VOCABULARY},
                        PossessionMo::new));
        lessonSet.put("where_from",
                new Lesson("Where are they from? - using the preposition 'à(s)'",
                        new String[] {"english", "place_gd", "gender"},
                        new Lesson.topicTags[] {Lesson.topicTags.PREPOSITIONS, Lesson.topicTags.PLACE},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.RESPONSE_QA, Lesson.lessonOptions.VOCABULARY},
                        WhereFrom::new));
        lessonSet.put("where_in",
                new Lesson("Where are they (in)? - using the preposition 'ann an'",
                        new String[] {"english", "nom_sing", "gender"},
                        new Lesson.topicTags[] {Lesson.topicTags.PREPOSITIONS, Lesson.topicTags.PLACE},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.RESPONSE_QA, Lesson.lessonOptions.VOCABULARY},
                        WhereIn::new));
        lessonSet.put("comparisons",
                new Lesson("Sayings: Comparisons",
                        new String[] {"english","simile_en","adj_gd","simile_gd"},
                        new Lesson.topicTags[] {Lesson.topicTags.ADJECTIVES},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.TRANSLATE_WORDS, Lesson.lessonOptions.TRANSLATE, Lesson.lessonOptions.VOCABULARY},
                        SayingsComparisons::new));
        lessonSet.put("a9_comparatives_superlatives",
                new Lesson("Adjectives: Comparatives and superlatives",
                        new String[] {"english", "nom_sing", "gender"},
                        new Lesson.topicTags[] {Lesson.topicTags.ADJECTIVES},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.VOCABULARY, Lesson.lessonOptions.COMP_SUP, Lesson.lessonOptions.RESPONSE_BLANKS, Lesson.lessonOptions.TRANSLATE_WORDS, Lesson.lessonOptions.TRANSLATE},
                        CompSup::new));
        lessonSet.put("a5_time",
                new Lesson("Time",
                        new String[] {},
                        new Lesson.topicTags[] {Lesson.topicTags.TIME},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.TRANSLATE_NUMBERS, Lesson.lessonOptions.TRANSLATE},
                        Time::new));
        lessonSet.put("a7_which_season",
                new Lesson("Which season? [Prepositions]",
                        new String[] {},
                        new Lesson.topicTags[] {Lesson.topicTags.PREPOSITIONS, Lesson.topicTags.DATETIME},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.RESPONSE_BLANKS},
                        Seasons::new));
        lessonSet.put("a6_which_month",
                new Lesson("Which month? [Prepositions]",
                        new String[] {},
                        new Lesson.topicTags[] {Lesson.topicTags.PREPOSITIONS, Lesson.topicTags.DATETIME},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.RESPONSE_BLANKS},
                        Months::new));
        lessonSet.put("going_to",
                new Lesson("Going to [place]: the irregular verb 'rach' and the preposition 'do' with place-names",
                        new String[] {"english", "place_gd"},
                        new Lesson.topicTags[] {Lesson.topicTags.PREPOSITIONS, Lesson.topicTags.VERBS, Lesson.topicTags.PLACE},
                        new Lesson.lessonOptions[] {Lesson.lessonOptions.CHOSEN_TENSE, Lesson.lessonOptions.TRANSLATE_WORDS, Lesson.lessonOptions.TRANSLATE, Lesson.lessonOptions.VOCABULARY},
                        GoingTo::new));
     }
}
