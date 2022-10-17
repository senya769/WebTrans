package com.trans.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry){
//        viewControllerRegistry.addViewController("/").setViewName("pages/main");
        viewControllerRegistry.addViewController("/successLogin").setViewName("redirect:/");
        viewControllerRegistry.addViewController("/login").setViewName("pages/login");
    }
}
