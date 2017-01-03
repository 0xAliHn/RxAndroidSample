package com.android.rxandroid;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.rxandroid.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);

        mainBinding.startActivity1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SimpleRxActivity.class));
            }
        });

        mainBinding.startActivity2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AsynchronousRxActivity.class));
            }
        });

        mainBinding.startActivity3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SplitStringRxActivity.class));
            }
        });

        mainBinding.startActivity4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ErrorAndUnsubscribeRxActivity.class));
            }
        });
    }
}
