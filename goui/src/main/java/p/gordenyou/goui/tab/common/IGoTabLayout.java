package p.gordenyou.goui.tab.common;

import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/*
对外接口以及规范
 */
public interface IGoTabLayout<Tab extends ViewGroup, D> {
    // 通过数据寻找底部的 Tab
    Tab findTab(@NotNull D data);

    // 添加监听器
    void addTabSelectedChangeListener(onTabSelectedListener<D> listener);

    void defaultSelected(@NotNull D defaultInfo);

    void inflateInfo(@NotNull List<D> infoList);

    interface onTabSelectedListener<D> {
        void onTabSelectedChange(int index, @NotNull D prevInfo, @NotNull D nextInfo);
    }
}
