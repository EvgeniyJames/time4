package com.zamkovenko.time4child.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zamkovenko.time4child.R;
import com.zamkovenko.time4child.utils.SimUtils;

import java.util.List;

public class ChooseSimCardActivity extends AppCompatActivity {

    Button m_btn_sim1;
    Button m_btn_sim2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sim);

        m_btn_sim1 = (Button) findViewById(R.id.btn_sim_1);
        m_btn_sim2 = (Button) findViewById(R.id.btn_sim_2);

        m_btn_sim1.setOnClickListener(new OnChooseSimListener());
        m_btn_sim2.setOnClickListener(new OnChooseSimListener());

        SetupSimButtons();
    }

    private void SetupSimButtons() {
        List<String> networkOperator = SimUtils.getNetworkOperator(this);

        m_btn_sim1.setText(R.string.txt_empty);
        m_btn_sim2.setText(R.string.txt_empty);

        for (int i = 0; i < networkOperator.size(); i++) {
            Log.d(getClass().getSimpleName(), networkOperator.get(i));
        }

        for (int i = 0; i < networkOperator.size(); i++) {
            if (i == 0) {
                m_btn_sim1.setText(networkOperator.get(i));
            }
            if (i == 1) {
                m_btn_sim2.setText(networkOperator.get(i));
            }
        }
    }

    private class OnChooseSimListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_sim_1:
                    SimUtils.WriteToSharedPreferences(view.getContext(), 0);
                    break;

                case R.id.btn_sim_2:
                    SimUtils.WriteToSharedPreferences(view.getContext(), 1);
                    break;
            }
            finish();
        }
    }

}
