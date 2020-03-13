package top.ericson.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.ericson.common.vo.JsonResult;
import top.ericson.sys.entity.SysMenu;
import top.ericson.sys.service.SysMenuService;

// 类中的所有方法默认加@ResponseBody
@RestController
@RequestMapping("/menu/")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("doFindObjects")
    public JsonResult doFindObjects() {
        return new JsonResult(sysMenuService.findObjects());
    }

    /**
     * @author Ericson
     * @date 2020/02/06 15:16
     * @param id
     * @return
     * @description 删除菜单
     */
    @RequestMapping("doDeleteObject")
    public JsonResult doDeleteObject(Integer id) {
        sysMenuService.deleteObject(id);
        return new JsonResult("delete ok");
    }

    @RequestMapping("doFindZtreeMenuNodes")
    public JsonResult doFindZtreeMenuNodes() {
        return new JsonResult(sysMenuService.findZtreeMenuNodes());
    }
    
    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(SysMenu entity){
        sysMenuService.saveObject(entity);
        return new JsonResult("save ok");
    }

    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(SysMenu entity){
        sysMenuService.updateObject(entity);
        return new JsonResult("update ok");
    }


}
