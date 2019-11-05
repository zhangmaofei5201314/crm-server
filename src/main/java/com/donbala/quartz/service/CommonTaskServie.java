package com.donbala.quartz.service;

import com.donbala.quartz.model.CommonTaskModel;

import java.util.Map;

public interface CommonTaskServie {
    public Map<String,Object> selectJobRunLog(CommonTaskModel commonTaskModel);
}
