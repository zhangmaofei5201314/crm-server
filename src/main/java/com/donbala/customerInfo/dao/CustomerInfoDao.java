package com.donbala.customerInfo.dao;

import com.donbala.customerInfo.model.CustomerInfoModel;

import java.util.List;
import java.util.Map;

public interface CustomerInfoDao {
    //根据条件查询客户信息
    List<CustomerInfoModel> selectCustomrInfoByParam(String searchParam);
    //查询搜索历史
    List<String> selectSearchHistory(String userCode);

    //删除相同的内容
    int deleteHistoryData(Map<String, Object> map);

    //插入搜索历史
    int addSearchData(Map<String, Object> map);

    //保留该用户的前10个记录
    int deleteHistoryDataOutOf10(String userCode);
}
