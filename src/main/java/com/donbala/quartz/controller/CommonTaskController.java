package com.donbala.quartz.controller;

import com.donbala.quartz.model.CommonTaskModel;
import com.donbala.quartz.service.CommonTaskServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 *
 * @date 2019/9/20
 */
@RestController
public class CommonTaskController {
    @Autowired
    CommonTaskServie commonTaskService;
    /**
     * 查询日志记录
     * @param commonTaskModel
     * @return
     */
    @RequestMapping(value="/controller/quartz/runlog/search",method= RequestMethod.POST)
    public Map<String,Object> selectJobRunLog(CommonTaskModel commonTaskModel){
        System.out.println(commonTaskModel.getLimit());
        System.out.println(commonTaskModel.getOffset());
        System.out.println(commonTaskModel.getJobCode());
        System.out.println(commonTaskModel.getStartDate());
        return commonTaskService.selectJobRunLog(commonTaskModel);
    }
}
