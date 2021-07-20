package com.adg.todolist.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.adg.todolist.R;
import com.adg.todolist.models.Task;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private ArrayList<Task> tasks;

    public TaskListAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
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

    public class TaskViewHolder extends RecyclerView.ViewHolder
    {
        private CardView taskCardView;
        private CheckBox checkBox;
        private TextView taskTitle;

        private int taskPosition;

        public TaskViewHolder(View itemView) {
            super(itemView);

            taskCardView = itemView.findViewById(R.id.task_cardView);
            checkBox = itemView.findViewById(R.id.task_checkBox);
            taskTitle = itemView.findViewById(R.id.task_taskTitle);

            TaskStatusOnClickListener taskStatusOnClickListener = new TaskStatusOnClickListener();
            taskCardView.setOnClickListener(taskStatusOnClickListener);
            checkBox.setOnClickListener(taskStatusOnClickListener);

            taskCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    taskCardView.setCardBackgroundColor(taskCardView.getContext()
                            .getResources()
                            .getColor(R.color.white, null));

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

            Log.d("taskStatus", task.getStatus() == 0 ? "on going" : "done");
            Log.d("taskType", task.getType() == 0 ? "urgent" : "normal");
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

        public class TaskStatusOnClickListener implements View.OnClickListener
        {
            @Override
            public void onClick(View v) {
                Task clickedTask = getTask((String) ((TextView)v.findViewById(R.id.task_taskTitle)).getText());

                clickedTask.setStatus(1 - clickedTask.getStatus());

                updateCheckBox(clickedTask);
                updateCardView(clickedTask);
            }

            // Find out what task has been clicked
            private Task getTask(String title) {
                for (Task t : tasks) {
                    if (t.getTitle().equals(title)) {
                        return t;
                    }
                }
                return null;
            }
        }
    }
}
