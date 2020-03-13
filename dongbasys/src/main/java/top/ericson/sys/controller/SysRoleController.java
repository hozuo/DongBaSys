package top.ericson.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.ericson.common.vo.JsonResult;
import top.ericson.common.vo.PageObject;
import top.ericson.sys.entity.SysRole;
import top.ericson.sys.service.SysRoleService;

@RestController
@RequestMapping("/role/")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("doRoleListUI")
    public String doRolelistUI() {
        return "sys/role_list";
    }

    @RequestMapping("doRoleEditUI")
    public String doRoleEditUI() {
        return "sys/role_edit";
    }

    @RequestMapping("doFindPageObjects")
    public JsonResult doFindPageObjects(String name, Integer pageCurrent) {
        PageObject<SysRole> pageObject = sysRoleService.findPageObjects(name, pageCurrent);
        return new JsonResult(pageObject);
    }

    @RequestMapping("doDeleteObject")
    public JsonResult doDeleteObject(Integer id) {
        sysRoleService.deleteObject(id);
        return new JsonResult("delete Ok");
    }

    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(SysRole entity, Integer[] menuIds) {
        sysRoleService.saveObject(entity, menuIds);
        return new JsonResult("save ok");
    }

    @RequestMapping("doFindObjectById")
    public JsonResult doFindObjectById(Integer id) {
        return new JsonResult(sysRoleService.findObjectById(id));
    }

    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(SysRole entity, Integer[] menuIds) {
        sysRoleService.updateObject(entity, menuIds);
        return new JsonResult("update ok");
    }
    
    @RequestMapping("doFindRoles")
    public JsonResult doFindRoles() {
        return new JsonResult(sysRoleService.findObjects());
    }


}