package com.zamkovenko.time4parent.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zamkovenko.time4parent.R;

public class ChooseSimCardActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sim);
    }
}
