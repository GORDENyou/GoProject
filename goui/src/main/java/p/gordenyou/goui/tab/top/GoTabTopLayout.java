package p.gordenyou.goui.tab.top;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import p.gordenyou.golibrary.util.GoDisplayUtil;
import p.gordenyou.goui.tab.common.IGoTabLayout;

public class GoTabTopLayout extends HorizontalScrollView implements IGoTabLayout<GoTabTop, GoTabTopInfo<?>> {

    private static final String TAG_TAB_TOP = "TAG_TAB_TOP";
    private List<OnTabSelectedListener<GoTabTopInfo<?>>> tabSelectedListeners = new ArrayList<>();
    private GoTabTopInfo<?> selectedInfo;
    private List<GoTabTopInfo<?>> infoList;

    public GoTabTopLayout(Context context) {
        this(context, null);
    }

    public GoTabTopLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoTabTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);
    }

    private void autoScroll(GoTabTopInfo<?> nextInfo) {
        // 找到选中 Tab
        GoTabTop tabTop = findTab(nextInfo);
        if (tabTop == null) return;
        int index = infoList.indexOf(nextInfo);

        // 获取点击的控件在屏幕的位置
        int[] location = new int[2];
        tabTop.getLocationInWindow(location);

        int scrollWidth;
        if (tabWidth == 0) {
            // 初始化得到每个 tab 的宽度
            tabWidth = tabTop.getWidth();
        }
        // 判断点击了屏幕的左侧还是右侧
        if ((location[0] + tabWidth / 2) > GoDisplayUtil.getDisplayWidthInPx(getContext()) / 2) {
            scrollWidth = rangeScrollWidth(index, 2);
        } else {
            scrollWidth = rangeScrollWidth(index, -2);
        }

        scrollTo(getScrollX() + scrollWidth, 0);
    }

    /**
     * 获取可滚动的范围
     *
     * @param index 从第几个开始
     * @param range 向前向后的范围
     * @return 可滚动的范围
     */
    private int rangeScrollWidth(int index, int range) {
        int scrollWidth = 0;
        for (int i = 0; i < Math.abs(range); i++) {
            int next;
            if (range < 0) {
                next = range + i + index;
            } else {
                next = range - i + index;
            }

            if (next >= 0 && next < infoList.size()) {
                if (range < 0) {
                    scrollWidth -= scrollWidth(next, false);
                } else {
                    scrollWidth += scrollWidth(next, true);
                }
            }
        }
        return scrollWidth;
    }

    /**
     * 指定位置的控件可滚动的距离
     *
     * @param index   指定位置的控件
     * @param toRight 是否是点击了屏幕右侧
     * @return 可滚动的距离
     */
    private int scrollWidth(int index, boolean toRight) {
        GoTabTop target = findTab(infoList.get(index));
        if (target == null) return 0;
        Rect rect = new Rect();
        target.getLocalVisibleRect(rect);
        if (toRight) {//点击屏幕右侧
            if (rect.right > tabWidth) {//right坐标大于控件的宽度时，说明完全没有显示
                return tabWidth;
            } else {//显示部分，减去已显示的宽度
                return tabWidth - rect.right;
            }
        } else {
            if (rect.left <= -tabWidth) {//left坐标小于等于-控件的宽度，说明完全没有显示
                return tabWidth;
            } else if (rect.left > 0) {//显示部分
                return rect.left;
            }
            return 0;
        }
    }

    int tabWidth = 0;

    @Override
    public GoTabTop findTab(@NotNull GoTabTopInfo<?> tabTopInfo) {
        ViewGroup view = getRootLayout(false);
        for (int i = 0; i < view.getChildCount(); i++) {
            View child = view.getChildAt(i);
            if (child instanceof GoTabTop) {
                GoTabTop tabTop = (GoTabTop) child;
                if (tabTop.getTabInfo() == tabTopInfo) return tabTop;
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<GoTabTopInfo<?>> listener) {
        tabSelectedListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NotNull GoTabTopInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    private void onSelected(@NotNull GoTabTopInfo<?> nextInfo) {
        // 这个是用来做什么的？
        for (OnTabSelectedListener<GoTabTopInfo<?>> listener : tabSelectedListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;

        // 我们从这里通过选中的控件内容开始移动控件
        autoScroll(nextInfo);
    }

    @Override
    public void inflateInfo(@NotNull List<GoTabTopInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;

        LinearLayout linearLayout = getRootLayout(true);

        selectedInfo = null;

        // 移除 TabListener
        tabSelectedListeners.clear();

        for (int i = 0; i < infoList.size(); i++) {
            final GoTabTopInfo<?> info = infoList.get(i);
            GoTabTop tab = new GoTabTop(getContext());
            tabSelectedListeners.add(tab);
            tab.setGoTabInfo(info);
            linearLayout.addView(tab);
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(info);
                }
            });
        }
    }

    private LinearLayout getRootLayout(boolean clear) {
        LinearLayout rootView = (LinearLayout) getChildAt(0);
        if (rootView == null) {
            rootView = new LinearLayout(getContext());
            rootView.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            addView(rootView, params);
        } else if (clear) {
            rootView.removeAllViews();
        }
        return rootView;
    }
}
