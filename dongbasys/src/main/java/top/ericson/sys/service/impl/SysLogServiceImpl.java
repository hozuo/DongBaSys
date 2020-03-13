package top.ericson.sys.service.impl;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import top.ericson.common.exception.ServiceException;
import top.ericson.common.vo.PageObject;
import top.ericson.sys.dao.SysLogDao;
import top.ericson.sys.entity.SysLog;
import top.ericson.sys.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public PageObject<SysLog> findPageObjects(String name,
        Integer pageCurrent) {
        // 1.验证参数合法性
        // 1.1验证pageCurrent的合法性，
        // 不合法抛出IllegalArgumentException异常
        if (pageCurrent == null || pageCurrent < 1) {
            throw new IllegalArgumentException("当前页码不正确");
        }
        // 2.基于条件查询总记录数
        // 2.1) 执行查询
        int rowCount = sysLogDao.getRowCount(name);
        // 2.2) 验证查询结果，假如结果为0不再执行如下操作
        if (rowCount == 0) {
            throw new ServiceException("系统没有查到对应记录");
        }
        // 3.基于条件查询当前页记录(pageSize定义为2)
        // 3.1)定义pageSize
        int pageSize = 5;
        // 3.2)计算startIndex
        int startIndex = (pageCurrent - 1) * pageSize;
        // 3.3)执行当前数据的查询操作
        List<SysLog> records = sysLogDao.findPageObjects(name, startIndex, pageSize);
        // 4.对分页信息以及当前页记录进行封装
        // 4.1)构建PageObject对象
        PageObject<SysLog> pageObject = new PageObject<>();
        // 4.2)封装数据
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setPageSize(pageSize);
        pageObject.setRowCount(rowCount);
        pageObject.setRecords(records);
        pageObject.setPageCount((rowCount - 1) / pageSize + 1);
        // 5.返回封装结果。
        return pageObject;
    }

    /**
     * @author Ericson
     * @date 2020/02/04 21:18
     * @param ids
     * @return
     * @see top.ericson.sys.service.SysLogService#deleteObjects(java.lang.Integer[])
     * @description 
     */
    @Override
    public int deleteObjects(Integer... ids) {
        // 1.判定参数合法性
        if (ids == null || ids.length == 0) {
            throw new IllegalArgumentException("没有选择记录");
        }
        // 2.执行删除操作
        int rows;
        try {
            rows = sysLogDao.deleteObjects(ids);
        } catch (Throwable e) {
            e.printStackTrace();
            // 发出报警信息(例如给运维人员发短信)
            throw new ServiceException("系统故障，正在恢复中...");
        }
        // 4.对结果进行验证
        if (rows == 0)
            throw new ServiceException("记录可能已经不存在");
        // 5.返回结果
        return rows;
    }

    @Async("asyncPoolExecutor")
    @Override
    // 异步返回值需要使用封装类
    public Future<Integer> saveObject(SysLog entity) {
        System.out.println("saveObject:"+Thread.currentThread());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int rows = sysLogDao.insertObject(entity);
        return new AsyncResult<Integer>(rows);
    }

}
