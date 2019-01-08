package com.avneetksethi.emojichecker;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import static com.avneetksethi.emojichecker.R.style.MyDialogTheme;

public class ChangePassword extends Fragment {
    private EditText etcurrentPassword;
    private EditText etnewPassword;
    private Button saveChangesButton;
    private TextView changePassTitletv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        etcurrentPassword = (EditText) view.findViewById(R.id.currentPassword);
        etnewPassword = (EditText) view.findViewById(R.id.newPassword);
        saveChangesButton = (Button) view.findViewById(R.id.saveChangesButton);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        changePassTitletv = (TextView) view.findViewById(R.id.changePassTitletv);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Shink.ttf");
        changePassTitletv.setTypeface(typeface);

        return view;
    }

    public void changePassword() {
        final String currentPassword = etcurrentPassword.getText().toString();
        final String newPassword = etnewPassword.getText().toString();
        final RequestQueue queue = Volley.newRequestQueue(getActivity());

        final StringRequest sr = new StringRequest(Request.Method.POST, "http://www.emoji-survey.me/auth/password/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                queue.stop();
                VolleyLog.v("Response:%n %s", response);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), MyDialogTheme);
                builder.setMessage("Password Changed")
                        .setPositiveButton("Ok", null)
                        .create()
                        .show();

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                error.printStackTrace();
                queue.stop();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Error")
                        .setNegativeButton("Retry", null)
                        .create()
                        .show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                //Add the data to send to the server.
                Map<String, String> params = new HashMap<String, String>();
                params.put("new_password", newPassword);
                params.put("current_password", currentPassword);
                return params;
            }

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