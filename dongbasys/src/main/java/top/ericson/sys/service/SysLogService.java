package top.ericson.sys.service;

import java.util.concurrent.Future;

import top.ericson.common.vo.PageObject;
import top.ericson.sys.entity.SysLog;

public interface SysLogService {
    /**
    * 通过此方法实现分页查询操作
    * @param name 基于条件查询时的参数名
    * @param pageCurrent 当前的页码值
    * @return 当前页记录+分页信息
    */
    PageObject<SysLog> findPageObjects(String username, Integer pageCurrent);

    /**
     * @author Ericson
     * @date 2020/02/04 21:18
     * @param ids
     * @return int
     * @description 基于id删除
     */
    int deleteObjects(Integer... ids);

    /**
     * @author Ericson
     * @date 2020/02/08 23:37
     * @param entity
     * @return 
     * @description 
     */
    Future<Integer> saveObject(SysLog entity);

}
