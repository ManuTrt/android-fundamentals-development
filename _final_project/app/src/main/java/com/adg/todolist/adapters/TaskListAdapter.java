package com.adg.todolist.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
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
        void onDeleteIconClick(int position);
        void onSelectTaskToDeletion(Task task);
        void onDeselectTaskFromDeletion(Task task);
    }

    public static void debugTaskPrint(ArrayList<Task> tasks){
        StringBuilder s = new StringBuilder("Tasks:\n");

        int i =0;
        for (Task t : tasks) {
            s.append("\n" + "[").append(i).append("]").append(new Gson().toJson(t)).append("\n");
        }
        Log.d("TaskAdapter", s.toString());
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
        private ImageView deleteTaskView;

        public TaskViewHolder(View itemView) {
            super(itemView);

            taskCardView = itemView.findViewById(R.id.task_cardView);
            checkBox = itemView.findViewById(R.id.task_checkBox);
            taskTitle = itemView.findViewById(R.id.task_taskTitle);
            deleteTaskView = itemView.findViewById(R.id.task_deleteIcon);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task checkedTask = tasks.get(getBindingAdapterPosition());

                    checkedTask.setStatus(1 - checkedTask.getStatus());

                    updateCheckBox(checkedTask);
                    updateCardView(checkedTask);
                }
            });

            deleteTaskView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteIconClick(getBindingAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int taskCardViewColor = taskCardView.getCardBackgroundColor().getDefaultColor();
                    int white = taskCardView.getContext()
                            .getResources()
                            .getColor(R.color.white, null);

                    if (taskCardViewColor != white)
                        listener.onTaskClick(tasks.get(getBindingAdapterPosition()), getBindingAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int taskCardViewColor = taskCardView.getCardBackgroundColor().getDefaultColor();
                    int white = taskCardView.getContext()
                            .getResources()
                            .getColor(R.color.white, null);

                    if (taskCardViewColor == white) {
                        Task task = tasks.get(getBindingAdapterPosition());
                        updateCardView(task);
                        listener.onDeselectTaskFromDeletion(task);
                        checkBox.setVisibility(View.VISIBLE);
                        deleteTaskView.setVisibility(View.VISIBLE);
                    } else {
                        taskCardView.setCardBackgroundColor(white);
                        listener.onSelectTaskToDeletion(tasks.get(getBindingAdapterPosition()));
                        checkBox.setVisibility(View.GONE);
                        deleteTaskView.setVisibility(View.GONE);
                    }
                    return true;
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

            checkBox.setVisibility(View.VISIBLE);
            deleteTaskView.setVisibility(View.VISIBLE);
        }

        private void updateTaskTitleTextView(Task task) {
            taskTitle.setText(task.getTitle());
        }

        private void updateCheckBox(Task task) {
            checkBox.setChecked(task.getStatus() == Task.DONE);
        }
    }
}
