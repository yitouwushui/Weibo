package com.yitouwushui.weibo.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.net.NetQueryImpl;
import com.yitouwushui.weibo.utils.ImageOprator;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 发微博、评论、转发
 */
public class UpdateActivity extends AppCompatActivity {

    String title;
    String statusIdstr;
    NetQueryImpl netQuery;

    GridLayout gridLayout;
    int i = 0;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;

    EditText editText;
    TextView textView;
    TextView textView_title;

    ArrayList<ImageView> views = new ArrayList<>();
    ArrayList<Uri> pictures = new ArrayList<>();
    Uri imageUri;

    private static final int REQUEST_PICTURE = 0x1012;    //选择图片请求码
    private static final int REQUEST_CAPTURE = 0x1013;    //照相请求码


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        init();

        showParam();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    textView.setText(String.valueOf(s.length()) + "/140");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * 获得标题，等参数
     */
    private void showParam() {

        Intent intent = getIntent();
        title = intent.getStringExtra(App.ACTION_UPDATE_TITLE);
        if (title.equals("转发")) {
            statusIdstr = intent.getStringExtra(App.ACTION_TRANLATE_STATUS_IDSTR);
        }
        if (title.equals("评论")) {
            statusIdstr = intent.getStringExtra(App.ACTION_COMMENT_STATUS_IDSTR);
        }
        textView_title.setText(title);


    }

    /**
     * 初始化
     */
    private void init() {
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView5 = (ImageView) findViewById(R.id.imageView5);
        imageView6 = (ImageView) findViewById(R.id.imageView6);
        imageView7 = (ImageView) findViewById(R.id.imageView7);
        imageView8 = (ImageView) findViewById(R.id.imageView8);
        imageView9 = (ImageView) findViewById(R.id.imageView9);
        views.add(imageView1);
        views.add(imageView2);
        views.add(imageView3);
        views.add(imageView4);
        views.add(imageView5);
        views.add(imageView6);
        views.add(imageView7);
        views.add(imageView8);
        views.add(imageView9);
        gridLayout = (GridLayout) findViewById(R.id.gridView_update);
        textView = (TextView) findViewById(R.id.word_size);
        editText = (EditText) findViewById(R.id.editText);
        textView_title = (TextView) findViewById(R.id.update_title);
        netQuery = NetQueryImpl.getInstance(this);
    }

    /**
     * 选择图片
     *
     * @param view
     */
    public void clickPicture(View view) {
        if (i < 9) {
            Intent intent = new Intent();

            //请求动作
            intent.setAction(Intent.ACTION_PICK);

            //请求路径
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            Uri uri = Uri.fromFile(path);

            //请求类型
            intent.setDataAndType(uri, "image/*");

            startActivityForResult(intent, REQUEST_PICTURE);
        }
    }

    /**
     * 选择照相
     *
     * @param view
     */
    public void clickCapture(View view) {
        if (i < 9) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            //格式化照片文件名字
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");
            String fileName = simpleDateFormat.format(new Date(System.currentTimeMillis()));

            //照片文件位置
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "weibo_" + fileName + ".jpg");

            imageUri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

            startActivityForResult(intent, REQUEST_CAPTURE);
        }
    }

    /**
     * 选择清除
     *
     * @param view
     */
    public void clickClear(View view) {
        i = 0;
        for (ImageView imageView : views) {
            imageView.setVisibility(View.INVISIBLE);
        }
        // 清除放好的图片
        pictures.clear();

    }

    /**
     * 请求返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri;
        Bitmap bitmap = null;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICTURE:
                    uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setPicture(ImageOprator.comp(bitmap));
                    pictures.add(uri);
                    break;
                case REQUEST_CAPTURE:
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setPicture(ImageOprator.comp(bitmap));
                    pictures.add(imageUri);
                    break;
            }
        }
    }

    /**
     * 放图片
     *
     * @param bitmap
     */
    private void setPicture(Bitmap bitmap) {
        if (i < views.size()) {
            views.get(i).setImageBitmap(bitmap);
            views.get(i++).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获得图片uri
     *
     * @return ArrayList<Uri>
     */
    private ArrayList<Uri> getPictures() {
        return pictures;
    }

    /**
     * 获得文本框输入
     *
     * @return
     */
    private String getInput() {
        String input = String.valueOf(editText.getText());
        String urlStr = null;
        try {
            urlStr = URLEncoder.encode(input, "utf-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return urlStr;
    }

    /**
     * 返回活动
     *
     * @param view
     */
    public void finish(View view) {
        finish();
    }

    /**
     * 点击确定
     *
     * @param view
     */
    public void doComplete(View view) {
        Intent intent;

        // 本来是只需要发广播，为了熟练两种方法特意分开使用一遍
        if (title.equals("发微博")) {
            intent = new Intent();
            intent.putExtra(App.ACTION_UPDATE_TITLE, title);
            intent.putExtra(App.ACTION_UPDATE_INPUT, getInput());
            setResult(RESULT_OK, intent);

            // 发广播
        } else {
            if (title.equals("转发")) {
                intent = new Intent(App.ACTION_TRANLATE);
                intent.putExtra(App.ACTION_TRANLATE_STATUS_IDSTR, statusIdstr);

            } else {
                intent = new Intent(App.ACTION_COMMENT);
                intent.putExtra(App.ACTION_COMMENT_STATUS_IDSTR, statusIdstr);
            }
            intent.putExtra(App.ACTION_UPDATE_INPUT, getInput());
            LocalBroadcastManager.getInstance(getApplicationContext())
                    .sendBroadcast(intent);
        }
        finish();
    }
}
