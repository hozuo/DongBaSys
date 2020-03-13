package top.ericson.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import top.ericson.common.vo.Node;
import top.ericson.sys.entity.SysMenu;

@Mapper
public interface SysMenuDao {

    /**
     * @author Ericson
     * @date 2020/02/05 21:15
     * @description 菜单数据的查询
     */
    List<Map<String, Object>> findObjects();

    /**
     * @author Ericson
     * @date 2020/02/06 15:32
     * @param id
     * @return
     * @description 查询子节点的个数
     */
    int getChildCount(Integer id);

    /**
     * @author Ericson
     * @date 2020/02/06 15:32
     * @param id
     * @return
     * @description 删除菜单
     */
    int deleteObject(Integer id);

    /**
     * @author Ericson
     * @date 2020/02/06 15:32
     * @return
     * @description 查询上级菜单相关信息
     */
    @Select("select id,name,parentId from sys_menus")
    List<Node> findZtreeMenuNodes();

    /**
     * @author Ericson
     * @date 2020/02/06 15:49
     * @param entity
     * @return
     * @description 存储菜单
     */
    int insertObject(SysMenu entity);

    /**
     * @author Ericson
     * @date 2020/02/06 15:49
     * @param entity
     * @return
     * @description 更新菜单
     */
    int updateObject(SysMenu entity);

    /**
     * @author Ericson
     * @date 2020/02/10 21:51
     * @param menuIds
     * @return List<String>
     * @description 基于菜单id查找权限标识
     */
    List<String> findPermissions(@Param("menuIds") Integer[] menuIds);

}