package com.xxx.dto;

import com.xxx.enums.ContentTypeEnum;
import com.xxx.util.RegexUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class ApiRequest extends HashMap<String, Object>
  implements Serializable
{
  private static final long serialVersionUID = 3518461079812863846L;
  private String apiVersion;
  private String apiKey;
  private String accessToken;
  private String functionCode;
  private String requestIp;
  private Date requestTime;
  private String apiSource;
  private ContentTypeEnum contentTypeEnum;

  public ApiRequest()
  {
  }

  public ApiRequest(ApiRequest oldApiReq)
  {
    this.apiVersion = oldApiReq.getApiVersion();
    this.apiKey = oldApiReq.getApiKey();
    this.accessToken = oldApiReq.getAccessToken();
    this.functionCode = oldApiReq.getFunctionCode();
    this.requestIp = oldApiReq.getRequestIp();
    this.requestTime = oldApiReq.getRequestTime();

    this.apiSource = oldApiReq.getApiSource();
    this.contentTypeEnum = oldApiReq.getContentTypeEnum();
  }

  public Integer getInt(String key) {
    Object value = get(key);
    if ((value != null) && (!"".equals(value))) {
      return Integer.valueOf(value.toString());
    }
    return null;
  }

  public Double getDouble(String key) {
    Object value = get(key);
    if ((value != null) && (!"".equals(value))) {
      return Double.valueOf(value.toString());
    }
    return null;
  }

  public String getString(String key) {
    Object value = get(key);
    if ((value != null) && (!"".equals(value))) {
      return value.toString();
    }
    return null;
  }

  public ContentTypeEnum getContentTypeEnum() {
    return this.contentTypeEnum;
  }

  public void setContentTypeEnum(ContentTypeEnum contentTypeEnum) {
    this.contentTypeEnum = contentTypeEnum;
  }

  public String getApiSource() {
    return this.apiSource;
  }

  public void setApiSource(String apiSource) {
    this.apiSource = apiSource;
  }

  public String getApiVersion()
  {
    return this.apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public String getApiKey() {
    return this.apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getFunctionCode() {
    return this.functionCode;
  }

  public void setFunctionCode(String functionCode) {
    this.functionCode = functionCode;
  }

  public String getAccessToken() {
    return this.accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRequestIp() {
    return this.requestIp;
  }

  public void setRequestIp(String requestIp) {
    this.requestIp = requestIp;
  }

  public Date getRequestTime() {
    return this.requestTime;
  }

  public void setRequestTime(Date requestTime) {
    this.requestTime = requestTime;
  }

  public Object put(String key, Object value)
  {
    Object obj = null;
    if ((value != null) && ((value instanceof String))) {
      String temp = (String)value;
      if ("".equals(temp))
        obj = super.put(key, null);
      else if (RegexUtil.isWholeNumber(temp))
        try {
          obj = super.put(key, Integer.valueOf(temp));
        } catch (Exception e) {
          obj = super.put(key, temp);
        }
      else
        obj = super.put(key, value);
    }
    else {
      obj = super.put(key, value);
    }
    return obj;
  }
}