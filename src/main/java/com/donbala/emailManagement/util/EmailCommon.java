package com.donbala.emailManagement.util;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 * 邮件公共类
 * @date 2019/10/17
 */
public class EmailCommon {
    /**
     * 邮件发送配置使用说明
     * 1. 在具体需要发邮件的模块调用EmailManagementService的sendEmailCommon方法（使用spring注入）
     *      传入三个参数：
     *        第一个参数：邮件的主题
     *        第二个参数：邮件的内容
     *        第三个参数：该功能模块发邮件的唯一性编码
     *
     * 2. 在数据库表cms_emailjobdef配置发送任务信息
     *      将1中的第三个参数放到该表的emailJobCode字段；
     *      给这个发送任务起个好听的名字--emailJobName字段；
     *      后面的字段随意吧。
     *
     *
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/24 14:25
     * @param null 1
     * @return
     */
}
