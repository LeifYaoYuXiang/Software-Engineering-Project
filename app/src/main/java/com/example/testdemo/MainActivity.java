package com.example.testdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    //@author Li Jiadi

    protected SeekBar seekBar;
    protected TextView textView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvLeftMenu;
    private Button start;
    private boolean isExit = false;

    protected HoloCircleSeekBar holoCircleSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();

        start = findViewById(R.id.startButton);
        start.setOnClickListener(new PopupMenuListener());

        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);


        ArrayList<ListItem> arrayList = new ArrayList<ListItem>();
        arrayList.add(new ListItem("历史记录", R.drawable.monitor));
        arrayList.add(new ListItem("会议笔记", R.drawable.file));
        arrayList.add(new ListItem("使用说明", R.drawable.setup));
        ListItemAdapter listItemAdaptar = new ListItemAdapter(this, arrayList);
        lvLeftMenu.setAdapter(listItemAdaptar);
        lvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                    startActivity(intent);
                }
                if (i == 1) {
                        Intent intent = new Intent(MainActivity.this, NoteListActivity.class);
                       startActivity(intent);
                }
                if (i == 2) {
                       Intent intent = new Intent(MainActivity.this, ProductionActivity.class);
                        startActivity(intent);
                }
            }
        });
        setSupportActionBar(toolbar);
    }

    /*  make drawer layout clickable */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        lvLeftMenu = (ListView) findViewById(R.id.drawerList);
        toolbar = findViewById(R.id.toorbar);
    }

    @Override
    public void onBackPressed() {
        if (isExit) {
            this.finish();

        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            isExit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        }

    }

    public class PopupMenuListener implements View.OnClickListener {
        protected int type = 1;
        protected int focusTime = 15 * 60;

        @Override
        public void onClick(View view) {
            AlertDialog.Builder aBuilder = new AlertDialog.Builder(MainActivity.this);
            aBuilder.setTitle("请选择自己的专注类型");
            final String[] types = {"专注学习", "专注运动", "专注会议"};
            //
            aBuilder.setSingleChoiceItems(types, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (types[i].equals("专注学习")) {
                        type = 1;
                    }
                    if (types[i].equals("专注运动")) {
                        type = 2;
                    }
                    if (types[i].equals("专注会议")) {
                        type = 3;
                    }
                }
            });

            //
            aBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this, "你取消了这一次专注", Toast.LENGTH_SHORT).show();
                }
            });
            //
            aBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //
                    if (type == 1) {
                        Toast.makeText(MainActivity.this, "你选择了专注学习", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder aBuilder = new AlertDialog.Builder(MainActivity.this);
                        View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.seek_bar_time, null);
                        aBuilder.setView(view1);

                        final AlertDialog dialog = aBuilder.create();
                        dialog.show();
                        holoCircleSeekBar = dialog.findViewById(R.id.time_toChoose);
                        textView = dialog.findViewById(R.id.time_diaplay);
                        Button buttonToSure = dialog.findViewById(R.id.forSure);
                        Button buttonNotToSure = dialog.findViewById(R.id.NotSure);
                        buttonToSure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "选择了"+holoCircleSeekBar.getValue()+"分钟", Toast.LENGTH_SHORT).show();

                                focusTime=holoCircleSeekBar.getValue()*60;
                                Bundle bundle = new Bundle();
                                bundle.putInt("timeSet", focusTime);
                                Intent intent = new Intent(MainActivity.this, FocusOnStudyActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                        buttonNotToSure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "您取消了这一次的专注", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                    }

                    if (type == 2) {
                        Toast.makeText(MainActivity.this, "你选择了专注运动", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder aBuilder = new AlertDialog.Builder(MainActivity.this);
                        View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.seek_bar_time,null);
                        aBuilder.setView(view1);

                        final AlertDialog dialog = aBuilder.create();
                        dialog.show();
                        holoCircleSeekBar = dialog.findViewById(R.id.time_toChoose);

                        textView = dialog.findViewById(R.id.time_diaplay);
                        Button buttonToSure = dialog.findViewById(R.id.forSure);
                        Button buttonNotToSure = dialog.findViewById(R.id.NotSure);

                        buttonToSure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "选择了" + holoCircleSeekBar.getValue() + "分钟", Toast.LENGTH_SHORT).show();
                                focusTime = holoCircleSeekBar.getValue() * 60;

                                Bundle bundle = new Bundle();
                                bundle.putInt("timeSet", focusTime);
                                Intent intent = new Intent(MainActivity.this, FocusOnSportsActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                        buttonNotToSure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "您取消了这一次的专注", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                    }

                    if (type == 3) {
                        Toast.makeText(MainActivity.this, "你选择了专注会议", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, FocusOnMeetingActivity.class);
                        startActivity(intent);
                    }
                }
            });
            aBuilder.show();

        }

    }
}
