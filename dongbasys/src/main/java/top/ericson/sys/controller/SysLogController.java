package top.ericson.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.ericson.common.vo.JsonResult;
import top.ericson.common.vo.PageObject;
import top.ericson.sys.entity.SysLog;
import top.ericson.sys.service.SysLogService;

/**
 * @author Ericson
 * @class SysLogController
 * @date 2020/02/06 14:43
 * @description 
 * @version 1.0
 */
@Controller
@RequestMapping("/log/")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @ResponseBody
    @RequestMapping("doFindPageObjects")
    public JsonResult doFindPageObjects(String username, Integer pageCurrent) {
        PageObject<SysLog> pageObject = sysLogService.findPageObjects(username, pageCurrent);
        return new JsonResult(pageObject);
    }

    /**
     * @author Ericson
     * @date 2020/02/06 14:45
     * @param ids 要删除的id数组
     * @return 字符串的包装
     * @description 删除日志
     */
    @ResponseBody
    @RequestMapping("doDeleteObjects")
    public JsonResult doDeleteObjects(Integer... ids) {
        sysLogService.deleteObjects(ids);
        return new JsonResult("delete ok");
    }
}
