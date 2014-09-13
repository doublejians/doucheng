package com.yaya.douban.common.http;

public abstract class BaseDataParser {
  /**
   * 解析json字串
   * 
   * @param json
   *          服务器返回的数据
   * @return
   */
  public abstract BaseDataResponse parser(String json);
}
