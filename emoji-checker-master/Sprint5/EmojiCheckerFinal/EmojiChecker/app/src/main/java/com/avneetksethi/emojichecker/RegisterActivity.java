package com.avneetksethi.emojichecker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private Button registerButton;
    private TextView registerTitletv;
    private EditText etFirstNameA;
    private EditText etLastNameA;
    private EditText etEmailA;
    private EditText etBirthDateA;
    private EditText etPhoneNumberA;
    private EditText etUsernameA;
    private EditText etPasswordA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstNameA = (EditText) findViewById(R.id.etFirstName);
        etLastNameA = (EditText) findViewById(R.id.etLastName);
        etEmailA = (EditText) findViewById(R.id.etEmail);
        etBirthDateA = (EditText) findViewById(R.id.etBirthDate);
        etPhoneNumberA = (EditText) findViewById(R.id.etPhoneNumber);
        etUsernameA = (EditText) findViewById(R.id.etUsername);
        etPasswordA = (EditText) findViewById(R.id.etPassword);
        registerButton = (Button) findViewById(R.id.registerButton);
        registerTitletv = (TextView) findViewById(R.id.registerTitletv);
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Shink.ttf");
            registerTitletv.setTypeface(typeface);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

    }

    public void createUser() {
        final String firstName = etFirstNameA.getText().toString();  //gets the text from first name field and stores it in firstName
        final String lastName = etLastNameA.getText().toString();
        final String email = etEmailA.getText().toString();
        final String birthDate = etBirthDateA.getText().toString();
        final String phoneNumber = etPhoneNumberA.getText().toString();
        final String formattedPhoneNum = PhoneNumberUtils.formatNumberToE164(phoneNumber, "US"); //format the number to E164
        final String username = etUsernameA.getText().toString();
        final String password = etPasswordA.getText().toString();
        final RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);

        StringRequest stringrequest = new  StringRequest(Request.Method.POST, "http://www.emoji-survey.me/auth/users/create", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                queue.stop();
                Intent intent = new Intent (RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent); //Launch Login Screen
            }}, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                error.printStackTrace();
                queue.stop();
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("Registration Failed")
                        .setNegativeButton("Retry", null)
                        .create()
                        .show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                //Add the data to send to the server.
                Map<String, String> params = new HashMap<String, String>();
                params.put("birth_date", birthDate);
                params.put("phone_number", formattedPhoneNum);
                params.put("first_name", firstName);
                params.put("last_name", lastName);
                params.put("email", email);
                params.put("username", username);
                params.put("password", password);
                return params;
            }

        };
        queue.add(stringrequest);
    }


}
