package top.ericson.common.util;

import org.apache.shiro.SecurityUtils;

import top.ericson.sys.entity.SysUser;

/**
 * @author Ericson
 * @class ShiroUtil
 * @date 2020/02/11 01:29
 * @description 
 * @version 1.0
 */
public class ShiroUtil {
    /**
     * @author Ericson
     * @date 2020/02/11 01:32
     * @return String LoginUserName
     * @description 从session中读取当前登录的用户名
     */
    public static String getLoginUserName() {
        SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
        return user.getUsername();
    }
}
