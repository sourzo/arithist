package com.github.sourzo.a_rithist.general;

import com.github.sourzo.a_rithist.LessonActivity;

public abstract class ExerciseGenerator {

    protected LessonActivity la;
    public ExerciseGenerator(LessonActivity la){
        this.la = la;
    }

    public abstract Exercise generate();

    public static String capitalise(String s){
        if (s.isEmpty()){
            return "";
        }
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }


}
