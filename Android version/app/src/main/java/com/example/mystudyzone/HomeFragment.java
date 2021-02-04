package com.example.mystudyzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import android.util.Log;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
        AppDatabase db = AppDatabase.getInstance(getContext());
        DeadlineDao deadlineDao = db.deadlineDao();
        Log.d("Database", "Connented to database");

        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BasicActivity.class);
                startActivity(intent);
            }
        });

        TextView performance = (TextView) view.findViewById(R.id.textView11);

        Date date = new Date();
        SimpleDateFormat DFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateString = DFormat.format(date);
        SimpleDateFormat TFormat = new SimpleDateFormat("HH-mm");
        String currentTimeString = TFormat.format(date);

        if(deadlineDao.getCountAll(currentDateString) != 0){
            String s = String.valueOf(((float)deadlineDao.getCountFinished(currentDateString)/(float)deadlineDao.getCountAll(currentDateString))*100);
            performance.setText(s.substring(0, 2));
        }
        else{
            performance.setText("100");
        }
        List<Deadline> deadlines = deadlineDao.getUpcoming(currentDateString);
        upcomingAdapter upadapter = new upcomingAdapter(getContext(), deadlines);
        ListView deadlineList = view.findViewById(R.id.deadlinesList);
        deadlineList.setAdapter(upadapter);
        return view;
    }
}
