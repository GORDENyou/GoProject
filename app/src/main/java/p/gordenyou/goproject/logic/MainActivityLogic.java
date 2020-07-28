package p.gordenyou.goproject.logic;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import p.gordenyou.common.tab.GoFragmentTabView;
import p.gordenyou.common.tab.GoTabViewAdapter;
import p.gordenyou.goproject.R;
import p.gordenyou.goproject.fragment.CategoryFragment;
import p.gordenyou.goproject.fragment.FavoriteFragment;
import p.gordenyou.goproject.fragment.HomePageFragment;
import p.gordenyou.goproject.fragment.ProfileFragment;
import p.gordenyou.goproject.fragment.RecommendFragment;
import p.gordenyou.goui.tab.bottom.GoTabBottomInfo;
import p.gordenyou.goui.tab.bottom.GoTabBottomLayout;
import p.gordenyou.goui.tab.common.IGoTabLayout;

public class MainActivityLogic {
    private GoFragmentTabView fragmentTabView;
    private GoTabBottomLayout goTabBottomLayout;
    private List<GoTabBottomInfo<?>> infoList;
    private ActivityProvider activityProvider;
    private int currentItemIndex;
    private final static String SAVED_CURRENT_ID = "SAVED_CURRENT_ID";

    public MainActivityLogic(ActivityProvider activityProvider, Bundle savedInstanceState) {
        this.activityProvider = activityProvider;
        // 修复 “不保留活动选项” 导致的 fragment 重叠问题（原因：Activity 被回收，而 fragment 没有被回收）
        if(savedInstanceState != null){
            currentItemIndex = savedInstanceState.getInt(SAVED_CURRENT_ID);
        }
        initTabBottom();
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SAVED_CURRENT_ID, currentItemIndex);
    }

    public GoFragmentTabView getFragmentTabView() {
        return fragmentTabView;
    }

    public GoTabBottomLayout getGoTabBottomLayout() {
        return goTabBottomLayout;
    }

    public List<GoTabBottomInfo<?>> getInfoList() {
        return infoList;
    }

    private void initTabBottom() {
        goTabBottomLayout = activityProvider.findViewById(R.id.tab_bottom_layout);
        goTabBottomLayout.setTabAlpha(0.85f);
        infoList = new ArrayList<>();
        int defaultColor = activityProvider.getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = activityProvider.getResources().getColor(R.color.tabBottomTintColor);
        GoTabBottomInfo homeInfo = new GoTabBottomInfo<Integer>(
                "首页",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_home),
                null,
                defaultColor,
                tintColor
        );
        homeInfo.fragment = HomePageFragment.class;
        GoTabBottomInfo infoFavorite = new GoTabBottomInfo<Integer>(
                "收藏",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_favorite),
                null,
                defaultColor,
                tintColor
        );
        infoFavorite.fragment = FavoriteFragment.class;
        GoTabBottomInfo infoCategory = new GoTabBottomInfo<Integer>(
                "分类",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_category),
                null,
                defaultColor,
                tintColor
        );
        infoCategory.fragment = CategoryFragment.class;
        GoTabBottomInfo infoRecommend = new GoTabBottomInfo<Integer>(
                "推荐",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_recommend),
                null,
                defaultColor,
                tintColor
        );
        infoRecommend.fragment = RecommendFragment.class;
        GoTabBottomInfo infoProfile = new GoTabBottomInfo<Integer>(
                "我的",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_profile),
                null,
                defaultColor,
                tintColor
        );
        infoProfile.fragment = ProfileFragment.class;

        infoList.add(homeInfo);
        infoList.add(infoFavorite);
        infoList.add(infoProfile);
        infoList.add(infoCategory);
        infoList.add(infoRecommend);
        goTabBottomLayout.inflateInfo(infoList);
        initFragmentTabView();
        goTabBottomLayout.addTabSelectedChangeListener(new IGoTabLayout.OnTabSelectedListener<GoTabBottomInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, @Nullable GoTabBottomInfo<?> prevInfo, @NotNull GoTabBottomInfo<?> nextInfo) {
                // 我们通过 fragmentTabView 实现对 fragment 的管理。
                fragmentTabView.setCurrentItem(index);

                // 记录当前的选中的 fragment 的序号
                MainActivityLogic.this.currentItemIndex = index;
            }
        });
        goTabBottomLayout.defaultSelected(infoList.get(currentItemIndex));

    }

    private void initFragmentTabView() {
        GoTabViewAdapter tabViewAdapter = new GoTabViewAdapter(activityProvider.getSupportFragmentManager(), infoList);
        fragmentTabView = activityProvider.findViewById(R.id.fragment_tab_view);
        fragmentTabView.setAdapter(tabViewAdapter);
    }


    public interface ActivityProvider {
        <T extends View> T findViewById(@IdRes int id);

        Resources getResources();

        FragmentManager getSupportFragmentManager();

        String getString(@StringRes int resId);
    }
}
