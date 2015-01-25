package com.xxx.service;

import com.xxx.dto.ApiRequest;
import com.xxx.dto.ApiResponse;
import com.xxx.model.Author;
import java.util.List;

public abstract interface AuthorService
{
  public abstract ApiResponse<Author> getAuthor(ApiRequest paramApiRequest);

  public abstract ApiResponse<List<Author>> list(ApiRequest paramApiRequest);
}