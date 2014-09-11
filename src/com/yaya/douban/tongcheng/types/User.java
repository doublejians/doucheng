package com.yaya.douban.tongcheng.types;

public class User extends SimpleUser {

  private boolean is_banned;
  private boolean is_suicide;
  private String type;
  private String large_avatar;

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

}
