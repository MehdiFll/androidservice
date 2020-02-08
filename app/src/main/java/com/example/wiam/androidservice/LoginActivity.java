package com.example.wiam.androidservice;

import android.app.Activity;
import android.os.Parcelable;

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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity {

    private EditText login,password;
    private Button sign_in_register, reg;
    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.1.9/server/user_control.php";
    private StringRequest request;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        sign_in_register = (Button) findViewById(R.id.sign_in_register);
        reg= findViewById(R.id.reg);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Register.class);

                startActivity(i);
            }
        });
        requestQueue = Volley.newRequestQueue(this);

        sign_in_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((login.getText().length()==0) && (password.getText().length()==0) ) {
                    Toast.makeText(getApplicationContext(),"Les champs sont vides",Toast.LENGTH_SHORT).show();
                }
                else if ((login.getText().length()==0) || (password.getText().length()==0) ) {
                    if (login.getText().length()==0) { Toast.makeText(getApplicationContext(),"Le champ du login est vide",Toast.LENGTH_SHORT).show(); }
                    if (password.getText().length()==0) { Toast.makeText(getApplicationContext(),"Le champ du mot de passe est vide",Toast.LENGTH_SHORT).show(); }

                }
                else {
                    System.out.println(login.getText().toString()+" "+password.getText().toString());
                    request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                System.out.println(response);
                                JSONObject jsonObject = new JSONObject(response);

                                if(jsonObject.getString("success").equals("true")){
                                    Toast.makeText(getApplicationContext(),"SUCCESS ",Toast.LENGTH_SHORT).show();
                                    User u;
                                    if(jsonObject.getString("type").equals("E")) {
                                         u= new User(jsonObject.getInt("id"), jsonObject.getString("nom"), jsonObject.getString("prenom"), jsonObject.getString("login"), jsonObject.getString("password"), jsonObject.getString("email"), jsonObject.getString("type"), jsonObject.getString("competence"), jsonObject.getString("bio"));
                                    }
                                    else {
                                        u = new User(jsonObject.getInt("id"), jsonObject.getString("nom"), jsonObject.getString("prenom"), jsonObject.getString("login"), jsonObject.getString("password"), jsonObject.getString("email"), jsonObject.getString("type"), jsonObject.getString("bio"));

                                    }
                                    Intent i = new Intent(getApplicationContext(),Welcome.class);
                                    i.putExtra("user", u);
                                    startActivity(i);
                                }else {
                                    Toast.makeText(getApplicationContext(), "Login ou mot de passe est erroné", Toast.LENGTH_SHORT).show();
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
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("login",login.getText().toString());
                            hashMap.put("password",password.getText().toString());

                            return hashMap;
                        }
                    };

                    requestQueue.add(request);
                }

            }
        });
    }
}


