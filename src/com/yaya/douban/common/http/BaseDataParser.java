package com.yaya.douban.common.http;

public abstract class BaseDataParser {

  public abstract BaseDataResponse parser(String json);
}
