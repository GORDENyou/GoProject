package p.gordenyou.common.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 用来管理 Fragment 和 Fragment Adapter
 */
public class GoFragmentTabView extends FrameLayout {

    private GoTabViewAdapter mAdapter;
    private int currentPosition;

    public GoFragmentTabView(@NonNull Context context) {
        super(context);
    }

    public GoFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GoFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GoTabViewAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(GoTabViewAdapter mAdapter) {
        if(this.mAdapter != null || mAdapter == null){
            return;
        }
        this.mAdapter = mAdapter;
        currentPosition = -1;
    }

    public void setCurrentItem(int position){
        if (position < 0 || position > mAdapter.getCount()){
            return;
        }
        if(currentPosition != position){
            currentPosition = position;
            mAdapter.instantiateItem(this, position);
        }
    }


    public int getCurrentItem(){
        return currentPosition;
    }

    public Fragment getCurrentFragment(){
        if(this.mAdapter == null){
            throw new IllegalArgumentException("Please call setAdapter first");
        }
        return mAdapter.getCurFragment();
    }
}
