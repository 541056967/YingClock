package com.example.yingclock;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import memo.DBHelper;
import memo.Memo;
import memo.MemoOperator;

public class MainActivity extends AppCompatActivity implements DateRangePickerFragment.OnDateRangeSelectedListener, TimePickerDialog.OnTimeSetListener  {

    CalendarUtil calendarUtil = new CalendarUtil();
    ArrayList<Long> dataList = new ArrayList<>();
    private  TextView  tv_today;
    private Button add;
    private ListView list;

    FragmentManager fragmentManager;
    TimePickerDialog timePickerDialog ;
    int Hour, Minute;
    Calendar calendar ;

    final MemoOperator memoOperator = new MemoOperator(MainActivity.this);


    ArrayList<HashMap<String, String>> memoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置状态栏背景色
        WindowsUtils.setStatusBarColor(getWindow(),getResources(),R.color.green);
        setContentView(R.layout.activity_main);

//        DateRangePickerFragment dateRangePickerFragment= DateRangePickerFragment.newInstance((DateRangePickerFragment.OnDateRangeSelectedListener) MainActivity.this,false);
//        dateRangePickerFragment.show(getSupportFragmentManager(),"datePicker");


        list = findViewById(R.id.list);
        add = findViewById(R.id.add);
        tv_today = findViewById(R.id.tv_today);

        Date date = new Date();
        String time = date.toLocaleString().split(" ")[0];
        tv_today.setText(time);

        lodaData();
//        SimpleAdapter adapter = new SimpleAdapter(this,memoList,R.layout.item_memo,new String[]{"context","title","context","time"},new int[]{R.id.card_view,R.id.note_title,R.id.note_content,R.id.note_time});
        MyAdapter adapter = new MyAdapter(memoList,this);
        list.setAdapter(adapter);

        fragmentManager = getSupportFragmentManager();
        setListener();

    }

    //重写点击“确定”按钮触发的操作
    @Override
    public void onDateRangeSelected(int startDay, int startMonth, int startYear) {
        Log.e("range : ","from: "+startDay+"-"+startMonth+"-"+startYear);

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = "Time: "+hourOfDay+"h"+minute+"m"+second;
        Toast.makeText(MainActivity.this, time, Toast.LENGTH_LONG).show();
    }


    public void Timepicker(View v) {
        timePickerDialog = TimePickerDialog.newInstance(MainActivity.this, Hour, Minute,false );
        timePickerDialog.setThemeDark(false);
        //timePickerDialog.showYearPickerFirst(false);
        timePickerDialog.setTitle("Time Picker");
        timePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Toast.makeText(MainActivity.this, "Timepicker Canceled", Toast.LENGTH_SHORT).show();
            }
        });
        timePickerDialog.show(fragmentManager, "TimePickerDialog");
    }

    public void setListener(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });

        if (memoList.size() != 0) {
            //点击listView的任何一项跳到详情页面
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long i) {
                    String id = memoList.get(position).get("id");
                    Intent intent = new Intent();

                    switch (memoList.get(position).get("compare")){
                        case "1":
                            intent.setClass(MainActivity.this, DetailActivity.class);
                            intent.putExtra("memo_id", Integer.parseInt(id));
                            startActivity(intent);
                            break;

                        case "0":
                            intent.setClass(MainActivity.this, DetailsActivity.class);
                            intent.putExtra("memo_id", Integer.parseInt(id));
                            startActivity(intent);
                            break;
                    }
                }
            });

            //长按实现对列表的删除
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("确定删除？");
                    builder.setTitle("提示");

                    //添加AlterDialog.Builder对象的setPositiveButton()方法
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            String id = memoList.get(position).get("id");
//                            noteOperator.delete(Integer.parseInt(id));
                            //设置finish状态为true
                            memoOperator.delete(Integer.valueOf(id));
                            memoList.remove(position);
                            MyAdapter adapter = new MyAdapter(memoList,MainActivity.this);
                            //SimpleAdapter adapter = new SimpleAdapter(MainActivity.this,memoList,R.layout.item_memo,new String[]{"title","context","time"},new int[]{R.id.note_title,R.id.note_content,R.id.note_time});
                            list.setAdapter(adapter);
                        }
                    });

                    //添加AlterDialog.Builder对象的setNegativeButton()方法
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.create().show();
                    return true;
                }
            });



        }

    }

    public void lodaData(){
        memoList = memoOperator.getMemoList();
    }


}