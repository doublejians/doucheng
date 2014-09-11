package com.yaya.douban.tongcheng.types;

public class Album {

  private boolean reply_limit;
  private User author;
  private String icon;
  private String image;
  private AlbumSize sizes;
  private int recs_count;
  private boolean need_watermark;
  private String alt;
  private String id;
  private int size;
  private String thumb;
  private String privacy;
  private String photo_order;
  private String title;
  private String cover;
  private String created;
  private String updated;
  private int liked_count;
  private String desc;

  public boolean isReply_limit() {
    return reply_limit;
  }

  public void setReply_limit(boolean reply_limit) {
    this.reply_limit = reply_limit;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public AlbumSize getSizes() {
    return sizes;
  }

  public void setSizes(AlbumSize sizes) {
    this.sizes = sizes;
  }

  public int getRecs_count() {
    return recs_count;
  }

  public void setRecs_count(int recs_count) {
    this.recs_count = recs_count;
  }

  public boolean isNeed_watermark() {
    return need_watermark;
  }

  public void setNeed_watermark(boolean need_watermark) {
    this.need_watermark = need_watermark;
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

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getThumb() {
    return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  public String getPrivacy() {
    return privacy;
  }

  public void setPrivacy(String privacy) {
    this.privacy = privacy;
  }

  public String getPhoto_order() {
    return photo_order;
  }

  public void setPhoto_order(String photo_order) {
    this.photo_order = photo_order;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getUpdated() {
    return updated;
  }

  public void setUpdated(String updated) {
    this.updated = updated;
  }

  public int getLiked_count() {
    return liked_count;
  }

  public void setLiked_count(int liked_count) {
    this.liked_count = liked_count;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

}
