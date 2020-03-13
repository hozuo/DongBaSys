package top.ericson.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import top.ericson.common.vo.Node;
import top.ericson.sys.entity.SysDept;

@Mapper
public interface SysDeptDao {
    /**
     * @author Ericson
     * @date 2020/02/07 17:53
     * @description 查找部门
     */
    List<Map<String, Object>> findObjects();

    /**
     * @author Ericson
     * @date 2020/02/07 17:53
     * @description 根据部门id统计子部门的个数
     */
    int getChildCount(Integer id);

    /**
     * @author Ericson
     * @date 2020/02/07 17:54
     * @description 根据部门id删除部门
     */
    int deleteObject(Integer id);
    
    /**
     * @author Ericson
     * @date 2020/02/07 22:08
     * @description 查询ztree节点
     */
    @Select("select id,name,parentId from sys_Depts")
    List<Node> findZtreeDeptNodes();
    
    /**
     * @author Ericson
     * @date 2020/02/07 22:08
     * @description 插入部门记录
     */
    int insertObject(SysDept entity);

    int updateObject(SysDept entity);
    
    /**
     * @author Ericson
     * @date 2020/02/07 23:47
     * @description 根据id查部门
     */
    @Select("select * from sys_depts where id = #{id}")
    SysDept findDeptById(Integer id);
}
