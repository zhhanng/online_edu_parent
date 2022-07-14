package com.atguigu.handler;

import com.atguigu.config.response.RetVal;
import com.atguigu.service.UserFeignService;

/**
 * Date:2022/7/8
 * Author:zh
 * Description:
 */
public class UserServiceHandlerImpl implements UserFeignService {
    @Override
    public RetVal getqueryRegistryNum(String day) {
        return RetVal.error().message("还有一大堆代码要写");
    }
}
