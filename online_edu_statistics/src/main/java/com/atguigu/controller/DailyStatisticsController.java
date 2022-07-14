package com.atguigu.controller;


import com.atguigu.config.response.RetVal;
import com.atguigu.service.DailyStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author zh
 * @since 2022-07-08
 */
@RestController
@RequestMapping("/daily/statistics")
@CrossOrigin
public class DailyStatisticsController {

    @Autowired
    private DailyStatisticsService dailyStatisticsService;

    @GetMapping("/generateData/{day}")
    public RetVal getAllMember(@PathVariable String day) {
        dailyStatisticsService.generateData(day);
        return RetVal.success();
    }

    @GetMapping("/showStatistics/{dataType}/{beginTime}/{endTime}")
    public RetVal showStatistics(@PathVariable("dataType") String dataType,
                                 @PathVariable("beginTime") String beginTime,
                                 @PathVariable("endTime") String endTime) {
        Map<String,Object> datamap =dailyStatisticsService.showStatistics(dataType,beginTime,endTime);
        return RetVal.success().data(datamap);
    }


}

