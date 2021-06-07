package com.example.gad.demo.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gad.demo.R;
import com.example.gad.demo.model.Task;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>
{
    private ArrayList<Task> mDataSource;

    public TaskListAdapter(ArrayList<Task> ds) {
        mDataSource = ds;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // load (inflate) the item layout into a variable
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);

        // return a new view holder instance based on the layout
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder
    {
        private TextView mTVTitle;
        private TextView mTVDescription;
        private CheckBox mCBStatus;

        private int     mBoundPosition;

        public TaskViewHolder(@NonNull View item_view) {
            super(item_view);

            mTVTitle        = item_view.findViewById(R.id.itemTask_tvTitle);
            mTVDescription  = item_view.findViewById(R.id.itemTask_tvDescription);
            mCBStatus       = item_view.findViewById(R.id.itemTask_cbStatus);

            mCBStatus.setOnClickListener(new StatusClickListener());
        }

        public void update(int position) {
            mBoundPosition = position;

            Task current_task = mDataSource.get(mBoundPosition);

            mTVTitle.setText(current_task.getTitle());
            mTVDescription.setText(current_task.getDescription());
            mCBStatus.setChecked(current_task.isDone());

            if (current_task.isDone())
                // set strike-through paint flag (or bitwise operation)
                mTVTitle.setPaintFlags(mTVTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            else
                // clear strike-through paint flag (and bitwise operation on inverted value)
                mTVTitle.setPaintFlags(mTVTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        public class StatusClickListener implements View.OnClickListener
        {
            @Override
            public void onClick(View v) {
                Task current_task = mDataSource.get(mBoundPosition);
                current_task.setStatus(!current_task.getStatus());

                mCBStatus.setChecked(current_task.isDone());

                update(mBoundPosition);
            }
        }
    }

}
