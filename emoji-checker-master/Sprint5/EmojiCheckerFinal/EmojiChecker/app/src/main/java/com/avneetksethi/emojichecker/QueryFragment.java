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
import android.widget.ImageView;
import android.widget.SeekBar;
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

public class QueryFragment extends Fragment {
    private static String token;
    private TextView textView;
    private TextView titletv;
    private SeekBar seekBar;
    private Button submitButton;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;
    int[] emojis = {R.drawable.sleepy, R.drawable.angry, R.drawable.nervous, R.drawable.passive, R.drawable.confused,
            R.drawable.neutral, R.drawable.content, R.drawable.giggly, R.drawable.happy};

    String keyword = "";
    int min = 1, max = 9, current = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_query, container, false);
        //Set the title of the Navigation Bar for this screen
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Query");

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        image1 = (ImageView) view.findViewById(R.id.sleepyImageView);
        image2 = (ImageView) view.findViewById(R.id.angryImageView);
        image3 = (ImageView) view.findViewById(R.id.nervousImageView);
        image4 = (ImageView) view.findViewById(R.id.passiveImageView);
        image5 = (ImageView) view.findViewById(R.id.confusedImageView);
        image6 = (ImageView) view.findViewById(R.id.neutralImageView);
        image7 = (ImageView) view.findViewById(R.id.contentImageView);
        image8 = (ImageView) view.findViewById(R.id.gigglyImageView);
        image9 = (ImageView) view.findViewById(R.id.happyImageView);
        submitButton = (Button) view.findViewById(R.id.submitButton);
        textView = (TextView) view.findViewById(R.id.textView);
        titletv = (TextView) view.findViewById(R.id.titletv);
            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Shink.ttf");
            titletv.setTypeface(typeface);

        seekBar.setMax(max - min);
        seekBar.setProgress(current - min);
        textView.setText("Sleepy");


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                current = progress + min;
                sliderEnabled(current);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send selected emoji from slider to the server and display the correct screen
                sendEmoji();
            }
        });
        return view;

    }

    public String sliderEnabled(int current) {
        //Map the value of the slider to each emoji
        switch (current) {
            case 1:
                textView.setText("Sleepy");
                image1.setImageResource(emojis[0]);
                keyword = "Sleepy";
                break;
            case 2:
                textView.setText("Angry");
                image2.setImageResource(emojis[1]);
                keyword = "Angry";
                break;
            case 3:
                textView.setText("Nervous");
                image3.setImageResource(emojis[2]);
                keyword = "Nervous";
                break;
            case 4:
                textView.setText("Passive-Aggressive");
                image4.setImageResource(emojis[3]);
                keyword = "Passive-Aggressive";
                break;
            case 5:
                textView.setText("Confused");
                image5.setImageResource(emojis[4]);
                keyword = "Confused";
                break;
            case 6:
                textView.setText("Neutral");
                image6.setImageResource(emojis[5]);
                keyword = "Neutral";
                break;
            case 7:
                textView.setText("Content");
                image7.setImageResource(emojis[6]);
                keyword = "Content";
                break;
            case 8:
                textView.setText("Giggly");
                image8.setImageResource(emojis[7]);
                keyword = "Giggly";
                break;
            case 9:
                textView.setText("Happy");
                image9.setImageResource(emojis[8]);
                keyword = "Happy";
                break;

        }
        return keyword; //Return the string keyword that matches emoji selected by user
    }

    public void sendEmoji() {
        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        String URL = "http://www.emoji-survey.me/responses/";
        StringRequest sr = new  StringRequest(Request.Method.POST, "http://www.emoji-survey.me/responses/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                //textView.setText(response.toString());
                queue.stop();
                openConfirmationScreen(); //Launch confirmation screen
            }}, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                //textView.setText("Error Sending Response");
                error.printStackTrace();
                queue.stop();
                openErrorScreen(); //Launch error screen
            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", "Token " + LoginActivity.token);
                return headers;

            }
            @Override
            public Map<String, String> getParams() {
                //Add the data to send to the server.
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("emoji", keyword);
                return MyData;
            }
        };
        queue.add(sr);
    }

    public void openConfirmationScreen() {
        //Used to launches the confirmation screen
        Fragment fragment = new QueryConfirmation();
        replaceFragment(fragment);
    }

    public void openErrorScreen() {
        //Used to launches the error screen
        Fragment fragment = new QueryError();
        replaceFragment(fragment);
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.flMain, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

