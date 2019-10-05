package com.teadone.imgm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	Environment env;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String uploadFolder=env.getProperty("imgm.upload.path");
		registry.addResourceHandler("/Upload/**").addResourceLocations(uploadFolder);
		
		String generateFolder=env.getProperty("imgm.generate.path");
		registry.addResourceHandler("/Generate/**").addResourceLocations(generateFolder);
	}
}
