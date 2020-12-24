/*
 * Copyright (C) 2015 Bilibili
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sdldemo.example.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sdldemo.R;
import com.example.sdldemo.example.application.AppActivity;
import com.example.sdldemo.example.fragments.SampleMediaListFragment;

public class SampleMediaActivity extends AppActivity {


    private static Intent newIntent(Context context, int type) {
        Intent intent = new Intent(context, SampleMediaActivity.class);
        intent.putExtra("type", type);
        return intent;
    }

    public static void intentTo(Context context, int type) {
        context.startActivity(newIntent(context,type));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int type = getIntent().getIntExtra("type", 1);
        Fragment newFragment = SampleMediaListFragment.newInstance(type);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.body, newFragment);
        transaction.commit();

        findViewById(R.id.onFinish1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });


    }


//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        boolean show = super.onPrepareOptionsMenu(menu);
//        if (!show) {
//            return show;
//        }
//
//        MenuItem item = menu.findItem(R.id.action_recent);
//        if (item != null) {
//            item.setVisible(false);
//        }
//
//        return true;
//    }
}
