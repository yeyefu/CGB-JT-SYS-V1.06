package com.jt.sys.service;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysLog;

public interface SysLogService{
	
	/**
	    * 基于id执行删除操作
	    * @param ids
	    * @return 删除的行数
	    */
	   int deleteObjects(Integer... ids);
	   
	   
    PageObject<SysLog> findPageObjects(
    		String username,
    		Integer pageCurrent);
}
