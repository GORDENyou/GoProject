package p.gordenyou.golibrary.log;

public class GoStackTraceUtil {
    /*
    GoApplication: stackTrace:
    	├ p.gordenyou.golibrary.log.GoLog.log(GoLog.java:99)
    	├ p.gordenyou.golibrary.log.GoLog.log(GoLog.java:82)
    	├ p.gordenyou.golibrary.log.GoLog.log(GoLog.java:71)
    	├ p.gordenyou.golibrary.log.GoLog.a(GoLog.java:57)
    	├ p.gordenyou.goproject.demo.GoLogDemoActivity.printLog(GoLogDemoActivity.kt:29)
    	├ p.gordenyou.goproject.demo.GoLogDemoActivity.access$printLog(GoLogDemoActivity.kt:11)
    	├ p.gordenyou.goproject.demo.GoLogDemoActivity$onCreate$1.onClick(GoLogDemoActivity.kt:15)
    	├ android.view.View.p
2020-07-02 14:37:18.866 2149-2149/p.gordenyou.GoProject A/GoApplication: erformClick(View.java:6256)
    	├ android.view.View$PerformClick.run(View.java:24697)
    	├ android.os.Handler.handleCallback(Handler.java:789)
    	├ android.os.Handler.dispatchMessage(Handler.java:98)
    	├ android.os.Looper.loop(Looper.java:164)
    	├ android.app.ActivityThread.main(ActivityThread.java:6541)
    	├ java.lang.reflect.Method.invoke(Native Method)
    	├ com.android.internal.os.Zygote$MethodAndArgsCaller.run(Zygote.java:240)
    	└ com.android.internal.os.ZygoteInit.main(ZygoteInit.java:767)
    ["8899"]
     */

    /**
     * 裁剪堆栈信息
     *
     * @param callStack 初始堆栈信息
     * @param maxDepth  堆栈最大深度
     * @return 裁剪后的堆栈
     */
    private static StackTraceElement[] cropStackTrace(StackTraceElement[] callStack, int maxDepth) {
        int realDepth = callStack.length;
        if (maxDepth > 0) {
            realDepth = Math.min(maxDepth, realDepth);
        }
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(callStack, 0, realStack, 0, realDepth);
        return realStack;
    }

    /**
     * 取出忽略包名之外的堆栈信息
     * @param stackTrace 初始堆栈
     * @param ignorePackage 忽略包名
     * @return 最终堆栈
     */
    private static StackTraceElement[] getRealStackTrace(StackTraceElement[] stackTrace, String ignorePackage){
        int ignoreDepth = 0;
        int allDepth = stackTrace.length;
        String className;

        /*
        最开始调用的函数最先压入栈中，所以这里从后往前遍历。
         */

        for (int i = allDepth - 1; i >= 0; i--) {
            className = stackTrace[i].getClassName();
            if(ignorePackage != null && className.startsWith(ignorePackage)){
                ignoreDepth = i + 1;
                break;
            }
        }
        int realDepth = allDepth - ignoreDepth;
        StackTraceElement[] realTrace = new StackTraceElement[realDepth];
        System.arraycopy(stackTrace, ignoreDepth, realTrace, 0, realDepth);
        return realTrace;
    }

    /**
     * 暴露接口
     * @param stackTrace
     * @param ignorePackage
     * @param maxDepth
     * @return
     */
    public static StackTraceElement[] getCroppedRealStackTrace(StackTraceElement[] stackTrace, String ignorePackage, int maxDepth) {
        return cropStackTrace(getRealStackTrace(stackTrace, ignorePackage), maxDepth);
    }
}
