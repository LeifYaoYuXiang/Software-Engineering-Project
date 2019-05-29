package com.example.testdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecordAdapter extends ArrayAdapter<record> {

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    private int resourceId;
    public RecordAdapter(Context context, int resource, List<record> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }
    private String timeExpression(int duringTime) {
        String s=  "";
        int minute = duringTime / 60;
        int second = duringTime % 60;
        s = s+minute+"分钟"+second+"秒";
        return s;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        record tempRecord = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = view.findViewById(R.id.recordItem);
            viewHolder.textView = view.findViewById(R.id.TypeTimeFinished);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();

        }

        String text = "";

        if (tempRecord.getType().equals("Study")) {
            viewHolder.imageView.setImageResource(R.drawable.bookmark);
            text =  "专注学习" + timeExpression(tempRecord.getTime());
            if (tempRecord.getFinished().equals("成功！已完成")){
                text = text + "\n成功完成自己任务";
            } else {
                text = text + "\n很遗憾并没有完成";
            }
        }
        if (tempRecord.getType().equals("Sports")) {
            viewHolder.imageView.setImageResource(R.drawable.paper_plane);
            text = "专注运动" + timeExpression(tempRecord.getTime());
            if (tempRecord.getFinished().equals("成功！已完成")) {
                text = text + "\n成功完成自己任务";
            } else {
                text = text + "\n很遗憾并没有完成";
            }
        }
        if (tempRecord.getType().equals("Meeting")) {
            viewHolder.imageView.setImageResource(R.drawable.group);
            text = "专注会议"+timeExpression(tempRecord.getTime());
            if (tempRecord.getFinished().equals("成功！已完成")){
                text = text + "\n成功完成自己任务";
            } else {
                text = text + "\n很遗憾并没有完成";
            }
        }

        viewHolder.textView.setText(text);

        return view;
    }
}
