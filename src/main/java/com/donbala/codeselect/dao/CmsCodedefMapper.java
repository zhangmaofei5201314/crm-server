package com.donbala.codeselect.dao;

import com.donbala.codeselect.model.CmsCodedef;
import com.donbala.codeselect.model.CmsCodedefKey;

public interface CmsCodedefMapper {
    int deleteByPrimaryKey(CmsCodedefKey key);

    int insert(CmsCodedef record);

    int insertSelective(CmsCodedef record);

    CmsCodedef selectByPrimaryKey(CmsCodedefKey key);

    int updateByPrimaryKeySelective(CmsCodedef record);

    int updateByPrimaryKey(CmsCodedef record);
}