package com.example.jim.myapplication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected EditText tall;
    protected EditText age;
    protected EditText weight;
    protected RadioButton boy;
    protected RadioButton girl;
    protected RadioGroup radioGroup;
    protected Button button;
    protected TextView standardWeight;
    protected TextView bodyFat;
    protected TextView BMI;
    protected TextView BMR;
    Button run;
    Button jump;
    TextView t_reduce;
    Double jump_cal=0.0;
    Double run_cal=0.0;

    int gender=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tall = (EditText) findViewById(R.id.tall);
        weight = (EditText) findViewById(R.id.weight);
        age = (EditText) findViewById(R.id.age);
        boy = (RadioButton) findViewById(R.id.boy);
        girl = (RadioButton) findViewById(R.id.girl);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        button = (Button) findViewById(R.id.button);
        standardWeight = (TextView) findViewById(R.id.standardWeight);
        bodyFat = (TextView) findViewById(R.id.bodyFat);
        BMI = (TextView) findViewById(R.id.BMI);
        BMR = (TextView) findViewById(R.id.BMR);
        run = (Button) findViewById(R.id.run);
        jump = (Button) findViewById(R.id.jump);


        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent();
                i.setClass(MainActivity.this,Main2Activity.class);
                double wei=Double.parseDouble(weight.getText().toString());
                i.putExtra("weight",wei);
                startActivityForResult(i,0);
            }
        });

        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a =new Intent();
                a.setClass(MainActivity.this,Main3Activity.class);
                double wei=Double.parseDouble(weight.getText().toString());
                a.putExtra("weight",wei);
                startActivityForResult(a,1);
            }
        });



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.boy:
                        gender = 1;
                        break;
                    case R.id.girl:
                        gender = 2;
                        break;
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAsyncTask();
            }
        });

    }
    private void runAsyncTask(){
        new AsyncTask<Void,Integer,Boolean>(){
            private ProgressDialog dialog =new ProgressDialog(MainActivity.this);
            protected void onPreExecute(){
                super.onPreExecute();
                dialog.setMessage("計算中");
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.show();
            }
            protected Boolean doInBackground(Void... voids){
                int progress =0;
                while (progress <=0){
                    try{
                        Thread.sleep(50);
                        publishProgress(Integer.valueOf(progress));
                        progress++;
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    } }
                return true;
            }

            protected void onProgressUpdate(Integer... values){
                super.onProgressUpdate(values);
                dialog.setProgressStyle(values[0]);
            }
            protected void onPostExecute(Boolean status){
                dialog.dismiss();
                double cal_Tall = Double.parseDouble(tall.getText().toString());
                double cal_Weight=Double.parseDouble(weight.getText().toString());
                double cal_age = Double.parseDouble(age.getText().toString());
                double cal_StandWeight =0;
                double cal_BodyFat=0;
                double cal_BMI=0;
                double cal_BMR=0;
                if(gender==1){
                    cal_StandWeight=(cal_Tall-80)*70/100;
                    cal_BMI=cal_Weight/(cal_Tall/100*cal_Tall/100);
                    cal_BodyFat=(cal_Weight - (0.88*cal_StandWeight))/cal_Weight*100;
                    cal_BMR=(13.7*cal_Weight)+(5*cal_Tall)-(6.8*cal_age)+66;
                }
                if(gender==2){
                    cal_StandWeight=(cal_Tall-70)*60/100;
                    cal_BMI=cal_Weight/(cal_Tall/100*cal_Tall/100);
                    cal_BodyFat=(cal_Weight - (0.82*cal_StandWeight))/cal_Weight*100;
                    cal_BMR=(9.6*cal_Weight)+(1.8*cal_Tall)-(4.7*cal_age)+655;
                }
                standardWeight.setText(String.format("%.2f",cal_StandWeight));
                bodyFat.setText(String.format("%.2f",cal_BodyFat));
                BMI.setText(String.format("%.2f",cal_BMI));
                BMR.setText(String.format("%.2f",cal_BMR));
            }




        }.execute();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode ) {
           case 0:
            if (resultCode == 101){
                Bundle a =data.getExtras();
                jump_cal = a.getDouble("jump_reduce");
            }
            break;

            case 1:
            if (resultCode == 100){
                Bundle b =data.getExtras();
                run_cal = b.getDouble("run_reduce");
            }
            break;
        }
        double cal = jump_cal + run_cal;
        t_reduce = (TextView) findViewById(R.id.reduce);
        t_reduce.setText(String.format("%.2f",cal));
    }

}
