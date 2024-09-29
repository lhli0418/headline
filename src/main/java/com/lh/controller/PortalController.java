package com.lh.controller;

import com.lh.service.HeadlineService;
import com.lh.service.TypeService;
import com.lh.utils.Result;
import com.lh.vo.PortalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * ClassName: TypeController
 * package: com.lh.controller
 * Description:
 *
 * @Author lh
 * @Create 2024/9/27 21:56
 * @Version 1.0
 */

@RestController
@RequestMapping("portal")
public class PortalController {

    @Autowired
    private HeadlineService headlineService;

    @Autowired
    private TypeService typeService;

    @GetMapping("findAllTypes")
    public Result findAllTypes(){
       Result result = typeService.findAllTypes();
       return result;
    }

    /**
     * 首页分页查询
     * @param portalVo
     * @return
     */

    @PostMapping("findNewsPage")
    public Result findNewPage(@RequestBody PortalVo portalVo){
        Result result = headlineService.findNewPage(portalVo);
        return result;
    }


    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }
}
