package com.lort.mail;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private List<Task> taskList;
    Task task;

    public TaskAdapter(List<Task> tasks) { taskList = tasks; }

    @Override
    public TaskAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_main, parent, false);
        return new TaskAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TaskAdapter.MyViewHolder holder, int position) {
        task = taskList.get(position);
        holder.name.setText(task.getName());
        holder.address.setText(task.getAddress());
        holder.time.setText(task.getTime());
        switch (task.getStatus()) {
            case "wait":
                holder.status.setBackgroundColor(Color.RED);
                break;
            case "progress":
                holder.status.setBackgroundColor(Color.YELLOW);
                break;
            case "done":
                holder.status.setBackgroundColor(Color.GREEN);
                break;
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.view.getContext(), TaskActivity.class);
                intent.putExtra("task", task);
                holder.view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return taskList.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView name;
        public TextView address;
        public LinearLayout status;
        public TextView time;

        public  MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = (TextView) itemView.findViewById(R.id.name_card);
            address = (TextView) itemView.findViewById(R.id.address_card);
            time = (TextView) itemView.findViewById(R.id.time_card);
            status = (LinearLayout) itemView.findViewById(R.id.status_card);
        }
    }

}
