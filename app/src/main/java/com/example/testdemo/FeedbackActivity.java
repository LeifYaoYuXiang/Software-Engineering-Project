package com.example.testdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        TextView textView=findViewById(R.id.feedback);

        String type=bundle.getString("type");
        boolean finished=bundle.getBoolean("finished");
        int duringTime=Math.abs(bundle.getInt("duringTime"));

        int minute=duringTime/60;
        int second=duringTime%60;

        String finishExp=null;
        if(finished){
            finishExp="成功！已完成";
        }else{
            finishExp="失败！未完成";
        }


        StringBuilder stringBuilder=new StringBuilder();
        if(finished){
            stringBuilder=new StringBuilder();
            stringBuilder.append("真棒!为自己加油喝彩！\n");
            if("Meeting".equals(type)){
                stringBuilder.append("这次会议上专注了");
            }
            if("Sports".equals(type)){
                stringBuilder.append("这次运动中专注了");
            }
            if("Study".equals(type)){
                stringBuilder.append("这次学习上专注了");
            }
            stringBuilder.append(minute+"分钟"+second+"秒"+"\n");
            stringBuilder.append("下次再接再厉哦");
        }else{
            stringBuilder=new StringBuilder();
            stringBuilder.append("太遗憾了\n");
            if("Meeting".equals(type)){
                stringBuilder.append("这次会议上专注了");
            }
            if("Sports".equals(type)){
                stringBuilder.append("这次运动中专注了");
            }
            if("Study".equals(type)){
                stringBuilder.append("这次学习上专注了");
            }
            stringBuilder.append(minute+"分钟"+second+"秒"+"\n");
            stringBuilder.append("下次再接再厉哦");
        }
        textView.setText(stringBuilder.toString());

        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuider=new NotificationCompat.Builder(this)
                .setContentTitle("本次专注已结束")
                .setSmallIcon(R.drawable.clock)
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL);

        notificationManager.notify(1,notificationBuider.build());

        HistoryDatabaseOpenHelper historyDatabaseOpenHelper=new HistoryDatabaseOpenHelper(this,"Records.db",null,1);
        SQLiteDatabase sqLiteDatabase=historyDatabaseOpenHelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("type",type);
        contentValues.put("time",duringTime);
        contentValues.put("finished",finishExp);
        contentValues.put("date",System.currentTimeMillis());

        sqLiteDatabase.insert("Record",null,contentValues);


        Button button=findViewById(R.id.returnFromFeedbackToHistory);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(FeedbackActivity.this,HistoryActivity.class);
                startActivity(intent1);
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(FeedbackActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
