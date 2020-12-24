package ru.mirea.filmboom.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import ru.mirea.filmboom.fragment.BoughtFragment;
import ru.mirea.filmboom.fragment.FavouriteFragment;
import ru.mirea.filmboom.fragment.TicketFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TicketFragment();
            case 1:
                return new FavouriteFragment();
            case 2:
                return new BoughtFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}