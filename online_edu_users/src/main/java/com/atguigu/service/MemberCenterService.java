package com.atguigu.service;

import com.atguigu.entity.MemberCenter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zh
 * @since 2022-07-08
 */
public interface MemberCenterService extends IService<MemberCenter> {

    Integer queryRegistryNum(String day);
}
