package p.gordenyou.goui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import p.gordenyou.golibrary.util.GoDisplayUtil;
import p.gordenyou.golibrary.util.GoViewUtil;
import p.gordenyou.goui.R;
import p.gordenyou.goui.tab.common.IGoTabLayout;

/**
 * 这是我们最外层的容器控件
 */
public class GoTabBottomLayout extends FrameLayout implements IGoTabLayout<GoTabBottom, GoTabBottomInfo<?>> {

    private List<OnTabSelectedListener<GoTabBottomInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();
    private GoTabBottomInfo<?> selectedInfo;
    private float bottomAlpha = 1f;
    //TabBottom高度
    private static float tabBottomHeight = 50;
    //TabBottom的头部线条高度
    private float bottomLineHeight = 0.5f;
    //TabBottom的头部线条颜色
    private String bottomLineColor = "#dfe0e1";
    private List<GoTabBottomInfo<?>> infoList;

    public GoTabBottomLayout(@NonNull Context context) {
        this(context, null);
    }

    public GoTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 由于我们需要在 finTab() 通过数据寻找 Tab ，所以我们需要能够快速的找到 TabBottomLayout
    @Override
    public GoTabBottom findTab(@NotNull GoTabBottomInfo<?> tabBottomInfo) {
        ViewGroup view = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < view.getChildCount(); i++) {
            View child = view.getChildAt(i);
            if (child instanceof GoTabBottom) {
                GoTabBottom tabBottom = (GoTabBottom) child;
                if (tabBottom.getTabInfo() == tabBottomInfo) return tabBottom;
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<GoTabBottomInfo<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NotNull GoTabBottomInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    public void setTabAlpha(float alpha) {
        this.bottomAlpha = alpha;
    }

    public static void setTabHeight(float tabHeight) {
        GoTabBottomLayout.tabBottomHeight = tabHeight;
    }

    public void setBottomLineHeight(float bottomLineHeight) {
        this.bottomLineHeight = bottomLineHeight;
    }

    public void setBottomLineColor(String bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

    private static final String TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM";

    @Override
    public void inflateInfo(@NotNull List<GoTabBottomInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;

        // todo 防止重复添加，我们需要将之前添加的控件移除
        // 第 0 个 View 是我们的列表，不能移除
        for (int i = getChildCount() - 1; i > 0; i--) {
            removeViewAt(i);
        }
        selectedInfo = null;
        addBackground();

        // 移除 TabListener
        tabSelectedChangeListeners.clear();

        // 我们将 Tab 和背景放在同一个容器里
        // 为什么不用 LinearLayout? 因为动态改变 child 高度后 Gravity 会失效。
        FrameLayout frameLayout = new FrameLayout(getContext());
        // 添加底部 TabBottom 的标签
        frameLayout.setTag(TAG_TAB_BOTTOM);

        int width = GoDisplayUtil.getDisplayWidthInPx(getContext()) / infoList.size();
        int height = GoDisplayUtil.dp2px(tabBottomHeight, getResources());
        for (int i = 0; i < infoList.size(); i++) {
            final GoTabBottomInfo<?> info = infoList.get(i);
            LayoutParams layoutParams = new LayoutParams(width, height);
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.leftMargin = i * width;

            GoTabBottom goTabBottom = new GoTabBottom(getContext());
            tabSelectedChangeListeners.add(goTabBottom);
            goTabBottom.setGoTabInfo(info);
            frameLayout.addView(goTabBottom, layoutParams);
            goTabBottom.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(info);
                }
            });
        }

        LayoutParams flParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        flParams.gravity = Gravity.BOTTOM;
        // 添加分割线
        addBottomLine();
        addView(frameLayout, flParams);

        fixContentView();
    }

    private void addBottomLine() {
        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor));

        LayoutParams blParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GoDisplayUtil.dp2px(bottomLineHeight, getResources()));
        blParams.gravity = Gravity.BOTTOM;
        blParams.bottomMargin = GoDisplayUtil.dp2px(tabBottomHeight - bottomLineHeight, getResources());
        addView(bottomLine, blParams);
        bottomLine.setAlpha(bottomAlpha);
    }

    private void onSelected(@NotNull GoTabBottomInfo<?> nextInfo) {
        // 这个是用来做什么的？
        for (OnTabSelectedListener<GoTabBottomInfo<?>> listener : tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
    }

    private void addBackground() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.go_tab_bottom_bg, null);

        // 两个参数分别为宽和高
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GoDisplayUtil.dp2px(tabBottomHeight, getResources()));
        params.gravity = Gravity.BOTTOM;
        addView(view, params); // 别忘了添加参数！！！ 解决问题：设置底部导航透明度导致全页面设置。
        view.setAlpha(bottomAlpha);
    }

    // 由于我们需要底部导航栏是透明而且对列表没有遮挡，所以我们需要将列表的底部 Padding 设置为 tabBottomHeight！
    private void fixContentView() {
        if (!(getChildAt(0) instanceof ViewGroup)) {
            return;
        }

        ViewGroup rootView = (ViewGroup) getChildAt(0);
        ViewGroup targetView = GoViewUtil.findTypeView(rootView, RecyclerView.class );

        if (targetView == null) {
            targetView = GoViewUtil.findTypeView(rootView, ScrollView.class);
        }
        if (targetView == null) {
            targetView = GoViewUtil.findTypeView(rootView, AbsListView.class);
        }
        if (targetView != null) {
            targetView.setPadding(0, 0, 0, GoDisplayUtil.dp2px(tabBottomHeight, getResources()));
            // 设置列表不可裁剪 Padding
            targetView.setClipToPadding(false);
        }
    }
}
