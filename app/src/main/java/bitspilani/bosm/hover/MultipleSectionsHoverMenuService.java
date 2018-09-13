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
package bitspilani.bosm.hover;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import bitspilani.bosm.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bitspilani.bosm.hover.HoverScreen.NotificationScreen;
import io.mattcarroll.hover.HoverMenu;
import io.mattcarroll.hover.HoverView;
import io.mattcarroll.hover.window.HoverMenuService;

/**
 * Extend {@link HoverMenuService} to get a Hover menu that displays the tabs and content
 * in your custom {@link HoverMenu}.
 *
 * This demo menu displays multiple sections of content.
 */
public class MultipleSectionsHoverMenuService extends HoverMenuService {

    private static final String TAG = "MultipleSectionsHoverMenuService";


    private static LayoutInflater inflater;
    //   public static TextView tv_number;

//    public static MultiSectionHoverMenu multiSectionHoverMenu;

    @Override
    protected void onHoverMenuLaunched(@NonNull Intent intent, @NonNull HoverView hoverView) {
        inflater = LayoutInflater.from(getApplicationContext());
        hoverView.setMenu(createHoverMenu());
        hoverView.collapse();
        hoverView.bringToFront();

    }


    @NonNull
    private HoverMenu createHoverMenu() {
        Log.d("aaaaaaaaaa","passed 1");
//        multiSectionHoverMenu = new MultiSectionHoverMenu(getBaseContext());
        return new MultiSectionHoverMenu(getBaseContext());

    }
//
//    public static void increase(Context context){
////        Log.d("aaaaaaaaaa","passed 2");
////        multiSectionHoverMenu.increaseNumber();
//    }

    private static class MultiSectionHoverMenu extends HoverMenu {

        private final Context mContext;
        private final List<Section> mSections;

        public MultiSectionHoverMenu(@NonNull Context context) {
            mContext = context;

            mSections = Arrays.asList(
                    new Section(
                            new SectionId("1"),
                            createTabView(),
                            new NotificationScreen(mContext)
                    )//
            );
        }

        private View createTabView() {
//            @SuppressLint("InflateParams") View iconScreen = inflater.inflate(R.layout.layout_hover_icon, null, false);
//                tv_number = (TextView)iconScreen.findViewById(R.id.tv_number);
//                tv_number.setText("");
//                tv_number.setVisibility(View.GONE);
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(R.drawable.baxter);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            return imageView;
        }

//        public static void increaseNumber(){
//            Log.d("aaaaaaaaaa","passed 3");
//
//            if(tv_number!=null) {
//                Log.d("aaaaaaaaaa","passed 4");
//                int no;
//                if (tv_number.getText().toString().isEmpty()) {
//                    no = 0;
//                } else {
//                    no = Integer.parseInt(tv_number.getText().toString());
//                }
//                no++;
//                tv_number.setText(no + "");
//                tv_number.setVisibility(View.VISIBLE);
//                Log.d("aaaaaaaaaa","passed 5");
//            }
//        }



        @Override
        public String getId() {
            return "multisectionmenu";
        }

        @Override
        public int getSectionCount() {
            return mSections.size();
        }

        @Nullable
        @Override
        public Section getSection(int index) {
            return mSections.get(index);
        }

        @Nullable
        @Override
        public Section getSection(@NonNull SectionId sectionId) {
            for (Section section : mSections) {
                if (section.getId().equals(sectionId)) {
                    return section;
                }
            }
            return null;
        }

        @NonNull
        @Override
        public List<Section> getSections() {
            return new ArrayList<>(mSections);
        }
    }

}
