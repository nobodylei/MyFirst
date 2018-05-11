package com.cxhy.www.fasetest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import org.xutils.x;

/**
 * Created by Administrator on 2018/4/18.
 */

public abstract class BaseActivity extends AppCompatActivity {
    TextView title;
    ImageView back;
    SharedPreferences preferences;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    private long fistPressedTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("config", MODE_PRIVATE);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        LinearLayout layout = findViewById(R.id.liner);
        View view = LayoutInflater.from(layout.getContext()).inflate(getResId(), null);
        layout.addView(view);
        x.view().inject(this);
        initView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = preferences.edit();
                edit.clear();
                edit.commit();
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - fistPressedTime < 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }
        fistPressedTime = System.currentTimeMillis();
    }

    abstract void initView();
    abstract int getResId();
}
