package com.example.wiam.androidservice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Demande> {
    private  final List<Demande> demandes  ;
    private final Context  context  ;
    private User u;

    public MyAdapter(@NonNull Context context,  @NonNull List<Demande> objects, User u) {
        super(context, 0, objects);
        this.context = context;
        this.demandes=objects;
        this.u=u;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null )
            listItem = LayoutInflater.from(context).inflate(R.layout.item2,parent,false);


        TextView titre = (TextView) listItem.findViewById(R.id.titre);
        TextView contenu = (TextView) listItem.findViewById(R.id.contenu);


        Demande d = demandes.get(position);

        titre.setText(d.getTitre());
        contenu.setText(d.getContenu());




        return listItem ;

    }
}
