package com.xxx.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiMethod {
	public abstract String value();

	public abstract String descript();

	public abstract ApiParam[] apiParams();

	public abstract boolean needLogin() default false;

	public abstract boolean privateApi() default false;

	public abstract boolean backendApi() default false;

	public abstract boolean webApi() default false;
}