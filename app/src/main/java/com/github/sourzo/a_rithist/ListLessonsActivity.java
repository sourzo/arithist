package com.github.sourzo.a_rithist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.sourzo.a_rithist.gaidhlig.LessonInfo;
import com.github.sourzo.a_rithist.general.Lesson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ListLessonsActivity extends AppCompatActivity {

    //List of lessons
    ListView lessonListView;
    Lesson.TopicTag topicTag;
    List<String> lessonIDs = new ArrayList<>();
    List<String> lessonDisplayTexts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent topicIntent = getIntent();
        topicTag = Lesson.TopicTag.valueOfLabel(topicIntent.getStringExtra("topic"));

        for (Map.Entry<String, Lesson> lesson : LessonInfo.lessonSet.entrySet()) {
            if (Arrays.asList(lesson.getValue().topics).contains(topicTag)) {
                lessonIDs.add(lesson.getKey());
                lessonDisplayTexts.add(lesson.getValue().displayName);
                Log.i("Info","Lesson added: " + lesson.getValue().displayName);
            }
        }

        lessonListView = findViewById(R.id.lessonList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                lessonDisplayTexts
        );
        lessonListView.setAdapter(arrayAdapter);

        lessonListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent lessonIntent = new Intent(this, OptionsActivity.class);
            lessonIntent.putExtra("lessonID", lessonIDs.get(position));
            startActivity(lessonIntent);
        });
    }
}