package p.gordenyou.golibrary.log;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

/**
 * GoLog 对外接口
 * Tips:
 * 1. 打印堆栈信息
 * 2. File输出
 * 3. 模拟控制台
 */
public class GoLog {
    private static final String GO_LOG_PACKAGE;

    static {
        String className = GoLog.class.getName();
        GO_LOG_PACKAGE = className.substring(0, className.lastIndexOf('.') + 1);
    }

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
        /*
        我们需要查看是否含有线程和堆栈的信息，若有，需要添加进去
         */
        if (config.includeThread()) {
            String threadInfo = GoLogConfig.GO_THREAD_FORMATTER.format(Thread.currentThread());
            sb.append(threadInfo).append("\n");
        }
        if (config.stackTraceDepth() > 0) {
            String traceInfo = GoLogConfig.GO_STACK_TRACE_FORMATTER.format(GoStackTraceUtil.getCroppedRealStackTrace(new Throwable().getStackTrace(), GO_LOG_PACKAGE, config.stackTraceDepth()));
            sb.append(traceInfo).append("\n");
        }

        String body = parseBody(contents, config);
        sb.append(body);
        List<GoLogPrinter> printers = config.getPrinters() != null ? Arrays.asList(config.getPrinters()) : GoLogManager.getInstance().getPrinters();

        if (printers == null) return;
        else {
            for (GoLogPrinter printer : printers) {
                printer.print(config, type, tag, sb.toString());
            }
        }
    }

    private static String parseBody(Object[] contents, GoLogConfig config) {
        // 当 contents 为对象时，我们需要将其序列化
        if (config.injectJsonParser() != null) {
            return config.injectJsonParser().toJson(contents);
        }

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
