package top.ericson.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.Future;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import top.ericson.common.anno.RequestLog;
import top.ericson.common.util.IPUtils;
import top.ericson.common.util.ShiroUtil;
import top.ericson.sys.entity.SysLog;
import top.ericson.sys.service.SysLogService;

@Aspect
@Component
@Slf4j
@Order(1)

public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    // @Pointcut("@annotation(top.ericson.common.anno.RequestLog)")
    // @Pointcut("bean(sysMenuServiceImpl)")
    @Pointcut("bean(sysUserServiceImpl)")
    public void logPointCut() {}

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        try {
            long startTime = System.currentTimeMillis();
            log.info("start:" + startTime);
            // 调用下一个切面方法或目标方法
            Object result = jp.proceed();
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            log.info("after:" + endTime);
            log.info("方法执行的总时长为:" + totalTime);
            saveSysLog(jp, totalTime);
            return result;
        } catch (Throwable e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * @author Ericson
     * @date 2020/02/08 23:46
     * @param jp
     * @param totalTime
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws JsonProcessingException 
     * @description 
     */
    private void saveSysLog(ProceedingJoinPoint jp,
        long totalTime) throws NoSuchMethodException, SecurityException, JsonProcessingException {
        // 获取类名
        Class<?> targetClass = jp.getTarget().getClass();
        String className = targetClass.getName();
        // 获取方法签名
        MethodSignature ms = (MethodSignature)jp.getSignature();
        // 获取方法名
        Method method = ms.getMethod();
        System.out.println(method);
        String methodName = method.getName();
        // 获取方法参数
        Object[] paramsObjs = jp.getArgs();
        // 将参数序列化为字符串
        String params = new ObjectMapper().writeValueAsString(paramsObjs);
        // 获取用户名,学完shiro再进行自定义实现,没有就先给固定值
        // String username=ShiroUtils.getPrincipal().getUsername();
        String username = ShiroUtil.getLoginUserName();

        // 获取参数列表
        // Class<?>[] parameterTypes = method.getParameterTypes();
        // 获取目标对象方法
        // Method targetMethod = targetClass.getDeclaredMethod(methodName, parameterTypes);

        // 2.封装日志信息
        /*
         * 添加注解使用链式编程
        SysLog log = new SysLog();
        log.setUsername(username);
        log.setMethod(className + "." + methodName);
        log.setParams(params);
        log.setIp(IPUtils.getIpAddr());
        log.setTime(totalTime);
        log.setCreatedTime(new Date());*/

        SysLog log = new SysLog().setUsername(username).setMethod(className + "." + methodName).setParams(params)
            .setIp(IPUtils.getIpAddr()).setTime(totalTime).setCreatedTime(new Date());
        // 假如目标方法对象上有注解,我们获取注解定义的操作值
        // RequestLog requestLog = targetMethod.getAnnotation(RequestLog.class);
        RequestLog requestLog = method.getAnnotation(RequestLog.class);
        if (requestLog != null) {
            log.setOperation(requestLog.value());
        }
        // 3.保存日志信息
        Future<Integer> saveObject = sysLogService.saveObject(log);
        System.out.println(saveObject);

    }
}
