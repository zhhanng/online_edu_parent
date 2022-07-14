package com.atguigu.service;

import com.atguigu.entity.EduOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;
import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zh
 * @since 2022-07-12
 */
public interface EduOrderService extends IService<EduOrder> {

    String createOrder(String courseId,String memberId);

    Map<String, Object> createPayQrCode(String orderNo) throws Exception;

    Map<String, String> queryPayState(String orderNo) throws Exception;

    void updateOrderStatus(Map<String, String> txRetMap) throws ParseException;
}
