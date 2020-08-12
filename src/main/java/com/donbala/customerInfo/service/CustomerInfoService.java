package com.donbala.customerInfo.service;

import com.donbala.customerInfo.model.CustomerInfoModel;

import java.util.List;
import java.util.Map;

public interface CustomerInfoService {

    List<CustomerInfoModel> searchCustomerInfo(String searchParam);

    List<String> selectSearchHistory(String userCode);

    void addSearchHistory(Map<String,Object> map);

}
