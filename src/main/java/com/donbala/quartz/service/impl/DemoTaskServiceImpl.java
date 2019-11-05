package com.donbala.quartz.service.impl;

import ch.qos.logback.classic.Logger;
import com.donbala.quartz.dao.CommonTaskDao;
import com.donbala.quartz.model.CommonTaskModel;
import com.donbala.quartz.service.DemoTaskService;
import com.donbala.util.DateUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 * 案例任务
 * @date 2019/9/20
 */
@Service
public class DemoTaskServiceImpl implements DemoTaskService {

    public final static Logger log = (Logger) LoggerFactory.getLogger(DemoTaskServiceImpl.class);
    @Autowired
    CommonTaskDao commonTaskDao;

    /**
     * 案例
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/20 15:40
     * @param jobCode 1
     * @param jobPlanCode 2
     * @return void
     */
    @Override
    public void demoTask(String jobCode, String jobPlanCode) {
        CommonTaskModel commonTaskModel = new CommonTaskModel();
        commonTaskModel.setJobCode(jobCode);
        commonTaskModel.setJobPlanCode(jobPlanCode);
        commonTaskModel.setRunState("0");
        String currentDate = DateUtil.getSysDate();
        log.info("开始时间："+currentDate);
        commonTaskModel.setStartDate(currentDate);
        commonTaskModel.setMakeDate(currentDate);
        commonTaskModel.setModifyDate(currentDate);
        //添加执行开始日志
        commonTaskDao.insertJobRunLog(commonTaskModel);

        //查询计划的上一次成功时间
        String lastSuccessDate = commonTaskDao.selectLastSuccessDateByJobPlan(commonTaskModel);
        if("".equals(lastSuccessDate) || lastSuccessDate == null) {
            lastSuccessDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 00:00:00";
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lastSuccessDate));
            calendar.add(Calendar.HOUR_OF_DAY,-2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lastSuccessDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        log.info("上次成功日期为："+lastSuccessDate);

        String errMsg = "";


        //具体业务逻辑
        log.info("案例任务 "+currentDate+" 执行了一次！");

        errMsg = "success";




        commonTaskModel.setRunState("1");
        if("success".equals(errMsg)){
            commonTaskModel.setRunResult("成功");
        }else{
            commonTaskModel.setRunResult("失败");
        }
        String date = DateUtil.getSysDate();
        log.info("结束时间："+date);
        commonTaskModel.setEndDate(date);
        commonTaskModel.setModifyDate(date);
        //更新执行结果
        commonTaskDao.updateJobRunLogBySerialno(commonTaskModel);
        log.info("更新日志完毕...");


    }
}
