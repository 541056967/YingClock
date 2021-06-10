package memo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.yingclock.CalendarUtil;
import com.example.yingclock.R;

import java.util.ArrayList;
import java.util.HashMap;


public class MemoOperator {
    private DBHelper dbHelper;
    CalendarUtil calendarUtil = new CalendarUtil();

    public MemoOperator(Context context) {

        dbHelper = new DBHelper(context);
    }

    /**
     * 插入数据
     *
     * @param memo
     * @return
     */
    public boolean insert(Memo memo) {
        //与数据库建立连接
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Memo.KEY_title, memo.title);
        contentValues.put(Memo.KEY_context, memo.context);
        contentValues.put(Memo.KEY_year,memo.year);
        contentValues.put(Memo.KEY_mouth,memo.mouth);
        contentValues.put(Memo.KEY_day,memo.day);
        contentValues.put(Memo.KEY_hour,memo.hour);
        contentValues.put(Memo.KEY_minute,memo.minute);
        contentValues.put(Memo.KEY_second,memo.second);
        //插入每一行数据
        long memo_id = db.insert(Memo.TABLE, null, contentValues);
        db.close();
        if (memo_id != -1)
            return true;
        else
            return false;
    }

    /**
     * 删除数据
     *
     * @param memo_id
     */
    public void delete(int memo_id) {
        //与数据库建立连接
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Memo.TABLE, Memo.KEY_id + "=?", new String[]{String.valueOf(memo_id)});
        db.close();
    }

    /**
     * 从数据库中查找 id，title，context, 年月日时分秒
     *
     * @return ArrayList
     */
    public ArrayList<HashMap<String, String>> getMemoList() {
        //与数据库建立连接
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select " + Memo.KEY_id + "," + Memo.KEY_title + "," + Memo.KEY_context + ","
                + Memo.KEY_year + "," + Memo.KEY_mouth + "," + Memo.KEY_day + ","
                + Memo.KEY_hour +"," + Memo.KEY_minute + "," + Memo.KEY_second +
                " from " + Memo.TABLE;
        //通过游标将每一条数据放进ArrayList中
        ArrayList<HashMap<String, String>> memoList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> memo = new HashMap<String, String>();
            memo.put("id", cursor.getString(cursor.getColumnIndex(Memo.KEY_id)));
            //"memoID"
            memo.put("title", cursor.getString(cursor.getColumnIndex(Memo.KEY_title)));
            memo.put("year", cursor.getString(cursor.getColumnIndex(Memo.KEY_year)));
            memo.put("mouth", cursor.getString(cursor.getColumnIndex(Memo.KEY_mouth)));
            memo.put("day", cursor.getString(cursor.getColumnIndex(Memo.KEY_day)));
            memo.put("hour", cursor.getString(cursor.getColumnIndex(Memo.KEY_hour)));
            memo.put("minute", cursor.getString(cursor.getColumnIndex(Memo.KEY_minute)));
            memo.put("second", cursor.getString(cursor.getColumnIndex(Memo.KEY_second)));
            memo.put("context", cursor.getString(cursor.getColumnIndex(Memo.KEY_context)));
            if(calendarUtil.compare(cursor.getInt(cursor.getColumnIndex(Memo.KEY_year)),cursor.getInt(cursor.getColumnIndex(Memo.KEY_mouth)),cursor.getInt(cursor.getColumnIndex(Memo.KEY_day)),
                    cursor.getInt(cursor.getColumnIndex(Memo.KEY_hour)),cursor.getInt(cursor.getColumnIndex(Memo.KEY_minute)))){
                memo.put("compare", "1");
            }else{
                memo.put("compare","0");
            }

            String time = cursor.getString(cursor.getColumnIndex(Memo.KEY_year))+"年"+cursor.getString(cursor.getColumnIndex(Memo.KEY_mouth))+"月"+
                    cursor.getString(cursor.getColumnIndex(Memo.KEY_day))+"日 "+cursor.getString(cursor.getColumnIndex(Memo.KEY_hour))+"时"+ cursor.getString(cursor.getColumnIndex(Memo.KEY_minute)) +"分";

            memo.put("time",time);
            memoList.add(memo);
        }
        cursor.close();
        db.close();
        return memoList;
    }



    /**
     * 通过id查找，返回一个Memo对象
     *
     * @param id
     * @return
     */
    public Memo getMemoById(int id) {
        //与数据库建立连接
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select " + Memo.KEY_title + "," + Memo.KEY_context + "," + Memo.KEY_year + ","
                + Memo.KEY_mouth + "," + Memo.KEY_day + "," + Memo.KEY_hour + "," + Memo.KEY_minute + "," + Memo.KEY_second +
                " from " + Memo.TABLE + " where " + Memo.KEY_id + "=?";
        Memo memo= new Memo();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        while (cursor.moveToNext()) {
            memo.title = cursor.getString(cursor.getColumnIndex(Memo.KEY_title));
            memo.context = cursor.getString(cursor.getColumnIndex(Memo.KEY_context));
            memo.year = cursor.getInt(cursor.getColumnIndex(Memo.KEY_year));
            memo.mouth = cursor.getInt(cursor.getColumnIndex(Memo.KEY_mouth));
            memo.day = cursor.getInt(cursor.getColumnIndex(Memo.KEY_day));
            memo.hour = cursor.getInt(cursor.getColumnIndex(Memo.KEY_hour));
            memo.minute = cursor.getInt(cursor.getColumnIndex(Memo.KEY_minute));
            memo.second = cursor.getInt(cursor.getColumnIndex(Memo.KEY_second));
        }
        cursor.close();
        db.close();
        return memo;
    }

    /**
     * 更新数据
     *
     * @param memo
     */
    public void update(Memo memo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Memo.KEY_title, memo.title);
        contentValues.put(Memo.KEY_context, memo.context);
        contentValues.put(Memo.KEY_year,memo.year);
        contentValues.put(Memo.KEY_mouth,memo.mouth);
        contentValues.put(Memo.KEY_day,memo.day);
        contentValues.put(Memo.KEY_hour,memo.hour);
        contentValues.put(Memo.KEY_minute,memo.minute);
        contentValues.put(Memo.KEY_second,memo.second);
        db.update(Memo.TABLE, contentValues, Memo.KEY_id + "=?", new String[]{String.valueOf(memo.memo_id)});
        db.close();
    }
}

