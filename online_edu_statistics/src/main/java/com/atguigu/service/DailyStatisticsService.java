package com.atguigu.service;

import com.atguigu.entity.DailyStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author zh
 * @since 2022-07-08
 */
public interface DailyStatisticsService extends IService<DailyStatistics> {

    Map<String, Object> showStatistics(String dataType, String beginTime, String endTime);

    void generateData(String day);

}
