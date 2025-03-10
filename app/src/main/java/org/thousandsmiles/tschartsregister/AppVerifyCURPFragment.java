/*
 * (C) Copyright Syd Logan 2020
 * (C) Copyright Thousand Smiles Foundation 2020
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

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class AppVerifyCURPFragment extends Fragment {
    private Activity m_activity = null;
    private boolean m_hasCurp = false;

    public static AppVerifyCURPFragment newInstance() {
        return new AppVerifyCURPFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            m_activity = (Activity) context;
        }
    }

    public void handleNextButtonPress(View v) {

        if (false && m_hasCurp == false) {  // XXX
            startActivity(new Intent(m_activity, PatientInfoActivity.class));
            m_activity.finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle(m_activity.getString(R.string.title_verify_curp));
            builder.setMessage(m_activity.getString(R.string.msg_were_you_able_to_verify_curp));

            builder.setPositiveButton(m_activity.getString(R.string.button_yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (false /* && SessionSingleton.getInstance().getCURPLookup()*/) {
                        startActivity(new Intent(m_activity, PatientInfoActivity.class));
                    } else {
                        startActivity(new Intent(m_activity, MedicalHistoryActivity.class));
                    }
                    m_activity.finish();
                }
            });

            builder.setNegativeButton(m_activity.getString(R.string.button_no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent(m_activity, PatientInfoActivity.class));
                    m_activity.finish();
                }
            });

            if (m_hasCurp == false) {
                builder.setNeutralButton(m_activity.getString(R.string.button_skip), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(m_activity, MedicalHistoryActivity.class));
                        m_activity.finish();
                    }
                });
            }

            AlertDialog alert = builder.create();
            alert.setCancelable(false);
            alert.setCanceledOnTouchOutside(false);
            alert.show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle b = getArguments();
        m_hasCurp = b.getBoolean("hasCurp");
        WebView v = getActivity().findViewById(R.id.webview);

        if (v != null) {
            WebSettings webSettings = v.getSettings();
            webSettings.setJavaScriptEnabled(true);
            CURPWebViewClient wvc = new CURPWebViewClient();
            wvc.setHasCurp(m_hasCurp);
            v.setWebViewClient(wvc);
            v.loadUrl("https://www.gob.mx/curp/");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_verify_curp_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
   }
}