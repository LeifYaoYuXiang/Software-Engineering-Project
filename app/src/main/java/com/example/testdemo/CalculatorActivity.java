package com.example.testdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener{
    //creater Su Zhan && Li ZiGen
    Button btn_0,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_pt;
    Button btn_mul,btn_div,btn_add,btn_sub;
    Button btn_clr,btn_del,btn_eq;
    EditText et_input;
    boolean clr_flag;
    /**
    判断et编辑文本框中是否清空
    **/
    double preAnswer=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        btn_0= findViewById(R.id.btn_0);
        btn_1= findViewById(R.id.btn_1);
        btn_2= findViewById(R.id.btn_2);
        btn_3= findViewById(R.id.btn_3);
        btn_4= findViewById(R.id.btn_4);
        btn_5= findViewById(R.id.btn_5);
        btn_6= findViewById(R.id.btn_6);
        btn_7= findViewById(R.id.btn_7);
        btn_8= findViewById(R.id.btn_8);
        btn_9= findViewById(R.id.btn_9);
        btn_pt= findViewById(R.id.btn_pt);
        btn_add= findViewById(R.id.btn_plus);
        btn_sub= findViewById(R.id.btn_min);
        btn_mul= findViewById(R.id.btn_mul);
        btn_div= findViewById(R.id.btn_div);
        btn_clr= findViewById(R.id.btn_clr);
        btn_del= findViewById(R.id.btn_del);
        btn_eq= findViewById(R.id.btn_eq);
        et_input= findViewById(R.id.et_input);

        et_input.setCursorVisible(false);

        //给按钮设置的点击事件

        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_pt.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
        btn_mul.setOnClickListener(this);
        btn_div.setOnClickListener(this);
        btn_clr.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_eq.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String str=et_input.getText().toString();

        switch (view .getId()){
            case   R.id.btn_0:
            case   R.id.btn_1:
            case   R.id.btn_2:
            case   R.id.btn_3:
            case   R.id.btn_4:
            case   R.id.btn_5:
            case   R.id.btn_6:
            case   R.id.btn_7:
            case   R.id.btn_8:
            case   R.id.btn_9:
            case   R.id.btn_pt:
                if(clr_flag){
                    clr_flag=false;
                    str="";
                    et_input.setText("");
                }
                et_input.setText(str+((Button)view ).getText());
                break;

            case R.id.btn_plus:
            case R.id.btn_min:
            case R.id.btn_mul:
            case R.id.btn_div:
                if(clr_flag){
                    clr_flag=false;
                    str="";
                    et_input.setText("");
                }
                if(str.contains("+")||str.contains("-")||str.contains("×")||str.contains("÷")) {
                    str=str.substring(0,str.indexOf(" "));
                }
                et_input.setText(str+" "+((Button)view ).getText()+" ");
                break;
            case R.id.btn_clr:
                if(clr_flag) {
                    clr_flag=false;
                }
                str="";
                et_input.setText("");

                break;
//判断是否为空，然后在进行删除
            case R.id.btn_del:
                if(clr_flag){
                    clr_flag=false;
                    str="";
                    et_input.setText("");
                }

                else if(str!=null&&!"".equals(str)){
                    et_input.setText(str.substring(0,str.length()-1));
                }
                break;
            //单独运算最后结果
            case R.id.btn_eq:
                //调用下面的方法
                getResult();
                break;
            default:
                break;
        }
    }

    private void getResult() {
        String exp=et_input.getText().toString();
        if(exp==null|| "".equals(exp)) {
            return ;
        }
        //因为没有运算符所以不用运算
        if(!exp.contains(" ")){
            return ;
        }
        if(clr_flag){
            clr_flag=false;
            return;

        }
        clr_flag=true;
        //截取运算符前面的字符串
        String s1=exp.substring(0,exp.indexOf(" "));
        //截取的运算符
        String op=exp.substring(exp.indexOf(" ")+1,exp.indexOf(" ")+2);
        //截取运算符后面的字符串
        String s2=exp.substring(exp.indexOf(" ")+3);
        double cnt=0;

        if(!"".equals(s1) &&!"".equals(s2)){

            double d1=0.0;
            double d2=0.0;
            try{
                d1=Double.parseDouble(s1);
                d2=Double.parseDouble(s2);
            }
            catch (Exception e) {
                Toast.makeText(this, "输入数据违法", Toast.LENGTH_SHORT).show();
            }
            if("+".equals(op)){
                cnt=d1+d2;
            }

            if("-".equals(op)){
                cnt=d1-d2;
            }
            if("×".equals(op)){
                cnt=d1*d2;
            }

            if("÷".equals(op)){
                if(d2==0) {
                    cnt=0;
                } else {
                    cnt=d1/d2+(d1%d2);
                }
            }
            double answer=cnt;
            if(!s1.contains(".")&&!s2.contains(".")&&!"÷".equals(op)) {
                int res = (int) answer;
                DecimalFormat df = new DecimalFormat( "0.00");
                et_input.setText(df.format(res)+"");
                this.preAnswer=res;
            }else {
                DecimalFormat df = new DecimalFormat( "0.00");
                et_input.setText(df.format(answer)+"");
                this.preAnswer=answer;
            }
        }

        //如果s1不是空    s2是空  就执行下一步

        else if(!"".equals(s1) && "".equals(s2)){
            double d1=0.0;
            try{
                d1=Double.parseDouble(s1);
            }
            catch (Exception e) {
                Toast.makeText(this, "输入数据违法", Toast.LENGTH_SHORT).show();
            }

            if("+".equals(op)){
                cnt=d1;
                this.preAnswer=d1;
            }
            if("-".equals(op)){
                cnt=d1;
                this.preAnswer=d1;
            }
            //noinspection AlibabaUndefineMagicConstant
            if("×".equals(op)){
                cnt=0;
                this.preAnswer=0;
            }
            if("÷".equals(op)){
                cnt=0;
                this.preAnswer=0;
            }
            et_input.setText(cnt+"");

        }

        //如果s1是空    s2不是空  就执行下一步
        else if("".equals(s1) &&!"".equals(s2)){
            double d2=0.0;
            try{
                d2=Double.parseDouble(s2);
            }
            catch (Exception e) {
                Toast.makeText(this, "输入数据违法", Toast.LENGTH_SHORT).show();
            }
            if("+".equals(op)){
                cnt=this.preAnswer+d2;
                this.preAnswer=d2;
            }
            if("-".equals(op)){
                cnt=preAnswer-d2;
                this.preAnswer=cnt;
            }
            if("×".equals(op)){
                cnt=this.preAnswer*d2;
                this.preAnswer=cnt;

            }
            if("÷".equals(op)){
                if(d2==0) {
                    cnt=0;
                } else {
                    cnt=preAnswer/d2+(preAnswer%d2);
                }
                this.preAnswer=cnt;
            }

            if(!s2.contains(".")) {
                double answer=cnt;
                DecimalFormat df = new DecimalFormat( "0.00");
                this.preAnswer=answer;
                et_input.setText(df.format(answer)+"");
            }else {
                double answer=cnt;
                DecimalFormat df = new DecimalFormat( "0.00");
                this.preAnswer=answer;
                et_input.setText(df.format(answer)+"");
            }
        }
        else {
            et_input.setText("");
        }
    }
}
