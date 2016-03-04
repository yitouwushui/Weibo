package com.yitouwushui.weibo.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    FragmentManager fm;
    DiscoveryFragment discoveryFragment;
    FirstPageFragment firstPageFragment;
    MeFragment meFragment;
    MessageFragment messageFragment;
    BottomAdapter bottomAdapter;
    GridView gridView_bottom;

    String access_token = null;
    String uid;
    String expires_in;
    User user;
    Bitmap bitmap = null;
    Picasso picasso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            discoveryFragment = new DiscoveryFragment();
            firstPageFragment = new FirstPageFragment();
            meFragment = new MeFragment();
            messageFragment = new MessageFragment();
        }
        init();

        /**
         * 获取参数
         */
        getParam();


    }

    /**
     * 获得参数
     */
    private void getParam() {
        Volley.newRequestQueue(MainActivity.this).add(
                new StringRequest(
                        Request.Method.GET,
                        "https://api.weibo.com/2/users/show.json?access_token=" + access_token + "&uid=" + uid,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject json = new JSONObject(response);

                                    user = new User();
                                    user.setId(json.getLong("id"));
                                    user.setScreen_name(json.getString("screen_name"));
                                    user.setProfile_image_url(json.getString("profile_image_url"));
                                    user.setProvince(json.getString("province"));
                                    user.setCity(json.getString("city"));
                                    user.setLocation(json.getString("location"));
                                    user.setDescription(json.getString("description"));
                                    user.setProfile_url(json.getString("profile_url"));
                                    user.setGender(json.getString("gender"));
                                    user.setFollowers_count(json.getInt("followers_count"));
                                    user.setFriends_count(json.getInt("friends_count"));
                                    user.setStatuses_count(json.getInt("statuses_count"));
                                    user.setFavourites_count(json.getInt("favourites_count"));
                                    user.setCreated_at(json.getString("created_at"));
                                    user.setAvatar_large(json.getString("avatar_large"));
                                    user.setAvatar_hd(json.getString("avatar_hd"));
                                    user.setBi_followers_count(json.getInt("bi_followers_count"));

                                    user.save();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("error", error.toString());
                            }
                        }));
    }

    /**
     * 初始化
     */
    private void init() {
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        gridView_bottom = (GridView) findViewById(R.id.gridView);

        fm = getSupportFragmentManager();
        setVisibility(firstPageFragment);

        bottomAdapter = new BottomAdapter(MainActivity.this);
        gridView_bottom.setAdapter(bottomAdapter);

        gridView_bottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bottomAdapter.select = position;
                switch (position) {
                    case 0:
                        setVisibility(firstPageFragment);
                        break;
                    case 1:
                        setVisibility(messageFragment);
                        break;
                    case 2:
                        showMsg("+");
                        break;
                    case 3:
                        setVisibility(discoveryFragment);
                        break;
                    case 4:
                        setVisibility(meFragment);
                        break;
                    default:
                        break;
                }
                bottomAdapter.notifyDataSetChanged();
                gridView_bottom.setAdapter(bottomAdapter);
            }
        });
        // 获取参数
        Intent intent = getIntent();
        access_token = intent.getStringExtra(App.ACCESS_TOKEN);
        uid = intent.getStringExtra("uid");
        expires_in = intent.getStringExtra("expires_in");
        picasso = Picasso.with(MainActivity.this);
    }

    /**
     * 切换显示的fragment
     *
     * @param fragment 片段
     */
    public void setVisibility(Fragment fragment) {
        fm.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    /**
     * 通知消息
     *
     * @param msg 消息
     */
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void exit(View view) {
    }

    public void weiBo(View view) {

    }

    public void follow(View view) {

    }

    public void following(View view) {
    }

    public void homePage(View view) {
    }


//    private class MeThread extends Thread {
//        @Override
//        public void run() {
//            super.run();
//            try {
//
////                URL url = new URL(user.getAvatar_large());
////
////                URLConnection urlConnection = url.openConnection();
////                HttpURLConnection conn = (HttpURLConnection) urlConnection;
//
//                URL url = new URL(user.getAvatar_large());
//                bitmap = BitmapFactory.decodeStream(url.openStream());
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
////    }


}
