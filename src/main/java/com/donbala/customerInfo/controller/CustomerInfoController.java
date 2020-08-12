package com.donbala.customerInfo.controller;

import com.donbala.Commons.controller.Common;
import com.donbala.customerInfo.model.CustomerInfoModel;
import com.donbala.customerInfo.service.CustomerInfoService;
import com.donbala.userManagement.service.CmsUserServiceIntf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 * 客户信息接口
 * @date 2020/4/15
 */
@RestController
public class CustomerInfoController {

    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private CmsUserServiceIntf cmsUserServiceIntf;
    /**
     * 查询客户信息
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2020/4/15 11:47
     * @param searchParam 1
     * @return java.util.List<com.donbala.customerInfo.model.CustomerInfoModel>
     */
    @RequestMapping(value = "/controller/customer/info/selectCustomerByParam", method = RequestMethod.POST)
    public List<CustomerInfoModel> searchCustomerByParam(String searchParam) {
        return customerInfoService.searchCustomerInfo(searchParam);
    }
    /**
     * 查询搜索历史
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2020/4/15 17:05
     * @param token 1
     * @return java.util.List<java.lang.String>
     */
    @RequestMapping(value = "/controller/customer/info/selectSearchHistoryData", method = RequestMethod.POST)
    public List<String> selectSearchHistoryData(String token){
        String userCode = cmsUserServiceIntf.getUserByToken(token).getUsercode();
        return customerInfoService.selectSearchHistory(userCode);
    }
    /**
     * 添加搜索历史
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2020/4/15 17:05
     * @param historyName 1
     * @param token 2
     * @return void
     */
    @RequestMapping(value = "/controller/customer/info/addSearchHistoryData", method = RequestMethod.POST)
    public /*Map<String, Object>*/ @ResponseBody void addHistoryData(String historyName, String token){
        String userCode = cmsUserServiceIntf.getUserByToken(token).getUsercode();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("historyName", historyName);
        map.put("userCode", userCode);
        customerInfoService.addSearchHistory(map);
        Map<String, Object> rMap = Common.insertSuccess("成功");
//        return rMap;
    }

}
