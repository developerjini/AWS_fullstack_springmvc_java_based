package com.developerjini.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {RootConfig.class, SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {ServletConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		MultipartConfigElement configElement = new MultipartConfigElement("c:/upload/tmp");
		registration.setMultipartConfig(configElement);
	}

	// 필터를 이용해 인코딩 처리
	// 리턴 : 필터 배열 타입
//	@Override
//	protected Filter[] getServletFilters() {
////		DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
//		
//		CharacterEncodingFilter filter = new CharacterEncodingFilter("utf-8", true);
//		return new Filter[] {filter};
//	}
	
	
	
	
}
