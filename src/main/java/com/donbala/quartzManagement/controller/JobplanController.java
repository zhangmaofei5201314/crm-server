package com.donbala.quartzManagement.controller;

import ch.qos.logback.classic.Logger;
import com.donbala.loginManagement.controller.LoginController;
import com.donbala.quartzManagement.model.Quartz;
import com.donbala.quartzManagement.service.QuartzServiceIntf;
import com.donbala.userManagement.model.CmsUser;
import com.donbala.userManagement.service.CmsUserServiceIntf;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @CLassName: JobplanController
 * @Program: springbootdemo
 * @Author: wangran
 * @Date: 2019/8/8-14:51
 * @Description: todo
 **/
@RestController
public class JobplanController {

    public final static Logger log = (Logger) LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private QuartzServiceIntf quartzServiceIntf;
    @Autowired
    private CmsUserServiceIntf cmsUserServiceIntf;

    @GetMapping("/controller/queryjobplanlist")
    public List<Quartz> getJobPlanList() {

        return quartzServiceIntf.getJobPlanList();
    }
    /**
     * 获取作业参数
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/18 11:15
     * @param
     * @return java.util.List<com.donbala.quartzManagement.model.Quartz>
     */
    @RequestMapping(value="/controller/quartz/management/getJobParamList",method= RequestMethod.POST)
    public List<Quartz> getJobParamList(String jobCode){
        List<Quartz> list=quartzServiceIntf.getJobParamList(jobCode);
        return list;
    }
    /**
     * 新建一个任务
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/19 11:38
     * @param quartz 1
     * @param session 2
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value="/controller/quartz/management/insertJob",method= RequestMethod.POST)
    public Map<String, Object> insertJobPlan(Quartz quartz, HttpSession session){
//        String user=((CmsUser) session.getAttribute("user")).getUsercode();
        CmsUser userByToken = cmsUserServiceIntf.getUserByToken(quartz.getToken());
        String user = userByToken.getName();
        quartz.setMakeUser(user);
        quartz.setModifyUser(user);
        Map<String, Object> map=quartzServiceIntf.insertJobAndParam(quartz);
        return map;
    }
    /**
     * 启动任务
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/19 16:16
     * @param quartz 1
     * @param session 2
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value="/controller/quartz/management/startJob",method= RequestMethod.POST)
    public Map<String, Object> startJobPlan(Quartz quartz, HttpSession session){
        System.out.println("进来");
//        String user=((CmsUser) session.getAttribute("user")).getUsercode();
        CmsUser userByToken = cmsUserServiceIntf.getUserByToken(quartz.getToken());
        String user = userByToken.getName();
        quartz.setMakeUser(user);
        quartz.setModifyUser(user);
        Map<String, Object> map=quartzServiceIntf.startJob(quartz);
        return map;
    }
    /**
     * 停止任务
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/19 18:05
     * @param quartz 1
     * @param session 2
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value="/controller/quartz/management/stopJob",method= RequestMethod.POST)
    public Map<String, Object> stopJobPlan(Quartz quartz, HttpSession session){
        System.out.println("进来");
//        String user=((CmsUser) session.getAttribute("user")).getUsercode();
        CmsUser userByToken = cmsUserServiceIntf.getUserByToken(quartz.getToken());
        String user = userByToken.getName();
        quartz.setMakeUser(user);
        quartz.setModifyUser(user);
        Map<String, Object> map=quartzServiceIntf.stopJob(quartz);
        return map;
    }
    /**
     * 删除一个任务
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/19 18:29
     * @param quartz 1
     * @param session 2
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value="/controller/quartz/management/deleteJob",method= RequestMethod.POST)
    public Map<String, Object> deleteJobPlan(Quartz quartz, HttpSession session){
        System.out.println("进来");
//        String user=((CmsUser) session.getAttribute("user")).getUsercode();
        CmsUser userByToken = cmsUserServiceIntf.getUserByToken(quartz.getToken());
        String user = userByToken.getName();
        quartz.setMakeUser(user);
        quartz.setModifyUser(user);
        Map<String, Object> map=quartzServiceIntf.removeJob(quartz);
        return map;
    }
    /**
     * 计划回显
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/20 9:48
     * @param qz 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/controller/quartz/management/returnJobView",method=RequestMethod.POST)
    public Map<String,Object> selectReturnView(@RequestBody Quartz qz) {
        return quartzServiceIntf.selectReturnView(qz);
    }
    /**
     * 修改计划
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/20 12:01
     * @param quartz 1
     * @param session 2
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value="/controller/quartz/management/editJobPlan",method= RequestMethod.POST)
    public Map<String, Object> editJobPlan(Quartz quartz, HttpSession session){
        System.out.println("修改");
//        String user=((CmsUser) session.getAttribute("user")).getUsercode();
        CmsUser userByToken = cmsUserServiceIntf.getUserByToken(quartz.getToken());
        String user = userByToken.getName();
        quartz.setMakeUser(user);
        quartz.setModifyUser(user);
        Map<String, Object> map=quartzServiceIntf.updateJobPlanAndParam(quartz);
        return map;
    }
}
