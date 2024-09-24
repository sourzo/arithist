package com.github.sourzo.a_rithist.english;

import com.github.sourzo.a_rithist.gaidhlig.GrammaticalPerson;
import com.github.sourzo.a_rithist.gaidhlig.SentenceType;
import com.github.sourzo.a_rithist.gaidhlig.Tense;
import com.github.sourzo.a_rithist.general.AndroidAppRes;
import com.github.sourzo.a_rithist.general.VocabTable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GrammarEn {
    //Setup ----------------------------------------------------------------------------------------
    AndroidAppRes appRes;
    public VocabTable en;
    /**Creates a new English Grammar instance. Requires context to load vocab tables.*/
    public GrammarEn(AndroidAppRes appRes){
        this.appRes = appRes;
        en = new VocabTable(appRes, "grammar_english.csv");
    }

    //Regular Expressions --------------------------------------------------------------------------
    /**Regular expression matching words which start with vowels (a, e, i, o, u).*/
    String startsWithVowels = "[aeiou].*";
    /**Regular expression matching words which have a vowel (a, e, i, o, u) as the penultimate letter.*/
    String penultimateVowel = ".*[aeiou].";

    //Helper methods -------------------------------------------------------------------------------
    public static boolean endsWith(String word, Collection<String> suffixSet){
        for (String suffix : suffixSet){
            if (word.endsWith(suffix)){
                return true;
            }
        }
        return false;
    }

    //Methods --------------------------------------------------------------------------------------
    /**dd a/an to a word (using vowels) Note, a/an is applied in English by sound rather than
     * spelling, so this will be wrong sometimes (eg "an unicorn")*/
    public String enIndefArticle(String word) {
        if (word.toLowerCase().matches(startsWithVowels)) {
            return "an " + word;
        } else {
            return "a " + word;
        }
    }

    public String pluralise(String word){
        Set<String> pluralSameAsSingular = new HashSet<>(Arrays.asList("sheep", "deer", "fish", "trousers", "glasses"));
        Set<String> ooToEe = new HashSet<>(Arrays.asList("tooth","foot", "goose"));
        Set<String> fWordExceptions = new HashSet<>(Arrays.asList("roof", "chef", "belief", "chief"));
        Set<String> sxz = new HashSet<>(Arrays.asList("s","x","z"));
        Set<String> shCh = new HashSet<>(Arrays.asList("sh","ch"));
        if (pluralSameAsSingular.contains(word)){
            return word;
        } else if (ooToEe.contains(word)){
            return word.charAt(0) + "ee" + word.substring(3);
        } else if (word.equals("mouse")){
            return "mice";
        } else if (word.endsWith("y") && word.matches(penultimateVowel)) {
            return word.substring(0, word.length()-1)  + "ies";
        } else if (word.endsWith("person")){
            return word.replace("person","people");
        } else if (word.endsWith("child")){
            return word + "ren";
        } else if (word.endsWith("man")){
            return word.substring(0,word.length()-3) + "men";
        } else if (word.endsWith("o") && word.matches(penultimateVowel)){
            return word + "es";
        } else if (word.endsWith("fe")){
            return word.substring(0,word.length()-2)+"ves";
        } else if (word.endsWith("f") && !fWordExceptions.contains(word)){
            return word.substring(0,word.length()-1) + "ves";
        } else if (endsWith(word,sxz) || endsWith(word,shCh) && !word.equals("stomach")) {
            return word + "es";
        } else {
            return word + "s";
        }
    }

    public String transformVerb(HashMap<String,String> verb,
                                GrammaticalPerson person,
                                Tense tense,
                                SentenceType sentenceType) {

        String neg = "";
        if (!sentenceType.isPositive){
            if (!sentenceType.isQuestion){
                neg = " not";
            } else {
                neg = "n't";
            }
        }

        if (sentenceType.isQuestion){
            if (tense.equals(Tense.PRESENT_VERBAL_NOUN)){
                if (person.equals(GrammaticalPerson.FIRST_SINGULAR) && !sentenceType.isPositive){
                    return "Aren't " + person.en_subj + " " + verb.get("en_vn");
                } else {
                    return en.filterMatches("en_subj", person.en_subj).get("be_pres",0) + neg + " " + person.en_subj + " " + verb.get("en_vn");
                }
            } else if (tense.equals(Tense.PAST)){
                return "Did" + neg + " " + person.en_subj + " " + verb.get("english");
            } else if (tense.equals(Tense.PAST_VERBAL_NOUN)){
                return en.filterMatches("en_subj", person.en_subj).get("be_past",0) + neg + " " + person.en_subj + " " + verb.get("en_vn");
            } else {
                String start;
                if (sentenceType.isPositive) {
                    start = "Will ";
                } else {
                    start = "Won't ";
                }
                if (tense.equals(Tense.FUTURE)) {
                    return start + person.en_subj + " " + verb.get("english");
                } else if (tense.equals(Tense.FUTURE_VERBAL_NOUN)){
                    return start + person.en_subj + " be " + verb.get("en_vn");
                }
            }
        } else {
            if (tense.equals(Tense.PRESENT_VERBAL_NOUN)){
                return person.en_subj + " " + en.filterMatches("en_subj", person.en_subj).get("be_pres",0) + neg + " " + verb.get("en_vn");
            } else if (tense.equals(Tense.PAST)){
                if (sentenceType.isPositive){
                    return person.en_subj + " " + verb.get("en_past");
                } else {
                    return person.en_subj + " did not " + verb.get("english");
                }
            } else if (tense.equals(Tense.PAST_VERBAL_NOUN)){
                return person.en_subj + " " +
                        en.filterMatches("en_subj", person.en_subj).get("be_past",0) +
                        neg + " " + verb.get("en_vn");
            } else if (tense.equals(Tense.FUTURE)){
                return person.en_subj + " will" + neg + " " + verb.get("english");
            } else if (tense.equals(Tense.FUTURE_VERBAL_NOUN)){
                return person.en_subj + " will" + neg + " be " + verb.get("en_vn");
            }
        }
        return "error in making english form of verb";
    }

    /** Conjugates the verb "to go" and returns the pronoun with the verb.
     * @return [person] + [verb: to go]*/
    public String toGo(GrammaticalPerson person, Tense tense) {
        switch(tense){
            case PAST:
                return person.en_subj + " went";
            case FUTURE:
                return person.en_subj + " will go";
            case FUTURE_VERBAL_NOUN:
                return person.en_subj + " will be going";
            case PAST_VERBAL_NOUN:
                return person.en_subj + " " + en.filterMatches("en_subj", person.en_subj).get(0,"be_past") + " going";
            case PRESENT_VERBAL_NOUN:
                return person.en_subj + " " + en.filterMatches("en_subj", person.en_subj).get(0,"be_pres") + " going";
        }
    return "error - toGo - tense not recognised";
    }
}
