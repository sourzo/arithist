package com.github.sourzo.a_rithist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.sourzo.a_rithist.gaidhlig.LessonInfo;

import java.util.Objects;

public class LessonActivity extends AppCompatActivity {
    public Generatable exGen;
    public String lessonID;
    public String vocabListName;
    public VocabTable sampleVocabList;
    public int vocabListSize;
    public boolean translateFromGaelic;
    public String sentenceType;
    public String genderType;
    public boolean comparatives;
    public boolean superlatives;
    public boolean past;
    public boolean present;
    public boolean future;
    public boolean posStatements;
    public boolean negStatements;
    public boolean posQuestions;
    public boolean negQuestions;
    public boolean pronouns;
    public boolean nouns;
    public boolean checkAccents;
    public Exercise e;
    TextView lessonTitleView;
    TextView questionView;
    TextView promptView;
    TextView solutionView;
    EditText userResponseView;
    Button buttonView;
    boolean showingAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        lessonTitleView = findViewById(R.id.lesson_title);
        questionView = findViewById(R.id.question);
        promptView = findViewById(R.id.prompt);
        userResponseView = findViewById(R.id.user_response);
        buttonView = findViewById(R.id.lesson_button);
        solutionView = findViewById(R.id.solution);
        exGen = Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).getGenerator.apply(this);


        getOptionsForLesson();

        lessonTitleView.setText(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).displayName);

        //load vocab set
        sampleVocabList = new VocabTable(getApplicationContext(), vocabListName);
        sampleVocabList.getRandomRows(vocabListSize);

        newExercise();
    }

    public void buttonPress(View v){
        if (showingAnswer){
            showingAnswer = false;
            buttonView.setText(R.string.submit_solution);
            newExercise();
        } else {
            showingAnswer = true;
            buttonView.setText(R.string.next_exercise);
            checkUserResponse();
        }
    }
    public void checkUserResponse(){
        String userResponse = userResponseView.getText().toString();

        if (e.checkAnswer(userResponse, checkAccents)){
            solutionView.setText("Correct!");
        } else {
            String solutionMessage = "Incorrect! A correct answer is: " + e.getSolution();
            solutionView.setText(solutionMessage);
        }
        userResponseView.setEnabled(false);
    }

    /**Creates a new exercise, generating a new question, prompt and solution set, and resets the
     * activity with the new question and prompt, and a blank user response field, with keyboard showing*/
    private void newExercise(){
        e = exGen.generate(this);
        questionView.setText(e.getQuestion());
        promptView.setText(e.getPrompt());
        solutionView.setText(" ");
        userResponseView.setText("");
        userResponseView.setEnabled(true);
        userResponseView.requestFocus();
        InputMethodManager imm = getSystemService(InputMethodManager.class);
        imm.showSoftInput(userResponseView, InputMethodManager.SHOW_IMPLICIT);

    }
    /**Pipes the selected user options from the OptionsActivity*/
    private void getOptionsForLesson(){
        Intent i = getIntent();
        lessonID = i.getStringExtra("lessonID");
        translateFromGaelic = i.getBooleanExtra("translateFromGaelic", false);
        sentenceType = i.getStringExtra("sentenceType");
        genderType = i.getStringExtra("genderType");
        comparatives = i.getBooleanExtra("comparatives", false);
        superlatives = i.getBooleanExtra("superlatives", false);
        past = i.getBooleanExtra("past", false);
        present = i.getBooleanExtra("present", false);
        future = i.getBooleanExtra("future", false);
        posStatements = i.getBooleanExtra("posStatements", false);
        negStatements = i.getBooleanExtra("negStatements", false);
        posQuestions = i.getBooleanExtra("posQuestions", false);
        negQuestions = i.getBooleanExtra("negQuestions", false);
        pronouns = i.getBooleanExtra("pronouns", false);
        nouns = i.getBooleanExtra("nouns", false);
        vocabListName = i.getStringExtra("vocabListName");
        vocabListSize = i.getIntExtra("vocabListSize", 0);
        checkAccents = i.getBooleanExtra("checkAccents",true);

        Log.i("Options", "lessonID = " + lessonID);
        Log.i("Options", "translateFromGaelic = " + translateFromGaelic);
        Log.i("Options", "sentenceType = " + sentenceType);
        Log.i("Options", "genderType = " + genderType);
        Log.i("Options", "comparatives = " + comparatives);
        Log.i("Options", "superlatives = " + superlatives);
        Log.i("Options", "past = " + past);
        Log.i("Options", "present = " + present);
        Log.i("Options", "future = " + future);
        Log.i("Options", "posStatements = " + posStatements);
        Log.i("Options", "negStatements = " + negStatements);
        Log.i("Options", "posQuestions = " + posQuestions);
        Log.i("Options", "negQuestions = " + negQuestions);
        Log.i("Options", "pronouns = " + pronouns);
        Log.i("Options", "nouns = " + nouns);
        Log.i("Options", "vocabListName = " + vocabListName);
        Log.i("Options", "vocabListSize = " + vocabListSize);
        Log.i("Options","checkAccents = " + checkAccents);
    }
}