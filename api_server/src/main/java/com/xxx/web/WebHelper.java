package com.xxx.web;

import com.xxx.util.JsonUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.util.WebUtils;

public class WebHelper
{
  protected static final String MSG = "msg";
  protected static final String IS_SUCCESS = "isSuccess";

  public static void sendRedirect(HttpServletResponse response, String url)
    throws IOException
  {
    response.sendRedirect(response.encodeRedirectURL(url));
  }

  public static String getCurrRequestUrl(HttpServletRequest request) throws UnsupportedEncodingException {
    StringBuffer currRequestUrl = request.getRequestURL();
    Enumeration parameterNames = request.getParameterNames();
    boolean flag = true;
    while (parameterNames.hasMoreElements()) {
      String paramName = (String)parameterNames.nextElement();
      String value = request.getParameter(paramName);
      if (flag) {
        currRequestUrl.append("?" + paramName + "=" + URLEncoder.encode(value, "UTF-8"));
        flag = false;
      } else {
        currRequestUrl.append("&" + paramName + "=" + URLEncoder.encode(value, "UTF-8"));
        flag = false;
      }
    }
    return currRequestUrl.toString();
  }

  public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String domain)
  {
    Cookie cookie = WebUtils.getCookie(request, cookieName);
    if (cookie != null) {
      cookie.setMaxAge(0);
      if ((domain != null) && (!"".equals(domain.trim()))) {
        cookie.setDomain(domain);
      }
      cookie.setPath("/");
      response.addCookie(cookie);
    }
  }

  public static String getRequestIp(HttpServletRequest request)
  {
    String ip = request.getHeader("x-forwarded-for");
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  public static boolean isAjaxRequest(HttpServletRequest request)
  {
    if ((request.getHeader("accept").indexOf("application/json") > -1) || ((request.getHeader("X-Requested-With") != null) && (request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
      return true;
    }
    return false;
  }

  public static String outputJson(String json, HttpServletResponse response)
  {
    return output(json, response, "text/html;charset=UTF-8");
  }

  public static String output(String str, HttpServletResponse response, String contentType) {
    response.reset();
    response.setCharacterEncoding("UTF-8");
    response.setContentType(contentType);
    try {
      PrintWriter out = response.getWriter();
      out.print(str);
      out.flush();
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String outputMsg(boolean isSuccess, String msg, HttpServletResponse response)
  {
    Map retMap = new HashMap();
    retMap.put("isSuccess", Boolean.valueOf(isSuccess));
    retMap.put("msg", msg);
    return outputMsg(retMap, response);
  }

  public static String outputMsg(Map<String, Object> retMap, HttpServletResponse response)
  {
    String json = JsonUtil.objectToJson(retMap);
    return outputJson(json, response);
  }

  public static String outputForJsonp(String jsonpCallback, boolean isSuccess, String msg, HttpServletResponse response)
  {
    Map retMap = new HashMap();
    retMap.put("isSuccess", Boolean.valueOf(isSuccess));
    retMap.put("msg", msg);
    return outputForJsonp(jsonpCallback, retMap, response);
  }

  public static String outputForJsonp(String jsonpCallback, Map<String, Object> retMap, HttpServletResponse response) {
    String json = JsonUtil.objectToJson(retMap);
    return outputForJsonp(jsonpCallback, json, response);
  }

  public static String outputForJsonp(String jsonpCallback, String json, HttpServletResponse response) {
    return outputJson(jsonpCallback + "(" + json + ")", response);
  }

  public static Map<String, Object> buildParamMapFromRequest(HttpServletRequest request) {
    Map paramMap = new HashMap();
    Enumeration e = request.getParameterNames();
    while (e.hasMoreElements()) {
      String name = (String)e.nextElement();
      String value = request.getParameter(name);
      paramMap.put(name, value);
    }
    return paramMap;
  }
}