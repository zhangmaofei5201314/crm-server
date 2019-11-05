package com.donbala.menuManagement.dao;

import com.donbala.menuManagement.model.CmsMenutrace;

public interface CmsMenutraceMapper {
    int deleteByPrimaryKey(String id);

    int insert(CmsMenutrace record);

    int insertSelective(CmsMenutrace record);

    CmsMenutrace selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsMenutrace record);

    int updateByPrimaryKey(CmsMenutrace record);
}