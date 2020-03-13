package top.ericson.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import top.ericson.common.web.TimeAccessInterceptor;

/**
 * @author Ericson
 * @class SpringWebConfig
 * @date 2020/02/10 16:26
 * @description 代替web.xml中过滤器的配置
 * @version 1.0
 */
@Configuration
public class SpringWebConfig implements WebMvcConfigurer {

    /**
     * @author Ericson
     * @date 2020/02/11 22:26
     * @param registry
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
     * @description 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TimeAccessInterceptor()).addPathPatterns("/user/doLogin");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public FilterRegistrationBean newFilterRegistrationBean() {
        // 创建过滤器注册器
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        // 创建过滤器
        DelegatingFilterProxy filter = new DelegatingFilterProxy("shiroFilterFactory");
        frBean.setFilter(filter);
        // 配置映射路径
        frBean.addUrlPatterns("/*");
        return frBean;
    }
}
