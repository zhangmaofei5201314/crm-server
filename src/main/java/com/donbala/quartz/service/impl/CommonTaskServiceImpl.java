package com.donbala.quartz.service.impl;

import com.donbala.Commons.controller.Common;
import com.donbala.quartz.dao.CommonTaskDao;
import com.donbala.quartz.model.CommonTaskModel;
import com.donbala.quartz.service.CommonTaskServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 *
 * @date 2019/9/20
 */
@Service
public class CommonTaskServiceImpl implements CommonTaskServie {

    @Autowired
    CommonTaskDao commonTaskDao;
    /**
     * 查询运行日志
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/20 16:22
     * @param commonTaskModel 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> selectJobRunLog(CommonTaskModel commonTaskModel) {
        List<CommonTaskModel> list=commonTaskDao.selectJobRunLog(commonTaskModel);
        int resultTotal = commonTaskDao.selectJobRunLogCount(commonTaskModel);
        Map<String,Object> resultMap = Common.getSuccess(list);
        resultMap.put("rows", list);
        resultMap.put("total", resultTotal);
        return resultMap;
    }
}
