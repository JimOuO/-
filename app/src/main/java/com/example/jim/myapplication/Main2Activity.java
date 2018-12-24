package com.example.jim.myapplication;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Main2Activity extends Activity {

    TextView hour,min,sec,cal;
    Button start,stop,zero,back;
    int h=0,m=0,s=0;
    double se=0;
    double weight;
    double ca;
    boolean flagstrat=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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
                Intent i = new Intent();
                Bundle a = new Bundle();
                a.putDouble("jump_reduce",temp);
                i.putExtras(a);
                setResult(101, i);
                finish();
            }
        });


    }


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
                    ca=weight*((h*60)+m+(se/60))*9;
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
