package com.yaya.douban.tongcheng.types;

public class User {

  private boolean is_banned;
  private boolean is_suicide;
  private String large_avatar;
  protected String avatar;// 头像小图
  protected String alt;
  protected String id;
  protected String name;
  protected String uid;
  protected String type; // user,site
  protected String relation;// 和当前登录用户的关系，friend或contact
  protected String created;// 注册时间
  protected String loc_id; // 城市id
  protected String loc_name; // 所在地全称
  protected String desc;

  public boolean isIs_banned() {
    return is_banned;
  }

  public void setIs_banned(boolean is_banned) {
    this.is_banned = is_banned;
  }

  public boolean isIs_suicide() {
    return is_suicide;
  }

  public void setIs_suicide(boolean is_suicide) {
    this.is_suicide = is_suicide;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getLarge_avatar() {
    return large_avatar;
  }

  public void setLarge_avatar(String large_avatar) {
    this.large_avatar = large_avatar;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getAlt() {
    return alt;
  }

  public void setAlt(String alt) {
    this.alt = alt;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

}
