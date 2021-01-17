package com.example.mystudyzone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddTaskFragment extends Fragment {
    private int mDay, mMonth, MYear;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addtask, container, false);
        Spinner spinner_catagory = (Spinner) view.findViewById(R.id.task_catagory);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_catagory = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.task_catagory, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_catagory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_catagory.setAdapter(adapter_catagory);

        Spinner spinner_subject = (Spinner) view.findViewById(R.id.task_Subject);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_subject = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.task_subject, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_subject.setAdapter(adapter_subject);

        /*TextView data_picker = (TextView) view.findViewById(R.id.date_picker);
        data_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calender cal = Calender.getInstance();
            }
        });*/
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
