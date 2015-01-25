package com.xxx.dto;

import java.io.Serializable;

public class FinalApiResponse<T>
  implements Serializable
{
  private static final long serialVersionUID = -3747150192343646103L;
  private String apiVersion;
  private String functionCode;
  private Boolean isSuccess;
  private String code;
  private String msg;
  private Integer count;
  private Integer totalPages;
  private Integer curPage;
  private Integer pageSize;
  private T results;

  public String getApiVersion()
  {
    return this.apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public String getFunctionCode() {
    return this.functionCode;
  }

  public void setFunctionCode(String functionCode) {
    this.functionCode = functionCode;
  }

  public Boolean getIsSuccess() {
    return this.isSuccess;
  }

  public void setIsSuccess(Boolean isSuccess) {
    this.isSuccess = isSuccess;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return this.msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Integer getCount() {
    return this.count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Integer getTotalPages() {
    return this.totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public Integer getCurPage() {
    return this.curPage;
  }

  public void setCurPage(Integer curPage) {
    this.curPage = curPage;
  }

  public Integer getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public T getResults() {
    return this.results;
  }

  public void setResults(T results) {
    this.results = results;
  }
}