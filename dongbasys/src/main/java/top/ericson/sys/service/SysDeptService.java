package top.ericson.sys.service;

import java.util.List;
import java.util.Map;

import top.ericson.common.vo.Node;
import top.ericson.sys.entity.SysDept;

public interface SysDeptService {

    List<Map<String, Object>> findObjects();
    
    int deleteObject(Integer id);

    /**
     * @author Ericson
     * @date 2020/02/07 21:35
     * @return
     * @description 
     */
    List<Node> findZtreeDeptNodes();

    /**
     * @author Ericson
     * @date 2020/02/07 22:10
     * @param entity
     * @return
     * @description 保存一条数据
     */
    int saveObject(SysDept entity);

    /**
     * @author Ericson
     * @date 2020/02/07 22:18
     * @param entity
     * @return
     * @description 
     */
    int updateObject(SysDept entity);
}
