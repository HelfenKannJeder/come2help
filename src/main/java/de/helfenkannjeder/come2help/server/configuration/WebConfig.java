package de.helfenkannjeder.come2help.server.configuration;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class WebConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // Spring MVC by default uses the last dot in the URL as file type separator (e.g.:  xxx.xml).
        // To allow domain names as part of the URL we need switch of this behaviour.
        configurer.setUseSuffixPatternMatch(false);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("**/*.css", "**/*.js", "**/*.map", "*.html").addResourceLocations("classpath:META-INF/resources/").setCachePeriod(0);
    }
}