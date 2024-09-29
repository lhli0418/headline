package com.lh.controller;

import com.lh.pojo.Headline;
import com.lh.service.HeadlineService;
import com.lh.utils.JwtHelper;
import com.lh.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;

/**
 * ClassName: HeadlineController
 * package: com.lh.controller
 * Description:
 *
 * @Author lh
 * @Create 2024/9/28 22:06
 * @Version 1.0
 */
@RestController
@RequestMapping("headline")
@CrossOrigin
public class HeadlineController {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private HeadlineService headlineService;

    @PostMapping("publish")
    public Result publish(@RequestHeader String token, @RequestBody Headline headline){
        Result result = headlineService.publish(token,headline);
        return result;
    }

    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid){
        Headline headline = headlineService.getById(hid);
        Map data = new HashMap();
        data.put("headline",headline);
        return Result.ok(data);
    }

    @PostMapping("updata")
    public Result updata(@RequestBody Headline headline){
        Result result = headlineService.updataHeadline(headline);
        return result;
    }

    @PostMapping("removeByHid")
    public Result removeByHid(Integer hid){

        headlineService.removeById(hid);
        return Result.ok(null);
    }

}
