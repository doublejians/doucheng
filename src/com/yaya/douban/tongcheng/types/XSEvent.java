package com.yaya.douban.tongcheng.types;

public class XSEvent {
  private String id;
  private String alt;
  private String title;
  private String desc;
  private String[] tags;
  private String created;
  private String begin_time;
  private String end_time;
  private String related_url;
  private String shuo_topic;// 对应广播的#主题#
  private boolean cascade_invite;// 用户能不能邀请友邻加入
  private String group_id;// 关联小组的id
  private String album_id;// 对应相册的id
  private int participant_count;// 参加人数
  private int photo_count;// 照片数
  private int liked_count;// 喜欢数
  private int recs_count;// 推荐数
  private String icon;
  private String thumb;
  private String cover;
  private String image;
  private User owner;
  private boolean liked;// 当前用户是否喜欢，参加
  private boolean joined;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAlt() {
    return alt;
  }

  public void setAlt(String alt) {
    this.alt = alt;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String[] getTags() {
    return tags;
  }

  public void setTags(String[] tags) {
    this.tags = tags;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getBegin_time() {
    return begin_time;
  }

  public void setBegin_time(String begin_time) {
    this.begin_time = begin_time;
  }

  public String getEnd_time() {
    return end_time;
  }

  public void setEnd_time(String end_time) {
    this.end_time = end_time;
  }

  public String getRelated_url() {
    return related_url;
  }

  public void setRelated_url(String related_url) {
    this.related_url = related_url;
  }

  public String getShuo_topic() {
    return shuo_topic;
  }

  public void setShuo_topic(String shuo_topic) {
    this.shuo_topic = shuo_topic;
  }

  public boolean isCascade_invite() {
    return cascade_invite;
  }

  public void setCascade_invite(boolean cascade_invite) {
    this.cascade_invite = cascade_invite;
  }

  public String getGroup_id() {
    return group_id;
  }

  public void setGroup_id(String group_id) {
    this.group_id = group_id;
  }

  public String getAlbum_id() {
    return album_id;
  }

  public void setAlbum_id(String album_id) {
    this.album_id = album_id;
  }

  public int getParticipant_count() {
    return participant_count;
  }

  public void setParticipant_count(int participant_count) {
    this.participant_count = participant_count;
  }

  public int getPhoto_count() {
    return photo_count;
  }

  public void setPhoto_count(int photo_count) {
    this.photo_count = photo_count;
  }

  public int getLiked_count() {
    return liked_count;
  }

  public void setLiked_count(int liked_count) {
    this.liked_count = liked_count;
  }

  public int getRecs_count() {
    return recs_count;
  }

  public void setRecs_count(int recs_count) {
    this.recs_count = recs_count;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getThumb() {
    return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public boolean isLiked() {
    return liked;
  }

  public void setLiked(boolean liked) {
    this.liked = liked;
  }

  public boolean isJoined() {
    return joined;
  }

  public void setJoined(boolean joined) {
    this.joined = joined;
  }

  @Override
  public String toString() {
    return "id:" + id + "  title:" + title;
  }

}
