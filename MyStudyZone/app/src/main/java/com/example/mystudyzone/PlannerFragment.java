package com.example.mystudyzone;



import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class PlannerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_planner, container, false);

        view.findViewById(R.id.buttonBasic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasicCalFragment fragment = new BasicCalFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment, "planner").commit();
            }
        });

        view.findViewById(R.id.buttonAsynchronous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsynchronousCalFragment fragment = new AsynchronousCalFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment, "planner").commit();
            }
        });
        return view;
    }

}