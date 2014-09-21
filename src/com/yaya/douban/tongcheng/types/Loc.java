package com.yaya.douban.tongcheng.types;

/**
 * 对应Loc
 * 
 * @author doublejian
 * 
 */
public class Loc {
  private String parent="";
  private String habitable="";
  private String id="";
  private String name="";
  private String uid="";

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public String getHabitable() {
    return habitable;
  }

  public void setHabitable(String habitable) {
    this.habitable = habitable;
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

  @Override
  public String toString() {
    return "City [parent=" + parent + ", habitable=" + habitable + ", id=" + id
        + ", name=" + name + ", uid=" + uid + "]";
  }

}
