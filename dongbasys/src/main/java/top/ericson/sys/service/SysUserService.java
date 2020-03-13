package top.ericson.sys.service;

import java.util.Map;

import top.ericson.common.vo.PageObject;
import top.ericson.common.vo.SysUserDeptVo;
import top.ericson.sys.entity.SysUser;

public interface SysUserService {
    
    PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent);
    
    int validById(Integer id,Integer valid,String modifiedUser);

    /**
     * @author Ericson
     * @date 2020/02/08 17:14
     * @param entity
     * @param roleIds
     * @return
     * @description 
     */
    int saveObject(SysUser entity,
        Integer[] roleIds);

    /**
     * @author Ericson
     * @date 2020/02/08 18:02
     * @param userId
     * @return
     * @description 
     */
    Map<String, Object> findObjectById(Integer userId);

    /**
     * @author Ericson
     * @date 2020/02/08 18:16
     * @param entity
     * @param roleIds
     * @return
     * @description 
     */
    int updateObject(SysUser entity,
        Integer[] roleIds);

    /**
     * @author Ericson
     * @date 2020/02/08 18:30
     * @param password
     * @param newPassword
     * @param cfgPassword
     * @return
     * @description 
     */
    int updatePassword(String password,
        String newPassword,
        String cfgPassword);

}
