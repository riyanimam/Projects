package com.avneetksethi.emojichecker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextView registerLink;
    private TextView emojicheckertv;
    private Button loginButton;
    public static EditText etUsername;
    public static EditText etPassword;
    public static String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerLink = (TextView) findViewById(R.id.tvRegister);
        emojicheckertv = (TextView) findViewById(R.id.emojicheckertv);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Shink.ttf");
        emojicheckertv.setTypeface(typeface);

        //opens the register/sign up page when the user clicks Sign Up
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    public String loginUser() {
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        final RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

        final StringRequest sr = new StringRequest(Request.Method.POST, "http://www.emoji-survey.me/auth/token/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                queue.stop();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    token = jsonObject.getString("auth_token");
                    Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                    intent.putExtra("name", username); //Launch Home Screen
                    LoginActivity.this.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }}, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                error.printStackTrace();
                queue.stop();
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.MyDialogTheme);
                builder.setMessage("Login Failed")
                        .setNegativeButton("Retry", null)
                        .create()
                        .show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                //Add the data to send to the server.
                Map<String, String> params = new HashMap<String, String>();
                params.put("password", password);
                params.put("username", username);
                return params;
            }

        };
        queue.add(sr);
        return token;
    }

}