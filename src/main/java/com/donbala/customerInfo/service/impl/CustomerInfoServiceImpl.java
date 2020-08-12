package com.donbala.customerInfo.service.impl;

import com.donbala.customerInfo.dao.CustomerInfoDao;
import com.donbala.customerInfo.model.CustomerInfoModel;
import com.donbala.customerInfo.service.CustomerInfoService;
import com.donbala.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 *
 * @date 2020/4/15
 */
@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {

    @Autowired
    private CustomerInfoDao customerInfoDao;

    /**
     * 查询客户信息
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2020/4/15 11:40
     * @param searchParam 1
     * @return com.donbala.customerInfo.model.CustomerInfoModel
     */
    @Override
    public List<CustomerInfoModel> searchCustomerInfo(String searchParam) {
        if(searchParam == null){
            searchParam = "";
        }
        return customerInfoDao.selectCustomrInfoByParam(searchParam);
    }
    /**
     * 查询搜索历史
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2020/4/15 16:35
     * @param userCode 1
     * @return java.util.List<java.lang.String>
     */
    @Override
    public List<String> selectSearchHistory(String userCode) {
        return customerInfoDao.selectSearchHistory(userCode);
    }

    /**
     * 添加搜索历史
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2020/4/15 16:35
     * @param map 1
     * @return void
     */
    @Override
//    @Transactional
    public void addSearchHistory(Map<String, Object> map) {

        map.put("createDate", DateUtil.getSysDate());
        map.put("serialNo", UUID.randomUUID().toString().replace("-",""));
        customerInfoDao.deleteHistoryData(map);
        customerInfoDao.addSearchData(map);
        customerInfoDao.deleteHistoryDataOutOf10((String) map.get("userCode"));

    }
}
