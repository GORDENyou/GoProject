package p.gordenyou.golibrary.log;

public abstract class GoLogConfig {
    public String getGlobalTag() {
        return "GoLog";
    }

    public boolean enable() {
        return true;
    }
}
