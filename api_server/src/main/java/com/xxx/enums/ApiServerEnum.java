package com.xxx.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum ApiServerEnum
{
  user(
    "用户体系api"), 
  content(
    "内容体系api"), 
  activity(
    "活动体系api"), 
  commonservice(
    "公用服务api");

  private String descript;

  private ApiServerEnum(String _descript) {
    this.descript = _descript;
  }

  public String getDescript() {
    return this.descript;
  }

  public static Map<String, String> getApiServerEnumMap() {
    Map retMap = new LinkedHashMap();
    ApiServerEnum[] enumArr = values();
    for (ApiServerEnum aEnum : enumArr) {
      retMap.put(aEnum.name(), aEnum.getDescript());
    }
    return retMap;
  }

  public static ApiServerEnum getApiServerEnum(String name) {
    try {
      return valueOf(name); } catch (Exception e) {
    }
    return null;
  }
}