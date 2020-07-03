package p.gordenyou.golibrary.log;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class GoLogMo {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.CHINA);
    public long timeMillis;
    public int level;
    public String tag;
    public String log;

    public GoLogMo(long timeMillis, int level, String tag, String log) {
        this.timeMillis = timeMillis;
        this.level = level;
        this.tag = tag;
        this.log = log;
    }

    // 日志拼接
    public String flattenedLog() {
        return getFlattened() + "\n" + log;
    }

    // 信息拼接
    public String getFlattened() {
        return format(timeMillis) + '|' + level + '|' + tag + "|:";
    }

    // 日期格式化
    private String format(long timeMillis) {
        return sdf.format(timeMillis);
    }
}
