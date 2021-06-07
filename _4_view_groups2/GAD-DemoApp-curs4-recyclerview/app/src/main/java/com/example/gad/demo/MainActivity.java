package com.example.gad.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.gad.demo.adapter.TaskListAdapter;
import com.example.gad.demo.model.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView mTaskList;
    private ArrayList<Task> mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get a reference to the recycler view
        mTaskList = findViewById(R.id.actMain_listTasks);
        mTaskList.hasFixedSize();

        // set the layout manager for that recycler view
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        mTaskList.setLayoutManager(llm);

        // create a mocked data source
        mDataSource = new ArrayList<Task>();
        for (int i = 0; i < 30; i++) {
            // generate a task, alternate between status "true" and "false"
            mDataSource.add(new Task("Task #" + i, "Description for task #" + i, (i % 2 == 0)));
        }

        // create a new adapter instance for a data-source
        TaskListAdapter adapter = new TaskListAdapter(mDataSource);

        // set adapter to the recycler view
        mTaskList.setAdapter(adapter);
    }
}
