package com.cirederf.go4lunch.Activities;

import com.cirederf.go4lunch.Activities.BaseActivity;
import com.cirederf.go4lunch.R;

public class StartActivity extends BaseActivity {


    @Override
    public int getActivityLayout() {
        return R.layout.activity_start;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isCurrentUserLogged()) {
            this.startMain();
        } else {
            startLogin();
        }
    }
}