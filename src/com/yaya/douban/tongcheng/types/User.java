package com.yaya.douban.tongcheng.types;

public class User {
  private String name;
  private boolean is_banned;
  private boolean is_suicide;
  private String avatar;
  private String uid;
  private String alt;
  private String type;
  private String id;
  private String large_avatar;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

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

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getAlt() {
    return alt;
  }

  public void setAlt(String alt) {
    this.alt = alt;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLarge_avatar() {
    return large_avatar;
  }

  public void setLarge_avatar(String large_avatar) {
    this.large_avatar = large_avatar;
  }

}
