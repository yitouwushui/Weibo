package com.yitouwushui.weibo.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.AccessToken;
import com.yitouwushui.weibo.entity.User;
import com.yitouwushui.weibo.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    WebView webView;
    int countBack;
    String access_token;
    String expires_in;
    String uid;
    User user;
    boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (isFirst) {
            webView = (WebView) findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);// 启动JS
            webView.getSettings().setSupportZoom(false);// 支持缩放
//        webView.getSettings().setBlockNetworkImage(false);// 是否加载图片
            webView.getSettings().setDisplayZoomControls(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.loadUrl(App.URL);
            webView.setWebViewClient(new MyClient());
            Log.e("------------", "第一次加载");
        } else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Log.e("------------", "重复次加载");
            startActivity(intent);
        }

    }

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

            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 请求令牌
                    if (url.startsWith(App.REDIRECTURL)) {
                        int index = url.indexOf("=");
                        final String code = url.substring(index + 1);

                        Log.e("code", code);
                        Volley.newRequestQueue(getApplicationContext()).add(
                                new StringRequest(
                                        Request.Method.POST,
                                        "https://api.weibo.com/oauth2/access_token?client_id=3078393470&client_secret=fa341b31035872b7ee9657288ba2dc4f&grant_type=authorization_code&redirect_uri=https://api.weibo.com/oauth2/default.html&code=" + code,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                try {
                                                    JSONObject json = new JSONObject(s);
                                                    access_token = json.getString("access_token");
                                                    expires_in = json.getString("expires_in");
                                                    uid = json.getString("uid");
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    intent.putExtra(App.ACCESS_TOKEN, access_token);
                                                    intent.putExtra("expires_in", expires_in);
                                                    intent.putExtra("uid", uid);
                                                    AccessToken accessToken = new AccessToken(access_token, expires_in, uid);
                                                    accessToken.save();
                                                    isFirst = false;
                                                    startActivity(intent);
                                                    // 退出界面
                                                    finish();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                                Log.e("警告", volleyError.toString());
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String, String> map = new HashMap<>();
                                        map.put("code", code);
                                        map.put("redirect_uri", App.REDIRECTURL);
                                        return map;
                                    }
                                }
                        );
                    }
                }
            }).start();

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
