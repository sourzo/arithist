package com.github.sourzo.a_rithist.gaidhlig;

public enum SentenceType {
    POS_STATEMENT(false,true),
    NEG_STATEMENT(false,false),
    POS_QUESTION(true,true),
    NEG_QUESTION(true,false);

    final public boolean isQuestion;
    final public boolean isPositive;
    SentenceType(boolean isQuestion, boolean isPositive) {
        this.isQuestion = isQuestion;
        this.isPositive = isPositive;
    }
}
