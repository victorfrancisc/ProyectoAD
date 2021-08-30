package com.example.arduino11;

import android.app.Activity;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.aware.WifiAwareManager;
import android.os.Build;
import android.os.Handler;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Formatter;

public class Rutas extends Activity {

    String movimiento;

    String datore = "";

    int tamanodejson=0;
    String datomnoviento;
    String categoria;
    String idArduinoMo;
    int valor=0;
    double dass;
    public String ru(String dato) {
       return "http://aplicaciones.uteq.edu.ec/ServiceSilentKiller/webresources/generic/" + dato;
    }

    public int convertidorTem(int dato)
    {

         if(dato<=100) {
             valor= 37;
         }
         if(dato>100)
         {
             dass=(dato-32)*0.55;
             valor=  (int)Math.round(dass);
         }
      return valor;
    }


}
