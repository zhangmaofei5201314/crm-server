package com.donbala.quartzManagement.service;

import com.donbala.quartzManagement.model.Quartz;

import java.util.List;
import java.util.Map;

/**
 * @CLassName: QuartzServiceIntf
 * @Program: springbootdemo
 * @Author: wangran
 * @Date: 2019/8/7-17:24
 * @Description: todo
 **/
public interface QuartzServiceIntf {
      void initJobsOnstart();

      List<Quartz> getJobPlanList();

      List<Quartz> getJobParamList(String jobCode);

      public Map<String,Object> insertJobAndParam(Quartz qz);

      public Map<String, Object> startJob(Quartz qz);

      public Map<String,Object> stopJob(Quartz qz);

      public Map<String, Object> updateJobPlanAndParam(Quartz qz);

      public Map<String, Object> removeJob(Quartz qz);

      public Map<String, Object> selectReturnView(Quartz qz);
}
