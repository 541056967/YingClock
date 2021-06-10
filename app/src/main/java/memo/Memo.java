package memo;

public class Memo {
    //表名
    public static final String TABLE = "memo";
    //列名
    public static final String KEY_id = "id";
    public static final String KEY_title = "title"; //保存memo标题
    public static final String KEY_context = "context";
    public static final String KEY_year = "year"; // 保存memo发生的年
    public static final String KEY_mouth = "mouth"; // 保存memo发生的月
    public static final String KEY_day = "day"; // 保存memo发生的天
    public static final String KEY_hour = "hour"; // 保存memo发生的小时
    public static final String KEY_minute = "minute"; //是否删除
    public static final String KEY_second = "second"; // 是否完成

    public int memo_id;
    public String title;
    public String context;
    public int year;
    public int mouth;
    public int day;
    public int hour;
    public int minute;
    public int second;


}
