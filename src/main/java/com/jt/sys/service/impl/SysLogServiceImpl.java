package com.jt.sys.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jt.common.vo.PageObject;
import com.jt.common.vo.ServiceException;
import com.jt.sys.dao.SysLogDao;
import com.jt.sys.entity.SysLog;
import com.jt.sys.service.SysLogService;

@Transactional(rollbackFor=Throwable.class)
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
	@Autowired
	private SysLogDao sysLogDao;
	
	//底层基于AOP机制,方法执行之前开启事务,执行结束提交事务,出现异常会回滚事务
		@Override
		public int deleteObjects(Integer... ids) {
			//1.验证参数合法性
			if(ids==null||ids.length==0)
			throw new IllegalArgumentException("请选择删除记录");
			//2.基于dao执行删除操作
			int rows=0;
			try{
			rows=sysLogDao.deleteObjects(ids);
			}catch(Throwable e){
			e.printStackTrace();//Log
			throw new ServiceException("系统故障,片刻以后访问");
			}
			//3.验证删除结果
			if(rows==0)
			throw new ServiceException("记录可能已经不存在");
			//4.返回结果.
			return rows;
		}
		
	@Transactional(readOnly=true,timeout=30)
	@Override
	public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent) {
        //1.验证参数合法性
		if(pageCurrent==null||pageCurrent<1)
		throw new ServiceException("参数不合法");
		//2.查询总记录数
		int rowCount=sysLogDao.getRowCount(username);
		if(rowCount==0)
		throw new ServiceException("没有记录");
	    //2.查询当前页记录
		//2.1定义页面大小(每页最多现实多少条记录)
		int pageSize=3;
		//2.2计算当前页位置
		int startIndex=(pageCurrent-1)*pageSize;
		//2.3查询当前数据
		List<SysLog> list=sysLogDao.findPageObjects(
				username, 
				startIndex,
				pageSize);
		//3.封装数据
		PageObject<SysLog> pageObject=new PageObject<>();
		pageObject.setRecords(list);
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCurrent(pageCurrent);
		return pageObject;
	}

}
