package com.example.android.todos;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<todo> arrayList;
    private Context context;
    private DbHandler dbHandler;
    public RecyclerViewAdapter(ArrayList<todo> todoArrayList, Context context) {
        this.arrayList = todoArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        todo td = arrayList.get(position);
//        if (td.isChecked()){
//            holder.checkbox.setEnabled(true);
//        } else {
//            holder.checkbox.setEnabled(false);
//        }
        if(td.newText == null){
            holder.newTask.setText("");
        } else {
            holder.newTask.setText(td.newText);
        }
        holder.taskDate.setText(td.reminderDate);
        holder.taskTime.setText(td.reminderTime);

//        holder.checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isChecked = holder.checkbox.isChecked();
//                if (isChecked){
//                    td.setChecked(true);
//                    holder.newTask.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                    dbHandler.updateTask(td);
//                    Toast.makeText(context, "You completed your task successfully!!!", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (td.isChecked()){
//                        td.setChecked(false);
//                        holder.newTask.setPaintFlags(~ Paint.STRIKE_THRU_TEXT_FLAG);
//                        dbHandler.updateTask(td);
//                    }
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView newTask, taskDate, taskTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newTask = (TextView) itemView.findViewById(R.id.taskDetail);
            taskDate = (TextView) itemView.findViewById(R.id.dateDetail);
            taskTime = (TextView) itemView.findViewById(R.id.timeDetail);
        }
    }
}
