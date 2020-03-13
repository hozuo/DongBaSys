package top.ericson.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Ericson
 * @class SysRoleMenuDao
 * @date 2020/02/06 21:59
 * @description 角色-菜单关联查询
 * @version 1.0
 */
@Mapper
public interface SysRoleMenuDao {
    /**
     * @author Ericson
     * @date 2020/02/06 14:32
     * @description 基于菜单id删除关系数据
     */
    int deleteObjectsByMenuId(Integer menuId);

    /**
     * @author Ericson
     * @date 2020/02/06 21:58
     * @description 基于角色id删除关系数据
     */
    @Delete("delete from sys_role_menus where role_id=#{roleId}")
    int deleteObjectsByRoleId(Integer roleId);

    /**
     * @author Ericson
     * @date 2020/02/06 21:58
     * @description 添加菜单
     */
    int insertObjects(@Param("roleId") Integer roleId,
        @Param("menuIds") Integer[] menuIds);

    /**
     * @author Ericson
     * @date 2020/02/06 21:57
     * @description 使用角色id查菜单id
     */
    List<Integer> findMenuIdsByRoleId(Integer id);

    /**
     * @author Ericson
     * @date 2020/02/10 21:55
     * @param roleIds
     * @return List<Integer>
     * @description 使用角色ids查菜单ids
     */
    List<Integer> findMenuIdsByRoleIds(@Param("roleIds") Integer[] roleIds);

}
