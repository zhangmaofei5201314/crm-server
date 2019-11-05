package com.donbala.quartz.dao;

import com.donbala.quartz.model.CommonTaskModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonTaskDao {
	
	List<CommonTaskModel> selectJobPlanParam(CommonTaskModel commonTaskModel);
	
	int insertJobRunLog(CommonTaskModel commonTaskModel);
	
	int insertJobRunDetailLog(CommonTaskModel commonTaskModel);
	
	int updateJobRunLog(CommonTaskModel commonTaskModel);		
	
	String selectLastSuccessDate(CommonTaskModel commonTaskModel);

	String selectLastSuccessDateByJobPlan(CommonTaskModel commonTaskModel);
	
	String selectHandAllMergePoolLastSuccessDate();
	
	String selectHandAllRefreshPoolLastSuccessDate();
	
	List<CommonTaskModel> selectJobRunLog(CommonTaskModel commonTaskModel);
	
	int selectJobRunLogCount(CommonTaskModel commonTaskModel);
	
	List<CommonTaskModel> selectJobName();
	
	int updateJobRunLogBySerialno(CommonTaskModel commonTaskModel);
	
	int updateJobRunDetailLogBySerialno(CommonTaskModel commonTaskModel);	
	
	int selectSuccessJobRunLogDetailCount(CommonTaskModel commonTaskModel);

	String selectJobNameByJobCode(@Param("jobCode") String jobCode);
}
