package p.gordenyou.golibrary.log;

public abstract class GoLogConfig {
    public String getGlobalTag() {
        return "GoLog";
    }

    public boolean enable() {
        return true;
    }

    // 一行中最长的字节长度
    static int MAX_LINE = 512;

    // 格式化器在很多地方可以用到，我们使用单例模式
    static GoThreadFormatter GO_THREAD_FORMATTER = new GoThreadFormatter();
    static GoStackTraceFormatter GO_STACK_TRACE_FORMATTER = new GoStackTraceFormatter();

    public JsonParser injectJsonParser() {
        return null;
    }

    // 堆栈信息的深度
    public int stackTraceDepth() {
        return 5;
    }

    // 是否包含线程信息
    public boolean includeThread(){
        return false;
    }

    // 允许用户获取打印器
    public GoLogPrinter[] getPrinters() {
        return null;
    }

    public interface JsonParser {
        String toJson(Object src);
    }
}
