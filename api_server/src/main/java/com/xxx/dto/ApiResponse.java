package com.xxx.dto;

import com.xxx.enums.ApiMsgEnum;
import java.io.Serializable;

public class ApiResponse<T>
  implements Serializable
{
  private static final long serialVersionUID = -1390658031289975172L;
  private ApiMsgEnum returnEnum;
  private Integer count;
  private T results;

  public ApiResponse(ApiMsgEnum _returnEnum)
  {
    this.returnEnum = _returnEnum;
  }

  public ApiResponse(ApiMsgEnum _returnEnum, Integer _count, T _results) {
    this.returnEnum = _returnEnum;
    this.count = _count;
    this.results = _results;
  }

  public ApiMsgEnum getReturnEnum() {
    return this.returnEnum;
  }

  public void setReturnEnum(ApiMsgEnum returnEnum) {
    this.returnEnum = returnEnum;
  }

  public Integer getCount() {
    return this.count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public T getResults() {
    return this.results;
  }

  public void setResults(T results) {
    this.results = results;
  }
}