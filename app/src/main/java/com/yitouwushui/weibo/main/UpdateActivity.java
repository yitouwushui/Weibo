package com.yitouwushui.weibo.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitouwushui.weibo.R;

public class UpdateActivity extends AppCompatActivity {

    // 顶栏标题
    TextView text_title;
    EditText editText;
    ImageView imageView;
    Button button_ensure;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        init();

    }

    private void init() {
        text_title = (TextView) findViewById(R.id.text_titile);
        editText = (EditText) findViewById(R.id.edit_word);
        imageView = (ImageView) findViewById(R.id.img_pic);
        button_ensure = (Button) findViewById(R.id.button_ensure);
    }
}
