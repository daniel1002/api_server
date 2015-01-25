package com.xxx.model;

import java.io.Serializable;

public class Author
  implements Serializable
{
  private static final long serialVersionUID = -8195951787248201120L;
  private Integer id;
  private String name;
  private String icon;
  private String cover;

  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIcon() {
    return this.icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getCover() {
    return this.cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }
}