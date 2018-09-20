package com.bawei.chenxiaoxu.month1.witget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.chenxiaoxu.month1.R;


/**
 * Created by _ヽ陌路离殇ゞ on 2018/9/19.
 */

public class MyJIaJianView extends LinearLayout {

    private View inflate;
    private TextView jia;
    private TextView jian;
    private EditText Num;
    private int num=1;

    public MyJIaJianView(Context context) {
        this(context,null);
    }

    public MyJIaJianView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyJIaJianView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate = LayoutInflater.from(context).inflate(R.layout.jia_jian_layout, this, true);

        jia = inflate.findViewById(R.id.jia);
        jian = inflate.findViewById(R.id.jian);
        Num = inflate.findViewById(R.id.num);
         Num.setText(num+"");

         jia.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 num++;
                 Num.setText(num+"");
                 if(jiaJianListener!=null){
                     jiaJianListener.getNum(num);
                 }
             }
         });
         jian.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 num--;
                 if(num<=0){
                     Toast.makeText(getContext(), "数量不能小于0", Toast.LENGTH_SHORT).show();
                     num=1;
                 }
                 Num.setText(num+"");
                 if(jiaJianListener!=null){
                     jiaJianListener.getNum(num);
                 }

             }
         });

    }
    public  void setNum(int n){
        Num.setText(n+"");
        num=Integer.parseInt(Num.getText().toString());
    }
    private JiaJianListener jiaJianListener;
    public void setJiaJianListener(JiaJianListener jiaJianListener){
        this.jiaJianListener=jiaJianListener;
    }
    public interface JiaJianListener{
        void getNum(int num);
    }

}
