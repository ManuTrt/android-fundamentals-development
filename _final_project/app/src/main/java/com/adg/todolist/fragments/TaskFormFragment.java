package com.adg.todolist.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.adg.todolist.MainActivity;
import com.adg.todolist.R;
import com.adg.todolist.models.Task;
import com.google.gson.Gson;

public class TaskFormFragment extends Fragment
{
    /*
     * Tipurile de actiuni posibile:
     * editare task si creare task nou
     */
    public static final int EDIT_TASK = 0;
    public static final int NEW_TASK = 1;

    // Retinem ce fel de actiune are loc
    private int action_type;

    /*
     * In cazul modificari unui task
     * trebuie retinuta pozitia sa din
     * lista de task-uri pentru a fi ulterior
     * pus in pozitia corecta
     */
    private Task task;
    private int task_binding_position;

    private EditText taskTitleET;
    private EditText taskDescriptionET;
    private CheckBox taskUrgencyCB;
    private Button doneButton;

    /*
     * Listener-ul este cel care
     * va implementa metodele de care
     * fragmentul are nevoie pentru
     * a asigura independenta fata de
     * alte entitati ale aplicatiei
     */
    private Listener listener;

    public interface Listener
    {
        /*
         * Metoda apelata la crearea
         * unui nou task
         */
        void onDoneButtonClick(Task task);
        /*
         * Metoda apelata dupa ce s-a
         * modificat un Task existent
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

                // Un task fara nume nu se accepta
                if (!taskTitle.equals("")) {
                    String taskDescription = taskDescriptionET.getText().toString();
                    int taskUrgency = taskUrgencyCB.isChecked() ? Task.URGENT : Task.NORMAL;

                    if (action_type == EDIT_TASK) {
                        listener.onDoneButtonClick(new Task(taskTitle, taskDescription, taskUrgency, task.getStatus()), task_binding_position);
                    } else if (action_type == NEW_TASK) {
                        listener.onDoneButtonClick(new Task(taskTitle, taskDescription, taskUrgency));
                    }

                    resetForm();
                }
            }
        });

        /*
         * Se populeaza (daca este cazul) campurile cu
         * datele Task-ului in curs de editare
         */
        setupTaskFields();

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof TaskFormFragment.Listener) {
            // MainActivity va fi cel care va implementa metodele cerute de fragment
            listener = (TaskFormFragment.Listener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement TaskFormFragment.Listener ");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Prelucram bundle-ul primit daca este cazul
        getBundleArguments();
    }

    private void setupTaskFields() {
        if (task != null) {
            taskTitleET.setText(task.getTitle());
            taskDescriptionET.setText(task.getDescription());
            taskUrgencyCB.setChecked(task.getType() == Task.URGENT);
        }
    }

    private void resetForm() {
        taskTitleET.setText("");
        taskDescriptionET.setText("");
        taskUrgencyCB.setChecked(false);

        action_type = -1;
        task = null;
        task_binding_position = -1;

        Log.d("TaskForm: ", "Form reseted!");
    }

    private void getBundleArguments() {
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            action_type = bundle.getInt(MainActivity.ACTION_TYPE_KEY);

            if (action_type == MainActivity.EDIT_TASK) {
                String taskSerialized = bundle.getString(MainActivity.TASK_DATA_KEY);

                task = new Gson().fromJson(taskSerialized, Task.class);
                task_binding_position = bundle.getInt(MainActivity.TASK_BINDING_POSITION_KEY);
            }
        }

        setArguments(null);
    }
}