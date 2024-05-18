package com.github.sourzo.a_rithist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.gaidhlig.LessonInfo;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class OptionsActivity extends AppCompatActivity {
    //Views: user input ----------------------------------------------------------------------------
    Spinner vocabSpinnerView;
    EditText vocabListSizeView;
    EditText largestNumberView;
    com.google.android.material.switchmaterial.SwitchMaterial translationSwitchView;
    RadioGroup sentenceOptionsView;
    RadioButton sentenceFullView;
    RadioButton sentenceBlankView;
    RadioButton sentenceQAView;
    RadioGroup genderOptionsView;
    RadioButton genderDefArtView;
    RadioButton genderAdjView;
    CheckBox comparativesView;
    CheckBox superlativesView;
    CheckBox pastView;
    CheckBox presentView;
    CheckBox futureView;
    CheckBox posStatementsView;
    CheckBox negStatementsView;
    CheckBox posQuestionsView;
    CheckBox negQuestionsView;
    CheckBox pronounsView;
    CheckBox nounsView;
    com.google.android.material.switchmaterial.SwitchMaterial checkAccentsSwitchView;


    //Views: Titles --------------------------------------------------------------------------------
    TextView lessonTitle;
    TextView translationDirectionTitle;
    TextView vocabTitle;
    TextView vocabListSizeTitle;
    TextView largestNumberTitle;
    TextView sentenceTitle;
    TextView genderTitle;
    TextView compSupTitle;
    TextView verbTenseTitle;
    TextView verbFormTitle;
    TextView prepObjTitle;

    //Views : Section Dividers ---------------------------------------------------------------------
    View translateDiv;
    View vocabDiv;
    View largestNumberDiv;
    View sentenceDiv;
    View genderDiv;
    View compSupDiv;
    View verbTenseDiv;
    View verbFormDiv;
    View prepObjDiv;

    //Options values -------------------------------------------------------------------------------
    String lessonID;
    List<String> validVocabFileNames;
    VocabTable fullVocabList;
    String vocabListName;
    int vocabListSize;
    int largestNumber;
    boolean translateFromGaelic;
    String sentenceType;
    String genderType;
    boolean comparatives;
    boolean superlatives;
    boolean past;
    boolean present;
    boolean future;
    boolean posStatements;
    boolean negStatements;
    boolean posQuestions;
    boolean negQuestions;
    boolean pronouns;
    boolean nouns;
    boolean checkAccents;

    //Activity setup -------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        setAllViews();

        //Get info from main menu selection
        Intent i = getIntent();
        lessonID = i.getStringExtra("lessonID");
        lessonTitle.setText(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).displayName);

        //Vocab list spinner: Change max number of words as switch is pressed
        setValidVocabFiles(new HashSet<>(Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).requiredColumns)));

        if (validVocabFileNames.size() == 0){
            vocabDiv.setVisibility(View.GONE);
            vocabTitle.setVisibility(View.GONE);
            vocabSpinnerView.setVisibility(View.GONE);
            vocabListSizeTitle.setVisibility(View.GONE);
            vocabListSizeView.setVisibility(View.GONE);
        } else {
            ArrayAdapter<String> vocabSpinnerAdapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, validVocabFileNames);
            vocabSpinnerAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            vocabSpinnerView.setAdapter(vocabSpinnerAdapter);

            vocabSpinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //TODO
                    fullVocabList = new VocabTable(parent.getContext(), validVocabFileNames.get(position));
                    vocabListName = validVocabFileNames.get(position);

                    //Set the size of the vocabulary table
                    long maxWords = fullVocabList.size();
                    vocabListSizeView.setHint(String.valueOf(maxWords));
                    String prompt = getString(R.string.vocab_list_size_title, maxWords);
                    vocabListSizeTitle.setText(prompt);
                    //TODO: Toast error to user if max is exceeded
                    //TODO: if number is 1, then user can select the word?
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    fullVocabList = new VocabTable(parent.getContext(), validVocabFileNames.get(0));
                    vocabListName = validVocabFileNames.get(0);
                    long maxWords = fullVocabList.size();
                    vocabListSizeView.setHint(String.valueOf(maxWords));
                    String prompt = getString(R.string.vocab_list_size_title, maxWords);
                    vocabListSizeTitle.setText(prompt);
                }
            });
        }

        //translating numbers - max number field
        if (Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).options).contains(Lesson.lessonOptions.MAX_NUM)){
            String prompt = getString(R.string.largest_number_title, GrammarGd.largestTranslatableNumber);
            largestNumberTitle.setText(prompt);
            largestNumberView.setHint(String.valueOf(GrammarGd.largestTranslatableNumber));

        } else {
            largestNumberDiv.setVisibility(View.GONE);
            largestNumberTitle.setVisibility(View.GONE);
            largestNumberView.setVisibility(View.GONE);
        }

        //translation direction: change text as switch is pressed
        if (Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).options).contains(Lesson.lessonOptions.TRANSLATE)){
            setTranslationDirection(translationSwitchView.isChecked(), translationSwitchView, lessonID);

            translationSwitchView.setOnCheckedChangeListener((buttonView, isChecked) -> setTranslationDirection(isChecked, translationSwitchView, lessonID));
        } else {
            Log.d("Info","No option for translation direction");
            translateDiv.setVisibility(View.GONE);
            translationDirectionTitle.setVisibility(View.GONE);
            translationSwitchView.setVisibility(View.GONE);
        }

        //Sentence type
        if (!Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).options).contains(Lesson.lessonOptions.SENTENCE_QA)){
            if (!Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).options).contains(Lesson.lessonOptions.SENTENCE)){
                sentenceDiv.setVisibility(View.GONE);
                sentenceTitle.setVisibility(View.GONE);
                sentenceOptionsView.setVisibility(View.GONE);
            } else {
                sentenceQAView.setVisibility(View.GONE);
            }
        }

        //Gender options
        if (!Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).options).contains(Lesson.lessonOptions.GENDER_MODE)){
            genderDiv.setVisibility(View.GONE);
            genderTitle.setVisibility(View.GONE);
            genderOptionsView.setVisibility(View.GONE);
        }

        //comparatives/superlatives
        if (!Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).options).contains(Lesson.lessonOptions.COMP_SUP)){
            compSupDiv.setVisibility(View.GONE);
            compSupTitle.setVisibility(View.GONE);
            comparativesView.setVisibility(View.GONE);
            superlativesView.setVisibility(View.GONE);
        }

        //Verb tense
        if (!Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).options).contains(Lesson.lessonOptions.CHOSEN_TENSE)) {
            verbTenseDiv.setVisibility(View.GONE);
            verbTenseTitle.setVisibility(View.GONE);
            pastView.setVisibility(View.GONE);
            presentView.setVisibility(View.GONE);
            futureView.setVisibility(View.GONE);
        }

        //Sentence type
        if (!Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).options).contains(Lesson.lessonOptions.VERB_FORM)){
            verbFormDiv.setVisibility(View.GONE);
            verbFormTitle.setVisibility(View.GONE);
            posStatementsView.setVisibility(View.GONE);
            negStatementsView.setVisibility(View.GONE);
            posQuestionsView.setVisibility(View.GONE);
            negQuestionsView.setVisibility(View.GONE);
        }

        //Preposition object
        if (!Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).options).contains(Lesson.lessonOptions.PREP_OBJECT)){
            prepObjDiv.setVisibility(View.GONE);
            prepObjTitle.setVisibility(View.GONE);
            pronounsView.setVisibility(View.GONE);
            nounsView.setVisibility(View.GONE);
        }

        //Check accents
        setCheckAccentsSwitch(checkAccentsSwitchView.isChecked(), checkAccentsSwitchView);
        checkAccentsSwitchView.setOnCheckedChangeListener((buttonView, isChecked) -> setCheckAccentsSwitch(isChecked, checkAccentsSwitchView));
    }

    //User interaction -----------------------------------------------------------------------------

    /**When the submit button is clicked, the options values are set based on what it on-screen.
     * If any fields have been left blank, the user is prompted (toast) to make a choice. When all
     * relevant fields have values, the values are passed to the LessonActivity, which is activated.*/
    public void submitOptions(View v){

        //Apply settings
        if (vocabListSizeView.getVisibility() != View.GONE){
            if (vocabListSizeView.getText().toString().equals("")){
                vocabListSize = fullVocabList.size();
            } else {
                vocabListSize = Math.min(Integer.parseInt(vocabListSizeView.getText().toString()), fullVocabList.size());
            }
        }

        if (largestNumberView.getVisibility() != View.GONE){
            if (largestNumberView.getText().toString().equals("")){
                largestNumber = GrammarGd.largestTranslatableNumber;
            } else {
                largestNumber = Math.min(Integer.parseInt(largestNumberView.getText().toString()), GrammarGd.largestTranslatableNumber);
            }
        }

        if (sentenceOptionsView.getVisibility() != View.GONE){
            if (sentenceFullView.isChecked()){
                sentenceType = "full";
            } else if (sentenceBlankView.isChecked()){
                sentenceType = "blanks";
            } else if (sentenceQAView.isChecked()){
                sentenceType = "qa";
            } else {
                Toast.makeText(v.getContext(),"Please select the question type", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (genderOptionsView.getVisibility() != View.GONE){
            if (genderAdjView.isChecked()){
                genderType = "adj";
            } else if (genderDefArtView.isChecked()){
                genderType = "defArt";
            } else {
                Toast.makeText(v.getContext(),"Please select a gender of nouns option", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (compSupDiv.getVisibility() != View.GONE){
            if (!comparativesView.isChecked() && !superlativesView.isChecked()){
                Toast.makeText(v.getContext(),"Please select at least one of: comparatives or superlatives", Toast.LENGTH_SHORT).show();
                return;
            } else {
                comparatives = comparativesView.isChecked();
                superlatives = superlativesView.isChecked();
            }
        }

        if (verbTenseDiv.getVisibility() != View.GONE){
            if (!pastView.isChecked() && !presentView.isChecked() && !futureView.isChecked()){
                Toast.makeText(v.getContext(),"Please select at least one tense", Toast.LENGTH_SHORT).show();
                return;
            } else {
                past = pastView.isChecked();
                present = presentView.isChecked();
                future = futureView.isChecked();
            }
        }

        if (verbFormDiv.getVisibility() != View.GONE){
            if (!posStatementsView.isChecked() && !negStatementsView.isChecked() && !posQuestionsView.isChecked() && !negQuestionsView.isChecked()){
                Toast.makeText(v.getContext(),"Please select at least one sentence type (positive/negative, statement/question)", Toast.LENGTH_SHORT).show();
                return;
            } else {
                posStatements = posStatementsView.isChecked();
                negStatements = posStatementsView.isChecked();
                posQuestions = posQuestionsView.isChecked();
                negQuestions = negQuestionsView.isChecked();
            }
        }

        if (prepObjDiv.getVisibility() != View.GONE){
            if (!pronounsView.isChecked() && !nounsView.isChecked()){
                Toast.makeText(v.getContext(),"Please select at least one of: pronouns or nouns", Toast.LENGTH_SHORT).show();
                return;
            } else {
                pronouns = pronounsView.isChecked();
                nouns = nounsView.isChecked();
            }
        }

        //Go to lesson!
        Intent i = new Intent(this, LessonActivity.class);
        passOptionsToLesson(i);
        startActivity(i);
    }

    private void setCheckAccentsSwitch(boolean isChecked, SwitchMaterial switchView){
        checkAccents = isChecked;
        if (isChecked){
            switchView.setText(R.string.check_accents_switch_text_yes);
        } else {
            switchView.setText(R.string.check_accents_switch_text_no);
        }
    }

    /**Based on the Switch value, sets the text for the Switch view to the appropriate value for
     * translation direction, and sets the lesson options value*/
    private void setTranslationDirection(boolean isChecked, SwitchMaterial switchView, String lessonID){
        translateFromGaelic = isChecked;
        if (isChecked){
            if (Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).options).contains(Lesson.lessonOptions.TRANSLATE_WORDS)){
                switchView.setText(R.string.gd_en);
            } else if (Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).options).contains(Lesson.lessonOptions.TRANSLATE_NUMBERS)){
                switchView.setText(R.string.gd_dg);
            } else if (Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).options).contains(Lesson.lessonOptions.TRANSLATE_GENERIC)){
                switchView.setText(R.string.from_gd);
            }

        } else {
            if (Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).options).contains(Lesson.lessonOptions.TRANSLATE_WORDS)){
                switchView.setText(R.string.en_gd);
            } else if (Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).options).contains(Lesson.lessonOptions.TRANSLATE_NUMBERS)){
                switchView.setText(R.string.dg_gd);
            } else if (Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lessonID)).options).contains(Lesson.lessonOptions.TRANSLATE_GENERIC)){
                switchView.setText(R.string.from_en);
            }
        }
    }

    /**Get the names of the vocab files which contain the specified columns
     * @param requiredColumns The columns which must all be present in a CSV file for it to be included in the returned set */
    public void setValidVocabFiles(Set<String> requiredColumns) {
        validVocabFileNames = new ArrayList<>();
        if (requiredColumns.size()!=0){
            try {
                for (String s : Objects.requireNonNull(getAssets().list(""))) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open(s)))) {
                        Set<String> fileColumns = new HashSet<>(Arrays.asList(br.readLine().split(",")));
                        if (fileColumns.containsAll(requiredColumns)) {
                            validVocabFileNames.add(s);
                        }
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        Log.d("Error","Unable to open file " + s);
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.d("Error","Unable to list assets");
            }
        }
    }

    /**Finds all views for the OptionsActivity.*/
    private void setAllViews(){
        //Views: user input ----------------------------------------------------------------------------
        vocabSpinnerView = findViewById(R.id.vocab_list_spinner);
        vocabListSizeView = findViewById(R.id.vocab_list_size);
        largestNumberView = findViewById(R.id.largest_number);
        translationSwitchView = findViewById(R.id.translation_switch);
        sentenceOptionsView = findViewById(R.id.sentence_options);
        sentenceFullView = findViewById(R.id.full_sentence);
        sentenceBlankView = findViewById(R.id.fill_in_blanks);
        sentenceQAView = findViewById(R.id.q_and_a);
        genderOptionsView = findViewById(R.id.gender_mode_options);
        genderAdjView = findViewById(R.id.gender_adj);
        genderDefArtView = findViewById(R.id.gender_def_art);
        comparativesView = findViewById(R.id.comparatives);
        superlativesView = findViewById(R.id.superlatives);
        pastView = findViewById(R.id.past);
        presentView = findViewById(R.id.present);
        futureView = findViewById(R.id.future);
        posStatementsView = findViewById(R.id.positive_statements);
        negStatementsView = findViewById(R.id.negative_statements);
        posQuestionsView = findViewById(R.id.positive_questions);
        negQuestionsView = findViewById(R.id.negative_questions);
        pronounsView = findViewById(R.id.pronouns);
        nounsView = findViewById(R.id.nouns);
        checkAccentsSwitchView = findViewById(R.id.check_accents_switch);

        //Views: Titles --------------------------------------------------------------------------------
        lessonTitle = findViewById(R.id.lesson_name);
        translationDirectionTitle = findViewById(R.id.translation_title);
        vocabTitle = findViewById(R.id.vocab_list_spinner_title);
        vocabListSizeTitle = findViewById(R.id.vocab_list_size_title);
        largestNumberTitle = findViewById(R.id.largest_number_title);
        sentenceTitle = findViewById(R.id.sentence_title);
        genderTitle = findViewById(R.id.gender_mode_title);
        compSupTitle = findViewById(R.id.comp_sup_title);
        verbTenseTitle = findViewById(R.id.verb_tense_title);
        verbFormTitle = findViewById(R.id.verb_form_title);
        prepObjTitle = findViewById(R.id.obj_prep);

        //Views : Section Dividers ---------------------------------------------------------------------
        translateDiv = findViewById(R.id.translation_divider);
        vocabDiv = findViewById(R.id.vocab_divider);
        largestNumberDiv = findViewById(R.id.largest_number_divider);
        sentenceDiv = findViewById(R.id.sentence_divider);
        genderDiv = findViewById(R.id.gender_divider);
        compSupDiv = findViewById(R.id.comp_sup_divider);
        verbTenseDiv = findViewById(R.id.verb_tense_divider);
        verbFormDiv = findViewById(R.id.verb_form_divider);
        prepObjDiv = findViewById(R.id.obj_prep_divider);
    }

    /**Adds all the options values as extended data for the intent.*/
    private void passOptionsToLesson(Intent i){
        i.putExtra("lessonID",lessonID);
        i.putExtra("vocabListName", vocabListName);
        i.putExtra("vocabListSize", vocabListSize);
        i.putExtra("largestNumber",largestNumber);
        i.putExtra("translateFromGaelic", translateFromGaelic);
        i.putExtra("sentenceType", sentenceType);
        i.putExtra("genderType", genderType);
        i.putExtra("comparatives", comparatives);
        i.putExtra("superlatives", superlatives);
        i.putExtra("past", past);
        i.putExtra("present", present);
        i.putExtra("future", future);
        i.putExtra("posStatements", posStatements);
        i.putExtra("negStatements", negStatements);
        i.putExtra("posQuestions", posQuestions);
        i.putExtra("negQuestions", negQuestions);
        i.putExtra("pronouns", pronouns);
        i.putExtra("nouns", nouns);

        Log.i("Options", "lessonID = " + lessonID);
        Log.i("Options", "translateFromGaelic = " + translateFromGaelic);
        Log.i("Options", "vocabListName = " + vocabListName);
        Log.i("Options", "vocabListSize = " + vocabListSize);
        Log.i("Options", "largestNumber = " + largestNumber);
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
        Log.i("Options","checkAccents = " + checkAccents);
    }
}