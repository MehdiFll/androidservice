package com.example.wiam.androidservice;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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


public class Welcome extends Activity implements View.OnClickListener {

    private Button log_out;
    private Button mes_demandes;
    private Button profil;
    private Button notif=null;
    private ListView list;
    private User u;
    private String notifcontenu =" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mes_demandes = findViewById(R.id.demandes);

        mes_demandes.setOnClickListener(this);
        list = findViewById(R.id.liste_mur);
        profil = findViewById(R.id.profil);
        notif=findViewById(R.id.notification);
        notif.setVisibility(View.INVISIBLE);

        Intent i = getIntent();
        u = (User) i.getSerializableExtra("user");
        if(u.getType().equals("E")) {
            notif.setVisibility(View.VISIBLE);
            mes_demandes.setText("Mes postulations");
        }

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Profil.class);
                i.putExtra("user",u);
                startActivity(i);
            }
        });


        RequestQueue requestQueue;
        String URL = "http://192.168.1.9/server/demandes.php";
        final StringRequest request, request2;
        requestQueue = Volley.newRequestQueue(this);
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            List<Demande> maliste = new ArrayList<Demande>();
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int j=0;j<jsonArray.length();j++){
                        JSONObject o = jsonArray.getJSONObject(j);
                        maliste.add(new Demande(o.getInt("id"), o.getString("titre"), o.getString("contenu"),o.getString("user")));
                    }

                    ListMurAdapter monAdapter =
                            new ListMurAdapter(getApplicationContext(),maliste,u);
                    list.setAdapter(monAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                hashMap.put("op", "2");
                return hashMap;
            }
        };




        request2 = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);


                    for(int j=0;j<jsonArray.length();j++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        notifcontenu = notifcontenu +"\n\n"+ jsonObject.getString("contenu");

                    }
                    if (!notifcontenu.equals(" \n\nVous avez aucune notification")) {
                        notif.setText("Notifications ("+jsonArray.length()+")");
                    }

                    System.out.println(notifcontenu);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                hashMap.put("op", "6");
                hashMap.put("id_user", String.valueOf(u.getId()));
                return hashMap;
            }
        };


        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Notifcations.class);
                i.putExtra("user",u);
                i.putExtra("notifcontenu", notifcontenu);
                startActivity(i);
            }
        });


        requestQueue.add(request);
        requestQueue.add(request2);



        log_out = (Button) findViewById(R.id.button);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),Demandes.class);
        i.putExtra("user",u);
        startActivity(i);
    }
}
