package top.ericson.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
    // 首页
    @RequestMapping("doIndexUI")
    public String doIndexUI() {
        return "starter";
    }

    // 上一页下一页
    @RequestMapping("doPageUI")
    public String doPageUI() {
        return "common/page";
    }

    @RequestMapping("{module}/{moduleUI}")
    public String doModuleUI(@PathVariable String moduleUI) {
        return "sys/" + moduleUI;
    }

    @RequestMapping("doLoginUI")
    public String doLoginUI() {
        return "login";
    }

}
