package com.example.testdemo;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
//creater LiZigen
public class FocusOnMeetingActivity extends AppCompatActivity {
    protected Chronometer chronometer;
    protected long startTime;
    private boolean finished = true;
    private String type = "Meeting";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_on_meeting);

        this.chronometer = findViewById(R.id.tick_Meeting);
        this.startTime = SystemClock.elapsedRealtime();
        chronometer.setBase(this.startTime);
        chronometer.setFormat("时间已经过去\n%s");
        chronometer.start();


        Button button = findViewById(R.id.writeNoteInMeeting);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(FocusOnMeetingActivity.this);
                AlertDialog.Builder inputDialog = new AlertDialog.Builder(FocusOnMeetingActivity.this);
                inputDialog.setTitle("请输入该笔记的标题").setView(editText);
                inputDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String title = editText.getText().toString();
                                Bundle bundle = new Bundle();
                                bundle.putString("title", title);

                                Intent intent = new Intent(FocusOnMeetingActivity.this, WriteNoteActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                Toast.makeText(FocusOnMeetingActivity.this, "写下自己的笔记", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        ToggleButton toggleButton = findViewById(R.id.muteOnOff);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                            && !notificationManager.isNotificationPolicyAccessGranted()) {
                        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                        getApplicationContext().startActivity(intent);
                    }
                    AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    Toast.makeText(FocusOnMeetingActivity.this, "开启静音", Toast.LENGTH_SHORT).show();
                } else {
                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                            && !notificationManager.isNotificationPolicyAccessGranted()) {
                        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                        getApplicationContext().startActivity(intent);
                    }
                    AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    Toast.makeText(FocusOnMeetingActivity.this, "关闭静音", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder reminderBuilder = new AlertDialog.Builder(FocusOnMeetingActivity.this);
        reminderBuilder.setTitle("退出提示");
        reminderBuilder.setMessage("按下确认键则放弃此次专注");
        reminderBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int duringTime = (int) (SystemClock.elapsedRealtime() - startTime) / 1000;
                chronometer.stop();
                Intent intent2 = new Intent(FocusOnMeetingActivity.this, FeedbackActivity.class);
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
    protected void onDestroy() {
        super.onDestroy();
    }

}
