package com.github.sourzo.a_rithist.general;

public abstract class ExerciseGenerator {
    protected LessonOptions lo;

    //Vocab lists which are needed for particular lessons
    protected VocabTable monthsVocabList;
    protected VocabTable seasonsVocabList;
    protected VocabTable holidaysVocabList;

    public ExerciseGenerator(LessonOptions lo){
        this.lo = lo;
        monthsVocabList = new VocabTable(lo.appRes, "datetime_months.csv");
        seasonsVocabList = new VocabTable(lo.appRes, "datetime_seasons.csv");
        holidaysVocabList = new VocabTable(lo.appRes, "datetime_holidays.csv");
    }

    public abstract Exercise generate();

    public static String capitalise(String s){
        if (s.isEmpty()){
            return "";
        }
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }


}
