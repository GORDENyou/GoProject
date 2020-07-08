package p.gordenyou.goui.tab.common;

import androidx.annotation.Px;

import org.jetbrains.annotations.NotNull;

/*
同样的，这里 Tab 中的信息同样用泛型实现，并且实现了 IGoTabLayout 中的内部接口。
 */
public interface IGoTab<D> extends IGoTabLayout.onTabSelectedListener<D> {

    // 设置 Tab 信息
    void setGoTabInfo(@NotNull D data);

    // 动态修改高度
    void resetHeight(@Px int height);

}
