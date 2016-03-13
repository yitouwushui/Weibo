package com.yitouwushui.weibo.entity;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 微博
 */
public class Status extends SugarRecord implements Serializable {
    private String created_at;
    private String mid;
    private String idstr;
    private String text;
    private String source;
    private boolean favorited;
    private boolean truncated;
    private String in_reply_to_status_id;
    private String in_reply_to_user_id;
    private String in_reply_to_screen_name;
    private String thumbnail_pic;
    private String bmiddle_pic;
    private String original_pic;
    private User user;
    private Status retweeted_status;
    private int reposts_count;
    private int comments_count;
    private int attitudes_count;
    private long userIdStatus;
    private int type;
    private ArrayList<Pic_urls> pic_urls;


    public ArrayList<Pic_urls> getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(ArrayList<Pic_urls> pic_urls) {
        this.pic_urls = pic_urls;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAttitudes_count() {
        return attitudes_count;
    }

    public void setAttitudes_count(int attitudes_count) {
        this.attitudes_count = attitudes_count;
    }

    public String getBmiddle_pic() {
        return bmiddle_pic;
    }

    public void setBmiddle_pic(String bmiddle_pic) {
        this.bmiddle_pic = bmiddle_pic;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getIn_reply_to_screen_name() {
        return in_reply_to_screen_name;
    }

    public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
        this.in_reply_to_screen_name = in_reply_to_screen_name;
    }

    public String getIn_reply_to_status_id() {
        return in_reply_to_status_id;
    }

    public void setIn_reply_to_status_id(String in_reply_to_status_id) {
        this.in_reply_to_status_id = in_reply_to_status_id;
    }

    public String getIn_reply_to_user_id() {
        return in_reply_to_user_id;
    }

    public void setIn_reply_to_user_id(String in_reply_to_user_id) {
        this.in_reply_to_user_id = in_reply_to_user_id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public long getUserIdStatus() {
        return userIdStatus;
    }

    public void setUserIdStatus(long userIdStatus) {
        this.userIdStatus = userIdStatus;
    }

    public String getOriginal_pic() {
        return original_pic;
    }

    public void setOriginal_pic(String original_pic) {
        this.original_pic = original_pic;
    }

    public int getReposts_count() {
        return reposts_count;
    }

    public void setReposts_count(int reposts_count) {
        this.reposts_count = reposts_count;
    }

    public Status getRetweeted_status() {
        return retweeted_status;
    }

    public void setRetweeted_status(Status retweeted_status) {
        this.retweeted_status = retweeted_status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public void setThumbnail_pic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status() {
    }

    @Override
    public String toString() {
        return "Status{" +
                "attitudes_count=" + attitudes_count +
                ", created_at='" + created_at + '\'' +
                ", mid='" + mid + '\'' +
                ", idstr='" + idstr + '\'' +
                ", text='" + text + '\'' +
                ", source='" + source + '\'' +
                ", favorited=" + favorited +
                ", truncated=" + truncated +
                ", in_reply_to_status_id='" + in_reply_to_status_id + '\'' +
                ", in_reply_to_user_id='" + in_reply_to_user_id + '\'' +
                ", in_reply_to_screen_name='" + in_reply_to_screen_name + '\'' +
                ", thumbnail_pic='" + thumbnail_pic + '\'' +
                ", bmiddle_pic='" + bmiddle_pic + '\'' +
                ", original_pic='" + original_pic + '\'' +
                ", user=" + user +
                ", retweeted_status=" + retweeted_status +
                ", reposts_count=" + reposts_count +
                ", comments_count=" + comments_count +
                ", userIdStatus=" + userIdStatus +
                ", type=" + type +
                ", pic_urls=" + pic_urls +
                '}';
    }



//
//      public static class User extends SugarRecord {
//        private String idstr;
//        private String screen_name;
//        private String name;
//        private int province;
//        private int city;
//        private String location;
//        private String description;
//        private String url;
//        private String profile_image_url;
//        private String cover_image_phone;
//        private String profile_url;
//        private String domain;
//        private String weihao;
//        private String gender;
//        private int followers_count;
//        private int friends_count;
//        private int statuses_count;
//        private int favourites_count;
//        private String created_at;
//        private boolean following;
//        private boolean allow_all_act_msg;
//        private boolean geo_enabled;
//        private boolean verified;
//        private int verified_type;
//        private String remark;
//        private boolean allow_all_comment;
//        private String avatar_large;
//        private String avatar_hd;
//        private String verified_reason;
//        private boolean follow_me;
//        private int online_status;
//        private int bi_followers_count;
//        private String lang;
//
//        public boolean isAllow_all_act_msg() {
//            return allow_all_act_msg;
//        }
//
//        public void setAllow_all_act_msg(boolean allow_all_act_msg) {
//            this.allow_all_act_msg = allow_all_act_msg;
//        }
//
//        public boolean isAllow_all_comment() {
//            return allow_all_comment;
//        }
//
//        public void setAllow_all_comment(boolean allow_all_comment) {
//            this.allow_all_comment = allow_all_comment;
//        }
//
//        public String getAvatar_hd() {
//            return avatar_hd;
//        }
//
//        public void setAvatar_hd(String avatar_hd) {
//            this.avatar_hd = avatar_hd;
//        }
//
//        public String getAvatar_large() {
//            return avatar_large;
//        }
//
//        public void setAvatar_large(String avatar_large) {
//            this.avatar_large = avatar_large;
//        }
//
//        public int getBi_followers_count() {
//            return bi_followers_count;
//        }
//
//        public void setBi_followers_count(int bi_followers_count) {
//            this.bi_followers_count = bi_followers_count;
//        }
//
//        public int getCity() {
//            return city;
//        }
//
//        public void setCity(int city) {
//            this.city = city;
//        }
//
//        public String getCover_image_phone() {
//            return cover_image_phone;
//        }
//
//        public void setCover_image_phone(String cover_image_phone) {
//            this.cover_image_phone = cover_image_phone;
//        }
//
//        public String getCreated_at() {
//            return created_at;
//        }
//
//        public void setCreated_at(String created_at) {
//            this.created_at = created_at;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }
//
//        public String getDomain() {
//            return domain;
//        }
//
//        public void setDomain(String domain) {
//            this.domain = domain;
//        }
//
//        public int getFavourites_count() {
//            return favourites_count;
//        }
//
//        public void setFavourites_count(int favourites_count) {
//            this.favourites_count = favourites_count;
//        }
//
//        public boolean isFollow_me() {
//            return follow_me;
//        }
//
//        public void setFollow_me(boolean follow_me) {
//            this.follow_me = follow_me;
//        }
//
//        public int getFollowers_count() {
//            return followers_count;
//        }
//
//        public void setFollowers_count(int followers_count) {
//            this.followers_count = followers_count;
//        }
//
//        public boolean isFollowing() {
//            return following;
//        }
//
//        public void setFollowing(boolean following) {
//            this.following = following;
//        }
//
//        public int getFriends_count() {
//            return friends_count;
//        }
//
//        public void setFriends_count(int friends_count) {
//            this.friends_count = friends_count;
//        }
//
//        public String getGender() {
//            return gender;
//        }
//
//        public void setGender(String gender) {
//            this.gender = gender;
//        }
//
//        public boolean isGeo_enabled() {
//            return geo_enabled;
//        }
//
//        public void setGeo_enabled(boolean geo_enabled) {
//            this.geo_enabled = geo_enabled;
//        }
//
//        public String getIdstr() {
//            return idstr;
//        }
//
//        public void setIdstr(String idstr) {
//            this.idstr = idstr;
//        }
//
//        public String getLang() {
//            return lang;
//        }
//
//        public void setLang(String lang) {
//            this.lang = lang;
//        }
//
//        public String getLocation() {
//            return location;
//        }
//
//        public void setLocation(String location) {
//            this.location = location;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public int getOnline_status() {
//            return online_status;
//        }
//
//        public void setOnline_status(int online_status) {
//            this.online_status = online_status;
//        }
//
//        public String getProfile_image_url() {
//            return profile_image_url;
//        }
//
//        public void setProfile_image_url(String profile_image_url) {
//            this.profile_image_url = profile_image_url;
//        }
//
//        public String getProfile_url() {
//            return profile_url;
//        }
//
//        public void setProfile_url(String profile_url) {
//            this.profile_url = profile_url;
//        }
//
//        public int getProvince() {
//            return province;
//        }
//
//        public void setProvince(int province) {
//            this.province = province;
//        }
//
//        public String getRemark() {
//            return remark;
//        }
//
//        public void setRemark(String remark) {
//            this.remark = remark;
//        }
//
//        public String getScreen_name() {
//            return screen_name;
//        }
//
//        public void setScreen_name(String screen_name) {
//            this.screen_name = screen_name;
//        }
//
//        public int getStatuses_count() {
//            return statuses_count;
//        }
//
//        public void setStatuses_count(int statuses_count) {
//            this.statuses_count = statuses_count;
//        }
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//
//        public boolean isVerified() {
//            return verified;
//        }
//
//        public void setVerified(boolean verified) {
//            this.verified = verified;
//        }
//
//        public String getVerified_reason() {
//            return verified_reason;
//        }
//
//        public void setVerified_reason(String verified_reason) {
//            this.verified_reason = verified_reason;
//        }
//
//        public int getVerified_type() {
//            return verified_type;
//        }
//
//        public void setVerified_type(int verified_type) {
//            this.verified_type = verified_type;
//        }
//
//        public String getWeihao() {
//            return weihao;
//        }
//
//        public void setWeihao(String weihao) {
//            this.weihao = weihao;
//        }
//
//        @Override
//        public String toString() {
//            return "User{" +
//                    "allow_all_act_msg=" + allow_all_act_msg +
//                    ", idstr='" + idstr + '\'' +
//                    ", screen_name='" + screen_name + '\'' +
//                    ", name='" + name + '\'' +
//                    ", province=" + province +
//                    ", city=" + city +
//                    ", location='" + location + '\'' +
//                    ", description='" + description + '\'' +
//                    ", url='" + url + '\'' +
//                    ", profile_image_url='" + profile_image_url + '\'' +
//                    ", cover_image_phone='" + cover_image_phone + '\'' +
//                    ", profile_url='" + profile_url + '\'' +
//                    ", domain='" + domain + '\'' +
//                    ", weihao='" + weihao + '\'' +
//                    ", gender='" + gender + '\'' +
//                    ", followers_count=" + followers_count +
//                    ", friends_count=" + friends_count +
//                    ", statuses_count=" + statuses_count +
//                    ", favourites_count=" + favourites_count +
//                    ", created_at='" + created_at + '\'' +
//                    ", following=" + following +
//                    ", geo_enabled=" + geo_enabled +
//                    ", verified=" + verified +
//                    ", verified_type=" + verified_type +
//                    ", remark='" + remark + '\'' +
//                    ", allow_all_comment=" + allow_all_comment +
//                    ", avatar_large='" + avatar_large + '\'' +
//                    ", avatar_hd='" + avatar_hd + '\'' +
//                    ", verified_reason='" + verified_reason + '\'' +
//                    ", follow_me=" + follow_me +
//                    ", online_status=" + online_status +
//                    ", bi_followers_count=" + bi_followers_count +
//                    ", lang='" + lang + '\'' +
//                    '}';
//        }
//    }

}
