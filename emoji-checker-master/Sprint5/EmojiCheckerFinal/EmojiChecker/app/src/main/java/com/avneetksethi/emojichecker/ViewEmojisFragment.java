package com.avneetksethi.emojichecker;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ViewEmojisFragment extends Fragment {
    private TextView result;
    private TextView title;
    String emojiName = "";
    String timestamp = "";
    String user = "";
    String data = "";
    final String username = LoginActivity.etUsername.getText().toString();
    final String password = LoginActivity.etPassword.getText().toString();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_emojis, container, false);
        title = (TextView) view.findViewById(R.id.pagetitle);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Shink.ttf");
        title.setTypeface(typeface);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Previous Emojis");

        result = (TextView) view.findViewById(R.id.resulttv);
        PreviousEmojiRequest();

        return view;

    }

    public void PreviousEmojiRequest() {
        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, "http://www.emoji-survey.me/responses/",
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                queue.stop();
                try {
                    JSONArray jsonarray = response.getJSONArray("results");
                    /*Retrieves the string labeled "emoji", "ts", "user" from
                    the response JSON Array and converts them into javascript objects*/
                    for(int i = jsonarray.length() - 1; i > jsonarray.length() - 11; i--) { //display last 10 queries
                        JSONObject results = jsonarray.getJSONObject(i);
                        emojiName = results.getString("emoji");
                        timestamp = results.getString("ts");

                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        Date date = null;
                        try {
                            date = inputFormat.parse(timestamp);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String formattedDate = outputFormat.format(date);

                        result.append("Emoji: " + emojiName + ", \n" + "Time Stamp: " + formattedDate + "\n\n");
                    }
                }
                // Try and catch are included to handle any errors due to JSON
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                queue.stop();
                error.printStackTrace();
                }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", "Token " + LoginActivity.token);
                return headers;

            }
        };
        queue.add(arrReq);
    }
}
