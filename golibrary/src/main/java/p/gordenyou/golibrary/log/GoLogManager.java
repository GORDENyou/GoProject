package p.gordenyou.golibrary.log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoLogManager {
    private GoLogConfig goLogConfig; // 管理器持有一个配置器
    private static GoLogManager instance; // 管理器使用单例模式

    public static void init(@NotNull GoLogConfig goLogConfig, GoLogPrinter... printers){ // 为了添加方便，设置为 GoLogPrinter...
        instance = new GoLogManager(goLogConfig, printers);
    }

    private GoLogManager(GoLogConfig goLogConfig, GoLogPrinter[] printers){
        this.goLogConfig = goLogConfig;
        this.printers.addAll(Arrays.asList(printers));
    }

    public static GoLogManager getInstance(){
        return instance;
    }

    public GoLogConfig getConfig(){
        return goLogConfig;
    }

    // 增加打印器的管理 2020/7/2
    private ArrayList<GoLogPrinter> printers = new ArrayList<>();

    public List<GoLogPrinter> getPrinters(){
        return printers;
    }

    public void addPrinter(GoLogPrinter printer){
        printers.add(printer);
    }

    public void removePrinter(GoLogPrinter printer){
        if(printers != null){
            printers.remove(printer);
        }
    }
}
