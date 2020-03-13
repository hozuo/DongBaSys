package top.ericson.common.web;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import top.ericson.common.exception.ServiceException;

/**
 * @author Ericson
 * @class TimeAccessInterceptor
 * @date 2020/02/11 22:23
 * @description 限定访问时间的拦截器
 * @version 1.0
 */
public class TimeAccessInterceptor implements HandlerInterceptor {
    /**
     * @author Ericson
     * @date 2020/02/11 22:24
     * @throws Exception
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     * @description 在目标controller执行前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
        HttpServletResponse response,
        Object handler) throws Exception {
        System.out.println("TimeAccessInterceptor.preHandle()");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 6);
        calendar.set(Calendar.SECOND, 6);
        Long startTime = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        Long endTime = calendar.getTimeInMillis();
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis < startTime || currentTimeMillis > endTime) {
            throw new ServiceException("每天23:00至次日6:00禁止访问");
        }
        return true;
    }
}
