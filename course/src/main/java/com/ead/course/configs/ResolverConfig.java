package com.ead.course.configs;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

//this resolver config was created with the purpose to implement advanced filters in
//our API, due the inclusion of specification-arg-resolver dependency

//this extends converts data received by variables in java
@Configuration
public class ResolverConfig extends WebMvcConfigurationSupport {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        argumentResolvers.add(new SpecificationArgumentResolver()); //included filters
        //however pagination must be considered in filters
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        argumentResolvers.add(resolver);
        super.addArgumentResolvers(argumentResolvers);
    }
}
