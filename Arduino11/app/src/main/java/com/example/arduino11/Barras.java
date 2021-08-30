package com.example.arduino11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class Barras extends AppCompatActivity {

    String idusaurio;
    String idArduino;
    String fecha;
    String horas;
    int datoBarrauno;
    int datoBarrados;
    CalendarView calendario;
    BarChart bar;
    LineChart line;
    Rutas r = new Rutas();
    String vector[]=  {"#e3f2fd","#bbdefb","#90caf9","#64b5f6","#42a5f5","#2196f3","#1e88e5","#e3f2fd","#bbdefb","#90caf9","#64b5f6","#42a5f5","#e3f2fd","#bbdefb","#90caf9"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barras);
        idArduino = getIntent().getExtras().getString("idArduino");
        idusaurio = getIntent().getExtras().getString("usuario");
        calendario = findViewById(R.id.calendarView);
        bar = findViewById(R.id.graficoBarra);
        line = findViewById(R.id.graficoLinea);
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                int mes = month + 1;
                fecha = year + "-" + mes + "-" + dayOfMonth;
                enviarDatos();
            }
        });


    }


    public JSONObject json() {
        JSONObject postDataB = new JSONObject();
        try {
            postDataB.put("idArduino", idArduino);
            postDataB.put("fecha", fecha);
            postDataB.put("bucle", "2");
            return postDataB;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDataB;
    }

    public void enviarDatos() {
        json();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, r.ru("fechas"), json(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ArrayList<Double> valueListDatos = new ArrayList<Double>();
                    JSONArray row = response.getJSONArray("datos");


                    if (row.length() > 0) {
                        int dato=row.length();
                        for (int p = 0; p < row.length(); p++) {
                            if(p<row.length()) {
                                JSONObject aux = row.getJSONObject(p);
                                horas = aux.get("opcion1").toString();
                                String partes[] = horas.split(":");
                                valueListDatos.add(Double.valueOf(partes[0]));
                                datoBarrauno = Integer.valueOf(aux.get("opcion2").toString());
                                valueListDatos.add(Double.valueOf(datoBarrauno));
                            }

                        }
                        graficoBarra(valueListDatos);


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

    public void graficoBarra(ArrayList<Double> uno) {

        ArrayList<BarEntry> entries = new ArrayList<>();
        String title = "Horas";
        BarDataSet barDataSet;
        int inc = 0;
        int valor=0;


        for (int i = 0; i < uno.size() - 1; i++) {
            if(i<uno.size()) {
                i++;
                BarEntry barEntry = new BarEntry(Integer.valueOf(uno.get(inc).intValue()), uno.get(i).floatValue());
                inc = inc + 2;
                valor = i;
                entries.add(barEntry);
            }
        }
        barDataSet = new BarDataSet(entries, String.valueOf("Temperatura"));
       for(int i=0;i<valor;i++) {
            barDataSet.setColors(
                    new int[]{Color.parseColor(vector[0]),Color.parseColor(vector[2])});
        }

        BarData data = new BarData(barDataSet);
        bar.setData(data);
       // bar.invalidate();
         bar.animateXY(2000, 2000);
    }

    public void lineas(ArrayList<Double> lista) {
        List<Entry> list = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            list.add(new Entry(i, lista.get(i).floatValue()));

        }

        LineDataSet lineDataSet = new LineDataSet(list, "");
        LineData lineData = new LineData(lineDataSet);
        line.setData(lineData);
        line.animateXY(2000, 2000);


    }
}