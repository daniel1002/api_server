package com.xxx.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum ApiMsgEnum
{
  SUCCESS(
    Boolean.TRUE, "0000", "成功"), 
  FAIL(
    Boolean.FALSE, "0001", "失败"), 
  TOKEN_ERROR(
    Boolean.FALSE, "0002", "签名错误"), 
  INVOKE_ERROR(
    Boolean.FALSE, "0003", "调用错误"), 
  UNKNOWN_ERROR(
    Boolean.FALSE, "0004", "未知错误"), 
  POST_ONLY(
    Boolean.FALSE, "0005", "只支持post请求"), 
  MissParameterException(
    Boolean.FALSE, "0006", "缺少参数"), 
  DataFormatException(
    Boolean.FALSE, "0007", "数据格式错误"), 
  ForbiddenException(
    Boolean.FALSE, "0008", "提交数据有误"), 
  BadRequestException(
    Boolean.FALSE, "0009", "请求错误"), 

  UserNameIsNullException(
    Boolean.FALSE, "1000", "用户名不能为空"), 

  NotFondPackageException(
    Boolean.FALSE, "2000", "未找到你想要的游戏或软件包"), 

  GiftbagStatusException(
    Boolean.FALSE, "3000", "礼包不可领取"), 

  LinkExistedException(
    Boolean.FALSE, "4000", "有链接存在");

  public Boolean isSuccess;
  public String code;
  public String msg;

  private ApiMsgEnum(Boolean isSuccess, String code, String msg) { this.isSuccess = isSuccess;
    this.code = code;
    this.msg = msg; }

  public Boolean getIsSuccess()
  {
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

  public static Map<String, String> getAll() {
    Map retMap = new LinkedHashMap();
    ApiMsgEnum[] enumArr = values();
    for (ApiMsgEnum aEnum : enumArr) {
      retMap.put(aEnum.getCode(), aEnum.getMsg());
    }
    return retMap;
  }

  public static ApiMsgEnum getByCode(String code) {
    ApiMsgEnum[] enumArr = values();
    for (ApiMsgEnum aEnum : enumArr) {
      if (aEnum.getCode().equals(code)) {
        return aEnum;
      }
    }
    return null;
  }
}