package com.adg.todolist.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    private FloatingActionButton deleteTasksButton;
    private ArrayList<Task> taskListArray;
    private Listener listener;

    private ArrayList<Task> toDeleteTasks;

    public interface Listener
    {
        void onTaskClicked(Task task, int position);
        void onNewTaskButtonClicked();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_list, container, false);

        setupTaskList();
        setupNewTaskButton();
        setupDeleteTasksButton();

        Log.d("TaskList", "onCreateView");

        return view;
    }


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TaskList", "onCreate");
        toDeleteTasks = new ArrayList<>();
        taskListArray = restoreTaskListFragmentData();

        getBundleArguments();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("TaskList", "onSaveInstanceState");
        saveTaskListFragmentData(taskListArray);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("TaskList", "onDetach");
        saveTaskListFragmentData(taskListArray);
    }

    private void getBundleArguments() {
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            int action_type = bundle.getInt(MainActivity.ACTION_TYPE_KEY);

            String taskSerialized = bundle.getString(MainActivity.TASK_DATA_KEY);
            Task task = new Gson().fromJson(taskSerialized, Task.class);

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
                // Daca orientarea este de tip landscape, salvarea datelor nu se face onDetach pentru
                // ca aceasta metoda nu este apelata, prin urmare trebuie sa salvam datele si aici
                if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    saveTaskListFragmentData(taskListArray);
                }
                listener.onNewTaskButtonClicked();
            }
        });
    }

    private void setupDeleteTasksButton() {
        deleteTasksButton = view.findViewById(R.id.fragTaskList_deleteTasksBtn);
        deleteTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskListArray.removeAll(toDeleteTasks);
                deleteTasksButton.hide();
                newTaskButton.show();
                toDeleteTasks.clear();
                Objects.requireNonNull(taskListRecyclerView.getAdapter()).notifyDataSetChanged();
            }
        });
        deleteTasksButton.hide();
    }

    private void setupTaskList() {
        taskListRecyclerView = view.findViewById(R.id.fragTaskList_taskList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        TaskListAdapter adapter = new TaskListAdapter(taskListArray, new TaskListAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(Task task, int position) {
                // Daca orientarea este de tip landscape, salvarea datelor nu se face onDetach pentru
                // ca aceasta metoda nu este apelata, prin urmare trebuie sa salvam datele si aici
                if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    saveTaskListFragmentData(taskListArray);
                }

                // Doar daca nu suntem in modul de stergere multipla
                // putem selecta alte taskuri pentru vizualizarea
                // detaliata a acestora
                if (toDeleteTasks.size() == 0) {
                    listener.onTaskClicked(task, position);
                }
            }

            @Override
            public void onDeleteIconClick(int position) {
                taskListArray.remove(position);
                Objects.requireNonNull(taskListRecyclerView.getAdapter()).notifyItemRemoved(position);
            }

            @Override
            public void onAddTaskToDeleteList(Task task) {
                toDeleteTasks.add(task);

                // Daca este primul task adaugat spre stergere
                // se va seta butonul de stergere multipla ca vizibil
                if (toDeleteTasks.size() == 1) {
                    deleteTasksButton.show();
                    newTaskButton.hide();
                }
            }

            @Override
            public void onRemoveTaskFromDeleteList(Task task) {
                toDeleteTasks.remove(task);

                if (toDeleteTasks.size() == 0) {
                    deleteTasksButton.hide();
                    newTaskButton.show();
                }
            }
        });

        taskListRecyclerView.setLayoutManager(layoutManager);
        taskListRecyclerView.setAdapter(adapter);
    }

    private void saveTaskListFragmentData(ArrayList<Task> taskListArray) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(TaskListFragment.RESTORE_TASKLIST_KEY, new Gson().toJson(taskListArray));
        editor.apply();

        Log.d("TaskList", "TaskListDataSaved");
        TaskListAdapter.debugTaskPrint(taskListArray);
    }

    private ArrayList<Task> restoreTaskListFragmentData() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String tasksSerialized = sharedPreferences.getString(TaskListFragment.RESTORE_TASKLIST_KEY, "");
        ArrayList<Task> taskListArray = new ArrayList<>();

        if (!tasksSerialized.equals("")) {
            Type myType = new TypeToken<ArrayList<Task>>() {}.getType();

            taskListArray = new Gson().fromJson(tasksSerialized, myType);
        }

        Log.d("TaskList", "TaskListDataRestored");

        TaskListAdapter.debugTaskPrint(taskListArray);

        return taskListArray;
    }
}