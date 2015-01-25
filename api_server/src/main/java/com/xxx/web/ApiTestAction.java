package com.xxx.web;

import com.xxx.enums.ApiMsgEnum;
import com.xxx.util.HttpClientUtil;
import com.xxx.util.JsonUtil;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/apitest"})
public class ApiTestAction
{
  @RequestMapping({"", "/"})
  public String apitest(HttpServletRequest req, ModelMap modelMap)
  {
    modelMap.addAttribute("functionCodeCatalogMap", SpringBeanProxy.getFunctionCodeCatalogMap());
    req.setAttribute("apiMsgMap", ApiMsgEnum.getAll());
    return "/apitest";
  }

  @RequestMapping({"/getFunctionListByCatalog"})
  public void getFunctionListByCatalog(@RequestParam String catalog, HttpServletResponse rsp)
  {
    Map paramMap = SpringBeanProxy.getFunctionListByCatalog(catalog);
    String json = JsonUtil.objectToJson(paramMap);
    WebHelper.outputJson(json, rsp);
  }

  @RequestMapping({"/getParamsByFunctionCode"})
  public void getParamsByFunctionCode(@RequestParam String functionCode, HttpServletResponse rsp)
  {
    Map paramMap = SpringBeanProxy.getParamsByFunctionCode(functionCode);
    String json = JsonUtil.objectToJson(paramMap);
    WebHelper.outputJson(json, rsp);
  }

  @RequestMapping({"/doApiTest"})
  public String doApiTest(HttpServletRequest req, HttpServletResponse rsp) {
    Map paramsMap = new HashMap();
    String[] paramNameArr = req.getParameterValues("paramName");
    String[] paramValueArr = req.getParameterValues("paramValue");
    if ((paramNameArr != null) && (paramNameArr.length > 0)) {
      int n = 0;
      for (String paramName : paramNameArr) {
        if ((paramName != null) && (!"".equals(paramName.trim()))) {
          paramsMap.put(paramName, paramValueArr[n]);
        }
        n++;
      }
    }
    paramsMap.put("functionCode", req.getParameter("functionCode"));
    paramsMap.put("apiKey", req.getParameter("apiKey"));
    paramsMap.put("token", null);

    Map dataMap = new HashMap();
    dataMap.put("data", JsonUtil.objectToJson(paramsMap, Map.class));
    HttpClientUtil clientUtil = new HttpClientUtil();
    String defaultApiServer = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/";
    if (req.getServerPort() == 80) {
      defaultApiServer = req.getScheme() + "://" + req.getServerName() + req.getContextPath() + "/";
    }
    String retJson = clientUtil.doHttpPost(defaultApiServer, dataMap);
    return WebHelper.outputJson(retJson, rsp);
  }
}