package com.atguigu.service.impl;

import com.atguigu.entity.EduOrder;
import com.atguigu.entity.EduPayLog;
import com.atguigu.mapper.EduOrderMapper;
import com.atguigu.service.EduOrderService;
import com.atguigu.service.EduPayLogService;
import com.atguigu.utils.HttpClient;
import com.atguigu.utils.OrderNoUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zh
 * @since 2022-07-12
 */
@Service
public class EduOrderServiceImpl extends ServiceImpl<EduOrderMapper, EduOrder> implements EduOrderService {
    @Value("${wx.pay.app_id}")
    private String WX_PAY_APP_ID;
    @Value("${wx.pay.mch_id}")
    private String WX_PAY_MCH_ID;
    @Value("${wx.pay.spbill_create_ip}")
    private String WX_PAY_SPBILL_IP;
    @Value("${wx.pay.notify_url}")
    private String WX_PAY_NOTIFY_URL;
    @Value("${wx.pay.xml_key}")
    private String WX_PAY_XML_KEY;


    @Autowired
    private EduPayLogService payLogService;

    /**
     * 创建订单的方法,还需要去查询课程的title,cover等等,原本的逻辑应该是远程调用其他表进行查询
     * 这里简单的进行赋值,不再查询
     * @param courseId :课程id,购买了哪节课
     * @param memberId :账户id,哪个用户购买了
     * @return
     */
    @Override
    public String createOrder(String courseId, String memberId) {
        EduOrder order = new EduOrder();
        //a.生成订单需要课程id,用户id
        //b.订单号不能重复 需要唯一(不能自动增长)
        String orderNo = OrderNoUtil.getOrderNo();
        order.setOrderNo(orderNo);
        //c.课程信息需要远程RPC调用其他微服务
        order.setCourseId(courseId);
        order.setCourseTitle("拉夏贝尔 La Chapelle 连衣裙女2022年夏季");
        order.setCourseCover("https://img14.360buyimg.com/n7/jfs/t1/152569/34/9686/214300/6061d553Ef0abc16a/f0f11d04434ac1a9.jpg");
        //d.用户信息需要远程RPC调用其他微服务
        order.setTeacherName("李老师");
        order.setMemberId(memberId);
        order.setNickName("王xxx");
        order.setMobile("123434563");
        order.setTotalFee(new BigDecimal(0.01));
        order.setPayType(1);
        order.setStatus(0);
        //e.保存到数据库里面
        baseMapper.insert(order);
        //f.返回一个订单号
        return orderNo;
    }

    /**
     * 与微信支付进行交互的接口,按照要求发送相关的信息给微信,
     * 微信会返回相关的数据,转成map进行解析(包括二维码)
     * @param orderNo:前端传过来的orderNo,干嘛的?查到相关的订单信息用的,比如说订单金额
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> createPayQrCode(String orderNo) throws Exception{
        QueryWrapper<EduOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderNo);
        EduOrder order = baseMapper.selectOne(queryWrapper);
        //下边都是与微信进行交互所要求的描述.放入map中
        Map<String, String> requestParams = new HashMap<>();
        //公众账号ID
        requestParams.put("appid",WX_PAY_APP_ID);
        //商户号
        requestParams.put("mch_id",WX_PAY_MCH_ID);
        //随机字符串
        requestParams.put("nonce_str", WXPayUtil.generateNonceStr());
        //商品描述
        requestParams.put("body",order.getCourseTitle());
        //商户订单号
        requestParams.put("out_trade_no",orderNo);
        //标价金额
        String totalFee=order.getTotalFee().multiply(new BigDecimal(100)).intValue()+"";
        requestParams.put("total_fee",totalFee);
        //终端IP
        requestParams.put("spbill_create_ip",WX_PAY_SPBILL_IP);
        //通知地址
        requestParams.put("notify_url",WX_PAY_NOTIFY_URL);
        //交易类型
        requestParams.put("trade_type","NATIVE");
        //b.该接口接受的是xml
        String xmlParams=WXPayUtil.generateSignedXml(requestParams,WX_PAY_XML_KEY);
        //c.调用接口
        HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        httpClient.setXmlParam(xmlParams);
        httpClient.setHttps(true);
        httpClient.post();
        //d.得到返回值 进行解析
        String content = httpClient.getContent();
        Map<String, String> txRetMap = WXPayUtil.xmlToMap(content);
        String qrCodeUrl = txRetMap.get("code_url");

        Map<String, Object> retMap=new HashMap<>();
        //二维码
        retMap.put("qrCodeUrl",qrCodeUrl);
        //订单号,回显用的
        retMap.put("orderNo",orderNo);
        //本次的总金额
        retMap.put("totalFee",order.getTotalFee());
        //付完款后跳转到来时的页面
        retMap.put("courseId",order.getCourseId());
        return retMap;
    }


    /**
     * 该方法是为了查询用户有没有付款,查询支付信息,到不了内网,需要自己去查询
     * @param orderNo
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> queryPayState(String orderNo) throws Exception {
        //a.需要传订单号
        //b.按照上面的三步骤走(拼接条件 请求接口 解析结果)
        //a.拼接组织好微信下单需要的数据
        Map<String, String> requestParams = new HashMap<>();
        //公众账号ID
        requestParams.put("appid",WX_PAY_APP_ID);
        //商户号
        requestParams.put("mch_id",WX_PAY_MCH_ID);
        //随机字符串
        requestParams.put("nonce_str",WXPayUtil.generateNonceStr());
        //商户订单号
        requestParams.put("out_trade_no",orderNo);
        //b.该接口接受的是xml
        String xmlParams=WXPayUtil.generateSignedXml(requestParams,WX_PAY_XML_KEY);
        //c.调用接口
        HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
        httpClient.setXmlParam(xmlParams);
        httpClient.setHttps(true);
        httpClient.post();
        //d.得到返回值 进行解析
        String content = httpClient.getContent();
        Map<String, String> txRetMap = WXPayUtil.xmlToMap(content);
        return txRetMap;
    }

    /**
     * 这个方法是来更新order表中的状态和添加相关的paylog信息,修改操作涉及到两张表
     * 需要添加事务
     * @param txRetMap
     * @throws ParseException
     */
    @Transactional
    @Override
    public void updateOrderStatus(Map<String, String> txRetMap) throws ParseException {
        String orderNo = txRetMap.get("out_trade_no");
        QueryWrapper<EduOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        EduOrder order = baseMapper.selectOne(wrapper);
        order.setStatus(1);
        baseMapper.updateById(order);

        //先查询支付日志里面是否有记录
        QueryWrapper<EduPayLog> logWrapper = new QueryWrapper<>();
        logWrapper.eq("order_no", orderNo);
        EduPayLog payLog = payLogService.getOne(logWrapper);
        if (payLog == null) {
            //b.往支付日志里面添加记录
            payLog = new EduPayLog();
            payLog.setOrderNo(orderNo);
            //20220712142214
            String timeEnd = txRetMap.get("time_end");
            Date payTime = new SimpleDateFormat("yyyyMMddHHmmss").parse(timeEnd);
            payLog.setPayTime(payTime);
            payLog.setTotalFee(order.getTotalFee());
            //微信那边的订单id
            payLog.setTransactionId(txRetMap.get("transaction_id"));
            payLog.setTradeState(txRetMap.get("trade_state"));
            payLog.setPayType(1);
            payLogService.save(payLog);
        }
    }
}
