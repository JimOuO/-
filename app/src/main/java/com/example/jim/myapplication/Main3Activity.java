package com.example.jim.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;






public class Main3Activity extends AppCompatActivity implements
        OnMapReadyCallback {
    private final static int REQUEST_PERMISSIONS = 1;
    private LocationManager LocationManager;
    private String commad;
    public static final int MY_LOCATION=11;
    public double a,b,xx,yy;
    TextView hour,min,sec,cal;
    Button start,stop,zero,back;
    int h=0,m=0,s=0;
    double se=0;
    double weight;
    double ca;
    boolean flagstrat=false;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                for(int result:grantResults){
                    if(result !=
                            PackageManager.PERMISSION_GRANTED) {
                        finish();
                    } else {
                        SupportMapFragment map =
                                (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
                        map.getMapAsync(this);
                    }
                }
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        commad=LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new
                            String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS);
        } else{
            SupportMapFragment map =(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
            map.getMapAsync(this);
        }
        hour = (TextView)findViewById(R.id.hour);
        min  = (TextView)findViewById(R.id.min);
        sec  = (TextView)findViewById(R.id.sec);
        start = (Button)findViewById(R.id.start);
        stop = (Button)findViewById(R.id.stop);
        zero = (Button)findViewById(R.id.zero);
        cal = (TextView)findViewById(R.id.cal);
        back = (Button)findViewById(R.id.back);

        Timer timer = new Timer();
        timer.schedule(tTask, 0, 1000);//每一秒執行一次，零秒後開始執行
        weight = getIntent().getExtras().getDouble("weight", 0);

        start.setOnClickListener(listener);
        stop.setOnClickListener(listener);
        zero.setOnClickListener(listener);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double temp = ca;
                Intent a = new Intent();
                Bundle b = new Bundle();
                b.putDouble("run_reduce",temp);
                a.putExtras(b);
                setResult(100, a);
                finish();
            }
        });

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if
                (ActivityCompat.checkSelfPermission(Main3Activity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Main3Activity.this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main3Activity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},MY_LOCATION);
            return;
        }

        googleMap.setMyLocationEnabled(true);

        LocationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        LocationManager.requestLocationUpdates(commad,2000,1,locationListener);
        Location location = LocationManager.getLastKnownLocation(commad);
        PolylineOptions polylineOpt = new PolylineOptions();
        a=location.getLongitude();
        b=location.getLatitude();
        //polylineOpt.add(new LatLng(25.033611, 121.565000));
        polylineOpt.add(new LatLng(b, a));
       // polylineOpt.add(new LatLng(25.047924, 121.517081));
        polylineOpt.color(Color.BLUE);
        Polyline polyline = googleMap.addPolyline(polylineOpt);
        polyline.setWidth(5);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(25.034, 121.545), 10));
    }
    public LocationListener locationListener=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {


        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public TimerTask tTask = new TimerTask(){
        public void run(){
            //每秒要執行的程式
            if(flagstrat==true){
                Message message = new Message();

                if(s<60){
                    s++;
                    message.what=1;
                }
                else {
                    s=0;
                    m++;
                    message.what=2;
                    if(m==60)
                    {
                        m=0;
                        h++;
                        message.what=3;
                    }

                }

                handler.sendMessage(message);

            }
        }

    };


    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch(msg.what){
                case 1:
                    if(s<10){
                        sec.setText("0"+String.valueOf(s));//秒數小於10補個零
                    }
                    else if(s==60){
                        sec.setText("00");
                    }
                    else{
                        sec.setText(String.valueOf(s));//用textview顯示秒數
                    }
                    break;
                case 2:
                    if(m<10){
                        min.setText("0"+String.valueOf(m));//分數小於10補個零
                    }
                    else{
                        min.setText(String.valueOf(m));//用textview顯示分數
                    }
                    break;
                case 3:
                    if(h<10){
                        hour.setText("0"+String.valueOf(h));//時數小於10補個零
                    }
                    else{
                        hour.setText(String.valueOf(h));//用textview顯示時數
                    }
                    break;
                default:
            }

        }
    };

    private Button.OnClickListener listener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch(v.getId()){
                case R.id.start:
                    flagstrat=true;
                    cal.setText(String.format("計算中...."));
                    break;
                case R.id.stop:
                    se=s;
                    ca=weight*((h*60)+m+(se/60))*9.4;
                    cal.setText(String.format("%.2f",ca));
                    flagstrat=false;
                    break;
                case R.id.zero:
                    cal.setText(String.format("0.00"));
                    h=0;
                    m=0;
                    s=0;
                    hour.setText("00");
                    min. setText("00");
                    sec. setText("00");
                    break;
            }


        }

    };
}
