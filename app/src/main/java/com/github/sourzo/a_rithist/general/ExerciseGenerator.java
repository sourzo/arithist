package com.github.sourzo.a_rithist.general;

public abstract class ExerciseGenerator {

    protected LessonOptions lo;
    public ExerciseGenerator(LessonOptions lo){
        this.lo = lo;
    }

    public abstract Exercise generate();

    public static String capitalise(String s){
        if (s.isEmpty()){
            return "";
        }
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }


}
