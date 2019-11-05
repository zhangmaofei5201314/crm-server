package com.donbala.loginManagement.dao;

import com.donbala.loginManagement.model.CmsLogintrace;

public interface CmsLogintraceMapper {
    int deleteByPrimaryKey(String id);

    int insert(CmsLogintrace record);

    int insertSelective(CmsLogintrace record);

    CmsLogintrace selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsLogintrace record);

    int updateByPrimaryKey(CmsLogintrace record);
}