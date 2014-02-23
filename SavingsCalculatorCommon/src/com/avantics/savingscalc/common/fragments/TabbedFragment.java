package com.avantics.savingscalc.common.fragments;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avantics.savingscalc.common.PagingAdapter;
import com.avantics.savingscalc.common.R;

/**
 * Created by tom on 02/06/13.
 */
public class TabbedFragment extends Fragment {
    PagingAdapter mVCollectionPageAdapter;
    ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_swipe_activity, container, false);

        setupView(view);

        return view;
    }

    protected void setupView(View view) {
        setupPager(view);

        setupABar();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupABar() {
        final Activity currentActivity = getActivity();

        // obtain the action bar
        final ActionBar aBar = currentActivity.getActionBar();

        // set it to tab mode
        aBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // add a tab listeners to handle changing the current fragment
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

            }
        };

        // add the tabs, register the event handler for the tabs
        aBar.addTab(aBar.newTab().setText("Incumbent").setTabListener(tabListener));
        aBar.addTab(aBar.newTab().setText("Vendor").setTabListener(tabListener));
        aBar.addTab(aBar.newTab().setText("Summary").setTabListener(tabListener));
    }

    private void setupPager(View view) {
        final Activity currentActivity = getActivity();

        // the page adapter contains all the fragment registrations
        mVCollectionPageAdapter = new PagingAdapter(getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.pager);

        // set the contents of the ViewPager
        mViewPager.setAdapter(mVCollectionPageAdapter);

        // add a on page change listener to change the actionBar's selected tab # (fragment will then be changed by actionBar's code)
        // the change listener is called on swiping
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onPageSelected(int pos) {
                currentActivity.getActionBar().setSelectedNavigationItem(pos);
            }
        });
    }
}

