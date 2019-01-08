package com.avneetksethi.emojichecker;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_home, container, false);

        final TextView welcomeMessage = (TextView) view.findViewById(R.id.tvWelcomeMessage);

        Intent intent = getActivity().getIntent();
        String name = intent.getStringExtra("name");

        String message = "Welcome, " + name + "!";
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Shink.ttf");
        welcomeMessage.setTypeface(typeface);
        welcomeMessage.setText(message);

        return view;
    }
}

