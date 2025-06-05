package com.github.sourzo.a_rithist.general;

import com.github.sourzo.a_rithist.gaidhlig.Tense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class ExerciseGenerator {
    protected LessonOptions lo;

    //Vocab lists which are needed for particular lessons
    protected VocabTable monthsVocabList;
    protected VocabTable seasonsVocabList;
    protected VocabTable holidaysVocabList;
    protected VocabTable namesVocabList;
    protected VocabTable professionsVocabList;


    public ExerciseGenerator(LessonOptions lo){
        this.lo = lo;
        monthsVocabList = new VocabTable(lo.appRes, "datetime_months.csv");
        seasonsVocabList = new VocabTable(lo.appRes, "datetime_seasons.csv");
        holidaysVocabList = new VocabTable(lo.appRes, "datetime_holidays.csv");
        namesVocabList = new VocabTable(lo.appRes, "people_names.csv");
        professionsVocabList = new VocabTable(lo.appRes, "people_professions.csv");
    }

    public abstract Exercise generate();

    /**Since lessons can be quite short, a "true" random number generator will
     * produce streaks where the same item shows up loads of times, or an item
     * never shows up. So this method will choose sort-of randomly, but the
     * likelihood of a vocabulary item being picked will depend on how many times
     * it has already appeared.
     * @return row number for a vocabulary item.*/
    protected int randomUniformVocabNum() {
        int totalUses = 0;
        int maxUses = 0;
        for (int i = 0; i < lo.sampleVocabList.size(); i++) {
            totalUses += lo.sampleVocabList.getInt(i, "timesUsed");
            maxUses = Math.max(maxUses, lo.sampleVocabList.getInt(i, "timesUsed"));
            if (totalUses == 0) {
                return new Random().nextInt(lo.sampleVocabList.size());
            } else {

            }
        }
    }

    protected Tense randomTense() {
        ArrayList<Tense> tenseOptions = new ArrayList<>();
        if (lo.past){
            tenseOptions.add(Tense.PAST);
            tenseOptions.add(Tense.PAST_VERBAL_NOUN);
        }
        if (lo.present){
            tenseOptions.add(Tense.PRESENT_VERBAL_NOUN);
        }
        if (lo.future){
            tenseOptions.add(Tense.FUTURE);
            tenseOptions.add(Tense.FUTURE_VERBAL_NOUN);
        }
        return tenseOptions.get(new Random().nextInt(tenseOptions.size()));
    }

    public static String capitalise(String s){
        if (s == null || s.isEmpty()){
            return "";
        }
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }


}
