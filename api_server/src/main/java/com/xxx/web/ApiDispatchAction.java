package com.xxx.web;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xxx.dto.ApiKeyDto;
import com.xxx.dto.ApiRequest;
import com.xxx.dto.ApiResponse;
import com.xxx.dto.FinalApiResponse;
import com.xxx.enums.ApiMethodEnum;
import com.xxx.enums.ApiMsgEnum;
import com.xxx.util.JsonUtil;
import com.xxx.util.RegexUtil;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApiDispatchAction
{
  private static final Logger log = Logger.getLogger(ApiDispatchAction.class);

  @Value("${api.version}")
  private String apiVersion;
  private static final Map<String, ApiKeyDto> apiKeyMap = new HashMap();

  static {
    apiKeyMap.put("android_client", new ApiKeyDto("android_client", "5b2e1c483b4cf67c87399e1de4554cf9", Integer.valueOf(1), "android_client"));
    apiKeyMap.put("android_web", new ApiKeyDto("android_web", "c111801f8af3dad7f209f22a045175ee", Integer.valueOf(1), "android_web"));
    apiKeyMap.put("android_wap", new ApiKeyDto("android_wap", "e6b5de7672c248f7775d84031ab0aeae", Integer.valueOf(1), "android_wap"));
    apiKeyMap.put("android_backend", new ApiKeyDto("android_backend", "6c1e1c12d70fc08c823d90e791a46e0c", Integer.valueOf(1), "android_backend"));

    apiKeyMap.put("ios_client", new ApiKeyDto("ios_client", "83a64e7068237b02ab8d9d8b5c7d8409", Integer.valueOf(2), "ios_client"));
    apiKeyMap.put("ios_web", new ApiKeyDto("ios_web", "1397eb666b0d7fe6653cb57b872639e3", Integer.valueOf(2), "ios_web"));
    apiKeyMap.put("ios_wap", new ApiKeyDto("ios_wap", "1792f4a7f43a070bc4594f29076ce10d", Integer.valueOf(2), "ios_wap"));
    apiKeyMap.put("ios_backend", new ApiKeyDto("ios_backend", "4e36135ea371fb62522de7c2275140ff", Integer.valueOf(2), "ios_backend"));
  }

  @RequestMapping({"/"})
  public String entry(HttpServletRequest req, HttpServletResponse rsp)
  {
    ApiRequest apiReq = buildApiRequest(req);
    String json = processApiRequest(apiReq);
    return WebHelper.outputJson(json, rsp);
  }

  private String processApiRequest(ApiRequest apiReq)
  {
    ApiResponse apiRsp = invokeApi(apiReq);
    FinalApiResponse finalApiRsp = buildFinalApiResponse(apiReq, apiRsp);
    return JsonUtil.objectToJson(finalApiRsp);
  }

  private FinalApiResponse buildFinalApiResponse(ApiRequest apiReq, ApiResponse apiRsp)
  {
    FinalApiResponse finalApiRsp = new FinalApiResponse();
    finalApiRsp.setApiVersion(this.apiVersion);
    finalApiRsp.setFunctionCode(apiReq == null ? "unknown" : apiReq.getFunctionCode());
    finalApiRsp.setIsSuccess(apiRsp.getReturnEnum().getIsSuccess());
    finalApiRsp.setCode(apiRsp.getReturnEnum().getCode());
    finalApiRsp.setMsg(apiRsp.getReturnEnum().getMsg());
    finalApiRsp.setCount(apiRsp.getCount());
    finalApiRsp.setResults(apiRsp.getResults());
    if ((apiRsp.getCount() != null) && (apiRsp.getCount().intValue() > 0) && (!StringUtils.isEmpty(apiReq.get("page_size"))) && 
      (RegexUtil.isPositiveInteger(apiReq.get("page_size").toString()))) {
      int pageSize = Integer.parseInt(apiReq.get("page_size").toString());
      int page = 1;
      if ((!StringUtils.isEmpty(apiReq.get("page"))) && (RegexUtil.isPositiveInteger(apiReq.get("page").toString()))) {
        page = Integer.valueOf(apiReq.get("page").toString()).intValue();
      }
      int totalPages = apiRsp.getCount().intValue() / pageSize;
      if (apiRsp.getCount().intValue() % pageSize > 0) {
        totalPages++;
      }
      finalApiRsp.setTotalPages(Integer.valueOf(totalPages));
      finalApiRsp.setCurPage(Integer.valueOf(page));
      finalApiRsp.setPageSize(Integer.valueOf(pageSize));
    }
    return finalApiRsp;
  }

  private ApiResponse invokeApi(ApiRequest apiReq)
  {
    ApiResponse apiRsp = checkApiRequestParam(apiReq);
    if (apiRsp != null)
      return apiRsp;
    try
    {
      beforeInvokeApi(apiReq);
      String functionCode = apiReq.getFunctionCode();
      Object bean = SpringBeanProxy.getBeanByFunctionCode(functionCode);
      Method method = SpringBeanProxy.getMethodByFunctionCode(functionCode);
      Object rspObj = method.invoke(bean, new Object[] { apiReq });

      if ((rspObj instanceof ApiResponse)) {
        apiRsp = (ApiResponse)rspObj;
        afterInvokeApi(apiReq, apiRsp);
        return apiRsp;
      }
    } catch (Exception e) {
      log.error("logId:" + SuperDispatcherServlet.getLogIdByThreadId(Thread.currentThread().getId()) + ",invokeApi exception=", e);
      return new ApiResponse(ApiMsgEnum.INVOKE_ERROR);
    }
    return new ApiResponse(ApiMsgEnum.UNKNOWN_ERROR);
  }

  private ApiRequest buildApiRequest(HttpServletRequest req)
  {
    String data = req.getParameter("data");
    JsonElement dataJsonEle = (JsonElement)JsonUtil.jsonToObject(data, JsonElement.class);
    if ((dataJsonEle != null) && (dataJsonEle.isJsonObject())) {
      JsonObject paramJsonObj = dataJsonEle.getAsJsonObject();
      ApiRequest apiReq = new ApiRequest();
      for (Map.Entry paramEntry : paramJsonObj.entrySet()) {
        String paramName = (String)paramEntry.getKey();
        JsonElement paramValueEle = (JsonElement)paramEntry.getValue();
        String paramValue = null;
        if ((paramValueEle != null) && (!paramValueEle.isJsonNull()) && (paramValueEle.isJsonPrimitive())) {
          paramValue = paramValueEle.getAsString();
        }
        if ("functionCode".equals(paramName)) {
          apiReq.setFunctionCode(paramValue);
          ApiMethodEnum aEnum = ApiMethodEnum.getApiMethodEnum(paramValue);
          if (aEnum != null)
            apiReq.setContentTypeEnum(aEnum.getContentType());
        }
        else if ("apiKey".equals(paramName)) {
          apiReq.setApiKey(paramValue);
          ApiKeyDto keyDto = (ApiKeyDto)apiKeyMap.get(paramValue);
          if (keyDto != null)
            apiReq.setApiSource(keyDto.getApiSource());
        }
        else if ("token".equals(paramName)) {
          apiReq.setAccessToken(paramValue);
        } else if ("requestIp".equals(paramName)) {
          apiReq.setRequestIp(paramValue);
        }
        else if ((paramValueEle == null) || (paramValueEle.isJsonNull())) {
          apiReq.put(paramName, null);
        } else {
          apiReq.put(paramName, paramValue);
        }

        if (StringUtils.isEmpty(apiReq.getRequestIp())) {
          apiReq.setRequestIp(WebHelper.getRequestIp(req));
        }
      }
      return apiReq;
    }
    return null;
  }

  private ApiResponse checkApiRequestParam(ApiRequest apiReq)
  {
    if (apiReq == null) {
      return new ApiResponse(ApiMsgEnum.ForbiddenException);
    }
    String functionCode = apiReq.getFunctionCode();
    if (StringUtils.isEmpty(functionCode)) {
      return new ApiResponse(ApiMsgEnum.ForbiddenException);
    }
    String apiKey = apiReq.getApiKey();
    if (StringUtils.isEmpty(apiKey)) {
      return new ApiResponse(ApiMsgEnum.ForbiddenException);
    }

    return null;
  }

  private void beforeInvokeApi(ApiRequest apiReq)
  {
  }

  private void afterInvokeApi(ApiRequest apiReq, ApiResponse apiRsp)
  {
  }
}