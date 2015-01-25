package com.xxx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xxx.annotations.ApiMethod;
import com.xxx.annotations.ApiService;
import com.xxx.dto.ApiRequest;
import com.xxx.dto.ApiResponse;
import com.xxx.enums.ApiMsgEnum;
import com.xxx.model.Author;
import com.xxx.service.AuthorService;

@Service
@ApiService(descript = "厂商")
public class AuthorServiceImpl extends BaseServiceImpl implements AuthorService {
	@ApiMethod(value = "author.info", descript = "获取单个厂商", apiParams = { @com.xxx.annotations.ApiParam(name = "author_id", descript = "厂商ID") })
	public ApiResponse<Author> getAuthor(ApiRequest apiReq) {
		if (StringUtils.isEmpty(apiReq.get("author_id"))) {
			return new ApiResponse(ApiMsgEnum.MissParameterException);
		}

		Author a = new Author();
		a.setId(apiReq.getInt("author_id"));
		a.setName("a");
		return new ApiResponse(ApiMsgEnum.SUCCESS, Integer.valueOf(1), a);
	}

	@ApiMethod(value = "author.list", descript = "厂商列表", apiParams = { @com.xxx.annotations.ApiParam(name = "page", descript = "页码"),
			@com.xxx.annotations.ApiParam(name = "page_size", descript = "页大小") })
	public ApiResponse<List<Author>> list(ApiRequest apiReq) {
		setDefaultPageSize(apiReq);

		List list = new ArrayList();
		Author a = new Author();
		a.setId(Integer.valueOf(1));
		a.setName("a");
		list.add(a);

		Author b = new Author();
		b.setId(Integer.valueOf(2));
		b.setName("b");
		list.add(b);
		return new ApiResponse(ApiMsgEnum.SUCCESS, Integer.valueOf(list.size()), list);
	}
}