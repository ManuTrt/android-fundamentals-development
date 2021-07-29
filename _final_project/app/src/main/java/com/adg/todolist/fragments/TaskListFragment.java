package com.adg.todolist.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adg.todolist.MainActivity;
import com.adg.todolist.R;
import com.adg.todolist.adapters.TaskListAdapter;
import com.adg.todolist.models.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class TaskListFragment extends Fragment
{
    public static final String RESTORE_TASKLIST_KEY = "restore_tasklist";

    private View view;
    private RecyclerView taskListRecyclerView;
    private FloatingActionButton newTaskButton;
    private ArrayList<Task> taskListArray;
    private Listener listener;

    public interface Listener
    {
        void onTaskClicked(Task task, int position);
        void onNewTaskButtonClicked();
        void saveTaskListFragmentData(ArrayList<Task> taskListArray);
        ArrayList<Task> restoreTaskListFragmentData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_list, container, false);

        setupTaskList();
        setupNewTaskButton();

        Log.d("TaskList", "onCreateView");

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TaskList", "onCreate");
        taskListArray = listener.restoreTaskListFragmentData();

        getBundleArguments();
    }

    private void getBundleArguments() {
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            int action_type = bundle.getInt(MainActivity.ACTION_TYPE_KEY);

            String taskSerialized = bundle.getString(MainActivity.TASK_DATA_KEY);
            Task task = MainActivity.getGson().fromJson(taskSerialized, Task.class);

            if (action_type == MainActivity.EDIT_TASK) {
                int task_binding_position = bundle.getInt(MainActivity.TASK_BINDING_POSITION_KEY);

                Log.d("TaskList-Edited", "task: " + taskSerialized + " bind pos: " + String.valueOf(task_binding_position));
                taskListArray.set(task_binding_position, task);
            } else {
                Log.d("TaskList-New", "task: " + taskSerialized);
                taskListArray.add(task);
            }
        }
    }

    private void setupNewTaskButton() {
        newTaskButton = view.findViewById(R.id.fragTaskList_newTaskBtn);
        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNewTaskButtonClicked();
            }
        });
    }

    private void setupTaskList() {
        taskListRecyclerView = view.findViewById(R.id.fragTaskList_taskList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        TaskListAdapter adapter = new TaskListAdapter(taskListArray, new TaskListAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(Task task, int position) {
                listener.onTaskClicked(task, position);
            }
        });

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

//    private void addTask(Task task) {
//        taskListArray.add(task);
//        Objects.requireNonNull(taskListRecyclerView.getAdapter()).notifyDataSetChanged();
//    }
//
//    private void editTask(Task newTaskData, int oldTaskPosition) {
//        taskListArray.set(oldTaskPosition, newTaskData);
//        Objects.requireNonNull(taskListRecyclerView.getAdapter()).notifyItemChanged(oldTaskPosition);
//    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof TaskListFragment.Listener) {
            listener = (TaskListFragment.Listener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement TaskListFragment.Listener ");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("TaskList", "OnDetach");

        listener.saveTaskListFragmentData(taskListArray);

        listener = null;
    }
}