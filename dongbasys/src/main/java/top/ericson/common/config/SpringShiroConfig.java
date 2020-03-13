package top.ericson.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @Configuration 注解描述的类为一个配置对象,
 * 此对象也会交给spring管理
 */
@Configuration
public class SpringShiroConfig {

    @Bean
    public SecurityManager securityManager(Realm realm,
        CacheManager cacheManager,
        CookieRememberMeManager rememberManager,
        DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
        sManager.setRealm(realm);
        sManager.setCacheManager(cacheManager);
        sManager.setRememberMeManager(rememberManager);
        sManager.setSessionManager(sessionManager);
        return sManager;
    }

    // bean对象的生命周期管理
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    // 关联了切入点,扫描spring中所有advice对象并为其创建代理对象
    // 通过如下配置要为目标业务对象创建代理对象（SpringBoot中可省略）
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    // 配置advisor对象,shiro框架底层会通过此对象的matchs方法返回值决定是否创建代理对象,进行权限控制
    // 实现spring中的Advisor接口,要提供切入点,关联通知对象进行功能扩展
    @Bean
    public AuthorizationAttributeSourceAdvisor newAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean("shiroFilterFactory")
    public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager) {
        ShiroFilterFactoryBean sfBean = new ShiroFilterFactoryBean();
        // 添加登陆url
        sfBean.setSecurityManager(securityManager);
        sfBean.setLoginUrl("/doLoginUI");
        // 定义map指定请求过滤规则(哪些资源允许匿名访问,哪些必须认证访问)
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        // 静态资源允许匿名访问:"anon"
        map.put("/bower_components/**", "anon");
        map.put("/build/**", "anon");
        map.put("/dist/**", "anon");
        map.put("/plugins/**", "anon");
        map.put("/user/doLogin", "anon");
        map.put("/doLogout", "logout");
        // 除了匿名访问的资源,其它都要认证("authc")后访问,记住我改为user
        map.put("/**", "user");
        sfBean.setFilterChainDefinitionMap(map);
        return sfBean;
    }

    @Bean
    public CacheManager shiroCacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    /**
     * @author Ericson
     * @date 2020/02/11 16:54
     * @description 记住我
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cManager = new CookieRememberMeManager();
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setMaxAge(10 * 60);
        cManager.setCookie(cookie);
        return cManager;
    }

    // 会话管理器配置
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sManager = new DefaultWebSessionManager();
        sManager.setGlobalSessionTimeout(60 * 60 * 1000);
        return sManager;
    }

}
