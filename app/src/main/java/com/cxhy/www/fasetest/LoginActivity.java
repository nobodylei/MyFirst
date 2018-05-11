package com.cxhy.www.fasetest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/18.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private static final int LOGIN = 1;

    @ViewInject(R.id.et_username)
    private EditText et_userName;
    @ViewInject(R.id.et_password)
    private EditText et_passWord;
    @ViewInject(R.id.btn_login)
    private Button btn_login;
    @ViewInject(R.id.rl_main)
    private RelativeLayout rl_main;
    @Override
    int getResId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        //title.setText("用户登录");
        String userName = preferences.getString("username", "");
        String passWord = preferences.getString("password","");
        if(!"".equals(userName) && !"".equals(passWord) && preferences.getBoolean("isOk", false)) {
            Intent intent = new Intent(LoginActivity.this, MessagePush.class);
            intent.putExtra("nickName", userName);
            startActivity(intent);
            finish();
        }
//        et_userName.setText(userName);
//        et_passWord.setText(passWord);
        rl_main.setVisibility(View.GONE);
        btn_login.setOnClickListener(this);

        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor edit = preferences.edit();
        String userName = et_userName.getText() + "";
        String passWord = et_passWord.getText() + "";
        switch(view.getId()) {
            case R.id.btn_login:
                networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo == null || !networkInfo.isAvailable()) {
                    Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("cityName", userName);
                map.put("passWord", passWord);
                edit.putString("username",userName);
                edit.putString("password",passWord);
                edit.commit();
                Xutils.getInstance().post("", map, new Xutils.XCallBack() {
                    @Override
                    public void onResponse(String result) {
                        try {
                            JSONObject json = new JSONObject(result);
                            int code = json.getInt("code");
                            String nickName = json.getString("data");
                            if(code == LOGIN) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("nickName", nickName);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                        }
                        //Log.d("tag", result);
                    }
                });
                break;
        }
    }
}
