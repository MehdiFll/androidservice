package com.example.wiam.androidservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class Ajouter extends Activity implements View.OnClickListener {
    private User u;
    EditText titre;
    EditText contenu;
    Button Ajouter;

    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.1.9/server/demandes.php";
    private StringRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);
        Ajouter = findViewById(R.id.Ajouter);
        titre = findViewById(R.id.titre);
        contenu = findViewById(R.id.contenu);

        requestQueue = Volley.newRequestQueue(this);


        Intent i = getIntent();
        u = (User) i.getSerializableExtra("user");
        Ajouter.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (titre.getText().length()==0 && contenu.getText().length()==0 ) {
            Toast.makeText(getApplicationContext(),"Les champs sont vides",Toast.LENGTH_SHORT).show();
        }
        else if (titre.getText().length()==0 || contenu.getText().length()==0 ) {
            if (titre.getText().length()==0) Toast.makeText(getApplicationContext(),"Le champ du titre est vide",Toast.LENGTH_SHORT).show();
            if (contenu.getText().length()==0) Toast.makeText(getApplicationContext(),"Le champ du contenu est vide",Toast.LENGTH_SHORT).show();
        }
        else {
            request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        System.out.println(response);
                        JSONObject jsonObject = new JSONObject(response);
                        System.out.println(jsonObject.getString("success"));

                        if(jsonObject.names().get(0).equals("success")){
                            Toast.makeText(getApplicationContext(),"Succès",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(),Demandes.class);
                            i.putExtra("user",u);
                            startActivity(i);
                        }else {
                            Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("no response");
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> hashMap = new HashMap<String, String>();
                    hashMap.put("op", "1");
                    hashMap.put("id", String.valueOf(u.getId()));
                    hashMap.put("titre",titre.getText().toString());
                    hashMap.put("contenu",contenu.getText().toString());
                    return hashMap;
                }
            };

            requestQueue.add(request);

        }

    }

}
