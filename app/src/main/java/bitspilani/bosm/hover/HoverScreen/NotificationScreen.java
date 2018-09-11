/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bitspilani.bosm.hover.HoverScreen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterEvents;
import bitspilani.bosm.adapters.AdapterNotifications;
import bitspilani.bosm.fragments.StallFragment;
import bitspilani.bosm.items.ItemEvent;
import bitspilani.bosm.items.ItemNotification;
import io.mattcarroll.hover.Content;

import static bitspilani.bosm.HomeActivity.getSFM;

/**
 * A screen that is displayed in our Hello World Hover Menu.
 */
public class NotificationScreen implements Content {

    private final Context mContext;
    private final View mWholeScreen;
    private LayoutInflater inflater;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public NotificationScreen(@NonNull Context context) {
        mContext = context.getApplicationContext();
        inflater = LayoutInflater.from(context);
        mWholeScreen = createScreenView();

    }

    @NonNull
    private View createScreenView() {
        @SuppressLint("InflateParams")
            View wholeScreen = inflater.inflate(R.layout.activity_hover, null, false);

        return wholeScreen;
    }

//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(HomeActivity.getSFM());
//        adapter.addFragment(new ProfileScreen(), "Profile");
//        adapter.addFragment(new ProfileScreen(), "Updates");
////        adapter.addFragment(new ThreeFragment(), "THREE");
//        viewPager.setAdapter(adapter);
//    }
//
//    class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public ViewPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
//        }
//    }
    // Make sure that this method returns the SAME View.  It should NOT create a new View each time
    // that it is invoked.
    @NonNull
    @Override
    public View getView() {

        return mWholeScreen;
    }

    @Override
    public boolean isFullscreen() {
        return true;
    }

    @Override
    public void onShown() {
        // No-op.
    }

    @Override
    public void onHidden() {
        // No-op.
    }
}
