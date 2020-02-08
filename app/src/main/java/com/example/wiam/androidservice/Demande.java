package com.example.wiam.androidservice;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.android.volley.toolbox.Volley;
import android.content.Context;
public class Demande {

    private int id;
    private String titre;
    private String contenu;
    private String user;
     static List<Demande> l;

    public static List<Demande> getL() {
        return l;
    }

    public Demande() {
    }

    public static void setL(List<Demande> l) {
        Demande.l = l;
    }

    public Demande(int id, String titre, String contenu, String user) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.user = user;
    }






    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


}

