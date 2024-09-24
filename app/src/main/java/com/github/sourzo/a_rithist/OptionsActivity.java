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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sourzo.a_rithist.gaidhlig.GrammarGd;
import com.github.sourzo.a_rithist.gaidhlig.LessonInfo;
import com.github.sourzo.a_rithist.general.AndroidAppRes;
import com.github.sourzo.a_rithist.general.Lesson;
import com.github.sourzo.a_rithist.general.LessonOptions;
import com.github.sourzo.a_rithist.general.VocabTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
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
    RadioGroup translationOptionsView;
    RadioButton toGaelicButtonView;
    RadioButton fromGaelicButtonView;
    RadioGroup responseTypeOptionsView;
    RadioButton responseFullView;
    RadioButton responseBlankView;
    RadioButton responseQAView;
    CheckBox genderDefArtNomView;
    CheckBox genderAdjView;
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
    LinearLayout checkAccentsSwitchContainerView;
    TextView checkAccentsReactiveTextView;

    //Views: Titles --------------------------------------------------------------------------------
    TextView lessonTitle;
    TextView translationDirectionTitle;
    TextView vocabTitle;
    TextView vocabListSizeTitle;
    TextView largestNumberTitle;
    TextView responseTypeTitle;
    TextView genderTitle;
    TextView compSupTitle;
    TextView verbTenseTitle;
    TextView sentenceTypeTitle;
    TextView prepObjTitle;

    //Views : Section Dividers ---------------------------------------------------------------------
    View translateDiv;
    View vocabDiv;
    View largestNumberDiv;
    View responseTypeDiv;
    View genderDiv;
    View compSupDiv;
    View verbTenseDiv;
    View sentenceTypeDiv;
    View prepObjDiv;

    //Options values -------------------------------------------------------------------------------
    LessonOptions lo;
    List<String> validVocabFileNames;
    VocabTable fullVocabList;
    AndroidAppRes androidAppRes;

    //Activity setup -------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lo = new LessonOptions();
        androidAppRes = new AndroidAppRes(this.getApplication().getAssets());
        setContentView(R.layout.activity_options);
        setAllViews();

        //Get info from main menu selection
        Intent i = getIntent();
        lo.lessonID = i.getStringExtra("lessonID");
        lessonTitle.setText(Objects.requireNonNull(LessonInfo.lessonSet.get(lo.lessonID)).displayName);

        //Vocab list spinner: Change max number of words as switch is pressed
        setValidVocabFiles(new HashSet<>(Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lo.lessonID)).requiredColumns)));

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
                    fullVocabList = new VocabTable(androidAppRes, validVocabFileNames.get(position));
                    lo.vocabListName = validVocabFileNames.get(position);

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
                    fullVocabList = new VocabTable(androidAppRes, validVocabFileNames.get(0));
                    lo.vocabListName = validVocabFileNames.get(0);
                    long maxWords = fullVocabList.size();
                    vocabListSizeView.setHint(String.valueOf(maxWords));
                    String prompt = getString(R.string.vocab_list_size_title, maxWords);
                    vocabListSizeTitle.setText(prompt);
                }
            });
        }

        //translating numbers - max number field
        if (Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lo.lessonID)).options).contains(Lesson.lessonOptions.MAX_NUM)){
            String prompt = getString(R.string.largest_number_title, GrammarGd.largestTranslatableNumber);
            largestNumberTitle.setText(prompt);
            largestNumberView.setHint(String.valueOf(GrammarGd.largestTranslatableNumber));

        } else {
            largestNumberDiv.setVisibility(View.GONE);
            largestNumberTitle.setVisibility(View.GONE);
            largestNumberView.setVisibility(View.GONE);
        }

        //translation direction
        if (!Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lo.lessonID)).options).contains(Lesson.lessonOptions.TRANSLATE)){
            translateDiv.setVisibility(View.GONE);
            translationDirectionTitle.setVisibility(View.GONE);
            translationOptionsView.setVisibility(View.GONE);
        } else if (Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lo.lessonID)).options).contains(Lesson.lessonOptions.TRANSLATE_NUMBERS)){
            toGaelicButtonView.setText(R.string.dg_gd);
            fromGaelicButtonView.setText(R.string.en_gd);
        } else if (Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lo.lessonID)).options).contains(Lesson.lessonOptions.TRANSLATE_GENERIC)){
            toGaelicButtonView.setText(R.string.from_en);
            fromGaelicButtonView.setText(R.string.from_gd);
        }

        //Sentence type
        if (!Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lo.lessonID)).options).contains(Lesson.lessonOptions.SENTENCE_QA)){
            if (!Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lo.lessonID)).options).contains(Lesson.lessonOptions.SENTENCE)){
                responseTypeDiv.setVisibility(View.GONE);
                responseTypeTitle.setVisibility(View.GONE);
                responseTypeOptionsView.setVisibility(View.GONE);
            } else {
                responseQAView.setVisibility(View.GONE);
            }
        }

        //Gender options
        if (!Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lo.lessonID)).options).contains(Lesson.lessonOptions.GENDER_MODE)){
            genderDiv.setVisibility(View.GONE);
            genderTitle.setVisibility(View.GONE);
            genderAdjView.setVisibility(View.GONE);
            genderDefArtNomView.setVisibility(View.GONE);
        }

        //comparatives/superlatives
        if (!Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lo.lessonID)).options).contains(Lesson.lessonOptions.COMP_SUP)){
            compSupDiv.setVisibility(View.GONE);
            compSupTitle.setVisibility(View.GONE);
            comparativesView.setVisibility(View.GONE);
            superlativesView.setVisibility(View.GONE);
        }

        //Verb tense
        if (!Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lo.lessonID)).options).contains(Lesson.lessonOptions.CHOSEN_TENSE)) {
            verbTenseDiv.setVisibility(View.GONE);
            verbTenseTitle.setVisibility(View.GONE);
            pastView.setVisibility(View.GONE);
            presentView.setVisibility(View.GONE);
            futureView.setVisibility(View.GONE);
        }

        //Sentence type
        if (!Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lo.lessonID)).options).contains(Lesson.lessonOptions.SENTENCE_TYPE)){
            sentenceTypeDiv.setVisibility(View.GONE);
            sentenceTypeTitle.setVisibility(View.GONE);
            posStatementsView.setVisibility(View.GONE);
            negStatementsView.setVisibility(View.GONE);
            posQuestionsView.setVisibility(View.GONE);
            negQuestionsView.setVisibility(View.GONE);
        }

        //Preposition object
        if (!Arrays.asList(Objects.requireNonNull(LessonInfo.lessonSet.get(lo.lessonID)).options).contains(Lesson.lessonOptions.PREP_OBJECT)){
            prepObjDiv.setVisibility(View.GONE);
            prepObjTitle.setVisibility(View.GONE);
            pronounsView.setVisibility(View.GONE);
            nounsView.setVisibility(View.GONE);
        }

        //Check accents
        setYNSwitch(checkAccentsSwitchView.isChecked(), checkAccentsReactiveTextView);
        checkAccentsSwitchView.setOnCheckedChangeListener((buttonView, isChecked) -> setYNSwitch(isChecked, checkAccentsReactiveTextView));
    }

    //User interaction -----------------------------------------------------------------------------

    /**When the submit button is clicked, the options values are set based on what it on-screen.
     * If any fields have been left blank, the user is prompted (toast) to make a choice. When all
     * relevant fields have values, the values are passed to the LessonActivity, which is activated.*/
    public void submitOptions(View v){

        //Apply settings
        if (vocabListSizeView.getVisibility() != View.GONE){
            if (vocabListSizeView.getText().toString().equals("")){
                lo.vocabListSize = fullVocabList.size();
            } else {
                lo.vocabListSize = Math.min(Integer.parseInt(vocabListSizeView.getText().toString()), fullVocabList.size());
            }
        }

        if (largestNumberView.getVisibility() != View.GONE){
            if (largestNumberView.getText().toString().equals("")){
                lo.largestNumber = GrammarGd.largestTranslatableNumber;
            } else {
                lo.largestNumber = Math.min(Integer.parseInt(largestNumberView.getText().toString()), GrammarGd.largestTranslatableNumber);
            }
        }

        if (translationOptionsView.getVisibility() != View.GONE){
            lo.translateFromGaelic = fromGaelicButtonView.isChecked();
        }

        if (responseTypeOptionsView.getVisibility() != View.GONE){
            if (responseFullView.isChecked()){
                lo.responseType = LessonOptions.ResponseType.FULL_SENTENCE;
            } else if (responseBlankView.isChecked()){
                lo.responseType = LessonOptions.ResponseType.BLANKS;
            } else if (responseQAView.isChecked()){
                lo.responseType = LessonOptions.ResponseType.Q_AND_A;
            } else {
                Toast.makeText(v.getContext(),"Please select the response type", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (genderDiv.getVisibility() != View.GONE){
           if (!genderAdjView.isChecked() && !genderDefArtNomView.isChecked()){
                Toast.makeText(v.getContext(),"Please select a gender of nouns option", Toast.LENGTH_SHORT).show();
                return;
           } else {
               lo.genderAdj = genderAdjView.isChecked();
               lo.genderDefArtNom = genderDefArtNomView.isChecked();
           }
        }

        if (compSupDiv.getVisibility() != View.GONE){
            if (!comparativesView.isChecked() && !superlativesView.isChecked()){
                Toast.makeText(v.getContext(),"Please select at least one of: comparatives or superlatives", Toast.LENGTH_SHORT).show();
                return;
            } else {
                lo.comparatives = comparativesView.isChecked();
                lo.superlatives = superlativesView.isChecked();
            }
        }

        if (verbTenseDiv.getVisibility() != View.GONE){
            if (!pastView.isChecked() && !presentView.isChecked() && !futureView.isChecked()){
                Toast.makeText(v.getContext(),"Please select at least one tense", Toast.LENGTH_SHORT).show();
                return;
            } else {
                lo.past = pastView.isChecked();
                lo.present = presentView.isChecked();
                lo.future = futureView.isChecked();
            }
        }

        if (sentenceTypeDiv.getVisibility() != View.GONE){
            if (!posStatementsView.isChecked() && !negStatementsView.isChecked() && !posQuestionsView.isChecked() && !negQuestionsView.isChecked()){
                Toast.makeText(v.getContext(),"Please select at least one sentence type (positive/negative, statement/question)", Toast.LENGTH_SHORT).show();
                return;
            } else {
                lo.posStatements = posStatementsView.isChecked();
                lo.negStatements = posStatementsView.isChecked();
                lo.posQuestions = posQuestionsView.isChecked();
                lo.negQuestions = negQuestionsView.isChecked();
            }
        }

        if (prepObjDiv.getVisibility() != View.GONE){
            if (!pronounsView.isChecked() && !nounsView.isChecked()){
                Toast.makeText(v.getContext(),"Please select at least one of: pronouns or nouns", Toast.LENGTH_SHORT).show();
                return;
            } else {
                lo.pronouns = pronounsView.isChecked();
                lo.nouns = nounsView.isChecked();
            }
        }

        //Go to lesson!
        Intent i = new Intent(this, LessonActivity.class);
        passOptionsToLesson(i);
        startActivity(i);
    }

    private void setYNSwitch(boolean isChecked, TextView reactiveText){
        lo.checkAccents = isChecked;
        if (isChecked){
            reactiveText.setText(R.string.check_accents_switch_text_yes);
        } else {
            reactiveText.setText(R.string.check_accents_switch_text_no);
        }
    }

    /**Get the names of the vocab files which contain the specified columns
     * @param requiredColumns The columns which must all be present in a CSV file for
     *                        it to be included in the returned set */
    public void setValidVocabFiles(Set<String> requiredColumns) {
        validVocabFileNames = new ArrayList<>();
        if (requiredColumns.size()!=0){
            try {
                for (String s : Objects.requireNonNull(getAssets().list(""))) {
                    if (!lo.lessonID.equals("where_in") || s.startsWith("places_")) { //some lessons need the "places" vocab lists, or the random sentences will make less sense than usual
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open(s)))) {
                            Set<String> fileColumns = new HashSet<>(Arrays.asList(br.readLine().split(",")));
                            if (fileColumns.containsAll(requiredColumns)) {
                                validVocabFileNames.add(s);
                            }
                        } catch (FileNotFoundException e) {
                            Log.d("Error","Unable to open file " + s);
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
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
        translationOptionsView = findViewById(R.id.translation_options);
        toGaelicButtonView = findViewById(R.id.toGaelicButton);
        fromGaelicButtonView = findViewById(R.id.fromGaelicButton);
        responseTypeOptionsView = findViewById(R.id.response_type_options);
        responseFullView = findViewById(R.id.full_sentence);
        responseBlankView = findViewById(R.id.fill_in_blanks);
        responseQAView = findViewById(R.id.q_and_a);
        genderAdjView = findViewById(R.id.gender_adj);
        genderDefArtNomView = findViewById(R.id.gender_def_art_nom);
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
        checkAccentsSwitchContainerView = findViewById(R.id.check_accents_switch_container);
        checkAccentsReactiveTextView = findViewById(R.id.check_accents_reactive_text);

        //Views: Titles --------------------------------------------------------------------------------
        lessonTitle = findViewById(R.id.lesson_name);
        translationDirectionTitle = findViewById(R.id.translation_title);
        vocabTitle = findViewById(R.id.vocab_list_spinner_title);
        vocabListSizeTitle = findViewById(R.id.vocab_list_size_title);
        largestNumberTitle = findViewById(R.id.largest_number_title);
        responseTypeTitle = findViewById(R.id.response_type_title);
        genderTitle = findViewById(R.id.gender_mode_title);
        compSupTitle = findViewById(R.id.comp_sup_title);
        verbTenseTitle = findViewById(R.id.verb_tense_title);
        sentenceTypeTitle = findViewById(R.id.sentence_type_title);
        prepObjTitle = findViewById(R.id.obj_prep_title);

        //Views : Section Dividers ---------------------------------------------------------------------
        translateDiv = findViewById(R.id.translation_divider);
        vocabDiv = findViewById(R.id.vocab_divider);
        largestNumberDiv = findViewById(R.id.largest_number_divider);
        responseTypeDiv = findViewById(R.id.response_type_divider);
        genderDiv = findViewById(R.id.gender_divider);
        compSupDiv = findViewById(R.id.comp_sup_divider);
        verbTenseDiv = findViewById(R.id.verb_tense_divider);
        sentenceTypeDiv = findViewById(R.id.sentence_type_divider);
        prepObjDiv = findViewById(R.id.obj_prep_divider);
    }

    /**Adds all the options values as extended data for the intent.*/
    private void passOptionsToLesson(Intent i){
        i.putExtra("lessonOptions", (Serializable) lo);

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