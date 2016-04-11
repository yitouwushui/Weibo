package com.yitouwushui.weibo.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.entity.AccessToken;
import com.yitouwushui.weibo.entity.Comments;
import com.yitouwushui.weibo.entity.Pic_urls;
import com.yitouwushui.weibo.entity.Status;
import com.yitouwushui.weibo.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yitouwushui on 2016/3/5.
 */
public class NetQueryImpl implements NetQuery {

    /**
     * d 唯一一份请求队列
     */
    public static RequestQueue queue;

    User user;
    AccessToken accessToken;
    String access_token;
    String uid;
    String expires_in;
    Gson gson = new Gson();
    Long atId;
    public static int accessTokenID;

    public NetQueryImpl() {

    }

    /**
     * 高效单例模式
     *
     * @param context
     * @return
     */
    public static NetQueryImpl getInstance(Context context) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
            queue.start();
        }
        return Inner.query;
    }

    /**
     * 静态的匿名内部类实例 queue，只有一份
     */
    private static class Inner {
        final static NetQueryImpl query = new NetQueryImpl();
    }

    /**
     * 获得登录用户
     */
    public void getUser() {
        AccessToken accessToken = AccessToken.findById(AccessToken.class, accessTokenID);
        access_token = accessToken.getAccess_token();
        expires_in = accessToken.getExpires_in();
        uid = accessToken.getUid();
    }

    /**
     * 登录请求
     *
     * @param handler
     * @param url
     */
    @Override
    public void loginQuery(final Handler handler, String url) {
        // 请求令牌
        if (url.startsWith(App.REDIRECTURL)) {
            int index = url.indexOf("=");
            final String code = url.substring(index + 1);

            queue.add(
                    new StringRequest(
                            Request.Method.POST,
                            "https://api.weibo.com/oauth2/access_token?client_id=3078393470&client_secret=fa341b31035872b7ee9657288ba2dc4f&grant_type=authorization_code&redirect_uri=https://api.weibo.com/oauth2/default.html&code=" + code,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {
                                    try {
                                        JSONObject json = new JSONObject(s);
                                        accessToken = new AccessToken(json.getString("access_token"), json.getString("expires_in"), json.getString("uid"));
                                        atId = accessToken.getId();
                                        accessToken.save();
                                        Log.e("-----------", accessToken.toString());
                                        Log.e("--------------", accessToken.getId().toString());
                                        getUser();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    Message message = Message.obtain();
                                    message.what = App.MESSAGE_LOGIN;
                                    message.obj = accessToken;
                                    handler.sendMessage(message);
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

    /**
     * 获得用户资料
     *
     * @param handler
     */
    @Override
    public void userQuery(final Handler handler) {
        getUser();
        // 定义用户资料请求
        queue.add(new StringRequest(
                        Request.Method.GET,
                        "https://api.weibo.com/2/users/show.json?access_token=" + access_token + "&uid=" + uid,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                user = gson.fromJson(response, User.class);

                                user.save();
                                Log.e("-------------", user.toString());
                                Message message = Message.obtain();
                                message.what = App.MESSAGE_USER;
                                message.obj = user;
                                handler.sendMessage(message);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("error", error.toString());
                            }
                        })
        );
    }

    /**
     * 获得用户资料
     *
     * @param handler
     */
    @Override
    public void userOtherQuery(final Handler handler, String idstr) {
        getUser();
        // 定义他人用户资料请求
        queue.add(new StringRequest(
                        Request.Method.GET,
                        "https://api.weibo.com/2/users/show.json?access_token=" + access_token + "&uid=" + idstr,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                user = gson.fromJson(response, User.class);

                                Message message = Message.obtain();
                                message.what = App.MESSAGE_USER;
                                message.obj = user;
                                handler.sendMessage(message);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("error", error.toString());
                            }
                        })
        );
    }

    /**
     * 获得公共微博
     *
     * @param handler
     */
    @Override
    public void publicStatusQuery(final Handler handler) {
        // 定义公共微博请求
        getUser();
        queue.add(new StringRequest(
                Request.Method.GET,
                "https://api.weibo.com/2/statuses/public_timeline.json?access_token=" + access_token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        JsonParser parser = new JsonParser();
                        JsonElement element = parser.parse(response);
                        JsonObject g1 = element.getAsJsonObject();
                        element = g1.get("statuses");

                        JsonArray jsonArray = element.getAsJsonArray();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            element = jsonArray.get(i);
                            Status status = gson.fromJson(element, Status.class);
                            // 公共微博类型为1
                            status.setType(1);

                            // 截取设备信息
                            String source = status.getSource();
                            if (source.indexOf(">") > 0) {
                                status.setSource(source.substring(source.indexOf(">") + 1, source.lastIndexOf("<")));
                            }

                            //将userId字段加到Status表中
                            long userId = status.getUser().getId();
                            ArrayList<Pic_urls> pic_urlses = status.getPic_urls();

                            // new 两个储存缩略图和高清图的builder
                            StringBuilder thumbnail = new StringBuilder();
                            StringBuilder bmiddle = new StringBuilder();
                            for (int j = 0; j < pic_urlses.size(); j++) {
                                StringBuilder builder = new StringBuilder(pic_urlses.get(j).getThumbnail_pic());
                                // 将缩略图信息保存在thumbnail并用","隔开
                                thumbnail.append(builder).append(",");
                                int index = builder.indexOf("thumbnail");
                                builder.replace(index, index + 9, "bmiddle");
                                bmiddle.append(builder).append(",");
                            }
                            status.setThumbnail_pic(thumbnail.toString());
                            status.setBmiddle_pic(bmiddle.toString());
                            status.setUserIdStatus(userId);
                            // 存储
                            status.save();
                            status.getUser().save();
                        }

                        // 获取数据完毕，发送消息
                        Message message = new Message();
                        message.what = App.MESSAGE_PUBLIC_STATUS;
                        handler.sendMessage(message);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));
    }

    /**
     * 获得关注微博
     *
     * @param handler
     */
    @Override
    public void friendStatusQuery(final Handler handler) {
        // 定义关注微博请求
        getUser();
        queue.add(new StringRequest(
                Request.Method.GET,
                "https://api.weibo.com/2/statuses/friends_timeline.json?access_token=" + access_token + "&count=50",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonParser parser = new JsonParser();
                        JsonElement element = parser.parse(response);
                        JsonObject g1 = element.getAsJsonObject();
                        element = g1.get("statuses");
                        JsonArray jsonArray = element.getAsJsonArray();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            element = jsonArray.get(i);
                            Status status = gson.fromJson(element, Status.class);
                            //关注微博类型为2
                            status.setType(2);

                            // 截取设备信息
                            String source = status.getSource();
                            if (source.indexOf(">") > 0) {
                                status.setSource(source.substring(source.indexOf(">") + 1, source.lastIndexOf("<")));
                            }
                            //将userId字段加到Status表中
                            long userId = status.getUser().getId();
                            ArrayList<Pic_urls> pic_urlses = status.getPic_urls();

                            // new 两个储存缩略图和高清图的builder
                            StringBuilder thumbnail = new StringBuilder();
                            StringBuilder bmiddle = new StringBuilder();
                            for (int j = 0; j < pic_urlses.size(); j++) {
                                StringBuilder builder = new StringBuilder(pic_urlses.get(j).getThumbnail_pic());
                                // 将缩略图信息保存在thumbnail并用","隔开
                                thumbnail.append(builder).append(",");
                                int index = builder.indexOf("thumbnail");
                                builder.replace(index, index + 9, "bmiddle");
                                bmiddle.append(builder).append(",");
                            }
                            status.setThumbnail_pic(thumbnail.toString());
                            status.setBmiddle_pic(bmiddle.toString());
                            status.setUserIdStatus(userId);
                            // 存储
                            status.save();
                            status.getUser().save();

                        }

                        // 获取数据完毕，发送消息
                        Message message = Message.obtain();
                        message.what = App.MESSAGE_FRIENDS_STATUS;
                        handler.sendMessage(message);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("没有数据", "233333333333333333");
                    }
                }));
    }

    @Override
    public void singleStatusQuery(Handler handler, String idstr) {
//https://api.weibo.com/2/statuses/show.json
    }

    /**
     * 获得自己的微博
     *
     * @param handler
     */
    @Override
    public void myStatusQuery(final Handler handler, String idstr, final int page) {
        // 定义自己的微博查询
        getUser();
        queue.add(new StringRequest(
                Request.Method.GET,
                "https://api.weibo.com/2/statuses/user_timeline.json?access_token=" + access_token + "&uid=" + idstr + "&page=" + page,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Status> myStaList = new ArrayList<Status>();
                        JsonParser parser = new JsonParser();
                        JsonElement element = parser.parse(response);
                        JsonObject g1 = element.getAsJsonObject();
                        element = g1.get("statuses");
                        JsonArray jsonArray = element.getAsJsonArray();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            element = jsonArray.get(i);
                            Status status = gson.fromJson(element, Status.class);

                            // 截取设备信息
                            String source = status.getSource();
                            if (source.indexOf(">") > 0) {
                                status.setSource(source.substring(source.indexOf(">") + 1, source.lastIndexOf("<")));
                            }

                            ArrayList<Pic_urls> pic_urlses = status.getPic_urls();
                            // new 两个储存缩略图和高清图的builder
                            StringBuilder thumbnail = new StringBuilder();
                            StringBuilder bmiddle = new StringBuilder();
                            for (int j = 0; j < pic_urlses.size(); j++) {
                                StringBuilder builder = new StringBuilder(pic_urlses.get(j).getThumbnail_pic());
                                // 将缩略图信息保存在thumbnail并用","隔开
                                thumbnail.append(builder).append(",");
                                int index = builder.indexOf("thumbnail");
                                builder.replace(index, index + 9, "bmiddle");
                                bmiddle.append(builder).append(",");
                            }
                            status.setThumbnail_pic(thumbnail.toString());
                            status.setBmiddle_pic(bmiddle.toString());

                            // 添加到List
                            myStaList.add(status);
                        }
                        Log.e("page", String.valueOf(page));

                        // 获取数据完毕，发送消息
                        Message message = Message.obtain();
                        message.what = App.MESSAGE_MY_STATUS;
                        message.obj = myStaList;
                        message.arg1 = page;
                        handler.sendMessage(message);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("没有数据", "233333333333333333");
                    }
                }));

    }

    /**
     * 微博评论查询
     *
     * @param handler
     * @param idstr
     */
    @Override
    public void statusCommentQuery(final Handler handler, final String idstr, int page) {
        getUser();
        queue.add(new StringRequest(
                        Request.Method.GET,
                        "https://api.weibo.com/2/comments/show.json?&access_token=" + access_token + "&id=" + idstr + "&page=" + page,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                ArrayList<Comments> commentsList = new ArrayList<>();
                                JsonParser parser = new JsonParser();
                                JsonElement element = parser.parse(response);
                                JsonObject g1 = element.getAsJsonObject();
                                element = g1.get("comments");

                                JsonArray jsonArray = element.getAsJsonArray();
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    element = jsonArray.get(i);
                                    Comments comments = gson.fromJson(element, Comments.class);

                                    // 将此条微博的信息ID存储在comments对象中
                                    comments.setStatusId(idstr);

                                    //将userId字段加到comments表中
                                    User user = comments.getUser();
                                    comments.setUserId(user.getIdstr());

                                    // 将comment和user存储到表中
                                    comments.save();
                                    user.save();
                                    commentsList.add(comments);
//                                    Log.e("22222222222", comments.toString());
                                }

                                // 获取数据完毕，发送消息
                                Message message = Message.obtain();
                                message.what = App.MESSAGE_STATUS_COMMENTS;
                                message.obj = commentsList;
                                handler.sendMessage(message);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("statusCommentQuery", error.toString());
                            }
                        })
        );
    }

    @Override
    public void commentStatus(final Handler handler, String str, final String statusId) {
        getUser();
        queue.add(new StringRequest(
                Request.Method.POST,
                "https://api.weibo.com/2/comments/create.json?&access_token=" + access_token + "&comment=" + str + "&id=" + statusId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Comments comments = gson.fromJson(response, Comments.class);
                        // 将此条微博的信息ID存储在comments对象中
                        comments.setStatusId(statusId);

                        //将userId字段加到comments表中
                        User user = comments.getUser();
                        comments.setUserId(user.getIdstr());

                        // 将comment和user存储到表中
                        comments.save();
                        user.save();

                        // 获取数据完毕，发送消息
                        Message message = Message.obtain();
                        message.what = App.MESSAGE_COMMENTS_STATUS_SUCCESS;
                        message.obj = comments;
                        handler.sendMessage(message);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Message message = Message.obtain();
                        message.what = App.MESSAGE_COMMENTS_STATUS_FAIL;
                        handler.sendMessage(message);

                    }
                }));
    }

    /**
     * 转发一条微博
     */
    @Override
    public void repostsStatusQuery(Handler handler, String Idstr) {

    }

    /**
     * 发布一条微博
     *
     * @param handler
     * @Param status
     */
    @Override
    public void updateStatus(final Handler handler, String string) {
        getUser();
        queue.add(new StringRequest(
                Request.Method.POST,
                "https://api.weibo.com/2/statuses/update.json?&access_token=" + access_token + "&status=" + string,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Status status = gson.fromJson(response, Status.class);
                        Log.e("g222222222", status.toString());
                        //关注微博类型为2
                        status.setType(2);
                        // 截取设备信息
                        String source = status.getSource();
                        if (source.indexOf(">") > 0) {
                            status.setSource(source.substring(source.indexOf(">") + 1, source.lastIndexOf("<")));
                        }
                        //将userId字段加到Status表中
                        long userId = status.getUser().getId();
                        status.setUserIdStatus(userId);
                        // 存储
                        status.save();
                        status.getUser().save();


                        // 获取数据完毕，发送消息
                        Message message = Message.obtain();
                        message.what = App.MESSAGE_UPDATE_STATUS_SUCCESS;
                        message.obj = status;
                        handler.sendMessage(message);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("updateStatus", error.toString());
                        Message message = Message.obtain();
                        message.what = App.MESSAGE_UPDATE_STATUS_FAIL;
                        handler.sendMessage(message);
                    }
                }));
    }

    /**
     * 发布一条带图片的微博
     *
     * @param handler
     * @param status
     */
    @Override
    public void uploadStatus(Handler handler, String status) {

    }

    @Override
    public void userFriendsQuery(final Handler handler, String idstr, int cursor) {
        getUser();
        queue.add(new StringRequest(
                        Request.Method.GET,
                        "https://api.weibo.com/2/friendships/friends.json?access_token=" + access_token + "&uid=" + idstr + "&cursor=" + cursor,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                ArrayList<User> userList = new ArrayList<>();
                                JsonParser parser = new JsonParser();
                                JsonElement element = parser.parse(response);
                                JsonObject g1 = element.getAsJsonObject();
                                element = g1.get("users");
                                JsonElement next_cursorElement = g1.get("next_cursor");
                                JsonElement previous_cursorElement = g1.get("previous_cursor");
                                JsonElement total_number = g1.get("total_number");
                                int next_cursor = Integer.valueOf(next_cursorElement.toString());
                                int previous_cursor = Integer.valueOf(previous_cursorElement.toString());

                                JsonArray jsonArray = element.getAsJsonArray();
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    element = jsonArray.get(i);
                                    User user = gson.fromJson(element, User.class);
                                    userList.add(user);
                                }

                                // 获取数据完毕，发送消息
                                Message message = Message.obtain();
                                message.what = App.MESSAGE_FRIENDS_USERS;
                                message.arg1 = next_cursor;
                                message.arg2 = previous_cursor;
                                message.obj = userList;
                                Log.e("message", String.valueOf(userList.size()));
                                handler.sendMessage(message);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("error", error.toString());
                            }
                        })
        );
    }

    @Override
    public void userFollowersQuery(final Handler handler, String idstr, int cursor) {
        getUser();
        queue.add(new StringRequest(
                        Request.Method.GET,
                        "https://api.weibo.com/2/friendships/followers.json?access_token=" + access_token + "&uid=" + idstr + "&cursor=" + cursor,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                ArrayList<User> userList = new ArrayList<>();
                                JsonParser parser = new JsonParser();
                                JsonElement element = parser.parse(response);
                                JsonObject g1 = element.getAsJsonObject();
                                element = g1.get("users");
                                JsonElement next_cursorElement = g1.get("next_cursor");
                                JsonElement previous_cursorElement = g1.get("previous_cursor");
                                JsonElement total_number = g1.get("total_number");
                                int next_cursor = Integer.valueOf(next_cursorElement.toString());
                                int previous_cursor = Integer.valueOf(previous_cursorElement.toString());

                                Log.e("-----user3333------", element.toString());

                                JsonArray jsonArray = element.getAsJsonArray();
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    element = jsonArray.get(i);
                                    User user = gson.fromJson(element, User.class);
                                    userList.add(user);
                                }

                                // 获取数据完毕，发送消息
                                Message message = Message.obtain();
                                message.what = App.MESSAGE_FRIENDS_USERS;
                                message.arg1 = next_cursor;
                                message.arg2 = previous_cursor;
                                message.obj = userList;
                                Log.e("message", String.valueOf(userList.size()));
                                handler.sendMessage(message);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("error", error.toString());
                            }
                        })
        );
    }

    @Override
    public void addFavorites(final Handler handler, String statusIdstr) {
        getUser();
        // 定义用户资料请求
        queue.add(new StringRequest(
                        Request.Method.POST,
                        "https://api.weibo.com/2/favorites/create.json?access_token=" + access_token + "&id=" + statusIdstr,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.e("-------------", user.toString());
                                Message message = Message.obtain();
                                message.what = App.MESSAGE_ADD_FAVORITES;
                                handler.sendMessage(message);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("error", error.toString());
                            }
                        })
        );
    }
}
