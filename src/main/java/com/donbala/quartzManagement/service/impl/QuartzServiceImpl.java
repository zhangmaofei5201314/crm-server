package com.donbala.quartzManagement.service.impl;

import ch.qos.logback.classic.Logger;
import com.donbala.quartz.MyTriggerListener;
import com.donbala.quartz.QuartzUtils;
import com.donbala.quartzManagement.dao.QuartzMapper;
import com.donbala.quartzManagement.model.Quartz;
import com.donbala.quartzManagement.service.QuartzServiceIntf;
import com.donbala.util.DateUtil;
import org.quartz.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *   定义运行状态    useFlag   trggerState
 *     停止运行        0         无
 *     等待运行        1         0
 *     正在运行        1         1
 * 这里不验证IP了，代码还保留，需要的话放开
 * @CLassName: QuatzService
 * @Program: springbootdemo
 * @Author: wangran
 * @Date: 2019/8/2-15:18
 * @Description: todo
 **/
@Service
public class QuartzServiceImpl implements QuartzServiceIntf {

    public final static Logger log = (Logger) LoggerFactory.getLogger(QuartzServiceImpl.class);

    @Autowired
    QuartzMapper quartzMapper;


    @Autowired
    private SchedulerFactoryBean schedulerFactory;


/**
*@description: 获取所有的定时任务添加到调度器里面
*/
    public void initJobsOnstart() {
        log.info("开始初始化所有批处理任务");
        List<Quartz> list = quartzMapper.getAvailableJobPlan();
        if(list.size()>0) {
            for (Quartz qz : list) {
                //逐个将启动的任务计划添加到任务调度器中
                try {
                    addQuartz(qz);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            log.info("任务调度初始化完成");
        }
    }




    public  void addQuartz(Quartz qz) throws Exception {
        //根据jobPlanCode确定唯一的作业名称，作业组名，触发器名称，触发器组名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取作业code
        String jobCode = qz.getJobCode();
        //获取作业计划code
        String jobPlanCode = qz.getJobPlanCode();
        //获取要执行的job类名
        String className = qz.getJobClassName();

        Class<?> clazz = Class.forName(className);
        String cron = QuartzUtils.cron(qz);
        log.info(cron);
        Date startDate = sdf.parse(qz.getStartDate());
        Date endDate = sdf.parse(qz.getEndDate());
        String runType = qz.getRunType();
        if(runType.equals("0")){//cron定时任务
            addJob(jobCode, jobPlanCode, jobPlanCode, jobPlanCode, clazz, cron, startDate, endDate);
        }else if(runType.equals("1")){//指定间隔任务
            long interval = getInterval(qz);
            System.out.println("间隔："+interval);
            addJob2(jobCode, jobPlanCode, jobPlanCode, jobPlanCode, clazz, interval, startDate, endDate);
        }
    }
    /**
     * 获取执行间隔
     * 单位超过'天'的,建议使用cron
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/29 12:22
     * @param qz 1
     * @return int
     */
    public long getInterval(Quartz qz){
        int interval=0;
        int repeatInterval= Integer.parseInt(qz.getRepeatInterval());
        String repeatUnit = qz.getRepeatUnit();
        if(repeatUnit.equals("1")){//秒
            interval=repeatInterval*1000;
        } else if (repeatUnit.equals("2")) {//分钟
            interval=repeatInterval*60*1000;

        }else if (repeatUnit.equals("3")) {//小时
            interval=repeatInterval*60*60*1000;

        }else if (repeatUnit.equals("4")) {//天
            interval=repeatInterval*24*60*60*1000;

        }else if (repeatUnit.equals("5")) {//月
            interval=repeatInterval*30*24*60*60*1000;

        }else if (repeatUnit.equals("6")) {//周
            interval=repeatInterval*7*24*60*60*1000;

        }

        return interval;
    }
    /**
     * 添加一个指定间隔的任务
     * 有问题，错过时间会立即补跑
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/29 11:12
     * @param jobName 1
     * @param jobGroupName 2
     * @param triggerName 3
     * @param triggerGroupName 4
     * @param jobClass 5
     * @param interval 6 单位：毫秒
     * @param startDate 7
     * @param endDate 8
     * @return void
     */
    public void addJob2(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass,
                        long interval,Date startDate,Date endDate){
        try {
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder =   TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            //modify by kongxz in 2018-09-20 更改触发器执行开始时间 不是刚创建就执行  而是根据表达式执行
            //triggerBuilder.startNow();
            //触发器开始时间
            triggerBuilder.startAt(startDate);
            //触发器结束时间
            triggerBuilder.endAt(endDate);
            // 触发器时间设定
            triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInMilliseconds(interval) //时间间隔
                            //.withRepeatCount(5)//指定执行次数
                            .repeatForever()//永远的执行下去
                            .withMisfireHandlingInstructionNextWithRemainingCount() // 重复次数
//                            .withMisfireHandlingInstructionNextWithExistingCount()

                    );
            // 创建Trigger对象
//            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            SimpleTrigger trigger= (SimpleTrigger) triggerBuilder.build();
//            Trigger trigger = triggerBuilder.build();
            Scheduler scheduler = schedulerFactory.getScheduler();
            /*添加TriggerListener*/
            TriggerListener myTriggerListener = new MyTriggerListener();
            scheduler.getListenerManager().addTriggerListener(myTriggerListener);
            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @Description: 添加一个cron定时任务
     *
     * @param jobName
     *            任务名
     * @param jobGroupName
     *            任务组名
     * @param triggerName
     *            触发器名
     * @param triggerGroupName
     *            触发器组名
     * @param jobClass
     *            任务
     * @param cron
     *            时间设置，参考quartz说明文档
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass,
                       String cron, Date startDate, Date endDate) throws Exception {

        // 任务名，任务组，任务执行类
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

        // 触发器
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
        // 触发器名,触发器组
        triggerBuilder.withIdentity(triggerName, triggerGroupName);
        //modify by kongxz in 2018-09-20 更改触发器执行开始时间 不是刚创建就执行  而是根据表达式执行
        //triggerBuilder.startNow();
        //触发器开始时间
        triggerBuilder.startAt(startDate);
        //触发器结束时间
        triggerBuilder.endAt(endDate);
        // 触发器时间设定
        triggerBuilder.withSchedule(
                CronScheduleBuilder.cronSchedule(cron)
                        //add by kongxz in 2018-09-25
                        //如果触发器错过触发时间(例如手动暂停、服务器挂了),当启动任务后或服务器恢复正常后
                        //任务将从下一次触发时间再执行,否则停止任务再重新启动或服务器挂掉重新启动后任务会立即执行
                        .withMisfireHandlingInstructionDoNothing());
        // 创建Trigger对象
        CronTrigger trigger = (CronTrigger) triggerBuilder.build();
        Scheduler scheduler = schedulerFactory.getScheduler();
        /*添加TriggerListener*/
        TriggerListener myTriggerListener = new MyTriggerListener();
        scheduler.getListenerManager().addTriggerListener(myTriggerListener);
        // 调度容器设置JobDetail和Trigger
        scheduler.scheduleJob(jobDetail, trigger);
        // 启动
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }

    }


    /**
     * 查询任务表格
     * @return
     */
    @Override
    public List<Quartz> getJobPlanList() {
        return quartzMapper.getAllJobPlan();
    }
    /**
     * 查询作业参数
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/18 11:26
     * @param jobCode 1
     * @return java.util.List<com.donbala.quartzManagement.model.Quartz>
     */
    @Override
    public List<Quartz> getJobParamList(String jobCode) {
        Quartz quartz = new Quartz();
        quartz.setJobCode(jobCode);
        return quartzMapper.selectJobParam(quartz);
    }
    /**
     * 新增作业计划
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/19 9:26
     * @param qz 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED,propagation= Propagation.REQUIRED)
    public Map<String, Object> insertJobAndParam(Quartz qz) {
        if(qz.getParamValue()==null || "".equals(qz.getParamValue())) {
            Map<String,Object> map = new HashMap<>();
            map.put("status", "warning");
            map.put("msg", "参数为空");
            return map;
        }
        //这里的paramvalue是多个，默认第一个参数是IP
//        String paramIP = qz.getParamValue().split(",")[0];
        String paramIP = DateUtil.getLocalHostIP();
        String localIP = DateUtil.getLocalHostIP();
        if(!paramIP.equals(localIP)) {
            Map<String,Object> map = new HashMap<>();
            map.put("status", "ipError");
            return map;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        if(qz.getStartDate()==null || qz.getStartDate().equals("")) {
            qz.setStartDate(date);
        }
        if(qz.getEndDate()==null || qz.getEndDate().equals("")) {
            qz.setEndDate("9999-12-31 00:00:00");
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        qz.setJobPlanCode(uuid);
        qz.setMakeDate(date);
        qz.setModifyDate(date);
        //新增作业计划时，默认设置为停止，需要手动启动
        qz.setUseFlag("0");
        qz.setTriggerState("0");
        //算出cron表达式，存放到数据库中 add by kongxz in 2019-05-24
        qz.setCronExp(QuartzUtils.cron(qz));
        qz.setRunType("0");//cron任务
        quartzMapper.insertJobPlanDef(qz);
        //前端参数是这么封装的   IP:10.1.18.56,endDate:2019-10-23 15:10:20,startDate:2019-10-23 15:10:19
        String[] values = qz.getParamValue().split(",");
        if(qz.getParamValue()!=null && !qz.getParamValue().equals("")) {

            List<Quartz> paramNames=quartzMapper.selectJobParam(qz);
            for(int i=0;i<values.length;i++) {
                for(int j=0;j<paramNames.size();j++) {
                    if(values[i].split(":")[0].equals(paramNames.get(j).getParamCode())){
                        qz.setParamCode(paramNames.get(j).getParamCode());
                        qz.setParamValue(values[i].split(":")[1]);
                        quartzMapper.insertJobPlanParam(qz);
                    }
                }
            }
        }

        //将新生成的作业添加到调度里，开始执行作业
        //Common.addQuartz(qz,quartzService);
        Map<String,Object> map = new HashMap<>();
        map.put("code", "200");
        map.put("status", "success");
        map.put("msg", "执行计划新增成功");
        return map;
    }
    /**
     * 启动一个任务
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/19 11:41
     * @param qz 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED,propagation= Propagation.REQUIRED)
    public Map<String, Object> startJob(Quartz qz) {
        if(qz.getParamValue()==null || "".equals(qz.getParamValue())) {
            Map<String,Object> map = new HashMap<>();
            map.put("status", "fail");
            map.put("msg", "IP参数为空");
            return map;
        }
//        String paramIP = qz.getParamValue().split(",")[0];
        String paramIP = DateUtil.getLocalHostIP();
        String localIP = DateUtil.getLocalHostIP();
        if(!paramIP.equals(localIP)) {
            Map<String,Object> map = new HashMap<>();
            map.put("status", "ipError");
            return map;
        }
        String jobPlanCode = qz.getJobPlanCode();
        Map<String,String> map = new HashMap<String,String>();
        map.put("jobPlanCode", jobPlanCode);
        //根据参数查询数据
        Quartz dealQz = quartzMapper.selectStopJobPlan(map);
        //改变数据库数据的状态
        quartzMapper.startJobPlanDef(map);
        //更新日志
//        Map<String,String> map1 = new HashMap<String,String>();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = df.format(new Date());
//        map1.put("runState", "1");
//        map1.put("jobPlanCode", jobPlanCode);
//        String serialNo=quartzMapper.selectJobRunLogSerialNO(map1);
//        map1.put("serialNo", serialNo);
//        map1.put("modifyDate", date);
//        quartzMapper.updateJobRunLog(map1);
        Map<String,Object> resultMap = new HashMap<>();
        String runState = quartzMapper.selectJobPlanState(jobPlanCode).getRunState();
        try {
            //将从数据库查询到的作业添加到调度里，开始执行作业
            addQuartz(dealQz);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("jobPlanCode", jobPlanCode);
            resultMap.put("runState", runState);
            resultMap.put("code", "500");
            resultMap.put("status", "failed");
            resultMap.put("msg", "启动作业失败");
        }

        resultMap.put("jobPlanCode", jobPlanCode);
        resultMap.put("runState", runState);
        resultMap.put("code", "200");
        resultMap.put("status", "success");
        resultMap.put("msg", "启动作业成功");
        return resultMap;
    }
    /**
     * 停止一个任务
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/19 13:43
     * @param qz 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    @Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)
    public Map<String, Object> stopJob(Quartz qz) {
        if(qz.getParamValue()==null || "".equals(qz.getParamValue())) {
            Map<String,Object> map = new HashMap<>();
            map.put("status", "fail");
            map.put("msg", "IP参数为空");
            return map;
        }
//        String paramIP = qz.getParamValue().split(",")[0];
        String paramIP = DateUtil.getLocalHostIP();
        String localIP = DateUtil.getLocalHostIP();
        if(!paramIP.equals(localIP)) {
            Map<String,Object> map = new HashMap<>();
            map.put("status", "ipError");
            return map;
        }

        String jobPlanCode = qz.getJobPlanCode();
        Map<String,String> map = new HashMap<String,String>();
        map.put("jobPlanCode", jobPlanCode);
        //改变数据库数据的状态
        quartzMapper.stopJobPlanDef(map);

        Scheduler scheduler = schedulerFactory.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobPlanCode, jobPlanCode);
        try {
            //根据触发器的key值，停止这个触发器,并且移除
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            //删除任务
            scheduler.deleteJob(JobKey.jobKey(jobPlanCode, jobPlanCode));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("code", "200");
        returnMap.put("runState", "停止运行");
        returnMap.put("status", "success");
        returnMap.put("msg", "停止作业成功");
        return returnMap;
    }
    /**
     * 修改一个任务
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/19 15:09
     * @param qz 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED,propagation= Propagation.REQUIRED)
    public Map<String, Object> updateJobPlanAndParam(Quartz qz) {
        if(qz.getParamValue()==null || "".equals(qz.getParamValue())) {
            Map<String,Object> map = new HashMap<>();
            map.put("status", "fail");
            map.put("msg", "参数为空");
            return map;
        }
//        String paramIP = qz.getParamValue().split(",")[0];
        String paramIP = DateUtil.getLocalHostIP();
        String localIP = DateUtil.getLocalHostIP();
        if(!paramIP.equals(localIP)) {
            Map<String,Object> map = new HashMap<>();
            map.put("status", "ipError");
            return map;
        }
        if(qz.getEndDate()==null || qz.getEndDate().equals("")) {
            qz.setEndDate("9999-12-31 00:00:00");
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());

        qz.setMakeDate(date);
        qz.setModifyDate(date);
        //更改完后，作业计划为停止状态  需要重新启动
        qz.setUseFlag("0");
        //运行状态为：等待运行
        qz.setTriggerState("1");
        //更改的时候更新任务调度表达式 add by kongxz in 2019-05-24
        qz.setCronExp(QuartzUtils.cron(qz));
        quartzMapper.updateJobPlan(qz);

        quartzMapper.deletePlanParam(qz);
//        qz.setJobCode(qz.getJobName());
        String[] values = qz.getParamValue().split(",");
        if(qz.getParamValue()!=null && !qz.getParamValue().equals("")) {

            List<Quartz> paramNames=quartzMapper.selectJobParam(qz);
            for(int i=0;i<values.length;i++) {
                for(int j=0;j<paramNames.size();j++) {
                    System.out.println(values[i].split(":")[0]+"*****"+paramNames.get(j).getParamCode());

                    if(values[i].split(":")[0].equals(paramNames.get(j).getParamCode())){
                        qz.setParamCode(paramNames.get(j).getParamCode());
                        qz.setParamValue(values[i].split(":")[1]);
                        quartzMapper.insertJobPlanParam(qz);
                    }
                }
            }
        }
        try {//删除任务
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(qz.getJobPlanCode(), qz.getJobPlanCode());
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(qz.getJobPlanCode(), qz.getJobPlanCode()));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //更改完任务计划后需要重新启动，不自动启动
        //Common.addQuartz(qz,quartzService);
        Map<String,Object> map = new HashMap<>();
        map.put("code", "200");
        map.put("status", "success");
        map.put("msg", "执行计划修改成功");
        return map;
    }
    /**
     * 删除一个任务
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/19 15:18
     * @param qz 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED,propagation= Propagation.REQUIRED)
    public Map<String, Object> removeJob(Quartz qz) {
        if(qz.getParamValue()==null || "".equals(qz.getParamValue())) {
            Map<String,Object> map = new HashMap<>();
            map.put("status", "fail");
            map.put("msg", "IP参数为空");
            return map;
        }
//        String paramIP = qz.getParamValue().split(",")[0];
        String paramIP = DateUtil.getLocalHostIP();
        String localIP = DateUtil.getLocalHostIP();
        if(!paramIP.equals(localIP)) {
            Map<String,Object> map = new HashMap<>();
            map.put("status", "ipError");
            return map;
        }
        try {
            String jobPlanCode = qz.getJobPlanCode();
            Map<String,String> map = new HashMap<String,String>();
            map.put("jobPlanCode", jobPlanCode);
            quartzMapper.deleteJobPlanDef(map);
            quartzMapper.deleteJobPlanParam(map);
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobPlanCode, jobPlanCode);
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobPlanCode, jobPlanCode));// 删除任务

            Map<String,Object> returnMap = new HashMap<>();
            returnMap.put("code", "200");
            returnMap.put("status", "success");
            returnMap.put("msg", "删除作业成功");
            return returnMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 计划回显
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/20 9:47
     * @param qz 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    @Transactional
    public Map<String, Object> selectReturnView(Quartz qz) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("returnView", quartzMapper.selectReturnView(qz));
        map.put("paramByCode", quartzMapper.selectParamByCode(qz));
        return map;
    }


}
