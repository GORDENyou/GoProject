package p.gordenyou.goui.tab.top;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import p.gordenyou.goui.R;
import p.gordenyou.goui.tab.common.IGoTab;

public class GoTabTop extends RelativeLayout implements IGoTab<GoTabTopInfo<?>> {

    private GoTabTopInfo<?> tabInfo;
    private ImageView tabImageView;
    private TextView tabNameView;
    private View indicator; // 底部指示器

    public GoTabTop(Context context) {
        this(context, null);
    }

    public GoTabTop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoTabTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GoTabTopInfo<?> getTabInfo() {
        return tabInfo;
    }

    public ImageView getTabImageView() {
        return tabImageView;
    }

    public TextView getTabNameView() {
        return tabNameView;
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.go_tab_top, this);
        tabImageView = findViewById(R.id.iv_image);
        tabNameView = findViewById(R.id.tv_name);
        indicator = findViewById(R.id.tab_top_indicator);
    }

    @Override
    public void setGoTabInfo(@NotNull GoTabTopInfo<?> goTabBottomInfo) {
        this.tabInfo = goTabBottomInfo;
        inflateInfo(false, true);
    }

    /**
     * 初始化单个 Tab 信息
     *
     * @param selected Tab 是否被选中
     * @param init     Tab 是否初始化
     */
    private void inflateInfo(boolean selected, boolean init) {
        // 我们这里有两种实现方式，我们分别考虑
        if (tabInfo.tabType == GoTabTopInfo.TabType.TEXT) {
            if (init) {
                tabImageView.setVisibility(GONE);
                tabNameView.setVisibility(VISIBLE);
            }

            if (!TextUtils.isEmpty(tabInfo.name)) {
                tabNameView.setText(tabInfo.name);
            }

            if (selected) {
                indicator.setVisibility(VISIBLE);
                tabNameView.setTextColor(getTextColor(tabInfo.tintColor));
            } else {
                indicator.setVisibility(GONE);
                tabNameView.setTextColor(getTextColor(tabInfo.defaultColor));
            }
        } else if (tabInfo.tabType == GoTabTopInfo.TabType.BITMAP) {
            if (init) {
                tabImageView.setVisibility(VISIBLE);
                tabNameView.setVisibility(GONE);
            }

            if (selected) {
                tabImageView.setImageBitmap(tabInfo.selectedBitmap);
            } else {
                tabImageView.setImageBitmap(tabInfo.defaultBitmap);
            }
        }
    }

    @ColorInt
    private int getTextColor(Object color) {
        if (color instanceof String) {
            return Color.parseColor((String) color);
        } else {
            return (int) color;
        }
    }

    // 重新设置某个 Tab 的高度
    @Override
    public void resetHeight(int height) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        setLayoutParams(layoutParams);
        getTabNameView().setVisibility(GONE);
    }

    @Override
    public void onTabSelectedChange(int index, @Nullable GoTabTopInfo<?> prevInfo, @NotNull GoTabTopInfo<?> nextInfo) {
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
            return;
        }

        // todo 这里其实没有理解什么意思
        if (prevInfo == tabInfo) {
            inflateInfo(false, false);
        } else {
            inflateInfo(true, false);
        }
    }
}
