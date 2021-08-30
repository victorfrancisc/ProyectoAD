package com.example.arduino11;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arduino11.databinding.ActivityNavegadorBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Navegador extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavegadorBinding binding;
    TextView txxinformation;
    String usuario;
    String idArduino;
    RelativeLayout botondeInfLineasTem;
    RelativeLayout botoActividad;
    RelativeLayout botoMovimiento;
    RelativeLayout botonTiemporealCi;
    RelativeLayout botonMonoxido;
    RelativeLayout botonMonoxidoLinea;
    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    Rutas r = new Rutas();
    int incendio = 0;
    int gas = 0;
    int controlador=0;
 int paramedirTemperatura;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavegadorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usuario = getIntent().getStringExtra("usuario");
        idArduino = getIntent().getStringExtra("idArduino");
        botondeInfLineasTem = findViewById(R.id.txtLineaTemp);
        botoActividad = findViewById(R.id.txtdeActividad);
        botoMovimiento=findViewById(R.id.txtmovimientoReal);
        botonMonoxido=findViewById(R.id.txtmonoxido);
        botonTiemporealCi=findViewById(R.id.txtCircu);
        botonMonoxidoLinea=findViewById(R.id.txtmonoxiLinea);

        ejecutarInformacion();



        botoActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle parametro = new Bundle();
                parametro.putString("usuario", usuario);
                parametro.putString("idArduino", idArduino);
                Intent pagiadeinformacion = new Intent(Navegador.this, Barras.class);
                pagiadeinformacion.putExtras(parametro);
                startActivity(pagiadeinformacion);
            }
        });

        botondeInfLineasTem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle parametro = new Bundle();
                parametro.putString("usuario", usuario);
                parametro.putString("idArduino", idArduino);
                Intent pagiadeinformacion = new Intent(Navegador.this, LineaTempe.class);
                pagiadeinformacion.putExtras(parametro);
                startActivity(pagiadeinformacion);

            }
        });

        botoMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle parametro = new Bundle();
                parametro.putString("idArduino", idArduino);
                Intent pagiadeMovimi = new Intent(Navegador.this, Movimiento.class);
                pagiadeMovimi.putExtras(parametro);
                startActivity(pagiadeMovimi);

            }
        });
        botonTiemporealCi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle parametro = new Bundle();
                parametro.putString("idArduino", idArduino);
                Intent pagiadeMovimi = new Intent(Navegador.this, Informacion.class);
                pagiadeMovimi.putExtras(parametro);
                startActivity(pagiadeMovimi);

            }
        });
        botonMonoxido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle parametro = new Bundle();
                parametro.putString("idArduino", idArduino);
                Intent pagiadeMovimi = new Intent(Navegador.this, Gas.class);
                pagiadeMovimi.putExtras(parametro);
                startActivity(pagiadeMovimi);

            }
        });
        botonMonoxidoLinea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle parametro = new Bundle();
                parametro.putString("idArduino", idArduino);
                Intent pagiadeMovimi = new Intent(Navegador.this, GasLineas.class);
                pagiadeMovimi.putExtras(parametro);
                startActivity(pagiadeMovimi);

            }
        });


        setSupportActionBar(binding.appBarNavegador.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navegador);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navegador, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navegador);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notificación";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification(int valorIn, int valorGas){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Notificación de Scanner");
        builder.setContentText("Valores elavados de temperatura:"+String.valueOf(valorIn)+" "+"valor de gases:"+valorGas);
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        Intent intent = new Intent(Navegador.this, Navegador.class);
        PendingIntent pendi = PendingIntent.getActivity(Navegador.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendi);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
      //  NotificationManagerCompat notificationManagerCompat = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }



    public void webserives() {


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject postData = new JSONObject();
        try {
            postData.put("idArduino", idArduino);
            postData.put("bucle", "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, r.ru("notificacion"), postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray row = response.getJSONArray("datos");
                    if (row.length() > 0) {
                        for (int p = 0; p < row.length(); p++) {
                            JSONObject aux = row.getJSONObject(p);
                            gas  = Integer.valueOf(aux.get("opcion2").toString());

                            incendio =r.convertidorTem(Integer.valueOf(aux.get("opcion1").toString()));


                        }
                    }
                    if (incendio >=315 || gas >= 75) {

                        createNotificationChannel();
                        createNotification(incendio,gas);
                        controlador++;
                        if(controlador==1) {
                            alertas(incendio, gas);
                        }
                    } else {
                        controlador=0;
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



    public void ejecutarInformacion() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                webserives();
                handler.postDelayed(this, 5000);//se ejecutara cada 10 segundos

            }
        }, 1000);

    }
public void alertas(int temperatura,int gas)
{
    AlertDialog.Builder builder = new AlertDialog.Builder(Navegador.this);
    builder.setIcon(R.drawable.peligro);
    builder.setMessage("Valores elavados de temperatura:"+String.valueOf(temperatura)+"°C"+" "+"valor de gases:"+String.valueOf(gas))
            .setTitle("Alerta").setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int id) {
        // sign in the user ...
    }
});
       AlertDialog dialog = builder.create();
    dialog.show();


}







}