package com.mab.chatroom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class AuthFragmentPagerAdapter extends FragmentPagerAdapter {

    private String title [] = {"ورود","ثبت نام"};
    public AuthFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new LoginFragment();
        else
            return new RegisterFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
