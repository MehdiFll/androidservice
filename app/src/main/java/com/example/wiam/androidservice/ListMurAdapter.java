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
import android.widget.Toast;

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

public class ListMurAdapter extends ArrayAdapter<Demande> {
    private  final List<Demande> demandes  ;
    private final Context  context  ;
    private User u;
    Demande d;

    public ListMurAdapter(@NonNull Context context, @NonNull List<Demande> objects, User u) {
        super(context, 0, objects);
        this.context = context;
        this.demandes=objects;
        this.u=u;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

            if (listItem == null)
                listItem = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        final TextView titre = (TextView) listItem.findViewById(R.id.titre);
        final TextView contenu = (TextView) listItem.findViewById(R.id.contenu);
        final TextView utilisateur = (TextView) listItem.findViewById(R.id.user);
        Button repondre = (Button) listItem.findViewById(R.id.repondre);


        if (u.getType().equals("U")) repondre.setVisibility(View.INVISIBLE);


        d = demandes.get(position);

        titre.setText(d.getTitre());
        contenu.setText(d.getContenu());
        utilisateur.setText(d.getUser());



        repondre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(utilisateur.getText().toString());
                System.out.println(titre.getText().toString());
                System.out.println(contenu.getText().toString());


                RequestQueue requestQueue;
                String URL = "http://192.168.1.9/server/demandes.php";
                StringRequest request;
                requestQueue = Volley.newRequestQueue(context);
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            System.out.println(object);
                            if(object.names().get(0).equals("success")){
                                Toast.makeText(context,"Succès",Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(context,object.getString("error"),Toast.LENGTH_SHORT).show();



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
                        hashMap.put("op", "4");
                        hashMap.put("id_user_co",  String.valueOf(u.getId()));
                        hashMap.put("titre", titre.getText().toString());
                        hashMap.put("contenu", contenu.getText().toString());
                        hashMap.put("user", utilisateur.getText().toString());
                        return hashMap;
                    }
                };

                requestQueue.add(request);



            }
        });



        return listItem ;

}
}
