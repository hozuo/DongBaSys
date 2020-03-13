package top.ericson.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import top.ericson.common.anno.RequestLog;
import top.ericson.common.exception.ServiceException;
import top.ericson.common.vo.Node;
import top.ericson.sys.dao.SysMenuDao;
import top.ericson.sys.dao.SysRoleMenuDao;
import top.ericson.sys.entity.SysMenu;
import top.ericson.sys.service.SysMenuService;

@Service
@Slf4j
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuDao sysMenuDao;

    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    @RequiresPermissions("sys:menu:view")
    @RequestLog("默认的请求参数")
    @Override
    public List<Map<String, Object>> findObjects() {
        List<Map<String, Object>> list = sysMenuDao.findObjects();
        if (list == null || list.size() == 0) {
            log.debug("没有找到对应的菜单信息");
            throw new ServiceException("没有找到对应的菜单信息");
        }
        return list;
    }

    /**
     * @author Ericson
     * @date 2020/02/06 15:17
     * @param id
     * @return
     * @see top.ericson.sys.service.SysMenuService#deleteObject(java.lang.Integer)
     * @description 删除菜单
     */
    @Override
    public int deleteObject(Integer id) {
        // 1.验证数据的合法性
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("请先选择");
        }
        // 2.基于id进行子元素查询
        int count = sysMenuDao.getChildCount(id);
        if (count > 0) {
            throw new ServiceException("请先删除子菜单");
        }
        // 3.删除角色,菜单关系数据
        sysRoleMenuDao.deleteObjectsByMenuId(id);
        // 4.删除菜单元素
        int rows = sysMenuDao.deleteObject(id);
        if (rows == 0) {
            throw new ServiceException("此菜单可能已经不存在");
        }
        // 5.返回结果
        return rows;
    }

    /**
     * @author Ericson
     * @date 2020/02/06 15:35
     * @return
     * @see top.ericson.sys.service.SysMenuService#findZtreeMenuNodes()
     * @description 查询上级菜单相关信息
     */
    @Override
    public List<Node> findZtreeMenuNodes() {
        return sysMenuDao.findZtreeMenuNodes();
    }

    /**
     * @author Ericson
     * @date 2020/02/06 15:51
     * @param entity
     * @return
     * @see top.ericson.sys.service.SysMenuService#saveObject(top.ericson.sys.entity.SysMenu)
     * @description 
     */
    @Override
    public int saveObject(SysMenu entity) {
        // 1.合法验证
        if (entity == null)
            throw new ServiceException("保存对象不能为空");
        if (StringUtils.isEmpty(entity.getName()))
            throw new ServiceException("菜单名不能为空");
        int rows;
        // 2.保存数据
        try {
            rows = sysMenuDao.insertObject(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("保存失败");
        }
        // 3.返回数据
        return rows;
    }

    /**
     * @author Ericson
     * @date 2020/02/06 16:06
     * @param entity
     * @return
     * @see top.ericson.sys.service.SysMenuService#updateObject(top.ericson.sys.entity.SysMenu)
     * @description 更新菜单
     */
    @Override
    public int updateObject(SysMenu entity) {
      //1.合法验证
        if(entity==null)
        throw new ServiceException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getName()))
        throw new ServiceException("菜单名不能为空");
        
        //2.更新数据
        int rows=sysMenuDao.updateObject(entity);
        if(rows==0)
        throw new ServiceException("记录可能已经不存在");
        //3.返回数据
        return rows;
    }

}
