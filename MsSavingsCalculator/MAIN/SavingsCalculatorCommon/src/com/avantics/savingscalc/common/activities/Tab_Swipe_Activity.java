package com.avantics.savingscalc.common.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.avantics.common.IBindManager;
import com.avantics.common.UiBindingContainer;
import com.avantics.savingscalc.common.R;
import com.avantics.savingscalc.common.UiBindingManager;
import com.avantics.savingscalc.common.ViewPagerCustom;
import com.avantics.savingscalc.common.vertCollectionPagerAdapter;

import java.util.ArrayList;

/**
 * Created by tom on 02/06/13.
 */
public class Tab_Swipe_Activity extends FragmentActivity implements IBindManager {
    public UiBindingManager binder = null;

    vertCollectionPagerAdapter mVCollectionPageAdapter;
    ViewPagerCustom mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab_swipe_activity);

        binder = new UiBindingManager();

        setupPager();

        setupABar();
    }

    private void setupABar() {
        // obtain the action bar
        final ActionBar aBar = getActionBar();

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
        aBar.addTab(aBar.newTab().setText("Client").setTabListener(tabListener));
        aBar.addTab(aBar.newTab().setText("Proposed").setTabListener(tabListener));
        aBar.addTab(aBar.newTab().setText("Savings").setTabListener(tabListener));
    }

    private void setupPager() {
        // the page adapter contains all the fragment registrations
        mVCollectionPageAdapter = new vertCollectionPagerAdapter(getSupportFragmentManager(), binder);
        mViewPager = (ViewPagerCustom) findViewById(R.id.pager);

        // set the contents of the ViewPager
        mViewPager.setAdapter(mVCollectionPageAdapter);

        // add a on page change listener to change the actionBar's selected tab # (fragment will then be changed by actionBar's code)
        // the change listener is called on swiping
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int pos) {
                getActionBar().setSelectedNavigationItem(pos);
            }
        });
    }

    @Override
    public ArrayList<UiBindingContainer> AttachToView(View view) {
        return binder.AttachToView(view);
    }
}
