package com.atguigu.controller;


import com.atguigu.config.response.MemberCenterVo;
import com.atguigu.config.response.RetVal;
import com.atguigu.service.MemberCenterService;
import com.atguigu.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zh
 * @since 2022-07-08
 */
@RestController
@RequestMapping("/member/center")
@CrossOrigin
public class MemberCenterController {
    @Autowired
    private MemberCenterService memberCenterService;

    @GetMapping("/queryRegistryNum/{day}")
    public RetVal getqueryRegistryNum(@PathVariable String day){
        Integer num = memberCenterService.queryRegistryNum(day);
        return RetVal.success().data("NUM",num);
    }

    //2.根据token获取用户的个人信息
    @GetMapping("getUserInfoByToken/{token}")
    public RetVal getUserInfoByToken(@PathVariable String token){
        Claims claims = JwtUtils.checkJWT(token);
        String id =(String) claims.get("id");
        String avatar =(String) claims.get("avatar");
        String nickname =(String) claims.get("nickname");
        MemberCenterVo memberCenter= new MemberCenterVo();
        memberCenter.setId(id);
        memberCenter.setNickname(nickname);
        memberCenter.setAvatar(avatar);
        return RetVal.success().data("memberCenterVo",memberCenter);
    }
}

