package com.jt.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.sys.service.SysLogService;

@RequestMapping("/log/")
@Controller
public class SysLogController {
	
	@Autowired
	private SysLogService sysLogService;
	@RequestMapping("doLogListUI")
	public String listUI() {
		return "sys/log_list";
	}

	@RequestMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(Integer... ids){
		sysLogService.deleteObjects(ids);
		return new JsonResult("delete ok");//state=1
	}
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username,Integer pageCurrent) {
		return new JsonResult(sysLogService.findPageObjects(username,
				pageCurrent));
	}
}
