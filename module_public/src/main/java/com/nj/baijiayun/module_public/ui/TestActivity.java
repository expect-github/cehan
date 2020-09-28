package com.nj.baijiayun.module_public.ui;

import android.os.Bundle;
import android.view.View;

import com.nj.baijiayun.module_public.R;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_activity_test);
    }

    public void close(View view) {
        finish();
    }
}
