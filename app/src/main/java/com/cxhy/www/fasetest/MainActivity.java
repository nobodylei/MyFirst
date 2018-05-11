package com.cxhy.www.fasetest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/4/18.
 */

public class MainActivity extends BaseActivity{
    private String nickName;
    @Override
    int getResId() {
        return R.layout.activity_show;
    }

    @Override
    void initView() {
        final Intent intent = getIntent();
        nickName = intent.getStringExtra("nickName");
        final CheckBox checkBox = findViewById(R.id.checkbox);
               findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       if(checkBox.isChecked()) {
                           SharedPreferences.Editor edit = preferences.edit();
                           edit.putBoolean("isOk", true);
                           edit.commit();
                           Intent intent1 = new Intent(MainActivity.this, MessagePush.class);
                           intent1.putExtra("nickName", nickName);
                           startActivity(intent1);
                           finish();
                       } else {
                           Toast.makeText(MainActivity.this,"请同意上述协议",Toast.LENGTH_SHORT).show();
                       }
                   }
               });
        back.setVisibility(View.GONE);
    }
}
