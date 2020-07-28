package p.gordenyou.goui.tab.top;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/*
泛型实现传入的颜色类型
 */
public class GoTabTopInfo<Color>{
    // 枚举类型定义不同的实现类型。
    public enum TabType {
        BITMAP, TEXT
    }

    public Class<? extends Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;

    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;

    public GoTabTopInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabType.BITMAP;
    }

    public GoTabTopInfo(String name, Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.TEXT;
    }
}
