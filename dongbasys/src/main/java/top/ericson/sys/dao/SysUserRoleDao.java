package top.ericson.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Ericson
 * @class Snippet
 * @date 2020/02/06 20:36
 * @description 
 * @version 1.0
 */
@Mapper
public interface SysUserRoleDao {
    /**
     * @author Ericson
     * @date 2020/02/08 17:02
     * @param userId
     * @param roleIds
     * @description 插入关系
     */
    int insertObjects(@Param("userId") Integer userId,
        @Param("roleIds") Integer[] roleIds);

    /**
     * @author Ericson
     * @date 2020/02/08 17:01
     * @param roleId
     * @description 根据角色id删除关系
     */
    @Delete("delete from sys_user_roles where role_id = #{roleId}")
    int deleteObjectsByRoleId(Integer roleId);
    
    /**
     * @author Ericson
     * @date 2020/02/08 18:00
     * @param id
     * @return List<Integer>
     * @description 基于用户id查询角色id信息的方法
     */
    @Select("select role_id from sys_user_roles where user_id = #{id}")
    List<Integer> findRoleIdsByUserId(Integer id);
    
    /**
     * @author Ericson
     * @date 2020/02/08 18:15
     * @description 删除关系
     */
    @Delete("delete from sys_user_roles where user_id = #{userId}")
    int deleteObjectsByUserId(Integer userId);
    
}
