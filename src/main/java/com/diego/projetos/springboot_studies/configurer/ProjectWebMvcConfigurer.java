//package com.diego.projetos.springboot_studies.configurer;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.List;
//
//@Configuration
//public class ProjectWebMvcConfigurer implements WebMvcConfigurer {
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolver) {
//        PageableHandlerMethodArgumentResolver pageHandler = new PageableHandlerMethodArgumentResolver();
//        pageHandler.setFallbackPageable(PageRequest.of(0, 3));
//        // 0 -> makes the page 0 (1st page) the default page of requests
//        // 5 -> makes 5 the default page size of each request
//        // these values can be changed on param, they're just default
//    }
//}
