package top.ericson.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import top.ericson.common.vo.CheckBox;
import top.ericson.common.vo.SysRoleMenuVo;
import top.ericson.sys.entity.SysRole;

@Mapper
public interface SysRoleDao {

    /**
     * @author Ericson
     * @date 2020/02/06 21:39
     * @param name 名字
     * @param startIndex 上一页的结束位置
     * @param pageSize 每页要查询的记录数
     * @return
     * @description 分页查询角色信息
     */
    List<SysRole> findPageObjects(@Param("name") String name,
        @Param("startIndex") Integer startIndex,
        @Param("pageSize") Integer pageSize);

    /**
     * @author Ericson
     * @date 2020/02/06 21:39
     * @param name
     * @return
     * @description 查询记录总数
     */
    int getRowCount(@Param("name") String name);

    /**
     * @author Ericson
     * @date 2020/02/06 21:38
     * @param id
     * @return
     * @description 删除角色
     */
    @Delete("delete from sys_roles where id=#{id}")
    int deleteObject(Integer id);

    /**
     * @author Ericson
     * @date 2020/02/06 21:39
     * @param entity
     * @return
     * @description 新增角色
     */
    int insertObject(SysRole entity);

    /**
     * @author Ericson
     * @date 2020/02/06 21:55
     * @param id
     * @return
     * @description 基于角色id查询角色信息
     */
    SysRoleMenuVo findObjectById(Integer id);

    /**
     * @author Ericson
     * @date 2020/02/06 22:07
     * @description 修改角色
     */
    int updateObject(SysRole entity);

    /**
     * @author Ericson
     * @date 2020/02/08 17:22
     * @return CheckBox
     * @description 查询角色id，name的相关方法
     */
    @Select("select id,name from sys_roles")
    List<CheckBox> findObjects();
}
