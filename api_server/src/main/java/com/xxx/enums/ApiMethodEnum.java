package com.xxx.enums;

import java.util.LinkedHashMap;
import java.util.Map;

import com.xxx.interfaces.Api;

public enum ApiMethodEnum implements Api {
	/**
	 * 获取单个厂商
	 */
	AUTHOR_INFO("author.info", "获取单个厂商", ApiServerEnum.content, ContentTypeEnum.AUTHOR, false, false, false) {
		public Map<String, String> getApiParams() {
			Map<String, String> paramMap = new LinkedHashMap<String, String>();
			paramMap.put("author_id", "厂商ID");
			return paramMap;
		}
	},
	;

	private String code;
	private String msg;
	private ApiServerEnum apiServer;
	private ContentTypeEnum contentType;
	private boolean privateApi;
	private boolean backendApi;
	private boolean webApi;

	private ApiMethodEnum(String code, String msg, ApiServerEnum apiServer, ContentTypeEnum contentType, boolean privateApi, boolean backendApi, boolean webApi) {
		this.code = code;
		this.msg = msg;
		this.apiServer = apiServer;
		this.contentType = contentType;
		this.privateApi = privateApi;
		this.backendApi = backendApi;
		this.webApi = webApi;
	}

	public String getCode() {
		return this.code;
	}

	public String getMsg() {
		return this.msg;
	}

	public ApiServerEnum getApiServer() {
		return this.apiServer;
	}

	public ContentTypeEnum getContentType() {
		return this.contentType;
	}

	public boolean isPrivateApi() {
		return this.privateApi;
	}

	public boolean isBackendApi() {
		return this.backendApi;
	}

	public boolean isWebApi() {
		return this.webApi;
	}

	public static Map<String, ApiMethodEnum> getApiMethodEnumMap() {
		Map retMap = new LinkedHashMap();
		ApiMethodEnum[] enumArr = values();
		for (ApiMethodEnum aEnum : enumArr) {
			retMap.put(aEnum.getCode(), aEnum);
		}
		return retMap;
	}

	public static Map<String, String> getApiMethodMapByServer(String apiServer) {
		ApiServerEnum sEnum = ApiServerEnum.getApiServerEnum(apiServer);
		Map retMap = new LinkedHashMap();
		if (sEnum != null) {
			ApiMethodEnum[] enumArr = values();
			for (ApiMethodEnum aEnum : enumArr) {
				if ((sEnum.name().equals(aEnum.getApiServer().name())) && (!aEnum.isPrivateApi()) && (!aEnum.isBackendApi()) && (!aEnum.isWebApi())) {
					retMap.put(aEnum.getCode(), aEnum.getMsg());
				}
			}
		}
		return retMap;
	}

	public static Map<String, String> getApiParamMapByMethod(String methodCode) {
		ApiMethodEnum mEnum = getApiMethodEnum(methodCode);
		if (mEnum != null) {
			return mEnum.getApiParams();
		}
		return null;
	}

	public static ApiMethodEnum getApiMethodEnum(String methodCode) {
		return getApiMethodEnumMap().get(methodCode);
	}

	public static ApiServerEnum getApiServerEnum(String methodCode) {
		ApiMethodEnum aEnum = getApiMethodEnumMap().get(methodCode);
		if (aEnum != null) {
			return aEnum.getApiServer();
		}
		return null;
	}
}