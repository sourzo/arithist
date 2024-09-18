package com.github.sourzo.a_rithist.gaidhlig;

import static java.util.Arrays.asList;

import com.github.sourzo.a_rithist.general.AndroidAppRes;
import com.github.sourzo.a_rithist.general.VocabTable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GrammarGd {

    //Setup -------------------------------------------------------------------
    /**Creates a new Gaelic Grammar instance. Requires context to load vocab tables.*/
    public GrammarGd(AndroidAppRes appRes){
        pp = new VocabTable(appRes,"grammar_prepPronouns.csv");
        names = new VocabTable(appRes,"people_names.csv");
        numbers = new VocabTable(appRes,"grammar_numbers.csv");
        professions = new VocabTable(appRes,"people_professions.csv");
        similes = new VocabTable(appRes,"adjectives_comparisons.csv");
        adjectives = new VocabTable(appRes,"adjectives_misc.csv");
        list_months = new VocabTable(appRes,"datetime_months.csv");
        list_seasons = new VocabTable(appRes,"datetime_seasons.csv");
        list_holidays = new VocabTable(appRes,"datetime_holidays.csv");
    }

    //Vocabulary/grammar files ------------------------------------------------
    public VocabTable pp;
    public VocabTable names;
    public VocabTable numbers;
    public VocabTable professions;
    public VocabTable similes;
    public VocabTable adjectives;
    public VocabTable list_months;
    public VocabTable list_seasons;
    public VocabTable list_holidays;

    //Enums --------------------------------------------------------------

    /**Enumerated type: BROAD (a, o, u) or SLENDER (e, i)*/
    public enum GrammaticalWidth {
        BROAD,
        SLENDER
    }

    /**Enumerated type: The grammatical gender (MASCULINE or FEMININE)*/
    public enum GrammaticalGender {
        MASCULINE,
        FEMININE,
        UNDETERMINED
    }
    public enum GrammaticalCase {
        NOMINAL,
        PREPOSITIONAL,
        POSSESSIVE,
        GENITIVE
    }


    //Regular Expressions --------------------------------------------------------------

    /**Regular expression matching words which start with broad vowels (a, o, u). Includes accented vowels.*/
    private final static String broadVowelsRegex = "[aàáoòóuùú].*";
    /**Regular expression matching words which start with slender vowels (e, i). Includes accented vowels.*/
    private final static String slenderVowelsRegex = "[eèéiìí].*";
    /**Regular expression matching words which start with vowels (a, e, i, o, u). Includes accented vowels.*/
    private final static String vowelsRegex = "[aàáeèéiìíoòóuùú].*";
    /**Regular expression matching words which start with the consonants l, r, n, or vowels (a, e, i, o, u). Includes accented vowels.*/
    private final static String lrnvRegex = "[lrnaàáeèéiìíoòóuùú].*";
    /**Regular expression matching the labials (b, m, f, p).*/
    private final static String labialsRegex = "[bmfp].*";
    /**Regular expression matching the dentals (d, t).*/
    private final static String dentalsRegex = "[dt].*";
    /**Regular expression matching the words which never lenite in writing (those starting with a vowel, l, r, n, sm, st, sg, sp, or t-).
     * <P>Note that not all lenition is written - for example, a word beginning with "l" does change the sound of
     * the "l" in a lenition scenario, but there's no "h" added in writing. This app only deals with written lenition.</P>*/
    private final static String neverLeniteRegex = "([lrnaàáeèéiìíoòóuùú]|sm|st|sg|sp|t-).*";
    /**Regular expression matching words which start with definite articles (an, am, na, a', an t-)*/
    private final static String definiteArticlesRegex = "(an |na |a' |a’ |am ).*";

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
        irregularPast.put("rach", asList("chaidh","deach"));
        irregularPast.put("abair", asList("thuirt", "tuirt"));
        irregularPast.put("dèan", asList("rinn", "do rinn"));
        irregularPast.put("cluinn", asList("chuala", "cuala"));
        irregularPast.put("faic", asList("chunnaic", "faca"));
        irregularPast.put("thig", asList("thàinig", "tàinig"));
        irregularPast.put("thoir", asList("thug", "tug"));
        irregularPast.put("ruig", asList("ràinig", "do ràinig"));
        irregularPast.put("beir", asList("rug", "do rug"));
        irregularPast.put("faigh", asList("fhuair", "d' fhuair"));
        irregularPast.put("bi", asList("bha", "robh"));

        irregularFuture.put("rach", asList("thèid", "tèid"));
        irregularFuture.put("abair", asList("canaidh", "can"));
        irregularFuture.put("dèan", asList("nì", "dèan"));
        irregularFuture.put("faic", asList("chì", "faic"));
        irregularFuture.put("thig", asList("thig", "tig"));
        irregularFuture.put("thoir", asList("bheir", "toir"));
        irregularFuture.put("faigh", asList("gheibh", "faigh"));
        irregularFuture.put("bi", asList("bidh", "bi"));
    }

    //Fields: other ----------------------------------------------------
    /**The largest number which the code is capable of translating!*/
    public static int largestTranslatableNumber = 100;

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
    public static GrammaticalWidth endWidth(String word) {
        word = word.toLowerCase();
        String lastVowel = "";
        for (int i = word.length()-1; i >= 0; i--) {
            lastVowel = Character.toString(word.charAt(i));
            if (lastVowel.matches(vowelsRegex)) {
                break;
            }
        }
        if (lastVowel.matches(slenderVowelsRegex)) {
            return GrammaticalWidth.SLENDER;
        }
        return GrammaticalWidth.BROAD;
    }

    /**Remove the definite article from a word*/
    public static String removeArticle(String word) {
        String wordLower = word.toLowerCase();
        if (wordLower.matches(definiteArticlesRegex)) {
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
    public static GrammaticalGender guessGender(String word) {
        String w = word.toLowerCase().trim();
        if (w.matches(definiteArticlesRegex)) {
            //Some definite articles are only used for feminine words
            if (w.startsWith("a' ") ||
                    w.startsWith("an fh") ||
                    (w.startsWith("an t-s") && Character.toString(w.charAt(6)).matches(vowelsRegex)) ||
                    (w.startsWith("an ") && Character.toString(w.charAt(3)).matches(vowelsRegex))
            ) {
                return GrammaticalGender.FEMININE;
            } else {
                return guessGender(removeArticle(w));
            }
        } else {
            String w1 = extractFirstWord(w)[0];
            if (w1.endsWith("ag") || (w1.endsWith("achd") + w1).equals("cailleach")) {
                return GrammaticalGender.FEMININE;
            }
            if (w1.equals("caraid") || w1.equals("nàmhaid")) {
                return GrammaticalGender.MASCULINE;
            }
            if (endWidth(w1) == GrammaticalWidth.SLENDER){
                return GrammaticalGender.FEMININE;
            }
            return GrammaticalGender.UNDETERMINED;
        }
    }

    /**Lenite words, excluding those which start with the letters which never lenite.
     * @param word The word to be lenited
     * @param dontLeniteDentals If {@code true} then words starting with the dentals (d, t) will not lenite
     * @return A word which will be lenited or not, depending on what letter(s) it starts with
     * @see #neverLeniteRegex*/
    public static String lenite(String word, boolean dontLeniteDentals) {
        String wordLower = word.toLowerCase();
        if (wordLower.matches(neverLeniteRegex)) {
            return word;
        } else if (wordLower.startsWith("h")){
            return word;
        } else if (dontLeniteDentals && wordLower.matches(dentalsRegex)) {
            return word;
        } else {
            return word.charAt(0) + "h" + word.substring(1);
        }
    }

    //TODO slenderise()

    /**Removes the last set of vowels from a word, and changes nn to n*/
    public String shorten(String word) {
        //TODO check indices!
        if (word.endsWith("il") || word.endsWith("in") || word.endsWith("ir")) {
            String consonants = word.replaceAll("aeiouàèìòùáéióú","");
            String penultimateConstant = consonants.substring(consonants.length()-2,consonants.length()-1);
            int cut_position = word.lastIndexOf(penultimateConstant);
            String shorter_word = word.substring(0,cut_position) + word.substring(word.length()-1);
            if (shorter_word.replaceAll("aeiouàèìòùáéióú","").length() == shorter_word.length()) {
                return word;
            } else {
                return shorter_word;
            }
        } else if (word.endsWith(("inn"))) {
            String consonants = word.replaceAll("aeiouàèìòùáéióú","");
            String prePenultimateConstant = consonants.substring(consonants.length()-3,consonants.length()-2);
            int cut_position = word.lastIndexOf(prePenultimateConstant);
            String shorter_word = word.substring(0,cut_position) + "n";
            if (shorter_word.replaceAll("aeiouàèìòùáéióú","").length() == shorter_word.length()) {
                return word;
            } else {
                return shorter_word;
            }
        } else {
            return word;
        }
    }

    /**Add "an" or "am" to the start of a word, depending on whether the word starts with the labials (b,m,f,p)*/
    public static String anm(String word) {
        String wordLower = word.toLowerCase();
        if (wordLower.matches(definiteArticlesRegex)) {
            return word;
        } else if (wordLower.matches(labialsRegex)) {
            return "am " + word;
        } else {
            return "an " + word;
        }
    }

    /**Add "cha" or "chan" to the start of a word*/
    public static String cha(String word) {
        String wordLower = word.toLowerCase();
        if (wordLower.matches(vowelsRegex)) {
            return "chan " + word;
        } else if (wordLower.charAt(0)=='f' && wordLower.substring(1).matches(vowelsRegex)) {
            return "chan fh" + word.substring(1);
        } else {
            return "cha " + lenite(word, true);
        }
    }

    /**Returns the Gaelic for the given integer. Currently works for numbers up to 100,
     * and uses the decimal counting system (as I've not learned the other).*/
    public String digitsToGd(int n) {
        String numUnits = String.valueOf(n%10);
        String numUnitsGd = numbers.filterMatches("number", numUnits).data.get(0).get("cardinal");
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

    /**The common article pattern used for singular nom-fem, prep, and gen-masc.*/
    public String articleStandard(String word) {
        String w = word.toLowerCase();
        if (Arrays.asList(new Character[]{'b', 'c', 'g', 'm', 'p'}).contains(w.charAt(0))) {
            return "a' " + lenite(word, true);
        } else if (w.charAt(0) == 's') {

            if (w.matches(lrnvRegex)) {
                return "an t-" + word;
            } else {
                return "an " + word;
                //Does this lenite ??
            }
        } else if (w.charAt(0) == 'f')
            return "an " + lenite(word, false);
        else {
            return anm(word);
        }
    }

    /**Add the common article ('the' in English) to a Gaelic word with no article
     sg_pl: sg/pl (singular or plural)
     gender: masc/fem
     case: nom/gen/prep (nominative, genitive, prepositional)
     (no vocative - slenderisation can't be automated)*/
    public String commonArticle(String word,
                                String sg_pl,
                                String gender,
                                GrammaticalCase caseType) {
        word = removeArticle(word);
        String wordLower = word.toLowerCase();
        //singular
        if (Objects.equals(sg_pl, "sg")) {
            //nominal case
            if (caseType == GrammaticalCase.NOMINAL) {  //masculine
                if (gender.equals("masc")) {
                    if (wordLower.matches(labialsRegex)) {
                        word = "am " + word;
                    } else if (wordLower.matches(vowelsRegex)) {
                        word = "an t-" + word;
                    } else {
                        word = anm(word);
                    }
                }
                //feminine
                else if (gender.equals("fem")) {
                    word = articleStandard(word);
                }
            }

            //genitive case
            else if
            (caseType == GrammaticalCase.GENITIVE) {
                //masculine
                if (gender.equals("masc")) {
                    word = articleStandard(word);
                }
                //feminine
                else if (gender.equals("fem")) {
                    if (wordLower.matches(vowelsRegex)) {
                        word = "na h-" + word;
                    } else {
                        word = "na " + word;
                    }
                }
            }

            //prepositional case
            else if (caseType == GrammaticalCase.PREPOSITIONAL) {
                //both genders
                word = articleStandard(word);
            }
        }

        //plural
        else if (sg_pl.equals("pl")) {
            //genitive
            if (caseType == GrammaticalCase.GENITIVE) {
                if (wordLower.matches(labialsRegex)) {
                    word = "nam " + word;
                } else {
                    word = "nan " + word;
                }
            } else {
                if (wordLower.matches(vowelsRegex)){
                    word = "na h-" + word;
                } else {
                    word = "na " + word;
                }
            }
        }
        return word;
    }

    /**The possessive article (plus the thing being possessed)
     * @param possession The thing being possessed
     * @param person 0 = my, 1 = your(sg), 2 = his, 3 = her, 4 = our, 5 = your(pl), 6 = their
     * @param outputPossession Whether to include the possession in the outputs
     * */
    public String articlePossessive(String possession, GrammaticalPerson person, boolean outputPossession) {
        int persNumPp = pp.getRow("en_subj", person.en_subj);

        String output;

        if (possession.matches("[aeiouàèìòùáéíóú].*")) {
            String[] whoseVowel = {"m'", "d'", "", "a", "àr", "ùr", "an"};
            output = whoseVowel[person.ordinal()];
        } else {
            output = pp.get(persNumPp, "possessive");
        }

        switch (person) {
            case FIRST_SINGULAR:
            case SECOND_SINGULAR:
            case THIRD_SINGULAR_MALE:
                // my/your/his -> lenition
                if (outputPossession) {
                    if (output.length() == 0) {
                        //his + vowel
                        output += lenite(possession, false);
                    } else {
                        output += " " + lenite(possession, false);
                    }
                }
                break;
            case THIRD_SINGULAR_FEMALE:
                //her + vowel -> "h-"
                if (possession.matches("[aeiouàèìòùáéíóú].*")) {
                    output += " h-";
                    if (outputPossession) {
                        output += possession;
                    }
                } else if (outputPossession) {
                    output += " " + possession;
                }
                break;
            case FIRST_PLURAL:
            case SECOND_PLURAL:
                // (y)our + vowel -> n-
                if (possession.matches("[aeiouàèìòùáéíóú].*")) {
                    output += " n-";
                    if (outputPossession) {
                        output += possession;
                    }
                } else if (outputPossession) {
                    output += " " + possession;
                }
                break;
            case THIRD_PLURAL:
                // bmfp: an -> am
                if (possession.matches("[bmfp].*")) {
                    output = "am";
                }
                if (outputPossession) {
                    output += " " + possession;
                }
                break;
            default:
                break;
        }
        return output;
    }

    /**Simple past/future tense of a verb*/
    //TODO Check grammatical accuracy of verb stuff - see IonnsaichSeo for examples
    public String transformVerb(String root, Tense tense, SentenceType sentenceType) {
        if (tense.equals(Tense.PAST_VERBAL_NOUN) || tense.equals(Tense.FUTURE_VERBAL_NOUN)){
            return transformVerb(root, tense, sentenceType);
        }
        root = root.toLowerCase();
        String verb = "";
        switch (tense) {
            case PAST:
                if (irregularPast.containsKey(root)) {
                    if (sentenceType.equals(SentenceType.POS_STATEMENT)) {
                        ////Primary form
                        verb = irregularPast.get(root).get(0);
                    } else {
                        //Secondary form
                        verb = irregularPast.get(root).get(1);
                    }
                } else {
                    //regular past: Lenite, and add "dh'" if it's a vowel or f+vowel
                    if (root.matches(vowelsRegex)) {
                        verb = "dh'" + root;
                    } else if (root.charAt(0) == 'f' && root.substring(1).matches(vowelsRegex)) {
                        verb = "dh'fh" + root.substring(1);
                    } else {
                        verb = lenite(root, false);
                    }
                    //secondary form: add 'do '
                    if (!sentenceType.equals(SentenceType.POS_STATEMENT))
                    {
                        verb = "do " + verb;
                    }
                }
                break;
            case FUTURE:
                if (irregularFuture.containsKey(root)) {
                    if (sentenceType.equals(SentenceType.POS_STATEMENT)){
                        //Primary form
                        verb = irregularFuture.get(root).get(0);
                    } else{
                        //Secondary form
                        verb = irregularFuture.get(root).get(1);
                    }
                }
                else {
                    //regular future
                    if (sentenceType.equals(SentenceType.POS_STATEMENT)){
                        //primary form
                        if (endWidth(root).equals(GrammaticalWidth.BROAD)) {
                            verb = root + "aidh";
                        } else {
                            //some slender-ended verbs need shortening
                            verb = shorten(root) + "idh";
                        }
                    } else {
                        //secondary form
                        if (sentenceType.equals(SentenceType.NEG_STATEMENT)) {
                            verb = lenite(root, false);
                        } else {
                            verb = root;
                        }
                    }
                }
                break;
            case PRESENT_VERBAL_NOUN:
                if (root.equals("bi")) {
                    if (sentenceType.equals(SentenceType.POS_STATEMENT)){
                        //primary form
                        verb = "tha";
                    } else {
                        //secondary form
                        verb = "eil";
                    }
                }
                break;
        }

        switch (sentenceType) {
            //Negative prefixes
            case NEG_QUESTION:
                if (verb.equals("faca")) {
                    verb = "nach fhaca";
                } else {
                    verb = "nach " + verb;
                }
                break;
            case NEG_STATEMENT:
                verb = cha(verb);
                break;
            //Positive prefixes (questions only)
            case POS_QUESTION:
                if (!verb.equals("eil")) {
                    verb = anm(verb);
                } else {
                    verb = "a bh" + verb;
                }
                break;
        }
        return verb;
    }

    /**Verbal noun tense of given verb, vn.
     Input must be the verbal noun form.*/
    public String verbal_noun(String vn, String person, Tense tense, SentenceType sentenceType) {
        if (tense.equals(Tense.PAST_VERBAL_NOUN)){
            tense = Tense.PAST;
        } else if (tense.equals(Tense.FUTURE_VERBAL_NOUN)) {
            tense = Tense.FUTURE;
        }
        String bi = transformVerb("bi", tense, sentenceType);
        if (vn.matches(vowelsRegex))
        {
            vn = "ag " + vn;
        } else {
            vn = "a' " + vn;
        }
        return bi + " " + person + " " + vn;
    }
}
