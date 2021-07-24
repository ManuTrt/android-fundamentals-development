package com.adg.todolist.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adg.todolist.R;
import com.adg.todolist.adapters.TaskListAdapter;
import com.adg.todolist.models.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class TaskListFragment extends Fragment {
    private View view;
    private RecyclerView taskListRecyclerView;
    private FloatingActionButton newTaskButton;
    private ArrayList<Task> taskListArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_task_list, container, false);

        initTaskList();

        newTaskButton = view.findViewById(R.id.fragTaskList_newTaskBtn);
        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewTaskButtonClick(v);
            }
        });

        return view;
    }

    private void initTaskList() {
        taskListRecyclerView = view.findViewById(R.id.fragTaskList_taskList);
        taskListArray = getDummyData();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        TaskListAdapter adapter = new TaskListAdapter(taskListArray);

        taskListRecyclerView.setLayoutManager(layoutManager);
        taskListRecyclerView.setAdapter(adapter);
    }

    private ArrayList<Task> getDummyData() {
        ArrayList<Task> tasks = new ArrayList<>();

        tasks.add(new Task("Doing the laundry", "I have a pile of dirty clothes\nGotta do sth before it becomes a rat nest!"));
        tasks.add(new Task("Get kids from school", "By 14:30", Task.URGENT));
        tasks.add(new Task("Write a CV", "I cant stand this job anymore...\nGotta find something that suits me better"));
        tasks.add(new Task("Pizza for dinner", "No time for cooking"));
        tasks.add(new Task("Going to the movie", "I have a pile of dirty clothes\nGotta do sth before it becomes a rat nest!"));

        return tasks;
    }

    private void addNewTask(Task task) {
        taskListArray.add(task);
        Objects.requireNonNull(taskListRecyclerView.getAdapter()).notifyDataSetChanged();
    }

    public void onNewTaskButtonClick(View view) {
        addNewTask(new Task("Now this is an interesting one!", "By 14:30", Task.URGENT));
    }
}