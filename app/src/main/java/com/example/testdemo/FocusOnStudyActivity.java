package com.example.testdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import androidx.annotation.RequiresApi;

public class
FocusOnStudyActivity extends AppCompatActivity {
    protected int timeSet;
    protected Chronometer chronometer;
    protected long startTime;
    //protected long startPoint;
    protected Vibrator vibrator;

    private boolean finished = false;
    private String type = "Study";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_on_study);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.timeSet = bundle.getInt("timeSet");


        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        this.chronometer = findViewById(R.id.tick_Study);
        this.startTime = SystemClock.elapsedRealtime();
        chronometer.setBase(this.startTime);
        chronometer.setFormat("时间已经过去\n%s");

        chronometer.start();

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (SystemClock.elapsedRealtime() - chronometer.getBase() >= timeSet * 1000) {
                    chronometer.stop();
                    finished = true;
                    vibrator.vibrate(3000);

                    Intent intent1 = new Intent ( FocusOnStudyActivity.this, FeedbackActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("duringTime", Math.abs(timeSet));
                    bundle1.putString("type", type);
                    bundle1.putBoolean("finished", finished);

                    intent1.putExtras(bundle1);
                    startActivity(intent1);
                }
            }
        });


        Button button = findViewById(R.id.calculator);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FocusOnStudyActivity.this, CalculatorActivity.class);
                startActivity(intent);
            }
        });

        Button button1 = findViewById(R.id.checkUp);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder aBuider = new AlertDialog.Builder(FocusOnStudyActivity.this);
                final EditText editText = new EditText(FocusOnStudyActivity.this);
                aBuider.setTitle("查询英文单词");
                aBuider.setView(editText);
                aBuider.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(FocusOnStudyActivity.this, DictionaryActivity.class);
                        Bundle bundle = new Bundle();
                        String s = editText.getText().toString();
                        bundle.putString("checkItem", s);

                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                aBuider.setNegativeButton("取消查询", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                aBuider.show();
            }

        });
    }



    @Override
    public void onBackPressed(){
        AlertDialog.Builder reminderBuilder = new AlertDialog.Builder(FocusOnStudyActivity.this);
        reminderBuilder.setTitle("退出提示");
        reminderBuilder.setMessage("按下确认键则放弃此次专注");
        reminderBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int duringTime =  (int) (SystemClock.elapsedRealtime() - startTime) / 1000;

                Intent intent2 = new Intent(FocusOnStudyActivity.this, FeedbackActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("duringTime", duringTime);
                bundle2.putString("type", type);
                bundle2.putBoolean("finished", finished);
                intent2.putExtras(bundle2);
                startActivity(intent2);
            }
        });
        reminderBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        reminderBuilder.show();
    }

    @Override
    protected void onPause() {
        Log.i("Pause", "我被Pause");
        chronometer.stop();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("Pause","我被Stop");
        chronometer.stop();
        super.onStop();
    }

    @Override
    protected void onRestart() {
        chronometer.start();
        super.onRestart();
    }


}
