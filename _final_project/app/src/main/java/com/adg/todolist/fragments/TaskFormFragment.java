package com.adg.todolist.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.adg.todolist.MainActivity;
import com.adg.todolist.R;
import com.adg.todolist.models.Task;

public class TaskFormFragment extends Fragment
{
    private int action_type;

    private Task task = null;
    private int task_binding_position = -1;

    private Listener listener;
    private EditText taskTitleET;
    private EditText taskDescriptionET;
    private CheckBox taskUrgencyCB;
    private Button doneButton;

    public interface Listener
    {
        /*
         * When a new task is created
         * this method should be called
         */
        void onDoneButtonClick(Task task);
        /*
         * When a task is just edited
         * this method should be called
         */
        void onDoneButtonClick(Task task, int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_task_form, container, false);

        taskTitleET = v.findViewById(R.id.fragTaskForm_taskTitleEditText);
        taskDescriptionET = v.findViewById(R.id.fragTaskForm_taskDescriptionEditText);
        taskUrgencyCB = v.findViewById(R.id.fragTaskForm_checkBox);
        doneButton = v.findViewById(R.id.fragTaskForm_doneButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTitle = taskTitleET.getText().toString();
                String taskDescription = taskDescriptionET.getText().toString();
                int taskUrgency = taskUrgencyCB.isChecked() ? Task.URGENT : Task.NORMAL;

                if (action_type == MainActivity.EDIT_TASK)
                    listener.onDoneButtonClick(new Task(taskTitle, taskDescription, taskUrgency, task.getStatus()), task_binding_position);
                else
                    listener.onDoneButtonClick(new Task(taskTitle, taskDescription, taskUrgency));
            }
        });

        setupTaskFields();

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof TaskFormFragment.Listener) {
            listener = (TaskFormFragment.Listener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement TaskFormFragment.Listener ");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getBundleArguments();
    }

    private void setupTaskFields() {
        if (task != null) {
            taskTitleET.setText(task.getTitle());
            taskDescriptionET.setText(task.getDescription());
            taskUrgencyCB.setChecked(task.getType() == Task.URGENT);
        }
    }

    private void getBundleArguments() {
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            action_type = bundle.getInt(MainActivity.ACTION_TYPE_KEY);

            if (action_type == MainActivity.EDIT_TASK) {
                String taskSerialized = bundle.getString(MainActivity.TASK_DATA_KEY);

                task = MainActivity.getGson().fromJson(taskSerialized, Task.class);
                task_binding_position = bundle.getInt(MainActivity.TASK_BINDING_POSITION_KEY);
            }
        }
    }
}