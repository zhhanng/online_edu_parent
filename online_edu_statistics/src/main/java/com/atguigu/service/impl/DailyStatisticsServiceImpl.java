package com.atguigu.service.impl;

import com.atguigu.config.response.RetVal;
import com.atguigu.entity.DailyStatistics;
import com.atguigu.mapper.DailyStatisticsMapper;
import com.atguigu.service.DailyStatisticsService;
import com.atguigu.service.UserFeignService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author zh
 * @since 2022-07-08
 */
@Service
public class DailyStatisticsServiceImpl extends ServiceImpl<DailyStatisticsMapper, DailyStatistics> implements DailyStatisticsService {

    @Autowired
    private UserFeignService userFeignService;


    /**
     * 模拟每天的各项数据,就是随机生成的模拟一下
     * @param day:哪一天
     */
    @Override
    public void generateData(String day) {
        RetVal retVal = userFeignService.getqueryRegistryNum(day);
        Integer registerNum = (Integer) retVal.getData().get("NUM");
        DailyStatistics statistics = new DailyStatistics();
        //设置相关的值
        statistics.setDateCalculated(day);
        statistics.setRegisterNum(RandomUtils.nextInt(1,6));
        statistics.setLoginNum(RandomUtils.nextInt(200, 300));
        statistics.setVideoViewNum(RandomUtils.nextInt(500, 600));
        statistics.setCourseNum(RandomUtils.nextInt(100, 200));
        baseMapper.insert(statistics);
    }

    /**
     * 前端查询树状图的方法,返回满足要求的数据
     * @param dataType:要查询的种类
     * @param beginTime:开始时间
     * @param endTime:结束时间
     * @return:返回一个集合,分别是横坐标和纵坐标
     */
    @Override
    public Map<String, Object> showStatistics(String dataType, String beginTime, String endTime) {
        QueryWrapper<DailyStatistics> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", beginTime, endTime);
        List<DailyStatistics> dailyStatistics = baseMapper.selectList(wrapper);
        Map<String, Object> retMap=new HashMap<>();
        //返回X轴信息,是日期
        ArrayList<String> xData = new ArrayList<>();
        //返回y轴类型,是integar
        ArrayList<Integer> yData = new ArrayList<>();
        for (DailyStatistics dailyStatistic : dailyStatistics) {
            //把日期放入xData中
            xData.add(dailyStatistic.getDateCalculated());
            switch (dataType) {
                case "login_num":
                    Integer loginNum = dailyStatistic.getLoginNum();
                    yData.add(loginNum);
                    break;
                case "register_num":
                    Integer registerNum = dailyStatistic.getRegisterNum();
                    yData.add(registerNum);
                    break;
                case "video_view_num":
                    Integer videoViewNum = dailyStatistic.getVideoViewNum();
                    yData.add(videoViewNum);
                    break;
                case "course_num":
                    Integer courseNum = dailyStatistic.getCourseNum();
                    yData.add(courseNum);
                    break;
                default:
                    break;
            }
        }
        retMap.put("xData",xData);
        retMap.put("yData",yData);
        return retMap;
    }
}
