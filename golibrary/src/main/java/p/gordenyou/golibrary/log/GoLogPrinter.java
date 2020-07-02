package p.gordenyou.golibrary.log;

import org.jetbrains.annotations.NotNull;

public interface GoLogPrinter {
    // 定义打印函数
    void print(@NotNull GoLogConfig config, int level, String tag, @NotNull String printString);
}
