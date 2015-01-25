package com.xxx.web;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.xxx.annotations.ApiMethod;
import com.xxx.annotations.ApiParam;
import com.xxx.annotations.ApiService;
import com.xxx.enums.ApiMethodEnum;

public class SpringBeanProxy {
	private static ApplicationContext applicationContext;
	private static Map<String, Object[]> API_METHOD_MAP = new HashMap<String, Object[]>();

	private static Map<String, String> functionCodeCatalogMap = new LinkedHashMap<String, String>();

	private static Map<String, Map<String, String>> functionCodeListMap = new LinkedHashMap();

	private static Map<String, Map<String, String>> functionCodeParamMap = new LinkedHashMap();

	public static synchronized void setApplicationContext(ApplicationContext arg0) {
		applicationContext = arg0;

		Map<String, Object> tempMap = applicationContext.getBeansWithAnnotation(ApiService.class);
		if ((tempMap != null) && (tempMap.size() > 0))
			for (Map.Entry<String, Object> entry : tempMap.entrySet()) {
				String beanName = entry.getKey();
				Object bean = entry.getValue();
				ApiService beanFc = bean.getClass().getAnnotation(ApiService.class);
				if (beanFc != null) {
					functionCodeCatalogMap.put(beanName, beanFc.descript());

					Method[] methodArr = bean.getClass().getDeclaredMethods();
					if ((methodArr != null) && (methodArr.length > 0)) {
						Map methodFunctionCodeMap = new LinkedHashMap();
						for (Method method : methodArr) {
							ApiMethod methodFc = method.getAnnotation(ApiMethod.class);
							if (methodFc != null) {
								String methodFunctionCode = methodFc.value();
								API_METHOD_MAP.put(methodFunctionCode, new Object[] { beanName, method, methodFc });

								StringBuffer str = new StringBuffer();
								str.append("/**\n");
								str.append(" * " + methodFc.descript() + "\n");
								str.append(" */\n");
								str.append(methodFunctionCode.toUpperCase().replaceAll("\\.", "_") + "(\"" + methodFunctionCode + "\", \"" + methodFc.descript()
										+ "\", ApiServerEnum.xxx,ContentTypeEnum.yyy," + methodFc.privateApi() + "," + methodFc.backendApi() + "," + methodFc.webApi() + ") {");
								str.append("\t@Override");
								str.append("\tpublic Map<String, String> getApiParams() {");
								str.append("\t\tMap<String, String> paramMap = new LinkedHashMap<String, String>();");
								Map paramMap = new LinkedHashMap();
								ApiParam[] params = methodFc.apiParams();
								if (params != null) {
									for (ApiParam param : params) {
										paramMap.put(param.name(), param.descript());
										str.append("\t\tparamMap.put(\"" + param.name() + "\", \"" + param.descript() + "\");");
									}
								}
								str.append("\t\treturn paramMap;");
								str.append("\t}");
								str.append("},\n");
								if (ApiMethodEnum.getApiMethodEnum(methodFunctionCode) == null) {
									System.out.println(str.toString());
								}
								functionCodeParamMap.put(methodFunctionCode, paramMap);

								methodFunctionCodeMap.put(methodFunctionCode, methodFc.descript());
							}
						}
						functionCodeListMap.put(beanName, methodFunctionCodeMap);
					}
				}
			}
	}

	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	public static Object getBeanByFunctionCode(String functionCode) {
		Object[] objArr = API_METHOD_MAP.get(functionCode);
		if ((objArr != null) && (objArr.length >= 3)) {
			String beanName = (String) objArr[0];
			return getBean(beanName);
		}
		return null;
	}

	public static Method getMethodByFunctionCode(String functionCode) {
		Object[] objArr = API_METHOD_MAP.get(functionCode);
		if ((objArr != null) && (objArr.length >= 3)) {
			return (Method) objArr[1];
		}
		return null;
	}

	public static ApiMethod getApiMethodByFunctionCode(String functionCode) {
		Object[] objArr = API_METHOD_MAP.get(functionCode);
		if ((objArr != null) && (objArr.length >= 3)) {
			return (ApiMethod) objArr[2];
		}
		return null;
	}

	public static Map<String, String> getFunctionListByCatalog(String functionCode) {
		return functionCodeListMap.get(functionCode);
	}

	public static Map<String, String> getParamsByFunctionCode(String functionCode) {
		return functionCodeParamMap.get(functionCode);
	}

	public static Map<String, String> getFunctionCodeCatalogMap() {
		return functionCodeCatalogMap;
	}
}