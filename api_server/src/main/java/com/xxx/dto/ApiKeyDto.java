package com.xxx.dto;

import java.io.Serializable;

public class ApiKeyDto
  implements Serializable
{
  private static final long serialVersionUID = -8342508427349003639L;
  private String apiKey;
  private String apiSecret;
  private Integer siteId;
  private String apiSource;

  public ApiKeyDto(String apiKey, String apiSecret, Integer siteId, String apiSource)
  {
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
    this.siteId = siteId;
    this.apiSource = apiSource;
  }

  public String getApiKey() {
    return this.apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getApiSecret() {
    return this.apiSecret;
  }

  public void setApiSecret(String apiSecret) {
    this.apiSecret = apiSecret;
  }

  public Integer getSiteId() {
    return this.siteId;
  }

  public void setSiteId(Integer siteId) {
    this.siteId = siteId;
  }

  public String getApiSource() {
    return this.apiSource;
  }

  public void setApiSource(String apiSource) {
    this.apiSource = apiSource;
  }
}