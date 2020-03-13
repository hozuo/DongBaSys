package top.ericson.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import top.ericson.common.vo.SysUserDeptVo;
import top.ericson.sys.entity.SysUser;

@Mapper
public interface SysUserDao {
    /**
     * @author Ericson
     * @date 2020/02/08 16:57
     * @param entity
     * @return
     * @description 插入用户
     */
    int insertObject(SysUser entity);

    /**
     * @author Ericson
     * @date 2020/02/07 23:26
     * @description 根据部门id查询所属员工个数
     */
    int getUserCountByDeptId(Integer DeptId);

    /**
     * @author Ericson
     * @date 2020/02/07 23:26
     * @description 查询user总数
     */
    int getRowCount(@Param("username") String username);

    /**
     * @author Ericson
     * @date 2020/02/07 23:27
     * @param username
     * @param startIndex
     * @param pageSize
     * @description 分页查询
     */
    List<SysUserDeptVo> findPageObjects(@Param("username") String username,
        @Param("startIndex") Integer startIndex,
        @Param("pageSize") Integer pageSize);

    /**
     * @author Ericson
     * @date 2020/02/08 16:05
     * @param id 用户id
     * @param valid 禁用为1,为禁用为0
     * @param modifiedUser 禁用该用户的对象
     * @description 禁用用户
     */
    int validById(@Param("id") Integer id,
        @Param("valid") Integer valid,
        @Param("modifiedUser") String modifiedUser);

    /**
     * @author Ericson
     * @date 2020/02/08 17:59
     * @param id
     * @description 基于用户id查询用户相关信息的方法
     */
    SysUserDeptVo findObjectById(Integer id);
    
    /**
     * @author Ericson
     * @date 2020/02/08 18:14
     * @description 更新用户
     */
    int updateObject(SysUser entity);
    
    /**
     * @author Ericson
     * @date 2020/02/08 18:29
     * @description 更新密码
     */
    int updatePassword(
        @Param("password")String password,
        @Param("salt")String salt,
        @Param("id")Integer id);

    /**
     * @author Ericson
     * @date 2020/02/10 17:30
     * @param username
     * @return SysUser
     * @description 根据用户名查询密码
     */
    @Select("select * from sys_users where username = #{username}")
    SysUser findUserByUserName(String username);
}
