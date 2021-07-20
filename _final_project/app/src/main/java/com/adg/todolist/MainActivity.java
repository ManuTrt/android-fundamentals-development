package com.adg.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.adg.todolist.adapters.TaskListAdapter;
import com.adg.todolist.models.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskList = findViewById(R.id.actMain_taskList);

        initTaskList();
    }

    private void initTaskList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        TaskListAdapter adapter = new TaskListAdapter(getDummyData());

        taskList.setLayoutManager(layoutManager);
        taskList.setAdapter(adapter);
    }

    private ArrayList<Task> getDummyData() {
        ArrayList<Task> tasks = new ArrayList<>();

        tasks.add(new Task("Doing the laundry", "I have a pile of dirty clothes\nGotta do sth before it becomes a rat nest!"));
        tasks.add(new Task("Get kids from school", "By 14:30", Task.URGENT));
        tasks.add(new Task("Write a CV", "I cant stand this job anymore...\nGotta find something that suits me better"));
        tasks.add(new Task("Pizza for dinner", "No time for cooking"));
        tasks.add(new Task("Going to the movie", "I have a pile of dirty clothes\nGotta do sth before it becomes a rat nest!"));
        tasks.add(new Task("Get kids from school2", "By 14:30", Task.URGENT));
        tasks.add(new Task("Write a CV2", "I cant stand this job anymore...\nGotta find something that suits me better"));
        tasks.add(new Task("Pizza for dinner2", "No time for cooking"));
        tasks.add(new Task("Doing the laundry2", "I have a pile of dirty clothes\nGotta do sth before it becomes a rat nest!"));
        tasks.add(new Task("Get kids from school3", "By 14:30", Task.URGENT));
        tasks.add(new Task("Write a CV3", "I cant stand this job anymore...\nGotta find something that suits me better"));
        tasks.add(new Task("Pizza for dinner3", "No time for cooking"));
        tasks.add(new Task("Doing the laundry3", "I have a pile of dirty clothes\nGotta do sth before it becomes a rat nest!"));
        tasks.add(new Task("Get kids from school4", "By 14:30", Task.URGENT));
        tasks.add(new Task("Write a C4V", "I cant stand this job anymore...\nGotta find something that suits me better"));
        tasks.add(new Task("Pizza for dinner4", "No time for cooking"));
        tasks.add(new Task("Doing the laundry4", "I have a pile of dirty clothes\nGotta do sth before it becomes a rat nest!"));
        tasks.add(new Task("Get kids from school5", "By 14:30", Task.URGENT));
        tasks.add(new Task("Write a CV5", "I cant stand this job anymore...\nGotta find something that suits me better"));
        tasks.add(new Task("Pizza for dinner5", "No time for cooking"));
        tasks.add(new Task("Going to the movie5", "I have a pile of dirty clothes\nGotta do sth before it becomes a rat nest!"));
        tasks.add(new Task("Get kids from school6", "By 14:30", Task.URGENT));
        tasks.add(new Task("Write a CV6", "I cant stand this job anymore...\nGotta find something that suits me better"));
        tasks.add(new Task("Pizza for dinner6", "No time for cooking"));
        tasks.add(new Task("Doing the laundry6", "I have a pile of dirty clothes\nGotta do sth before it becomes a rat nest!"));
        tasks.add(new Task("Get kids from school7", "By 14:30", Task.URGENT));
        tasks.add(new Task("Write a CV7", "I cant stand this job anymore...\nGotta find something that suits me better"));
        tasks.add(new Task("Pizza for dinner7", "No time for cooking"));
        tasks.add(new Task("Doing the laundry7", "I have a pile of dirty clothes\nGotta do sth before it becomes a rat nest!"));
        tasks.add(new Task("Get kids from school8", "By 14:30", Task.URGENT));
        tasks.add(new Task("Write a CV8", "I cant stand this job anymore...\nGotta find something that suits me better"));
        tasks.add(new Task("Pizza for dinner8", "No time for cooking"));

        return tasks;
    }
}