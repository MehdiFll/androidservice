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

public class Demandes extends Activity implements View.OnClickListener {
    private User u;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        u = (User) i.getSerializableExtra("user");
        setContentView(R.layout.activity_demandes);
        Button ajouter;
        ajouter = findViewById(R.id.Ajouter);
        if (u.getType().equals("E")) {
            ajouter.setVisibility(View.INVISIBLE);
        }
        ajouter.setOnClickListener(this);

        list = findViewById(R.id.listdemandes);
        /*Demande d1 = new Demande(1,"titre1","contenu1","tag1","user1");
        Demande d2 = new Demande(2,"titre2","contenu2","tag2","user2");
*/
        /*listdemandes.add(d1);
        listdemandes.add(d2);*/

        RequestQueue requestQueue;
        String URL = "http://192.168.1.9/server/demandes.php";
        StringRequest request;
        requestQueue = Volley.newRequestQueue(this);
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            List<Demande> maliste = new ArrayList<Demande>();
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int j=0;j<jsonArray.length();j++){
                        JSONObject o = jsonArray.getJSONObject(j);
                        maliste.add(new Demande(o.getInt("id"), o.getString("titre"), o.getString("contenu"),""));
                    }

                    MyAdapter monAdapter =
                new MyAdapter(getApplicationContext(),maliste,u);
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
                if(u.getType().equals("E")) {
                    hashMap.put("op", "5");
                }
               else { hashMap.put("op", "3");}
                hashMap.put("id_user", String.valueOf(u.getId()));
                return hashMap;
            }
        };

        requestQueue.add(request);



    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),Ajouter.class);
        i.putExtra("user",u);
        startActivity(i);
    }
}
