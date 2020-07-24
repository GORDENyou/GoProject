package p.gordenyou.common.tab;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import p.gordenyou.goui.tab.bottom.GoTabBottomInfo;

/**
 * 配合，完成对 Fragment 的管理
 */
public class GoTabViewAdapter {
    // 持有一个底部 Tab 列表
    private List<GoTabBottomInfo<?>> mInfoList;
    // 持有一个当前的 Fragment
    private Fragment mCurFragment;
    // 持有一个 Fragment 的管理器
    private FragmentManager mFragmentManager;


    public GoTabViewAdapter(FragmentManager fragmentManager, List<GoTabBottomInfo<?>> infoList) {
        this.mFragmentManager = fragmentManager;
        this.mInfoList = infoList;
    }

    /**
     * 实例化以及显示指定位置的 Fragment
     *
     * @param container 父容器
     * @param position  位置
     */
    public void instantiateItem(View container, int position) {
        FragmentTransaction mCurTransaction = mFragmentManager.beginTransaction();
        // 若当前 fragment 不为空则隐藏
        if (mCurFragment != null) {
            mCurTransaction.hide(mCurFragment);
        }

        // 为 fragment 设置 tag， 方便以后寻找
        String tag = container.getId() + ":" + position;
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);

        // 若 fragment 不为空则直接显示，为空则创建实例。
        if (fragment != null) {
            mCurTransaction.show(fragment);
        } else {
            fragment = getItem(position);
            if (!fragment.isAdded()) {
                mCurTransaction.add(container.getId(), fragment, tag);
            }
        }
        mCurFragment = fragment;
        mCurTransaction.commitAllowingStateLoss();
    }

    public Fragment getCurFragment() {
        return mCurFragment;
    }

    private Fragment getItem(int position) {
        try {
            return mInfoList.get(position).fragment.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getCount() {
        return mInfoList == null ? 0 : mInfoList.size();
    }
}
