package com.cirederf.go4lunch.Controllers.Activities;

import com.cirederf.go4lunch.Controllers.BaseActivity.BaseActivity;
import com.cirederf.go4lunch.R;

public class StartActivity extends BaseActivity {


    @Override
    public int getFragmentLayout() {
        return R.layout.activity_start;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isCurrentUserLogged()) {
            this.startMain();
        }else{
            startLogin();}
    }
}