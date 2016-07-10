package com.crpr.androidcinema.presentation.welcome_wizard.base;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by claudioribeiro on 09/07/16.
 */
public class CustomPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private Map<Integer, Fragment> retainedFragments;

    public CustomPagerAdapter(FragmentManager fm, @NonNull List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.retainedFragments = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        if(retainedFragments.containsKey(position)) {
            return retainedFragments.get(position);
        }
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @NonNull
    public List<Fragment> getFragments() {
        return fragments;
    }

    @NonNull
    public Collection<Fragment> getRetainedFragments() {
        return retainedFragments.values();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);

        retainedFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(retainedFragments.containsKey(position)) {
            retainedFragments.remove(position);
        }
        super.destroyItem(container, position, object);
    }

}
