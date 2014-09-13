package com.yaya.douban.tongcheng.types.json;

import com.yaya.douban.tongcheng.types.Album;
import com.yaya.douban.tongcheng.types.Photo;

public class AlbumPhotoJsonType extends BaseCountableType {
  private Album album;
  private Photo[] photos;

  public Album getAlbum() {
    return album;
  }

  public void setAlbum(Album album) {
    this.album = album;
  }

  public Photo[] getPhotos() {
    return photos;
  }

  public void setPhotos(Photo[] photos) {
    this.photos = photos;
  }

}
