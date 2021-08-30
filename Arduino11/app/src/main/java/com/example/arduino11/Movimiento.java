package com.example.arduino11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Movimiento extends AppCompatActivity {

    String id;
    TextView movi;
    ImageView imagen;
    String identidicador;
    Rutas r = new Rutas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento);
        movi = findViewById(R.id.textView13);
        imagen=findViewById(R.id.imagenViewMovi);
        id = getIntent().getStringExtra("idArduino");
        ejecutarInformacionMovi();


    }

    public void llamarmovi() {


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject postDataM = new JSONObject();
        try {
            postDataM.put("idArduino", id);
            postDataM.put("bucle", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, r.ru("movimiento"), postDataM, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray row = response.getJSONArray("datos");
                    if (row.length() > 0) {
                        for (int p = 0; p < row.length(); p++) {
                            JSONObject aux = row.getJSONObject(p);
                            identidicador=aux.get("opcion1").toString();
                        }

                        if(identidicador.equals("0"))
                        {movi.setText("Sin actividad");
                            imagen.setImageResource(R.drawable.parado);

                        }
                        else{
                            movi.setText("Detectado");
                            imagen.setImageResource(R.drawable.ppt_animation_332);

                        }
                    }

                } catch (Exception e) {
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);


    }

    public void ejecutarInformacionMovi() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                llamarmovi();
                handler.postDelayed(this, 5000);//se ejecutara cada 10 segundos
            }
        }, 1000);//empezara a ejecutarse después de 5 milisegundos

    }
}