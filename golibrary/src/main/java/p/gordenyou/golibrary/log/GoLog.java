package p.gordenyou.golibrary.log;

import android.util.Log;

import androidx.annotation.NonNull;

/**
 * GoLog 对外接口
 * Tips:
 *  1. 打印堆栈信息
 *  2. File输出
 *  3. 模拟控制台
 */
public class GoLog {
    public static void v(Object... contents) {
        log(GoLogType.V, contents);
    }

    public static void vt(String tag, Object... contents) {
        log(GoLogType.V, tag, contents);
    }

    public static void d(Object... contents) {
        log(GoLogType.D, contents);
    }

    public static void dt(String tag, Object... contents) {
        log(GoLogType.D, tag, contents);
    }

    public static void i(Object... contents) {
        log(GoLogType.I, contents);
    }

    public static void it(String tag, Object... contents) {
        log(GoLogType.I, tag, contents);
    }

    public static void w(Object... contents) {
        log(GoLogType.W, contents);
    }

    public static void wt(String tag, Object... contents) {
        log(GoLogType.W, tag, contents);
    }

    public static void e(Object... contents) {
        log(GoLogType.E, contents);
    }

    public static void et(String tag, Object... contents) {
        log(GoLogType.E, tag, contents);
    }

    public static void a(Object... contents) {
        log(GoLogType.A, contents);
    }

    public static void at(String tag, Object... contents) {
        log(GoLogType.A, tag, contents);
    }

    /**
     * 不带 tag
     *
     * @param type     类型
     * @param contents 内容
     */
    public static void log(@GoLogType.TYPE int type, Object... contents) {
        log(type, GoLogManager.getInstance().getConfig().getGlobalTag(), contents);
    }

    /**
     * 带 tag 的
     *
     * @param type     类型
     * @param tag      标签
     * @param contents 内容
     */
    public static void log(@GoLogType.TYPE int type, @NonNull String tag, Object... contents) {
        log(GoLogManager.getInstance().getConfig(), type, tag, contents);
    }

    public static void log(@NonNull GoLogConfig config, @GoLogType.TYPE int type, @NonNull String tag, Object... contents) {
        if (!config.enable()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        String body = parseBody(contents);
        sb.append(body);
        Log.println(type, tag, body);
    }

    private static String parseBody(Object[] contents) {
        StringBuilder sb = new StringBuilder();
        for (Object content : contents) {
            sb.append(content.toString()).append(";");
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }
}
