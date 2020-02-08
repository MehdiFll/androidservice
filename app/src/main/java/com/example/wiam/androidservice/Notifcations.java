package com.example.wiam.androidservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notifcations extends Activity implements View.OnClickListener {
    User u;
    String notifcontenu;
    TextView notif;
    Button retour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifcations);
        Intent i = getIntent();
        u = (User) i.getSerializableExtra("user");
        notifcontenu = i.getStringExtra("notifcontenu");
        notif = findViewById(R.id.notifcontenu);
        notif.setText(notifcontenu);

        retour = findViewById(R.id.btn);
        retour.setOnClickListener(this);

        RequestQueue requestQueue;
        String URL = "http://192.168.1.9/server/demandes.php";
        final StringRequest request;
        requestQueue = Volley.newRequestQueue(this);
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            List<Demande> maliste = new ArrayList<Demande>();
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("no response");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("op", "7");
                hashMap.put("id_user", String.valueOf(u.getId()));
                return hashMap;
            }
        };

        requestQueue.add(request);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),Welcome.class);
        i.putExtra("user",u);
        startActivity(i);
    }
}
