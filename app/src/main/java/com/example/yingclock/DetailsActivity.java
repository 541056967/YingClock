package com.example.yingclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import memo.Memo;
import memo.MemoOperator;

public class DetailsActivity extends AppCompatActivity {

    CalendarUtil calendarUtil = new CalendarUtil();
    ArrayList<Long> dataList = new ArrayList<>();

    private int memo_id=0;
    private TextView tv_time, tv_day, tv_hour, tv_minute, tv_second, tv_title;
    private int year,mouth,day,hour,minute;
    private  String title;
    private ImageView back;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            Long mSecond = calendarUtil.getSecond(year,mouth,day,hour,minute);
            dataList = calendarUtil.getDate(mSecond);
            tv_title.setText(title);
            tv_time.setText(year+"年"+mouth+"月"+day+"日 "+ hour+"时"+minute+"分");
            tv_day.setText(dataList.get(3).toString()+"天");
            tv_hour.setText(dataList.get(2).toString()+"小时");
            tv_minute.setText(dataList.get(1).toString()+"分钟");
            tv_second.setText(dataList.get(0).toString()+"秒");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowsUtils.setStatusBarColor(getWindow(),getResources(),R.color.green);
        setContentView(R.layout.activity_details);

        tv_title = findViewById(R.id.tv_title);
        tv_time = findViewById(R.id.tv_detailTime);
        tv_day = findViewById(R.id.tv_day);
        tv_hour = findViewById(R.id.tv_hour);
        tv_minute = findViewById(R.id.tv_minute);
        tv_second = findViewById(R.id.tv_second);

        back = findViewById(R.id.btn_back);
        Intent intent=getIntent();
        memo_id=intent.getIntExtra("memo_id",0);
        MemoOperator memoOperator=new MemoOperator( this);
        Memo memo = memoOperator.getMemoById(memo_id);
        year = memo.year;
        mouth = memo.mouth;
        day = memo.day;
        hour = memo.hour;
        minute = memo.minute;
        title = memo.title;
        refreshTime();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    //事件刷新线程
    private void refreshTime(){
        new Thread(){//每秒更新时间
            @Override
            public void run() {
                while (true){
                    Message meg  = new Message();
                    handler.sendMessage(meg);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}