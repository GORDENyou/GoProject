package p.gordenyou.golibrary.log;

/**
 * 线程格式化器，传入的为一个 Thread 类型数据
 */
public class GoThreadFormatter implements GoLogFormatter<Thread> {

    @Override
    public String format(Thread data) {
        return "Thread: " + data.getName();
    }
}
