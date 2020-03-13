package top.ericson.common.web;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import top.ericson.common.vo.JsonResult;

/**全局异常处理类*/
@ControllerAdvice
// 定义private Logger log=LoggerFactory.getLogger(GlobalExceptionHandler.class);
@Slf4j
public class GlobalExceptionHandler {

    // JDK中的自带的日志API
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public JsonResult doHandleRuntimeException(RuntimeException e) {
        // 也可以写日志
        log.error(e.getMessage());
        e.printStackTrace();
        // 封装异常信息
        return new JsonResult(e);
    }

    @ResponseBody
    @ExceptionHandler(ShiroException.class)
    public JsonResult doHandleShiroException(ShiroException e) {
        JsonResult r = new JsonResult();
        r.setState(0);
        if (e instanceof UnknownAccountException) {
            r.setMessage("账户不存在");
        } else if (e instanceof LockedAccountException) {
            r.setMessage("账户已被禁用");
        } else if (e instanceof IncorrectCredentialsException) {
            r.setMessage("密码不正确");
        } else if (e instanceof AuthorizationException) {
            r.setMessage("没有此操作权限");
        } else {
            r.setMessage("系统维护中");
        }
        e.printStackTrace();
        return r;
    }

}
