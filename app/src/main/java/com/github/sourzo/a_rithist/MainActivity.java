package com.github.sourzo.a_rithist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.sourzo.a_rithist.general.Lesson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //List of lessons
    ListView topicListView;
    static ArrayList<String> topicList = new ArrayList<>();
    static {
        for (Lesson.TopicTag topic : Lesson.TopicTag.values()) {
            topicList.add(topic.label);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topicListView = findViewById(R.id.lessonList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                topicList
        );
        topicListView.setAdapter(arrayAdapter);

        topicListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent topicIntent = new Intent(this, ListLessonsActivity.class);
            topicIntent.putExtra("topic", topicList.get(position));
            Log.i("Options","Topic = " + topicList.get(position));
            startActivity(topicIntent);
        });
    }
}