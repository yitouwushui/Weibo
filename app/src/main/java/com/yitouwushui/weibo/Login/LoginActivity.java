package com.yitouwushui.weibo.Login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.yitouwushui.weibo.net.NetQueryImpl;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.AccessToken;
import com.yitouwushui.weibo.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    WebView webView;
    int countBack;
    AccessToken accessToken;
    NetQueryImpl netQuery;
    int currentID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        // 给请求队列赋值
        netQuery = NetQueryImpl.getInstance(LoginActivity.this);
        currentID = getPreferences(MODE_PRIVATE).getInt(App.CURRENT_ID, 1);
//        boolean isFirst = getPreferences(MODE_APPEND).getBoolean("isFirst", true);
        accessToken = AccessToken.findById(AccessToken.class, currentID);
        if (accessToken == null) {
            webView = (WebView) findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);// 启动JS
            webView.getSettings().setSupportZoom(false);// 支持缩放
//        webView.getSettings().setBlockNetworkImage(false);// 是否加载图片
//            isFirst = getPreferences(MODE_PRIVATE).edit().putBoolean("isFirst", false).commit();
            getPreferences(MODE_PRIVATE).edit().putInt(App.CURRENT_ID, currentID).commit();
            NetQueryImpl.accessTokenID = currentID;
            webView.getSettings().setDisplayZoomControls(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.loadUrl(App.URL);
            webView.setWebViewClient(new MyClient());
            Log.e("------------", "第一次加载");
        } else {
            NetQueryImpl.accessTokenID = currentID;
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Log.e("------------", "重复次加载");
            startActivity(intent);
            finish();
        }

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case App.MESSAGE_LOGIN:
                    accessToken = (AccessToken) msg.obj;
                    netQuery.userQuery(handler);
                    break;
                case App.MESSAGE_USER:
                    netQuery.friendStatusQuery(handler);
                    netQuery.publicStatusQuery(handler);
                    break;
//                case App.MESSAGE_PUBLIC_STATUS:
//                    break;
                case App.MESSAGE_FRIENDS_STATUS:
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }

        }
    };

    class MyClient extends WebViewClient {
        /**
         * 开始加载页面
         *
         * @param view    WebView
         * @param url     加载页面的URL
         * @param favicon 位图(网址的图标)
         */
        public void onPageStarted(WebView view, final String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e("url", url);

            netQuery.loginQuery(handler, url);


        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                countBack = 0;
                return true;
            } else {
                countBack++;
                if (countBack == 2) {
                    return super.onKeyDown(keyCode, event);
                } else {
                    Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
