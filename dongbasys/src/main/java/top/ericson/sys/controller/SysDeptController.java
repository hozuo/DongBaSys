package top.ericson.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.ericson.common.vo.JsonResult;
import top.ericson.sys.entity.SysDept;
import top.ericson.sys.service.SysDeptService;

@RestController
@RequestMapping("/dept/")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @RequestMapping("doFindObjects")
    public JsonResult doFindObjects() {
        return new JsonResult(sysDeptService.findObjects());
    }

    @RequestMapping("doDeleteObject")
    public JsonResult doDeleteObject(Integer id) {
        sysDeptService.deleteObject(id);
        return new JsonResult("delete OK");
    }

    @RequestMapping("doDeptEditUI")
    public String doDeptEditUI() {
        return "sys/dept_edit";
    }

    @RequestMapping("doFindZtreeDeptNodes")
    public JsonResult doFindZtreeDeptNodes() {
        return new JsonResult(sysDeptService.findZtreeDeptNodes());
    }

    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(SysDept entity) {
        sysDeptService.saveObject(entity);
        return new JsonResult("save ok");
    }

    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(SysDept entity) {
        sysDeptService.updateObject(entity);
        return new JsonResult("update ok");
    }

}
