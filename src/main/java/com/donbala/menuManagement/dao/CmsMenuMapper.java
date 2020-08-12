package com.donbala.menuManagement.dao;

import com.donbala.menuManagement.model.CmsMenu;

import java.util.List;

public interface CmsMenuMapper {
    int deleteByPrimaryKey(String menuid);

    int insert(CmsMenu record);

    int insertSelective(CmsMenu record);

    CmsMenu selectByPrimaryKey(String menuid);

    int updateByPrimaryKeySelective(CmsMenu record);

    int updateByPrimaryKey(CmsMenu record);

    List<CmsMenu> selectByUsercode(String usercode);

    List<CmsMenu> selectMenuByUsercode(String usercode);

    List<CmsMenu> selectAllMenu();
}