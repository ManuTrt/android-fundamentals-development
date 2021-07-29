package com.adg.todolist.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.adg.todolist.R;
import com.adg.todolist.models.Task;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static ArrayList<Task> tasks;
    private static OnTaskClickListener listener;

    public interface OnTaskClickListener
    {
        void onTaskClick(Task task, int position);
    }

    public static void debugTaskPrint(ArrayList<Task> tasks){
        String s = "Tasks:\n";
        for (Task t : tasks) {
            s += "\n" +  new Gson().toJson(t) + "\n";

        }
        Log.d("TaskAdapter", s);
    }

    public TaskListAdapter(ArrayList<Task> tasks, OnTaskClickListener listener) {
        this.tasks = tasks;
        debugTaskPrint(this.tasks);
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TaskViewHolder) holder).update(position);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder
    {
        private CardView taskCardView;
        private CheckBox checkBox;
        private TextView taskTitle;

        public TaskViewHolder(View itemView) {
            super(itemView);

            taskCardView = itemView.findViewById(R.id.task_cardView);
            checkBox = itemView.findViewById(R.id.task_checkBox);
            taskTitle = itemView.findViewById(R.id.task_taskTitle);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task checkedTask = tasks.get(getBindingAdapterPosition());

                    checkedTask.setStatus(1 - checkedTask.getStatus());

                    updateCheckBox(checkedTask);
                    updateCardView(checkedTask);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onTaskClick(tasks.get(getBindingAdapterPosition()), getBindingAdapterPosition());
                }
            });
        }

        public void update(int position) {
            Task task = tasks.get(position);

            updateCardView(task);
            updateTaskTitleTextView(task);
            updateCheckBox(task);
        }

        private void updateCardView(Task task) {
            int color;

            if (task.getStatus() == Task.DONE) {
                color = taskCardView.getContext()
                        .getResources()
                        .getColor(R.color.done_task_color, null);
            } else if (task.getType() == Task.URGENT) {
                color = taskCardView.getContext()
                        .getResources()
                        .getColor(R.color.urgent_task_color, null);
            } else {
                color = taskCardView.getContext()
                        .getResources()
                        .getColor(R.color.normal_task_color, null);
            }
            taskCardView.setCardBackgroundColor(color);
        }

        private void updateTaskTitleTextView(Task task) {
            taskTitle.setText(task.getTitle());
        }

        private void updateCheckBox(Task task) {
            checkBox.setChecked(task.getStatus() == Task.DONE);
        }
    }
}
