package com.github.sourzo.a_rithist.gaidhlig;

import android.content.Context;

import com.github.sourzo.a_rithist.LessonActivity;
import com.github.sourzo.a_rithist.VocabTable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GrammarGd {

    //Setup -------------------------------------------------------------------
    LessonActivity lessonActivity;
    /**Creates a new Gaelic Grammar instance. Requires context to load vocab tables.*/
    public GrammarGd(LessonActivity lessonActivity){
        this.lessonActivity = lessonActivity;
        pp = new VocabTable(lessonActivity,"grammar_prepPronouns.csv");
        names = new VocabTable(lessonActivity,"people_names.csv");
        numbers = new VocabTable(lessonActivity,"grammar_numbers.csv");
        professions = new VocabTable(lessonActivity,"people_professions.csv");
        similes = new VocabTable(lessonActivity,"adjectives_comparisons.csv");
        adjectives = new VocabTable(lessonActivity,"adjectives_misc.csv");
        list_months = new VocabTable(lessonActivity,"datetime_months.csv");
        list_seasons = new VocabTable(lessonActivity,"datetime_seasons.csv");
        list_holidays = new VocabTable(lessonActivity,"datetime_holidays.csv");
    }

    //Vocabulary/grammar files ------------------------------------------------
    VocabTable pp;
    VocabTable names;
    VocabTable numbers;
    VocabTable professions;
    VocabTable similes;
    VocabTable adjectives;
    VocabTable list_months;
    VocabTable list_seasons;
    VocabTable list_holidays;


    //Definitions--------------------------------------------------------------
    /**The largest number which the code is capable of translating!*/
    public static int largestTranslatableNumber = 100;

    /**Enumerated type: BROAD (a, o, u) or SLENDER (e, i)*/
    private enum grammaticalWidth {
        BROAD,
        SLENDER
    }

    /**Enumerated type: The grammatical gender (MASCULINE or FEMININE)*/
    private enum grammaticalGender {
        MASCULINE,
        FEMININE
    }
    /**Set of broad vowels, including accents*/
    private final static Set<String> broadVowels = new HashSet<>(Arrays.asList("a", "à", "á", "o", "ò", "ó", "u", "ù", "ú"));
    /**Set of slender vowels, including accents*/
    private final static Set<String> slenderVowels = new HashSet<>(Arrays.asList("e", "è", "é", "i", "ì", "í"));
    /**Set containing all vowels, including accents*/
    private final static Set<String> vowels = new HashSet<>(broadVowels);//slender vowels added below
    static {vowels.addAll(slenderVowels);}
    /**Set of labials (b, m, f, p)*/
    private final static Set<String> labials = new HashSet<>(Arrays.asList("b", "m", "f", "p"));
    /**Set of dentals (d, t). These sometimes don't lenite.*/
    private final static Set<String> dentals = new HashSet<>(Arrays.asList("d", "t"));
    /**Set of letters and strings which never lenite in writing (vowels, l, r, n, sm, st, sg, sp, t-)
     * <P>Note that not all lenition is written - for example, a word beginning with "l" does change the sound of
     * the "l" in a lenition scenario, but there's no "h" added in writing. This app only deals with written lenition.*/
    private final static Set<String> neverLenite = new HashSet<>(Arrays.asList("l", "r", "n", "sm", "st", "sg", "sp", "t-")); //vowels added below
    static {neverLenite.addAll(vowels);}

    private final static Set<String> definiteArticles = new HashSet<>(Arrays.asList("an ", "na ", "a' ", "a’ ", "am ", "an t-"));

    //Irregular verbs ----------------------------------------------------------
    /**The irregular past-tense forms (primary and secondary, respectively) of verbs.
     * <P>The primary (independent form is used for positive statements
     * (Chunnaic mi Athair) and the secondary (dependent) form is used for negative statements and questions (Am faca tu Athair?*/
    private final static HashMap<String, List<String>> irregularPast = new HashMap<>();
    /**The irregular future-tense forms (primary and secondary, respectively) of verbs
     * <P>The primary (independent form is used for positive statements
     * (Chì mi Athair) and the secondary (dependent) form is used for negative statements and questions (Am faic tu Athair?*/
    private final static HashMap<String, List<String>> irregularFuture = new HashMap<>();
    static {
        irregularPast.put("rach", Arrays.asList("chaidh","deach"));
        irregularPast.put("abair", Arrays.asList("thuirt", "tuirt"));
        irregularPast.put("dèan", Arrays.asList("rinn", "do rinn"));
        irregularPast.put("cluinn", Arrays.asList("chuala", "cuala"));
        irregularPast.put("faic", Arrays.asList("chunnaic", "faca"));
        irregularPast.put("thig", Arrays.asList("thàinig", "tàinig"));
        irregularPast.put("thoir", Arrays.asList("thug", "tug"));
        irregularPast.put("ruig", Arrays.asList("ràinig", "do ràinig"));
        irregularPast.put("beir", Arrays.asList("rug", "do rug"));
        irregularPast.put("faigh", Arrays.asList("fhuair", "d' fhuair"));
        irregularPast.put("bi", Arrays.asList("bha", "robh"));

        irregularFuture.put("rach", Arrays.asList("thèid", "tèid"));
        irregularFuture.put("abair", Arrays.asList("canaidh", "can"));
        irregularFuture.put("dèan", Arrays.asList("nì", "dèan"));
        irregularFuture.put("faic", Arrays.asList("chì", "faic"));
        irregularFuture.put("thig", Arrays.asList("thig", "tig"));
        irregularFuture.put("thoir", Arrays.asList("bheir", "toir"));
        irregularFuture.put("faigh", Arrays.asList("gheibh", "faigh"));
        irregularFuture.put("bi", Arrays.asList("bidh", "bi"));
    }

    //Helper functions -----------------------------------------------------

    /**Separate the first word from a string, using space and '-' as delimiters
     <P>Examples:
     <UL>
     <LI>an t-seòmar -> "an" + " " + " t-seomar"
     <LI>an taigh beag -> "an" + " " + " taigh beag"
     <LI>taigh-beag -> "taigh" + "-" + " beag"
     </UL>
     @param str The string to be split
     @return An array of size 3: First word, separator (space or dash), remainder of string*/
    public static String[] extractFirstWord(String str) {
        int space = str.indexOf(" ");
        int dash = str.indexOf("-");
        int sepIndex;
        String[] out = new String[3];

        if (space >= 0 && dash >= 0) {
            sepIndex = Math.min(space, dash);
        } else {
            sepIndex = Math.max(space, dash);
        }

        if (sepIndex >= 0) {
            out[0] = str.substring(0, sepIndex);			//first word
            out[1] = str.substring(sepIndex, sepIndex + 1);	//separator
            out[2] = str.substring(sepIndex + 1);			//rest of string
        } else {
            //If there is no space or dash in the string
            out[0] = str;
            out[1] = "";
            out[2] = "";
        }
        return out;
    }

    /**Does a word end with a broad or slender vowel?
     * @param word A Gaelic word
     * @return BROAD or SLENDER (as an enumerated type)*/
    public static grammaticalWidth endWidth(String word) {
        word = word.toLowerCase();
        String lastVowel = "";
        for (int i = word.length()-1; i >= 0; i--) {
            lastVowel = Character.toString(word.charAt(i));
            if (vowels.contains(lastVowel)) {
                break;
            }
        }
        if (slenderVowels.contains(lastVowel)) {
            return grammaticalWidth.SLENDER;
        }
        return grammaticalWidth.BROAD;
    }

    /**Remove the definite article from a word*/
    public static String removeArticle(String word) {
        String wordLower = word.toLowerCase();
        if (definiteArticles.stream().anyMatch(art -> wordLower.startsWith(art))) {
            String stripWord = word.split(" ", 2)[1];
            if (stripWord.startsWith("t-")) {
                return stripWord.substring(2);
            }
            return stripWord;
        }
        return word;
    }

    /**Guesses the gender of a word.
     * <P> Note this does not always guess correctly - words starting
     * 'an ' + (d,l,r,n,sg,sp,st,sm) might be masc or fem, so it tries
     * to guess without the article. Also it does not check context
     * (eg does it describe a person, is it a country)
     * @param word The word of unknown gender
     * @return MASCULINE or FEMININE (as an enumerated type)*/
    public static grammaticalGender guessGender(String word) {
        String w = word.toLowerCase().trim();
        if (definiteArticles.stream().anyMatch(art -> w.startsWith(art))) {
            if (w.startsWith("a' ") ||
                    w.startsWith("an fh") ||
                    (w.startsWith("an t-s") && vowels.contains(Character.toString(w.charAt(6)))) ||
                    (w.startsWith("an ") && vowels.contains(Character.toString(w.charAt(3))))
            ) {
                return grammaticalGender.FEMININE;
            } else {
                return guessGender(removeArticle(w));
            }
        } else {
            String w1 = extractFirstWord(w)[0];
            if (w1.endsWith("ag") || w1.endsWith("achd") + w1 == "cailleach") {
                return grammaticalGender.FEMININE;
            }
            if (w1 == "caraid" || w1 == "nàmhaid") {
                return grammaticalGender.MASCULINE;
            }
            if (endWidth(w1) == grammaticalWidth.SLENDER){
                return grammaticalGender.FEMININE;
            }
            return grammaticalGender.MASCULINE;
        }
    }

    /**Lenite words - can add other letters which should not lenite, eg dentals
     * @param word The word to be lenited
     * @param alsoDontLenite A set of strings (letters) which should not lenite in addition to the usual set
     * @return A word which will be lenited or not, depending on what letter(s) it starts with*/
    public static String lenite(String word, Set<String> alsoDontLenite) {
        String wordLower = word.toLowerCase();
        if (alsoDontLenite.stream().anyMatch(l -> wordLower.startsWith(l))) {
            return word;
        }
        return lenite(word);
    }

    /**Lenite words, excluding those which start with the letters which never lenite.
     * @param word The word to be lenited
     * @return A word which will be lenited or not, depending on what letter(s) it starts with*/
    public static String lenite(String word) {
        String wordLower = word.toLowerCase();
        if (neverLenite.stream().anyMatch(l -> wordLower.startsWith(l))) {
            return word;
        } else if (wordLower.startsWith("h")){
            return word;
        } else {
            return word.substring(0,1) + "h" + word.substring(1);
        }
    }

    //TODO slenderise(), shorten()

    /**Add "an" or "am" to the start of a word, depending on whether the word starts with the labials (b,m,f,p)*/
    public static String anm(String word) {
        String wordLower = word.toLowerCase();
        if (definiteArticles.stream().anyMatch(art -> wordLower.startsWith(art))) {
            return word;
        } else if (labials.stream().anyMatch(l -> wordLower.startsWith(l))){
            return "am " + word;
        } else {
            return "an " + word;
        }
    }

    /**Add "cha" or "chan" to the start of a word*/
    public static String cha(String word) {
        String wordLower = word.toLowerCase();
        if (vowels.stream().anyMatch(v -> wordLower.startsWith(v))) {
            return "chan " + word;
        } else if (wordLower.charAt(0)=='f' && vowels.stream().anyMatch(v -> wordLower.substring(1).startsWith(v))) {
            return "chan fh" + word.substring(1);
        } else {
            return "cha " + lenite(word, dentals);
        }
    }

    /**Returns the Gaelic for the given integer. Currently works for numbers up to 100,
     * and uses the decimal counting system (as I've not learned the other).*/
    public String digitsToGd(int n) {
        String numUnits = String.valueOf(n%10);
        String numUnitsGd = numbers.filterMatches("number", String.valueOf(numUnits)).data.get(0).get("cardinal");
        if (n < 10){
            return numUnitsGd;
        } else if (n == 10) {
            return "deich";
        } else if (n == 12) {
            return "dà dheug";
        } else if (n < 20) {
            return numUnitsGd + " deug";
        } else if (n < 100) {
            String numTens = String.valueOf(Math.floorDiv(n,10)*10);
            String numTensGd = numbers.filterMatches("number",numTens).data.get(0).get("cardinal");
            if (numUnits.equals("0")){
                return numTensGd;
            } else if (numUnits.equals("1") || numUnits.equals("8")){
                return numTensGd + " 's a h-" + numUnitsGd;
            } else {
                return numTensGd + " 's a " + numUnitsGd;
            }
        }
        return "";
    }

}
