package p.gordenyou.golibrary.log;

import org.jetbrains.annotations.NotNull;

public class GoLogManager {
    private GoLogConfig goLogConfig; // 管理器持有一个配置器
    private static GoLogManager instance; // 管理器使用单例模式

    public static void init(@NotNull GoLogConfig goLogConfig){
        instance = new GoLogManager(goLogConfig);
    }

    private GoLogManager(GoLogConfig goLogConfig){
        this.goLogConfig = goLogConfig;
    }

    public static GoLogManager getInstance(){
        return instance;
    }

    public GoLogConfig getConfig(){
        return goLogConfig;
    }
}
