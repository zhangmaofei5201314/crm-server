package com.donbala.roleManagement.dao;

import com.donbala.roleManagement.model.CmsRole;

import java.util.List;

public interface CmsRoleMapper {
    int deleteByPrimaryKey(String roleid);

    int insert(CmsRole record);

    int insertSelective(CmsRole record);

    CmsRole selectByPrimaryKey(String roleid);

    List<CmsRole> selectAll(CmsRole cmsRole);

    int updateByPrimaryKeySelective(CmsRole record);

    int updateByPrimaryKey(CmsRole record);
}