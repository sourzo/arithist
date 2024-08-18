package com.github.sourzo.a_rithist.english;

import com.github.sourzo.a_rithist.general.AndroidAppRes;
import com.github.sourzo.a_rithist.general.VocabTable;

import java.util.Arrays;
import java.util.Collection;
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

    //Definitions ----------------------------------------------------------------------------------
    Set<Character> vowels = new HashSet<>(Arrays.asList('a','e','i','o','u'));

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
        if (vowels.contains(word.toLowerCase().charAt(0))) {
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
        } else if (word.endsWith("y") && !vowels.contains(word.charAt(word.length()-2))) {
            return word.substring(0, word.length()-1)  + "ies";
        } else if (word.endsWith("person")){
            return word.replace("person","people");
        } else if (word.endsWith("child")){
            return word + "ren";
        } else if (word.endsWith("man")){
            return word.substring(0,word.length()-3) + "men";
        } else if (word.endsWith("o") && !vowels.contains(word.charAt(word.length()-2))){
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

}
