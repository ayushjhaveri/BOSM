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
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.paytm.pgsdk.Log;

import net.glxn.qrgen.android.QRCode;

import bitspilani.bosm.R;
import bitspilani.bosm.WalletActivity;
import io.mattcarroll.hover.Content;

/**
 * A screen that is displayed in our Hello World Hover Menu.
 */
public class ProfileScreen extends Fragment{

    private static final String TAG = "Profile";
    private LayoutInflater inflater;

    public static ImageView iv_qr ;
    private FirebaseUser user;

    public ProfileScreen(){}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_hover_profile, null, false);

        iv_qr = (ImageView) view.findViewById(R.id.iv_qr);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return view;
        }

        ((TextView)view.findViewById(R.id.tv_email)).setText(user.getEmail());
        ((TextView)view.findViewById(R.id.tv_name)).setText(user.getDisplayName());
        //implement contents of layout

        return view;
    }



//    @NonNull
//    private View createScreenView() {
//        @SuppressLint("InflateParams") View wholeScreen = inflater.inflate(R.layout.layout_hover_profile, null, false);
//
//
//
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user==null){
//            return wholeScreen;
//        }
//
//        ((TextView)wholeScreen.findViewById(R.id.tv_email)).setText(user.getEmail());
//        ((TextView)wholeScreen.findViewById(R.id.tv_name)).setText(user.getDisplayName());
//        //implement contents of layout
//
//        return wholeScreen;
//    }

    // Make sure that this method returns the SAME View.  It should NOT create a new View each time
    // that it is invoked.



    public static void setQR(final String qr){
        @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                return QRCode.from(qr).bitmap();
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Bitmap bitmap = (Bitmap)o;
//                iv_qr.setImageBitmap(bitmap);
            }
        };
        asyncTask.execute();
    }



}
