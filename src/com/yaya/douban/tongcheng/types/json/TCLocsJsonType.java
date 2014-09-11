package com.yaya.douban.tongcheng.types.json;

import com.yaya.douban.tongcheng.types.Loc;

public class TCLocsJsonType extends BaseCountableType {
  private Loc[] locs;

  public Loc[] getLocs() {
    return locs;
  }

  public void setLocs(Loc[] locs) {
    this.locs = locs;
  }

}
