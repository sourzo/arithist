package com.github.sourzo.a_rithist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.sourzo.a_rithist.gaidhlig.LessonInfo;
import com.github.sourzo.a_rithist.general.AndroidAppRes;
import com.github.sourzo.a_rithist.general.Exercise;
import com.github.sourzo.a_rithist.general.ExerciseGenerator;
import com.github.sourzo.a_rithist.general.LessonOptions;
import com.github.sourzo.a_rithist.general.VocabTable;

import java.util.Objects;

public class LessonActivity extends AppCompatActivity {
    public LessonOptions lo;
    public ExerciseGenerator exGen;
    public Exercise e;
    TextView lessonTitleView;
    TextView questionView;
    TextView prePromptView;
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
        prePromptView = findViewById(R.id.prePrompt);
        userResponseView = findViewById(R.id.user_response);
        buttonView = findViewById(R.id.lesson_button);
        solutionView = findViewById(R.id.solution);

        //Import options from OptionsActivity
        getOptionsForLesson();

        //Instantiate exercise generator
        exGen = Objects.requireNonNull(LessonInfo.lessonSet.get(lo.lessonID)).getGenerator.apply(this.lo);
        //Activity title
        lessonTitleView.setText(Objects.requireNonNull(LessonInfo.lessonSet.get(lo.lessonID)).displayName);

        //load vocab set
        if (lo.vocabListName != null){
            lo.sampleVocabList = new VocabTable(lo.appRes, lo.vocabListName);
            lo.sampleVocabList = lo.sampleVocabList.getRandomRows(lo.vocabListSize);
        }
        newExercise();
        userResponseView.setOnKeyListener((v, keyCode, event) -> {
            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Perform action on key press
                buttonPress(buttonView);
                return true;
            }
            return false;
        });
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

        if (e.checkAnswer(userResponse, lo.checkAccents)){
            solutionView.setText("Correct!");
        } else {
            String solutionMessage = "Incorrect! A correct answer is: " + e.getSolution();
            solutionView.setText(solutionMessage);
        }
        userResponseView.setEnabled(false);
    }

    /**Creates a new exercise, generating a new question, prompts and solution set, and resets the
     * activity with the new question and prompt, and a blank user response field, with keyboard showing*/
    private void newExercise(){
        e = exGen.generate();
        questionView.setText(e.getQuestion());
        prePromptView.setText(e.getPrePrompt());
        solutionView.setText(" ");
        userResponseView.setText(e.getEditTextPrompt());
        userResponseView.setEnabled(true);
        userResponseView.requestFocus();
        userResponseView.setSelection(e.getEditTextCursorPosition());
        InputMethodManager imm = getSystemService(InputMethodManager.class);
        imm.showSoftInput(userResponseView, InputMethodManager.SHOW_IMPLICIT);

    }
    /**Pipes the selected user options from the OptionsActivity*/
    private void getOptionsForLesson(){
        Intent i = getIntent();
        Log.i("Test","Starting to get lo");
        lo = (LessonOptions) i.getSerializableExtra("lessonOptions");
        Log.i("Test","Got lo");
        lo.appRes = new AndroidAppRes(this.getAssets());

        Log.i("Options", "lessonID = " + lo.lessonID);
        Log.i("Options", "translateFromGaelic = " + lo.translateFromGaelic);
        Log.i("Options", "vocabListName = " + lo.vocabListName);
        Log.i("Options", "vocabListSize = " + lo.vocabListSize);
        Log.i("Options", "largestNumber = " + lo.largestNumber);
        Log.i("Options", "sentenceType = " + lo.responseType);
        Log.i("Options", "genderAdj = " + lo.genderAdj);
        Log.i("Options", "genderDefArtNom = " + lo.genderDefArtNom);
        Log.i("Options", "comparatives = " + lo.comparatives);
        Log.i("Options", "superlatives = " + lo.superlatives);
        Log.i("Options", "past = " + lo.past);
        Log.i("Options", "present = " + lo.present);
        Log.i("Options", "future = " + lo.future);
        Log.i("Options", "posStatements = " + lo.posStatements);
        Log.i("Options", "negStatements = " + lo.negStatements);
        Log.i("Options", "posQuestions = " + lo.posQuestions);
        Log.i("Options", "negQuestions = " + lo.negQuestions);
        Log.i("Options", "pronouns = " + lo.pronouns);
        Log.i("Options", "nouns = " + lo.nouns);
        Log.i("Options","checkAccents = " + lo.checkAccents);
    }
}