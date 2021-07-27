package com.adg.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.adg.todolist.fragments.TaskFormFragment;
import com.adg.todolist.fragments.TaskListFragment;
import com.adg.todolist.models.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements TaskListFragment.Listener, TaskFormFragment.Listener
{
    public static final int EDIT_TASK = 0;
    public static final int NEW_TASK = 1;
    public static final String ACTION_TYPE_KEY = "action_type";
    public static final String TASK_DATA_KEY = "task";
    public static final String TASK_BINDING_POSITION_KEY = "task_bind_pos";

    public static Gson gson = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setFragment(R.id.actMain_frameLayout, new TaskListFragment());
        } else {
            setFragment(R.id.actMain_taskListframeLayout, new TaskListFragment());
            setFragment(R.id.actMain_taskFormframeLayout, new TaskFormFragment());
        }
    }

    public static Gson getGson() {
        if (gson == null)
            gson = new Gson();
        return gson;
    }

    private void setFragment(int containerId, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment);
        fragmentTransaction.commit();
    }

    //region TaskListFragment.Listener method implementations
    @Override
    public void onTaskClicked(Task task, int position) {
        Log.d("taskClicked", task.toString());

        TaskFormFragment taskFormFragment = new TaskFormFragment();
        String taskSerialized = MainActivity.getGson().toJson(task);

        Bundle bundle = new Bundle();

        bundle.putInt(ACTION_TYPE_KEY, EDIT_TASK);
        bundle.putInt(TASK_BINDING_POSITION_KEY, position);
        bundle.putString(TASK_DATA_KEY, taskSerialized);

        taskFormFragment.setArguments(bundle);
        setFragment(R.id.actMain_frameLayout, taskFormFragment);
    }

    @Override
    public void onNewTaskButtonClicked() {
        TaskFormFragment taskFormFragment = new TaskFormFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ACTION_TYPE_KEY, NEW_TASK);

        taskFormFragment.setArguments(bundle);
        setFragment(R.id.actMain_frameLayout, taskFormFragment);
    }

    @Override
    public void saveTaskListFragmentData(ArrayList<Task> taskListArray) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Set<String> serializedTasks = new LinkedHashSet<>();

        for (Task task : taskListArray)
            serializedTasks.add(MainActivity.getGson().toJson(task));

        editor.putStringSet(TaskListFragment.RESTORE_TASKLIST_KEY, serializedTasks);
        editor.apply();
    }

    @Override
    public ArrayList<Task> restoreTaskListFragmentData() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        Set<String> tasksSerialized = sharedPreferences.getStringSet(TaskListFragment.RESTORE_TASKLIST_KEY, null);
        ArrayList<Task> taskListArray = new ArrayList<>();

        if (tasksSerialized != null) {
            for (String s : tasksSerialized) {
                Task t = MainActivity.getGson().fromJson(s, Task.class);
                taskListArray.add(t);
            }
        }

        return taskListArray;
    }

    //endregion

    //region TaskFormFragment.Listener method implementations
    @Override
    public void onDoneButtonClick(Task task) {
        Log.d("doneClicked", task.toString());
        TaskListFragment taskListFragment = new TaskListFragment();
        String taskSerialized = getGson().toJson(task);

        Bundle bundle = new Bundle();
        bundle.putInt(ACTION_TYPE_KEY, NEW_TASK);
        bundle.putString(TASK_DATA_KEY, taskSerialized);

        taskListFragment.setArguments(bundle);
        setFragment(R.id.actMain_frameLayout, taskListFragment);
    }

    @Override
    public void onDoneButtonClick(Task task, int position) {
        Log.d("doneClickedEditTask", task.toString());
        TaskListFragment taskListFragment = new TaskListFragment();
        String taskSerialized = getGson().toJson(task);

        Bundle bundle = new Bundle();
        bundle.putInt(ACTION_TYPE_KEY, EDIT_TASK);
        bundle.putInt(TASK_BINDING_POSITION_KEY, position);
        bundle.putString(TASK_DATA_KEY, taskSerialized);

        taskListFragment.setArguments(bundle);
        setFragment(R.id.actMain_frameLayout, taskListFragment);
    }
    //endregion
}