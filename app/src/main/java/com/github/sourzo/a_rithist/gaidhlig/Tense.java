package com.github.sourzo.a_rithist.gaidhlig;

import java.util.Random;

public enum Tense {
    PAST,
    FUTURE,
    PAST_VERBAL_NOUN,
    PRESENT_VERBAL_NOUN,
    FUTURE_VERBAL_NOUN;
    final private static Random numGen = new Random();

    public static Tense random() {
        Tense[] gpArray = values();
        return gpArray[numGen.nextInt(gpArray.length)];
    }

}
