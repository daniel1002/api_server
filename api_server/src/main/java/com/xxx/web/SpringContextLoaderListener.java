package com.xxx.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class SpringContextLoaderListener extends ContextLoaderListener
{
  public void contextInitialized(ServletContextEvent event)
  {
    super.contextInitialized(event);

    WebApplicationContext context = (WebApplicationContext)event.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

    SpringBeanProxy.setApplicationContext(context);
  }
}