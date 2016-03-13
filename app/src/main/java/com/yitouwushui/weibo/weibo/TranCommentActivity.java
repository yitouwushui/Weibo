package com.yitouwushui.weibo.weibo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitouwushui.weibo.R;

public class TranCommentActivity extends AppCompatActivity {

    // 顶栏标题
    TextView text_title;
    EditText editText;
    Button button_ensure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

    }
}
