package com.yaya.douban.tongcheng.types;

import org.codehaus.jackson.annotate.JsonProperty;

public class TCEvent {
  private boolean is_priv;
  private int participant_count;
  private String image;
  private String adapt_url;
  private String begin_time;
  private User owner;
  private String alt;
  private String geo;
  private String id;
  private String album;
  private String title;
  private int wisher_count;
  private String content;
  private String image_hlarge;
  private String end_time;
  private String image_lmobile;
  private String has_invited;
  private String can_invite;
  private String address;
  private String loc_name;
  private String loc_id;
  private String subcategory_name;
  private String category_name;
  private boolean has_ticket;

  public boolean isIs_priv() {
    return is_priv;
  }

  public void setIs_priv(boolean is_priv) {
    this.is_priv = is_priv;
  }

  public int getParticipant_count() {
    return participant_count;
  }

  public void setParticipant_count(int participant_count) {
    this.participant_count = participant_count;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getAdapt_url() {
    return adapt_url;
  }

  public void setAdapt_url(String adapt_url) {
    this.adapt_url = adapt_url;
  }

  public String getBegin_time() {
    return begin_time;
  }

  public void setBegin_time(String begin_time) {
    this.begin_time = begin_time;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public String getAlt() {
    return alt;
  }

  public void setAlt(String alt) {
    this.alt = alt;
  }

  public String getGeo() {
    return geo;
  }

  public void setGeo(String geo) {
    this.geo = geo;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getWisher_count() {
    return wisher_count;
  }

  public void setWisher_count(int wisher_count) {
    this.wisher_count = wisher_count;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getImage_hlarge() {
    return image_hlarge;
  }

  @JsonProperty("image-hlarge")
  public void setImage_hlarge(String image_hlarge) {
    this.image_hlarge = image_hlarge;
  }

  public String getEnd_time() {
    return end_time;
  }

  public void setEnd_time(String end_time) {
    this.end_time = end_time;
  }

  public String getImage_lmobile() {
    return image_lmobile;
  }

  @JsonProperty("image-lmobile")
  public void setImage_lmobile(String image_lmobile) {
    this.image_lmobile = image_lmobile;
  }

  public String getHas_invited() {
    return has_invited;
  }

  public void setHas_invited(String has_invited) {
    this.has_invited = has_invited;
  }

  public String getCan_invite() {
    return can_invite;
  }

  public void setCan_invite(String can_invite) {
    this.can_invite = can_invite;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getLoc_name() {
    return loc_name;
  }

  public void setLoc_name(String loc_name) {
    this.loc_name = loc_name;
  }

  public String getLoc_id() {
    return loc_id;
  }

  public void setLoc_id(String loc_id) {
    this.loc_id = loc_id;
  }

  public String getSubcategory_name() {
    return subcategory_name;
  }

  public void setSubcategory_name(String subcategory_name) {
    this.subcategory_name = subcategory_name;
  }

  public String getCategory_name() {
    return category_name;
  }

  public void setCategory_name(String category_name) {
    this.category_name = category_name;
  }

  public boolean isHas_ticket() {
    return has_ticket;
  }

  public void setHas_ticket(boolean has_ticket) {
    this.has_ticket = has_ticket;
  }

}
