package com.avneetksethi.emojichecker;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QueryConfirmation extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_queryconfirmation, container, false);

        TextView confirmationTextView = (TextView) view.findViewById(R.id.confirmationTextView);
        return view;
    }
}
