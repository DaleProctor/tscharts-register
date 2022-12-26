/*
 * (C) Copyright Syd Logan 2022
 * (C) Copyright Thousand Smiles Foundation 2022
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thousandsmiles.tschartsregister;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.AppCompatActivity;

import org.thousandsmiles.tscharts_lib.WristbandPrinterListFragment;

public class WristbandPrinterActivity extends AppCompatActivity {
    private Context m_context;
    private WristbandPrinterListFragment m_wristbandPrinterListFragment;

    public void handleNextButtonPress(View v) {
        onNextPressed();
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_wristband_printer);
        m_context = getApplicationContext();
        Bundle arguments;
        m_wristbandPrinterListFragment = new WristbandPrinterListFragment();
        arguments = new Bundle();
        m_wristbandPrinterListFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_panel, m_wristbandPrinterListFragment)
                .commit();
    }

    @Override
    protected void onResume() {

        final View root = getWindow().getDecorView().getRootView();
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                goImmersive();
            }
        });

        super.onResume();
        goImmersive();
    }

    public void onNextPressed() {
        startActivity(new Intent(WristbandPrinterActivity.this, PatientSearchActivity.class));
        finish();
    }

    /* see also  https://stackoverflow.com/questions/24187728/sticky-immersive-mode-disabled-after-soft-keyboard-shown */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            goImmersive();
        }
    }

    public void goImmersive() {
        View v1 = getWindow().getDecorView().getRootView();
        v1.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
