package com.example.arduino11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Registrar extends AppCompatActivity {

    TextView usuario;
    TextView passw;
    TextView confimar;
    TextView idArduino;
    Button bntEn;
    Rutas r=new Rutas();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registrar2);
        usuario = findViewById(R.id.txtUsuarios);
        passw = findViewById(R.id.txtPasswor);
        confimar = findViewById(R.id.txtconfirmar);
        idArduino= findViewById(R.id.txtarduino);
        bntEn = findViewById(R.id.buttonRegistrar);


        bntEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usuario.getText().length()>0 && passw.getText().length() >0 && idArduino.getText().length()>0)
                {
                    if(passw.getText().toString().equals(confimar.getText().toString())) {
                        enviar();
                        Toast.makeText(Registrar.this, "Realizada con éxito", Toast.LENGTH_SHORT).show();
                        direccionarRegistrarLogin();
                    }
                    else{
                        Toast.makeText(Registrar.this, "No son iguales", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    {
                        Toast.makeText(Registrar.this, "Llene las casillas", Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }


    public void enviar()
    {

        //String url = "http://192.168.1.2:8080/ArduinoServidor/webresources/generic/ingresar";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("usuario", usuario.getText().toString());
            postData.put("clave", passw.getText().toString());
            postData.put("idArduino", idArduino.getText().toString());
            postData.put("bucle","1");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, r.ru("ingresar"), postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray row = response.getJSONArray("datos");
                    if(row.length()>0) {
                        direccionarRegistrarLogin();
                    }else{
                        Toast.makeText(Registrar.this, "Transacción fallida", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
    public void direccionarRegistrarLogin() {
        Intent is = new Intent(Registrar.this, MainActivity.class);
        startActivity(is);

    }

}

