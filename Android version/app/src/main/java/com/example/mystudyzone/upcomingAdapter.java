package com.example.mystudyzone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class upcomingAdapter extends ArrayAdapter<String> {
    List<Deadline> deadline;
    Context mContext;
    public upcomingAdapter(@NonNull Context context, List<Deadline> deadline) {
        super(context, R.layout.upcoming_item);
        this.mContext = context;
        this.deadline = deadline;
    }

    @Override
    public int getCount() {
        return deadline.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.upcoming_item, parent, false);
            viewHolder.subject = (TextView) convertView.findViewById(R.id.task_subject);
            viewHolder.category = (TextView) convertView.findViewById(R.id.task_catagory);
            viewHolder.date = (TextView) convertView.findViewById(R.id.task_date);
            viewHolder.time = (TextView) convertView.findViewById(R.id.task_time);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.subject.setText(deadline.get(position).subject);
        viewHolder.category.setText(deadline.get(position).category);
        viewHolder.date.setText(deadline.get(position).date);
        viewHolder.time.setText(deadline.get(position).time);
        return convertView;
    }

    static class ViewHolder{
        TextView subject;
        TextView category;
        TextView date;
        TextView time;
    }
}

