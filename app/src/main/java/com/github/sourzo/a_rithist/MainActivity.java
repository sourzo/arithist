package com.github.sourzo.a_rithist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.sourzo.a_rithist.gaidhlig.LessonInfo;
import com.github.sourzo.a_rithist.general.Lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //List of lessons
    ListView lessonListView;
    static List<String> lessonIDs = new ArrayList<>();
    static List<String> lessonDisplayTexts = new ArrayList<>();
    static {
        for (Map.Entry<String, Lesson> lesson : LessonInfo.lessonSet.entrySet()){
            lessonIDs.add(lesson.getKey());
            lessonDisplayTexts.add(lesson.getValue().displayName);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lessonListView = findViewById(R.id.lessonList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                lessonDisplayTexts);
        lessonListView.setAdapter(arrayAdapter);

        lessonListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(MainActivity.this, OptionsActivity.class);
            i.putExtra("lessonID", lessonIDs.get(position));
            startActivity(i);
        });
    }
}