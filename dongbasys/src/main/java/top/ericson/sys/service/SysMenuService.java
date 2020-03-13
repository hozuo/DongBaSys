package top.ericson.sys.service;

import java.util.List;
import java.util.Map;

import top.ericson.common.vo.Node;
import top.ericson.sys.entity.SysMenu;

public interface SysMenuService {
    /**
     * @author Ericson
     * @date 2020/02/05 21:21
     * @return
     * @return List<Map<String,Object>>
     * @description 菜单数据的查询
     */
	 List<Map<String,Object>> findObjects();
	 
	 /**
	  * @author Ericson
	  * @date 2020/02/06 14:48
	  * @param id
	  * @return
	  * @description 基于id进行菜单删除的方法
	  */
	 int deleteObject(Integer id);
	 
	 List<Node> findZtreeMenuNodes();
	 
	 int saveObject(SysMenu entity);
	 
	 int updateObject(SysMenu entity);
}
