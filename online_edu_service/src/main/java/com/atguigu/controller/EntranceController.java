package com.atguigu.controller;


import com.atguigu.config.response.RetVal;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2022-06-27
 */
@RestController
@RequestMapping("/edu")
@CrossOrigin
public class EntranceController {

    @PostMapping("/user/login")
    public RetVal login() {
        return RetVal.success().data("token", "admin");
    }

    @GetMapping("/user/info")
    public RetVal info() {
        return RetVal.success()
                .data("name", "admin")
                .data("roles", "[admin]")
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}

