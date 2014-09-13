package com.yaya.douban.common.http;

/**
 * 返回结果类，封装了用户想要的数据与请求返回状态
 * 
 * @author doublejian
 * 
 */
public class BaseDataResponse {
  private int resultCode; // 返回码 200是成功
  private int total; // 总共多少条记录

  public int getResultCode() {
    return resultCode;
  }

  public void setResultCode(int resultCode) {
    this.resultCode = resultCode;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

}
