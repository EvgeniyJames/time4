package com.zamkovenko.time4parent.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zamkovenko.time4parent.R;

/**
 * User: Yevgeniy Zamkovenko
 * Date: 10.12.2017
 */

public class EnterParentPhoneActivity extends AppCompatActivity {

    public static final String PARAM_PARENT_PHONE = "5556";

    private EditText editTextPhone;
    private Button btnConfirmed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_phone);

        editTextPhone = (EditText) findViewById(R.id.txt_ip);
        btnConfirmed = (Button) findViewById(R.id.btn_confirm);

        btnConfirmed.setOnClickListener(new OnConfirmed());
    }

    class OnConfirmed implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Editable phone = editTextPhone.getText();

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(EnterParentPhoneActivity.this);
            prefs.edit().putString(PARAM_PARENT_PHONE, phone.toString()).apply();

            Toast.makeText(EnterParentPhoneActivity.this, "Number was set", Toast.LENGTH_SHORT).show();

            EnterParentPhoneActivity.this.finish();
        }
    }
}
