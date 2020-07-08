package p.gordenyou.goui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import org.jetbrains.annotations.NotNull;

import p.gordenyou.goui.R;
import p.gordenyou.goui.tab.common.IGoTab;

public class GoTabBottom extends RelativeLayout implements IGoTab<GoTabBottomInfo<?>> {

    private GoTabBottomInfo<?> tabInfo;
    private ImageView tabImageView;
    private TextView tabIconView;
    private TextView tabNameView;

    public GoTabBottom(Context context) {
        this(context, null);
    }

    public GoTabBottom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoTabBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.go_tab_bottom, this);
        tabImageView = findViewById(R.id.iv_image);
        tabIconView = findViewById(R.id.tv_icon);
        tabNameView = findViewById(R.id.tv_name);
    }

    @Override
    public void setGoTabInfo(@NotNull GoTabBottomInfo<?> goTabBottomInfo) {
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
        if (tabInfo.tabType == GoTabBottomInfo.TabType.ICON) {
            if (init) {
                tabImageView.setVisibility(GONE);
                tabIconView.setVisibility(VISIBLE);
            }

            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), tabInfo.iconFont);
            tabIconView.setTypeface(typeface);
            if (!TextUtils.isEmpty(tabInfo.name)) {
                tabNameView.setText(tabInfo.name);
            }

            if (selected) {
                tabIconView.setText(TextUtils.isEmpty(tabInfo.selectedIconName) ? tabInfo.defaultIconName : tabInfo.selectedIconName);
                tabIconView.setTextColor(getTextColor(tabInfo.tintColor));
                tabNameView.setTextColor(getTextColor(tabInfo.tintColor));
            } else {
                tabIconView.setText(tabInfo.defaultIconName);
                tabIconView.setTextColor(getTextColor(tabInfo.defaultColor));
                tabNameView.setTextColor(getTextColor(tabInfo.defaultColor));
            }
        } else if(tabInfo.tabType == GoTabBottomInfo.TabType.BITMAP){
            if(init){
                tabIconView.setVisibility(GONE);
                tabImageView.setVisibility(VISIBLE);
                if(TextUtils.isEmpty(tabInfo.name)){
                    tabNameView.setText(tabInfo.name);
                }
            }

            if(selected){
                // todo
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

    @Override
    public void resetHeight(int height) {

    }

    @Override
    public void onTabSelectedChange(int index, @NotNull GoTabBottomInfo<?> prevInfo, @NotNull GoTabBottomInfo<?> nextInfo) {

    }
}
