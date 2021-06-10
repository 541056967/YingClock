package com.example.yingclock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

    Date date = new Date();
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public Long getSecond(int year, int mouth, int day, int hour,int minute){
        long nowTime=System.currentTimeMillis();  //获取当前时间的毫秒数
        String time = year+"-"+mouth+"-"+day+" "+hour+":"+minute+":"+"00";
        try{
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long reset=date.getTime();   //获取指定时间的毫秒数
        long dateDiff=Math.abs(nowTime-reset);
        return  dateDiff;
    }

    public boolean compare(int year, int mouth, int day, int hour,int minute){
        long nowTime=System.currentTimeMillis();  //获取当前时间的毫秒数
        String time = year+"-"+mouth+"-"+day+" "+hour+":"+minute+":"+"00";
        try{
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long reset=date.getTime();   //获取指定时间的毫秒数
        boolean result = (nowTime > reset);
        return  result;
    }

    public ArrayList<Long> getDate(long dateDiff){
        ArrayList<Long> dateList = new ArrayList<>();
        long dateTemp1=dateDiff/1000; //秒
        long dateTemp2=dateTemp1/60; //分钟
        long dateTemp3=dateTemp2/60; //小时
        long dateTemp4=dateTemp3/24+1; //天数
        dateList.add(dateTemp1);
        dateList.add(dateTemp2);
        dateList.add(dateTemp3);
        dateList.add(dateTemp4);
        return dateList;
    }




}
