package com.avneetksethi.emojichecker;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SettingsFragment extends Fragment {
    private Button changePassButton;
    private Button logoutButton;
    private TextView settingsTitletv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Settings");

        settingsTitletv = (TextView) view.findViewById(R.id.settingsTitletv);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Shink.ttf");
        settingsTitletv.setTypeface(typeface);

        changePassButton = (Button) view.findViewById(R.id.changePassButton);

        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ChangePassword();
                replaceFragment(fragment);
            }
        });

        logoutButton = (Button) view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.flMain, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void logout() {
        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        String URL = "http://www.emoji-survey.me/responses/";
        StringRequest sr = new  StringRequest(Request.Method.POST, "http://www.emoji-survey.me/auth/token/logout", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                queue.stop();
                Intent intent = new Intent (getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
            }}, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                //textView.setText("Error Sending Response");
                error.printStackTrace();
                queue.stop();
            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", "Token " + LoginActivity.token);
                return headers;

            }
        };
        queue.add(sr);
    }
}
