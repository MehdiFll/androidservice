package com.example.wiam.androidservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class Profil extends Activity {
    User u;
    TextView login, nom, prenom, bio, competences, email, titrec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Intent i = getIntent();
        u = (User) i.getSerializableExtra("user");



        login = (TextView) findViewById(R.id.login);
        nom = (TextView) findViewById(R.id.nom);
        prenom = (TextView)findViewById(R.id.prenom);
        bio = (TextView) findViewById(R.id.bio);
        titrec = (TextView) findViewById(R.id.titrec);
        competences = (TextView) findViewById(R.id.competence);
        competences.setVisibility(View.INVISIBLE);
        titrec.setVisibility(View.INVISIBLE);
        email = (TextView)findViewById(R.id.email);


        System.out.println(u.getLogin());
        System.out.println(u.getNom());
        System.out.println(u.getCompetences());


        login.setText(u.getLogin());
        nom.setText(u.getNom());
        prenom.setText(u.getPrenom());
        bio.setText(u.getBio());
        email.setText(u.getEmail());

        if(u.getType().equals("E")){
            titrec.setVisibility(View.VISIBLE);
            competences.setText(u.getCompetences());
            competences.setVisibility(View.VISIBLE);
        }






    }


}
