package p.gordenyou.goproject;

import android.os.Bundle;

import p.gordenyou.common.ui.component.GoBaseActivity;
import p.gordenyou.goproject.logic.MainActivityLogic;


public class MainActivity extends GoBaseActivity implements MainActivityLogic.ActivityProvider {

    private MainActivityLogic activityLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityLogic = new MainActivityLogic(this);
    }
}