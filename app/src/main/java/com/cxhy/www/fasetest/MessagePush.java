package com.cxhy.www.fasetest;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.bumptech.glide.Glide;
import com.cxhy.www.fasetest.permission.PermissionReq;
import com.cxhy.www.fasetest.permission.PermissionResult;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.util.BitmapUtil;
import com.lzy.imagepicker.view.CropImageView;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

/**
 * Created by Administrator on 2018/4/18.
 */

public class MessagePush extends BaseActivity implements View.OnClickListener {
    private static final int IMAGE_PICKER = 1;
    private int number;
    private ProgressDialog progressDialog;
    private Map<String, String> map1 = new HashMap<String, String>();
    private Map<String, File> map2 = new HashMap<String, File>();

    private String host = "";
    private String path = "";
    private String appcode = "";

    Boolean is_old_format = false;//如果文档的输入中含有inputs字段，设置为True， 否则设置为False
    //请根据线上文档修改configure字段
    com.alibaba.fastjson.JSONObject configObj;
    String config_str;
    String method;
    Map<String, String> headers;
    Map<String, String> querys;

    int whickClick = 0;
    @ViewInject(R.id.t1)//布局控件注解
    private TextView tv1;
    @ViewInject(R.id.t2)//布局控件注解
    private TextView tv2;
    @ViewInject(R.id.t3)//布局控件注解
    private TextView tv3;
    @ViewInject(R.id.t4)//布局控件注解
    private TextView tv4;
    @ViewInject(R.id.tv_number)
    private TextView tvNumber;
    @ViewInject(R.id.tv_nickName)
    private TextView tv_nickName;

    @ViewInject(R.id.i1)//布局控件注解
    private ImageView i1;
    @ViewInject(R.id.i2)//布局控件注解
    private ImageView i2;
    @ViewInject(R.id.i3)//布局控件注解
    private ImageView i3;
    @ViewInject(R.id.i4)//布局控件注解
    private ImageView i4;
    @ViewInject(R.id.name)//布局控件注解
    private EditText name;
    @ViewInject(R.id.sfz_num)//布局控件注解
    private EditText sfzNum;
    @ViewInject(R.id.phone)//布局控件注解
    private EditText phone;
    @ViewInject(R.id.address)//布局控件注解
    private EditText address;
    @ViewInject(R.id.remark)//布局控件注解
    private EditText remark;
    @ViewInject(R.id.next)
    private Button btnNext;

    String userName = "";//识别的省份证姓名
    String num = "";
    String addre = "";
    String bankNum = "";

    String image1 = "";

    String image2 = "";

    String image3 = "";

    String image4 = "";
    File file1;
    File file2;
    File file3;
    File file4;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.i1:
                PermissionReq.with(this).permissions(Manifest.permission.CAMERA).result(new PermissionResult() {
                    @Override
                    public void onGranted() {
                        whickClick = 1;
                        Intent intent1 = new Intent(MessagePush.this, ImageGridActivity.class);
                        startActivityForResult(intent1, IMAGE_PICKER);
                    }

                    @Override
                    public void onDenied() {
                    }
                }).request();
                break;
            case R.id.i2:
                PermissionReq.with(this).permissions(Manifest.permission.CAMERA).result(new PermissionResult() {
                    @Override
                    public void onGranted() {
                        whickClick = 2;
                        Intent intent1 = new Intent(MessagePush.this, ImageGridActivity.class);
                        startActivityForResult(intent1, IMAGE_PICKER);
                    }

                    @Override
                    public void onDenied() {
                    }
                }).request();
                break;
            case R.id.i3:
                PermissionReq.with(this).permissions(Manifest.permission.CAMERA).result(new PermissionResult() {
                    @Override
                    public void onGranted() {
                        whickClick = 3;
                        Intent intent1 = new Intent(MessagePush.this, ImageGridActivity.class);
                        startActivityForResult(intent1, IMAGE_PICKER);
                    }

                    @Override
                    public void onDenied() {
                    }
                }).request();
                break;
            case R.id.i4:
                PermissionReq.with(this).permissions(Manifest.permission.CAMERA).result(new PermissionResult() {
                    @Override
                    public void onGranted() {
                        whickClick = 4;
                        Intent intent1 = new Intent(MessagePush.this, ImageGridActivity.class);
                        startActivityForResult(intent1, IMAGE_PICKER);
                    }

                    @Override
                    public void onDenied() {
                    }
                }).request();
                break;
            case R.id.next:
                networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isAvailable()) {
                    Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                String t1 = null;//name.getText().toString();
                String t5 = null;//address.getText().toString();
                try {
                    t1 = URLEncoder.encode(name.getText().toString(), "utf-8");
                    t1 = URLDecoder.decode(t1, "iso8859-1");
                    t5 = URLEncoder.encode(address.getText().toString(), "utf-8");
                    t5 = URLDecoder.decode(t5, "iso8859-1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //Log.d("tag", t1 + " " + t5);
                String t2 = sfzNum.getText().toString();
                String t3 = phone.getText().toString();
                String t4 = remark.getText().toString();
                String nickName = tv_nickName.getText().toString();
                if (file1 == null) {
                    Toast.makeText(this, "请上传身份证正面照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (file2 == null) {
                    Toast.makeText(this, "请上传手持身份证照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (file3 == null) {
                    Toast.makeText(this, "请上传银行卡正面照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (file4 == null) {
                    Toast.makeText(this, "请上传银行卡反面照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(t1)) {
                    Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(t2)) {
                    Toast.makeText(this, "请输入身份证号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(t3)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (t3.length() != 11) {
                    Toast.makeText(this, "手机号码有误", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(t5)) {
                    Toast.makeText(this, "请输入地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(t4)) {
                    Toast.makeText(this, "请输入银行卡号", Toast.LENGTH_SHORT).show();
                    return;
                }
                emptyAll();
                if (progressDialog == null) {
                    progressDialog = ProgressDialog.show(this, "", "正在上传...");
                } else if (progressDialog.isShowing()) {
                    progressDialog.setTitle("");
                    progressDialog.setMessage("正在上传...");
                }
                progressDialog.show();
                //Toast.makeText(MessagePush.this,"上传成功",Toast.LENGTH_SHORT).show();
                map1.put("t1", t1);
                map1.put("t2", t2);
                map1.put("t3", t3);
                map1.put("t4", t4);
                map1.put("t5", t5);
                map1.put("nickName", nickName);
//                if (map1.isEmpty() || map2.isEmpty()) {
//                    return;
//                }
                Xutils.getInstance().upLoadFile("", map1, map2, new Xutils.XCallBack() {
                    @Override
                    public void onResponse(String result) {
                        //Log.d("tag", result);
                        BitmapUtils.deleteCacheFile();
                        try {
                            org.json.JSONObject json = new org.json.JSONObject(result);
                            int code = json.getInt("code");
                            String nickName = json.getString("data");
                            if (code == 1) {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(MessagePush.this, "上传成功", Toast.LENGTH_SHORT).show();
                                number += 1;
                                tvNumber.setText("已上传：00000" + number);
                                map1.clear();
                                map2.clear();
                            } else {
                                Toast.makeText(MessagePush.this, "上传失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(MessagePush.this, "上传失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionReq.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                //Bitmap bitmap = BitmapFactory.decodeFile(images.get(0).path);
                String path = images.get(0).path;//Base64Util.bitmapToBase64(bitmap);
                String base64 = BitmapUtils.compressImageUpload(path);
                switch (whickClick) {
                    case 1:
                        tv1.setVisibility(View.GONE);
                        Glide.with(this).load(images.get(0).path).into(i1);
                        file1 = new File(base64);
                        map2.put("f1", file1);
                        fileToBase64(base64, false);
                        break;
                    case 2:
                        tv2.setVisibility(View.GONE);
                        Glide.with(this).load(images.get(0).path).into(i2);
                        file2 = new File(base64);
                        map2.put("f2", file2);
                        break;
                    case 3:
                        tv3.setVisibility(View.GONE);
                        Glide.with(this).load(images.get(0).path).into(i3);
                        file3 = new File(base64);
                        map2.put("f3", file3);
                        fileToBase64(base64, true);
                        Log.d("bank", "银行卡照片");
                        break;
                    case 4:
                        tv4.setVisibility(View.GONE);
                        Glide.with(this).load(images.get(0).path).into(i4);
                        file4 = new File(base64);
                        map2.put("f4", file4);
                        break;
                    default:
                        break;
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    public void getSFZ(String base64) {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("key","bc428bf503a01ba3485359398e11b738");
//        map.put("image", base64);
//        map.put("side", "front");
//        Xutils.getInstance().post("http://apis.juhe.cn/idimage/verify", map, new Xutils.XCallBack() {
//            @Override
//            public void onResponse(String result) {
//                //Toast.makeText(MessagePush.this, result, Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    @Override
    public void initView() {
        configObj = new com.alibaba.fastjson.JSONObject();
        configObj.put("side", "face");
        config_str = configObj.toString();
        method = "POST";
        headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        querys = new HashMap<String, String>();

        Intent intent = getIntent();
        tv_nickName.setText(intent.getStringExtra("nickName"));
        //Log.d("tag", intent.getStringExtra("nickName"));
        title.setText("基本信息录入");
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
                if (!TextUtils.isEmpty(path)) {
                    Glide.with(activity).load(path).into(imageView);
                }
            }

            @Override
            public void clearMemoryCache() {

            }
        });

        //设置图片加载器
        imagePicker.setShowCamera(true); //显示拍照按钮
        imagePicker.setCrop(true);       //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(600);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(700);//保存文件的高度。单位像素

        i1.setOnClickListener(this);
        i2.setOnClickListener(this);
        i3.setOnClickListener(this);
        i4.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    }

    public void emptyAll() {
        tv1.setVisibility(View.VISIBLE);
        tv2.setVisibility(View.VISIBLE);
        tv3.setVisibility(View.VISIBLE);
        tv4.setVisibility(View.VISIBLE);

        i1.setImageDrawable(null);
        i1.setBackgroundResource(R.drawable.add_bg);
        i2.setImageDrawable(null);
        i2.setBackgroundResource(R.drawable.add_bg);
        i3.setImageDrawable(null);
        i3.setBackgroundResource(R.drawable.add_bg);
        i4.setImageDrawable(null);
        i4.setBackgroundResource(R.drawable.add_bg);

        name.setText("");
        sfzNum.setText("");
        phone.setText("");
        address.setText("");
        remark.setText("");

        file1 = null;
        file2 = null;
        file3 = null;
        file4 = null;

    }

    @Override
    public int getResId() {
        return R.layout.message_push;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    name.setText(userName);
                    sfzNum.setText(num);
                    address.setText(addre);
                    break;
                case 1:
                    Toast.makeText(MessagePush.this, "识别失败，请手动输入", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    remark.setText(bankNum);
                    break;
                default:
                    break;
            }
        }
    };

    class MyThread extends Thread {
        String bodys = "";
        boolean b;
        boolean success = true;

        public MyThread(String str, boolean isBankCard) {
            this.bodys = str;
            b = isBankCard;
        }

        @Override
        public void run() {
            try {
                /**
                 * 重要提示如下:
                 * HttpUtils请从
                 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
                 * 下载
                 *
                 * 相应的依赖请参照
                 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
                 */
                HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
                String res = EntityUtils.toString(response.getEntity());
                JSONObject res_obj = JSON.parseObject(res);
                if (b) {
                    //Log.d("bank", response.toString() + " ; \n" + res_obj);
                    JSONArray outputArray = res_obj.getJSONArray("outputs");
                    String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
                    JSONObject out = JSON.parseObject(output);
                    bankNum = out.getString("card_num");
                    success = out.getBoolean("success");
                    handler.sendEmptyMessage(3);
                    //Log.d("bank", out.toString());
                } else {
                    int stat = response.getStatusLine().getStatusCode();
                    //Log.i("res", res);

                    if (stat != 200) {
                        handler.sendEmptyMessage(1);
                    /*System.out.println("Http code: " + stat);
                    System.out.println("http header error msg: "+ response.getFirstHeader("X-Ca-Error-Message"));
                    System.out.println("Http body error msg:" + EntityUtils.toString(response.getEntity()));*/
                        return;
                    }
                    if (is_old_format) {
                        JSONArray outputArray = res_obj.getJSONArray("outputs");
                        String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
                        JSONObject out = JSON.parseObject(output);
                        //Log.d("TAG", out.toJSONString());
                    } else {
                        //Log.d("TAG2", res_obj.toJSONString());
                        userName = res_obj.getString("name");
                        num = res_obj.getString("num");
                        addre = res_obj.getString("address");
                        success = res_obj.getBoolean("success");
                        handler.sendEmptyMessage(0);
                    }
                }
                if (!success) {
                    handler.sendEmptyMessage(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                handler.sendEmptyMessage(1);
            }
        }
    }

    MyThread myThread;

    /*
     * 获取参数的json对象
     */
    public static com.alibaba.fastjson.JSONObject getParam(int type, String dataValue) {
        com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
        try {
            obj.put("dataType", type);
            obj.put("dataValue", dataValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }


    public void fileToBase64(String imgFile, boolean isBankCard) {
        String bodys = "";
        try {
            File file = new File(imgFile);
            byte[] content = new byte[(int) file.length()];
            FileInputStream finputstream = new FileInputStream(file);
            finputstream.read(content);
            finputstream.close();
            image1 = new String(encodeBase64(content));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (isBankCard) {
            host = "https://yhk.market.alicloudapi.com";
            path = "/rest/160601/ocr/ocr_bank_card.json";
            headers.put("Content-Type", "application/json; charset=UTF-8");
            bodys = "{\"inputs\":[{\"image\":{\"dataType\":50,\"dataValue\":\"" + image1 + "\"}}]}";
        } else {
            host = "http://dm-51.data.aliyun.com";
            path = "/rest/160601/ocr/ocr_idcard.json";
            // 拼装请求body的json字符串
            JSONObject requestObj = new JSONObject();
            try {
                if (is_old_format) {
                    JSONObject obj = new JSONObject();
                    obj.put("image", getParam(50, image1));
                    if (config_str.length() > 0) {
                        obj.put("configure", getParam(50, config_str));
                    }
                    JSONArray inputArray = new JSONArray();
                    inputArray.add(obj);
                    requestObj.put("inputs", inputArray);
                } else {
                    requestObj.put("image", image1);
                    if (config_str.length() > 0) {
                        requestObj.put("configure", config_str);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            bodys = requestObj.toString();
        }
        myThread = new MyThread(bodys, isBankCard);
        myThread.start();
    }
}

