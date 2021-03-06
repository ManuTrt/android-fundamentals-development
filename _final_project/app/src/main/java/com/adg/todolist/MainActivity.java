package com.adg.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.adg.todolist.fragments.TaskFormFragment;
import com.adg.todolist.fragments.TaskListFragment;
import com.adg.todolist.models.Task;
import com.google.gson.Gson;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TaskListFragment.Listener, TaskFormFragment.Listener
{
    /*
     * Actiunile posibile de realizat
     * intr-un TaskForm: editare si creare task
     */
    public static final int EDIT_TASK = 0;
    public static final int NEW_TASK = 1;
    /*
     * Key-uri pentru preluarea/introducerea din/in Bundle
     * de date importante pentru un fragment
     */
    public static final String ACTION_TYPE_KEY = "action_type";
    public static final String TASK_DATA_KEY = "task";
    public static final String TASK_BINDING_POSITION_KEY = "task_bind_pos";

    // Un obiect gson pentru seriazliarea datelor spre transmiterea/stocarea acestora
    private static Gson gson = null;
    // Orientarea ecranului
    private int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d("MainActivity", "New taskListFrag -> PORTRAIT");
            setFragment(R.id.actMain_frameLayout, new TaskListFragment());
        } else {
            Log.d("MainActivity", "New taskListFrag -> LANDSCAPE");
            setFragment(R.id.actMain_taskListframeLayout, new TaskListFragment());
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

    private void removeFragment(int containerId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(Objects.requireNonNull(fragmentManager.findFragmentById(containerId)));
        fragmentTransaction.commit();
    }

    //region TaskListFragment.Listener method implementations
    @Override
    public void onTaskClicked(Task task, int position) {
        Log.d("taskClicked", task.toString());

        TaskFormFragment taskFormFragment = new TaskFormFragment();
        String taskSerialized = getGson().toJson(task);

        Bundle bundle = new Bundle();

        bundle.putInt(ACTION_TYPE_KEY, EDIT_TASK);
        bundle.putInt(TASK_BINDING_POSITION_KEY, position);
        bundle.putString(TASK_DATA_KEY, taskSerialized);

        taskFormFragment.setArguments(bundle);

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setFragment(R.id.actMain_frameLayout, taskFormFragment);
        } else {
            setFragment(R.id.actMain_taskFormframeLayout, taskFormFragment);
        }
    }

    @Override
    public void onNewTaskButtonClicked() {
        TaskFormFragment taskFormFragment = new TaskFormFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ACTION_TYPE_KEY, NEW_TASK);

        taskFormFragment.setArguments(bundle);
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setFragment(R.id.actMain_frameLayout, taskFormFragment);
        } else {
            setFragment(R.id.actMain_taskFormframeLayout, taskFormFragment);
        }
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
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setFragment(R.id.actMain_frameLayout, taskListFragment);
        } else {
            setFragment(R.id.actMain_taskListframeLayout, taskListFragment);
            removeFragment(R.id.actMain_taskFormframeLayout);
        }
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
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setFragment(R.id.actMain_frameLayout, taskListFragment);
        } else {
            setFragment(R.id.actMain_taskListframeLayout, taskListFragment);
            removeFragment(R.id.actMain_taskFormframeLayout);
        }
    }
    //endregion
}