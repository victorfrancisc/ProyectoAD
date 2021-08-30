
package com.example.arduino11;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Informacion extends AppCompatActivity {
    EditText plano;
    TextView entrada;
    int contadorUno = 0;
    int contadorDos = 5;
    int contadorTres = 10;
    TextView textViewince;
    TextView textViewmono;
    PieChart piechart;
    TextView textcolorinde;
    TextView textcolorgas;
    TextView textporcentajedetemperauta;
    TextView textporcentajedegas;
    int datodos = 0;
    int datouno = 0;
    int valordeTem=0;
    String idArduino;
    String idusaurio;
   Rutas ruta=new Rutas();
    List<PieEntry> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);
        idArduino = getIntent().getExtras().getString("idArduino");
        idusaurio = getIntent().getExtras().getString("usuario");
        textViewince = findViewById(R.id.textView10);
        textViewmono = findViewById(R.id.textView12);
        textcolorinde = findViewById(R.id.textView9);
        textcolorgas = findViewById(R.id.textView11);
        textporcentajedetemperauta=findViewById(R.id.textView13);
        textporcentajedegas=findViewById(R.id.textView16);
        piechart=findViewById(R.id.graficoPastel);
        ejecutar();

    }

    private void ejecutar() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                enviar();//llamamos nuestro metodo
                handler.postDelayed(this, 5000);//se ejecutara cada 10 segundos
            }
        }, 1000);//empezara a ejecutarse después de 5 milisegundos
    }

    private void metodoEjecutar() {

        contadorUno++;
        contadorDos++;
        contadorTres++;

    }

    public void enviar() {


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("idArduino", idArduino);
            postData.put("bucle", "2");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ruta.ru("sensores"), postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray row = response.getJSONArray("datos");
                    if (row.length() > 0) {
                        for (int p = 0; p < row.length(); p++) {
                            JSONObject aux = row.getJSONObject(p);
                            textViewince.setText(String.valueOf(aux.get("opcion1").toString()));
                            textViewmono.setText(String.valueOf(aux.get("opcion2").toString()));
                            datouno = ruta.convertidorTem(Integer.valueOf(aux.get("opcion1").toString()));
                            datodos =Integer.valueOf(aux.get("opcion2").toString());
                        }
                        graficoAlerta(datouno, datodos);

                    } else {

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


    public void graficoAlerta(int uno, int dos) {
        textViewince.setText(String.valueOf(uno));
        textViewmono.setText(String.valueOf(dos));
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(uno, "Temperatura"));
        pieEntries.add(new PieEntry(dos, "Monóxido"));
        PieDataSet pieDataSet = new PieDataSet(pieEntries, null);
        PieData pieData = new PieData(pieDataSet);
        piechart.setData(pieData);
        if(uno>=315)
        {
            textcolorinde.setBackgroundColor(Color.parseColor("#c30000"));
            textcolorgas.setBackgroundColor(Color.parseColor("#ffff00"));
            pieDataSet.setColors(Color.parseColor("#c30000"), Color.parseColor("#ffff00"));
        }
        if(dos>=75) {
            textcolorgas.setBackgroundColor(Color.parseColor("#c30000"));
            textcolorinde.setBackgroundColor(Color.parseColor("#76ff03"));
            pieDataSet.setColors(Color.parseColor("#76ff03"), Color.parseColor("#c30000"));
        }
        if(uno>315 && dos>75)
        {
            textcolorgas.setBackgroundColor(Color.parseColor("#ff3d00"));
            textcolorinde.setBackgroundColor(Color.parseColor("#c30000"));
            pieDataSet.setColors(Color.parseColor("#ff3d00"), Color.parseColor("#c30000"));
        }
        if(uno<315 && dos<75)
        {
            textcolorgas.setBackgroundColor(Color.parseColor("#ffff00"));
            textcolorinde.setBackgroundColor(Color.parseColor("#76ff03"));
            pieDataSet.setColors(Color.parseColor("#76ff03"), Color.parseColor("#ffff00"));

        }
        pieDataSet.setValueTextSize(20f);
        pieDataSet.setSliceSpace(8f);
        pieDataSet.setValueLineColor(Color.BLACK);
        Legend legend=piechart.getLegend();
        legend.setEnabled(true);
        legend.getTextColor();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        piechart.animateY(3000);  // El parámetro de animación en el eje Y es el tiempo de ejecución de la animación en milisegundos
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        pieDataSet.setValueLinePart1Length(0.6f);// Establecer la longitud de la línea de conexión de descripción
        // Establecer la ubicación de los datos
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        pieDataSet.setValueLinePart2Length(0.6f);// Establecer la longitud de la línea de conexión de datos
        // Establecer el color de las dos líneas de conexión
        pieDataSet.setValueLineColor(Color.RED);
        piechart.setEntryLabelColor(Color.BLACK);
        piechart.animateX(2500, Easing.EasingOption.EaseOutCirc);




        porcentaje(uno,dos);
    }

    public void porcentaje(int dos, int uno)
    {

        int totalgas;
        int totaltempera;
        if(uno<315) {
            totalgas=uno-100;
            totalgas=100+totalgas;
            textporcentajedegas.setText(String.valueOf(totalgas));
            textporcentajedegas.setTextColor(Color.parseColor("#2196f3"));
        }else if(uno>=315)
        {
            totalgas=200-100;
            totalgas=100-totalgas;
            textporcentajedegas.setText(String.valueOf(totalgas));
            textporcentajedegas.setTextColor(Color.parseColor("#7f0000"));

        }

        if(dos<=75) {

            textporcentajedetemperauta.setText(String.valueOf("100"));
            textporcentajedetemperauta.setTextColor(Color.parseColor("#2196f3"));
        }
        else if(dos>75)
        {

            totaltempera=200-100;
            totaltempera=100-totaltempera;
            textporcentajedetemperauta.setText(String.valueOf(totaltempera));
            textporcentajedetemperauta.setTextColor(Color.parseColor("#7f0000"));
        }

    }
}
