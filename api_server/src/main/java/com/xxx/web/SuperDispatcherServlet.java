package com.xxx.web;

import com.xxx.util.RandomIDUtil;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.DispatcherServlet;

public class SuperDispatcherServlet extends DispatcherServlet
{
  private static final Logger log = Logger.getLogger(SuperDispatcherServlet.class);
  private static final long serialVersionUID = -6763713779463635319L;
  public static final String LOG_ID = "logId";
  private static final Map<Long, String> logMap = new HashMap();

  protected void doService(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    long start = System.currentTimeMillis();
    String logId = RandomIDUtil.getNewUUID();
    long threadId = Thread.currentThread().getId();
    logMap.put(Long.valueOf(threadId), logId);
    request.setAttribute("logId", logId);
    String attribute = getAttributeLog(request);
    StringBuffer buf = new StringBuffer("{");
    buf.append("logId:").append(logId).append(" ,");
    buf.append("method:").append(request.getMethod()).append(" ,");
    buf.append("requestURI:").append(getRequestUri(request)).append(" ,");
    buf.append("requestAttribute:").append(attribute).append("}");
    log.info(buf.toString());
    super.doService(request, response);
    long end = System.currentTimeMillis() - start;

    buf = new StringBuffer("{");
    buf.append("logId:").append(logId).append(" ,");
    buf.append("totalTime:").append(end).append(" }");
    logMap.remove(Long.valueOf(threadId));
    log.info(buf.toString());
  }

  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
  }

  private static String getAttributeLog(HttpServletRequest request)
  {
    Map attribute = new HashMap();
    Enumeration attrNames = request.getParameterNames();
    while (attrNames.hasMoreElements()) {
      String attrName = (String)attrNames.nextElement();
      attribute.put(attrName, request.getParameter(attrName));
    }
    return attribute.toString();
  }

  private static String getRequestUri(HttpServletRequest request) {
    String uri = (String)request.getAttribute("javax.servlet.include.request_uri");
    if (uri == null) {
      uri = request.getRequestURI();
    }
    return uri;
  }

  public static String getLogIdByThreadId(long threadId)
  {
    return (String)logMap.get(Long.valueOf(threadId));
  }
}