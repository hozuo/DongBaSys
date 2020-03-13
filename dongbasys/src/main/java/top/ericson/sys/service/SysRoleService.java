package top.ericson.sys.service;

import java.util.List;

import top.ericson.common.vo.CheckBox;
import top.ericson.common.vo.PageObject;
import top.ericson.common.vo.SysRoleMenuVo;
import top.ericson.sys.entity.SysRole;

public interface SysRoleService {
    /**
     * @author Ericson
     * @date 2020/02/06 18:28
     * @param pageCurrent 当前表要查询的当前页的页码值
     * @return 封装当前实体数据以及分页信息
     * @description 分页查询角色信息,并查询角色总记录数据
     */
    PageObject<SysRole> findPageObjects(String name, Integer pageCurrent);

    /**
     * @author Ericson
     * @date 2020/02/06 20:39
     * @description 删除角色
     */
    int deleteObject(Integer id);

    /**
     * @author Ericson
     * @date 2020/02/06 21:19
     * @description 保存角色
     */
    int saveObject(SysRole entity, Integer[] menuIds);

    /**
     * @author Ericson
     * @date 2020/02/06 22:01
     * @description 修改角色时,查询角色菜单关联
     */
    SysRoleMenuVo findObjectById(Integer id);

    /**
     * @author Ericson
     * @date 2020/02/06 22:13
     * @param entity
     * @param menuIds
     * @return
     * @description 修改角色
     */
    int updateObject(SysRole entity, Integer[] menuIds);

    /**
     * @author Ericson
     * @date 2020/02/08 17:32
     * @return
     * @description 
     */
    List<CheckBox> findObjects();

}
