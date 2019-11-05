package com.donbala.codeselect.dao;

import com.donbala.codeselect.model.CodeAndName;

import java.util.List;

/**
 * @CLassName: CodeAndNameMapper
 * @Program: springbootdemo
 * @Author: wangran
 * @Date: 2019/7/12-14:28
 * @Description: todo
 **/
public interface CodeAndNameMapper {


    List<CodeAndName> selectRole();

    List<CodeAndName> selectJobInterval();

    List<CodeAndName> selectJobCodeAndName();

    List<CodeAndName> selectEmailJob();

    List<CodeAndName> selectEmailSender();

    List<CodeAndName> selectEmailReceiver();

    List<CodeAndName> selectEmailCopy();
}
