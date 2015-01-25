package com.xxx.service.impl;

import com.xxx.dto.ApiRequest;
import org.springframework.util.StringUtils;

public abstract class SuperServiceImpl
{
  protected void setDefaultPageSize(ApiRequest apiReq)
  {
    if (StringUtils.isEmpty(apiReq.get("page_size")))
      apiReq.put("page_size", Integer.valueOf(10));
  }
}