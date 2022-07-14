package com.atguigu.controller;


import com.atguigu.config.response.RetVal;
import com.atguigu.entity.EduOrder;
import com.atguigu.service.EduOrderService;
import com.atguigu.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author zh
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/edu/order")
@CrossOrigin
public class EduOrderController {
    @Autowired
    private EduOrderService eduOrderService;

    /**
     * 创建订单的方法,需要知道是哪个课程创建了订单,
     * 需要在这个方法中拿到用户的id
     * @param courseId
     * @return
     */
    @GetMapping("createOrder/{courseId}")
    public RetVal createOrder(@PathVariable String courseId, HttpServletRequest httpServletRequest) {
       String memberId = JwtUtils.getMemberIdByJwtToken(httpServletRequest);
       String orderNo = eduOrderService.createOrder(courseId,memberId);
        return RetVal.success().data("orderNo",orderNo);
    }

    /**
     * 微信支付的接口,返回二维码,订单id以及课程id
     * 封装在map里边
     * @param orderId
     * @return
     * @throws Exception
     */
    @GetMapping("createPayQrCode/{orderId}")
    public RetVal createPayQrCode(@PathVariable String orderId) throws Exception {
        Map<String, Object> payQrCode = eduOrderService.createPayQrCode(orderId);
        return RetVal.success().data(payQrCode);
    }


    /**
     * 查询支付状态的接口,发现付款成功后就修改订单相关的信息
     * @param orderId:用户的商单id
     * @return
     */
    @GetMapping("queryPayState/{orderId}")
    public RetVal queryPayState(@PathVariable String orderId) throws Exception {
        Map<String, String> txRetMap = eduOrderService.queryPayState(orderId);
        if(txRetMap.get("trade_state").equals("SUCCESS")){
            //支付成功之后 做相应事情
            eduOrderService.updateOrderStatus(txRetMap);
            return RetVal.success().message("支付成功");
        }else{
            return RetVal.error().message("支付失败");
        }
    }

    @GetMapping("queryOrderById/{orderNo}")
    public RetVal queryOrderById(@PathVariable String orderNo){
        QueryWrapper<EduOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderNo);
        EduOrder eduOrder = eduOrderService.getOne(queryWrapper);
        return RetVal.success().data("orderInfo",eduOrder);
    }
}

