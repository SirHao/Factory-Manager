package com.smartwater.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {
    public final static String SESSION_KEY = "username";

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

//        addInterceptor.excludePathPatterns("/error");

//        addInterceptor.excludePathPatterns("/login");
//        addInterceptor.addPathPatterns("/main");
////        addInterceptor.addPathPatterns("/main/**");
////        addInterceptor.addPathPatterns("/changeScale");
////        addInterceptor.addPathPatterns("/changeScale/**");
////        addInterceptor.excludePathPatterns("/static/**");
        addInterceptor.addPathPatterns("/**","/").excludePathPatterns("/login").excludePathPatterns("/postform").excludePathPatterns("/kaptcha.jpg");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
//        registry.addResourceHandler("/3Dresource/**").addResourceLocations("classpath:/META-INF/resource/static/3Dresource/");
//        super.addResourceHandlers(registry);
    }


    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            HttpSession session = request.getSession();

//            判断是否已有该用户登录的session
            if (session.getAttribute(SESSION_KEY) != null) {
                return true;
            }

//            跳转到登录页
            String url = "/login";
            response.sendRedirect(url);
            return false;
        }
    }

}