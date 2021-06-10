package com.example.yingclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import memo.Memo;
import memo.MemoOperator;

public class AddActivity extends AppCompatActivity implements DateRangePickerFragment.OnDateRangeSelectedListener, TimePickerDialog.OnTimeSetListener {

    private Button add_date, add_time;
    private ImageView back, save;
    private TextView tv_date, tv_time;
    private EditText edit_title;

    FragmentManager fragmentManager;
    TimePickerDialog timePickerDialog ;
    int Hour, Minute;

    Memo memo = new Memo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowsUtils.setStatusBarColor(getWindow(),getResources(),R.color.green);
        setContentView(R.layout.activity_add);

        add_date = findViewById(R.id.add_date);
        add_time = findViewById(R.id.add_time);
        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
        edit_title = findViewById(R.id.edit_title);
        back = findViewById(R.id.btn_back);
        save = findViewById(R.id.btn_save);


        //设置下划线
        tv_date.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_time.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        fragmentManager = getSupportFragmentManager();

        setListener();


    }

    private void setListener(){
        add_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateRangePickerFragment dateRangePickerFragment= DateRangePickerFragment.newInstance((DateRangePickerFragment.OnDateRangeSelectedListener) AddActivity.this,false);
                dateRangePickerFragment.show(getSupportFragmentManager(),"datePicker");

            }
        });

        add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timepicker(v);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //重写点击“确定”按钮触发的操作
    @Override
    public void onDateRangeSelected(int startDay, int startMonth, int startYear) {
        Log.e("range : ","from: "+startDay+"-"+startMonth+"-"+startYear);
        tv_date.setText(startYear + "-" + (startMonth+1) + "-" +startDay);
        memo.year = startYear;
        memo.mouth = startMonth + 1;
        memo.day = startDay;

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        tv_time.setText(hourOfDay + ":" + minute + ":" + "00");
        memo.hour = hourOfDay;
        memo.minute = minute;
        memo.second = second;

    }


    public void Timepicker(View v) {
        timePickerDialog = TimePickerDialog.newInstance(AddActivity.this, Hour, Minute,false );
        timePickerDialog.setThemeDark(false);
        timePickerDialog.setTitle("Time Picker");
        timePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Toast.makeText(AddActivity.this, "Timepicker Canceled", Toast.LENGTH_SHORT).show();
            }
        });
        timePickerDialog.show(fragmentManager, "TimePickerDialog");
    }

    public void save(){
        MemoOperator memoOperator = new MemoOperator(AddActivity.this);
        memo.title = edit_title.getText().toString();
        memo.context = "";
        if (TextUtils.isEmpty(memo.title) || TextUtils.isEmpty(String.valueOf(memo.year)) || TextUtils.isEmpty(String.valueOf(memo.hour))) {
            Toast.makeText(AddActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
        } else{
            boolean add = memoOperator.insert(memo);
            if (add) {
                Intent intent = new Intent();
                intent.setClass(AddActivity.this, MainActivity.class);
                intent.putExtra("Insert", 1);
                startActivity(intent);
            } else {
                Toast.makeText(AddActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}