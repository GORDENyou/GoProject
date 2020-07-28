package p.gordenyou.goproject.demo.tab;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import p.gordenyou.goproject.R;
import p.gordenyou.goui.tab.common.IGoTabLayout;
import p.gordenyou.goui.tab.top.GoTabTopInfo;
import p.gordenyou.goui.tab.top.GoTabTopLayout;

public class GoTabTopDemoActivity extends AppCompatActivity {

    String[] tabsStr = new String[]{
            "热门",
            "服装",
            "数码",
            "鞋子",
            "零食",
            "家电",
            "汽车",
            "百货",
            "家居",
            "装修",
            "运动"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_tab_top_demo);
        initTabTop();
    }

    private void initTabTop() {
        GoTabTopLayout hiTabTopLayout = findViewById(R.id.tab_top_layout);
        List<GoTabTopInfo<?>> infoList = new ArrayList<>();
        int defaultColor = getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = getResources().getColor(R.color.tabBottomTintColor);
        for (String s : tabsStr) {
            GoTabTopInfo<?> info = new GoTabTopInfo<>(s, defaultColor, tintColor);
            infoList.add(info);
        }
        hiTabTopLayout.inflateInfo(infoList);
        hiTabTopLayout.addTabSelectedChangeListener(new IGoTabLayout.OnTabSelectedListener<GoTabTopInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, @Nullable GoTabTopInfo<?> prevInfo, @NonNull GoTabTopInfo<?> nextInfo) {
                Toast.makeText(GoTabTopDemoActivity.this, nextInfo.name, Toast.LENGTH_SHORT).show();
            }
        });
        hiTabTopLayout.defaultSelected(infoList.get(0));
    }
}