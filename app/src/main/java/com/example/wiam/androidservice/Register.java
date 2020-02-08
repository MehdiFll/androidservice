package com.example.wiam.androidservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Register extends Activity implements View.OnClickListener {
    EditText nom, prenom, email, login, password, type,bio, competence;
    Button register;
    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.1.9/server/demandes.php";
    private StringRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nom = findViewById(R.id.nomr);
        prenom = findViewById(R.id.prenomr);
        email = findViewById(R.id.emailr);
        login = findViewById(R.id.loginr);
        password = findViewById(R.id.passwordr);
        type = findViewById(R.id.typer);
        bio = findViewById(R.id.bior);
        competence = findViewById(R.id.competencer);
        register= findViewById(R.id.register);
        requestQueue = Volley.newRequestQueue(this);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if ((login.getText().length() == 0) && (password.getText().length() == 0) && (email.getText().length() == 0)) {
            Toast.makeText(getApplicationContext(), "Les champs importants sont vides", Toast.LENGTH_SHORT).show();
        } else if ((login.getText().length() == 0) || (password.getText().length() == 0)) {
            if (login.getText().length() == 0) {
                Toast.makeText(getApplicationContext(), "Le champ du login est vide", Toast.LENGTH_SHORT).show();
            }
            if (password.getText().length() == 0) {
                Toast.makeText(getApplicationContext(), "Le champ du mot de passe est vide", Toast.LENGTH_SHORT).show();
            }

            if (email.getText().length() == 0) {
                Toast.makeText(getApplicationContext(), "Le champ de l'email est vide", Toast.LENGTH_SHORT).show();
            }

        } else {
            request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {


                        JSONObject jsonObject = new JSONObject(response);
                        System.out.println(jsonObject);

                        if (jsonObject.names().get(0).equals("success")) {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("success"), Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);

                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("erreur") , Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Problème de connexion", Toast.LENGTH_SHORT).show();
                    System.out.println("no response");
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("op", "8");
                    hashMap.put("nom", nom.getText().toString());
                    hashMap.put("prenom", prenom.getText().toString());
                    hashMap.put("login", login.getText().toString());
                    hashMap.put("password", password.getText().toString());
                    hashMap.put("email", email.getText().toString());
                    hashMap.put("type", type.getText().toString());
                    hashMap.put("bio", bio.getText().toString());
                    hashMap.put("competence", competence.getText().toString());

                    return hashMap;
                }
            };

            requestQueue.add(request);
        }
    }
}
