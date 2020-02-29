package com.creativodevelopers.foodwastagemanagment;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsAccessorAdapter extends FragmentPagerAdapter {

    public TabsAccessorAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                return  new ShowEventFragment();
            case 1:
                return  new myeventsfragment();
            default:
                return  null;
        }

    }

    @Override
    public int getCount() {

        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return  "Show Event";
            case 1:
                return  "Registered Events";

            default:
                return  null;
        }
    }
}
