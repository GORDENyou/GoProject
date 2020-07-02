package p.gordenyou.golibrary.log;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import static p.gordenyou.golibrary.log.GoLogConfig.MAX_LINE;

public class GoConsolePrinter implements GoLogPrinter{
    @Override
    public void print(@NotNull GoLogConfig config, int level, String tag, @NotNull String printString) {
        int len = printString.length();
        // 计算总长度
        int countOfSub = len / MAX_LINE;

        if(countOfSub > 0){
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                Log.println(level, tag, printString.substring(index, index + MAX_LINE));
                index += MAX_LINE;
            }

            // 最后没有平均分配的行数。
            if(index != len){
                Log.println(level, tag, printString.substring(index, len));
            }
        } else {
             // 当内容不足一行时，我们直接打印
            Log.println(level, tag, printString);
        }
    }
}
