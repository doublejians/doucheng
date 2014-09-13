package com.yaya.douban.tongcheng.types;

public class Photo {
  private User author;
  private String album_id;
  private String image;
  private AlbumSize sizes;
  private int recs_count;
  private String alt;
  private String album_title;
  private String id;
  private String icon;
  private String prev_photo;
  private int position;
  private String thumb;
  private String created;
  private String privacy;
  private String cover;
  private String large;
  private int liked_count;
  private int comments_count;
  private String desc;
  private String next_photo;

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public String getAlbum_id() {
    return album_id;
  }

  public void setAlbum_id(String album_id) {
    this.album_id = album_id;
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

  public String getAlt() {
    return alt;
  }

  public void setAlt(String alt) {
    this.alt = alt;
  }

  public String getAlbum_title() {
    return album_title;
  }

  public void setAlbum_title(String album_title) {
    this.album_title = album_title;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getPrev_photo() {
    return prev_photo;
  }

  public void setPrev_photo(String prev_photo) {
    this.prev_photo = prev_photo;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public String getThumb() {
    return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getPrivacy() {
    return privacy;
  }

  public void setPrivacy(String privacy) {
    this.privacy = privacy;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public String getLarge() {
    return large;
  }

  public void setLarge(String large) {
    this.large = large;
  }

  public int getLiked_count() {
    return liked_count;
  }

  public void setLiked_count(int liked_count) {
    this.liked_count = liked_count;
  }

  public int getComments_count() {
    return comments_count;
  }

  public void setComments_count(int comments_count) {
    this.comments_count = comments_count;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getNext_photo() {
    return next_photo;
  }

  public void setNext_photo(String next_photo) {
    this.next_photo = next_photo;
  }

}
