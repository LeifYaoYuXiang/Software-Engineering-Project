package com.example.testdemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    //@author:
    private ListView recordList;
    private HistoryDatabaseOpenHelper historyDatabaseOpenHelper;
    private List<record> recordsList=new ArrayList<>();

    protected int focusInOneWeekCount=0;
    protected int focusInOneWeekSum=0;

    protected int focusInOneWeekStudySum=0;
    protected int focusInOneWeekSportsSum=0;
    protected int focusInOneWeekMeetingSum=0;

    protected ViewPager viewPager;
    protected List<View> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recordList=findViewById(R.id.recordsList);
        initRecords();

        RecordAdapter recordAdapter=new RecordAdapter(HistoryActivity.this,R.layout.record_item,recordsList);
        recordList.setAdapter(recordAdapter);

        this.viewPager=findViewById(R.id.viewPagerForHistory);

        LayoutInflater layoutInflater=getLayoutInflater();
        View seven_day_text=layoutInflater.inflate(R.layout.seven_days_text_feedback,null);

        TextView textView=seven_day_text.findViewById(R.id.feedbackToConclude);
        textView.setText("在最近七天内,\n"+"您共计专注"+this.focusInOneWeekCount+"次\n"+"专注时长"+this.focusInOneWeekSum/60+"分钟"+this.focusInOneWeekSum%60+"秒\n"
                +"学习:"+this.focusInOneWeekStudySum/60+"分钟"+this.focusInOneWeekStudySum%60+"秒\n"
                +"运动:"+this.focusInOneWeekSportsSum/60+"分钟"+this.focusInOneWeekSportsSum%60+"秒\n"
                +"会议:"+this.focusInOneWeekMeetingSum/60+"分钟"+this.focusInOneWeekMeetingSum%60+"秒\n");

        View seven_day_diagram=layoutInflater.inflate(R.layout.seven_days_diagram_feedback,null);

        PieChart pieChart=seven_day_diagram.findViewById(R.id.pieChart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(true);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setHighlightPerTapEnabled(true);
        Legend l=pieChart.getLegend();
        l.setEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        //设置图例的形状
        l.setForm(Legend.LegendForm.DEFAULT);
        //设置图例的大小
        l.setFormSize(10);
        //设置每个图例实体中标签和形状之间的间距
        l.setFormToTextSpace(10f);
        l.setDrawInside(false);
        //设置图列换行(注意使用影响性能,仅适用legend位于图表下面)
        l.setWordWrapEnabled(true);
        //设置图例实体之间延X轴的间距（setOrientation = HORIZONTAL有效）
        l.setXEntrySpace(10f);
        //设置图例实体之间延Y轴的间距（setOrientation = VERTICAL 有效）
        l.setYEntrySpace(8f);
        //设置比例块Y轴偏移量
        l.setYOffset(0f);
        //设置图例标签文本的大小
        l.setTextSize(18f);
        l.setTextColor(Color.parseColor("#333333"));

        //数据列表
        ArrayList<PieEntry> pieEntryList = new ArrayList();

        int maxTemp=Math.max(this.focusInOneWeekStudySum,this.focusInOneWeekSportsSum);
        int max=Math.max(maxTemp,this.focusInOneWeekMeetingSum);

        if(max!=0){
            pieEntryList.add(new PieEntry(this.focusInOneWeekStudySum/this.focusInOneWeekCount,"专注学习"));
            pieEntryList.add(new PieEntry(this.focusInOneWeekSportsSum/this.focusInOneWeekCount,"专注运动"));
            pieEntryList.add(new PieEntry(this.focusInOneWeekMeetingSum/this.focusInOneWeekCount,"专注会议"));

            PieDataSet pieDataSet=new PieDataSet(pieEntryList,"");

            ArrayList<Integer> colors = new ArrayList<Integer>();
            colors.add(Color.RED);
            colors.add(Color.BLUE);
            colors.add(Color.YELLOW);

            pieDataSet.setColors(colors);

            PieData pieData = new PieData(pieDataSet);
            pieData.setDrawValues(false);

            pieChart.setData(pieData);
            pieChart.invalidate();

        }else{
            pieChart.setVisibility(View.INVISIBLE);
            TextView noneFocusExperience=seven_day_diagram.findViewById(R.id.noneFocusExperience);
            noneFocusExperience.setVisibility(View.VISIBLE);
        }


        this.list=new ArrayList<View>() ;



        this.list.add(seven_day_text);
        this.list.add(seven_day_diagram);

        PagerAdapter pagerAdapter=new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(list.get(position));
            }

            @NonNull
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(list.get(position));
                return list.get(position);
            }
        };

        viewPager.setAdapter(pagerAdapter);

    }

    private void initRecords(){
        this.historyDatabaseOpenHelper=new HistoryDatabaseOpenHelper(this,"Records.db",null,1);
        SQLiteDatabase sqLiteDatabase=historyDatabaseOpenHelper.getWritableDatabase();

        Cursor cursor=sqLiteDatabase.query("Record",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String type=cursor.getString(cursor.getColumnIndex("type"));
                int time=cursor.getInt(cursor.getColumnIndex("time"));
                String finished=cursor.getString(cursor.getColumnIndex("finished"));
                Long date=cursor.getLong(cursor.getColumnIndex("date"));
                record newRecord=new record(type,time,finished,0,date);
                this.recordsList.add(newRecord);
                if(SystemClock.currentThreadTimeMillis()-date<7*24*60*60*1000){
                    this.focusInOneWeekCount++;
                    this.focusInOneWeekSum=this.focusInOneWeekSum+time;
                    if("Study".equals(type)){
                        this.focusInOneWeekStudySum=this.focusInOneWeekStudySum+time;
                    }
                    if("Sports".equals(type)){
                        this.focusInOneWeekSportsSum=this.focusInOneWeekSportsSum+time;
                    }
                    if("Meeting".equals(type)){
                        this.focusInOneWeekMeetingSum=this.focusInOneWeekMeetingSum+time;
                    }
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
    }
}
