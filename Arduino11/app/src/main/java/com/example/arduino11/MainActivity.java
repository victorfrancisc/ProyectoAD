package com.example.arduino11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.util.ArrayUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URLEncoder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Formatter;
import java.util.Iterator;



public class MainActivity extends AppCompatActivity {
    Button bntIngresar;
    Button bntRegistrar;
    TextView textUser;
    TextView textPass;
    Rutas r=new Rutas();
    String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bntRegistrar = findViewById(R.id.bntRegistrar);
        bntIngresar = findViewById(R.id.bntIngresar);
        textUser = findViewById(R.id.txtIngresarUsuario);
        textPass = findViewById(R.id.txtIngresarClave);

        bntRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direccionarRegistrar();
            }
        });
        bntIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url();
            }
        });

    }

    public void direccionarRegistrar() {
        Intent is = new Intent(MainActivity.this, Registrar.class);
        startActivity(is);

    }

    public void navega(String usuario, String idArduino) {
        Intent is = new Intent(MainActivity.this, Navegador.class);
        is.putExtra("usuario",usuario);
        is.putExtra("idArduino", idArduino);
        startActivity(is);

    }

    public void url() {



        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("usuario", textUser.getText().toString());
            postData.put("clave", textPass.getText().toString());
            postData.put("bucle","2");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, r.ru("login"), postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                    try {
                        JSONArray row = response.getJSONArray("datos");
                        if(row.length()>0)
                        {
                            for(int p=0;p<row.length();p++) {
                               JSONObject aux=row.getJSONObject(p);
                                navega(aux.get("opcion1").toString(), aux.get("opcion2").toString());

                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Sus credenciales no son correctas", Toast.LENGTH_SHORT).show();
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

    public String getPostDataString(JSONObject params) throws Exception {


        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }


}
