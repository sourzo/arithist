package com.github.sourzo.a_rithist;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Scanner;

public class Exercise {
    //Fields------------------------------------------------------------------------
    private String question;
    private String prompt;
    private ArrayList<String> solutionSet = new ArrayList<String>();
    //Constructors------------------------------------------------------------------
    public Exercise(){}
    //Methods-----------------------------------------------------------------------
    public String getQuestion() {return question;}
    public String getPrompt() {return prompt;}

    public void setQuestion(String question) {this.question = question;}
    public void setPrompt(String prompt) {this.prompt = prompt;}

    public void addSolution (String newSolution) {
        solutionSet.add(newSolution);
    }
    public String getSolution(){return solutionSet.get(0);}

    /**A generic function to set a string to some standard format to aid sentence comparisons
     * <UL>
     *  <Li>Removes leading & trailing blanks
     *  <Li>Sets all characters to lower-case
     *  <LI>Sets all acute accents to grave
     * </UL>
     * @param anyString The sentence to standardise
     * @return (String) a sentence in a standardised format*/
    //TODO deal with apostrophes? In terms of messing up the code?
    public static String standardise(String anyString, boolean checkAccents) {
        anyString = anyString.trim().toLowerCase();
        if (checkAccents){
            anyString = anyString
                    .replaceAll("á", "à")
                    .replaceAll("é", "è")
                    .replaceAll("í", "ì")
                    .replaceAll("ó", "ò")
                    .replaceAll("ú", "ù");
        } else {
            anyString = anyString
                    .replaceAll("áà", "a")
                    .replaceAll("éè", "e")
                    .replaceAll("íì", "i")
                    .replaceAll("óò", "o")
                    .replaceAll("úù", "u");
        }

        return anyString;
    }

    /**Checks a user answer against the solution set
     * @param the user answer
     * @return whether the answer is in the solution set*/
    public boolean checkAnswer(String userAnswer, boolean checkAccents) {
        userAnswer = standardise(userAnswer, checkAccents);
        ArrayList<String> standardisedAnswers = new ArrayList<>();
        for (String solution : solutionSet){
            standardisedAnswers.add(standardise(solution, checkAccents));
        }
        return standardisedAnswers.contains(userAnswer);
    }
}
