package com.adg.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.adg.todolist.adapters.TaskListAdapter;
import com.adg.todolist.fragments.TaskFormFragment;
import com.adg.todolist.fragments.TaskListFragment;
import com.adg.todolist.models.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

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

    private void setFragment(int containerId, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment);
        fragmentTransaction.commit();
    }
}