package com.template.dictionary.controller;

import com.ich.core.base.JsonUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.dictionary.pojo.Bank;
import com.ich.dictionary.service.BankService;
import com.ich.module.annotation.Link;
import com.template.admin.base.AdminController;
import com.template.admin.base.AdminQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("dictionary")
public class BankController extends AdminController {

	@Autowired
	private BankService bankService;

	@RequestMapping(value="bank/query",produces= MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	@Link(name = "银行字典-分页列表",code = "admin-bank-query",
		level = Link.LEVEL_READ,  parent = "admin-bank-index")
	public String query(PageView pageView, AdminQuery query, String callback){
		List<Bank> list = bankService.findAllList();
		HttpEasyUIResponse response = new HttpEasyUIResponse(HttpEasyUIResponse.HTTP_OK,HttpEasyUIResponse.HTTP_MSG_OK,null);
		if(ObjectHelper.isNotEmpty(query.getSearchkey())){
			List<Bank> result = new ArrayList<>();
			for(Bank bank : list){
				if(bank.getName().indexOf(query.getSearchkey())!=-1)
					result.add(bank);
			}
			response.setRows(result);
			response.setTotal(result.size());
			return callback(callback, JsonUtils.objectToJson(result));
		}
		response.setRows(list);
		response.setTotal(list.size());
		return callback(callback, JsonUtils.objectToJson(list));
	}

	@RequestMapping("bank/saveOrUpdate")
	@ResponseBody
	@Link(name = "银行字典-新增修改",code = "admin-bank-saveOrUpdate",
		level = Link.LEVEL_WRITE,  parent = "admin-bank-index")
	public String saveOrUpdate(Bank bank,String callback){
		if(ObjectHelper.isEmpty(bank.getName())) return callback(callback, JsonUtils.objectToJson(getFailMap("请输入银行名称！")));
		if(ObjectHelper.isEmpty(bank.getCode())) return callback(callback, JsonUtils.objectToJson(getFailMap("请输入银行编码！")));
		HttpResponse response = bankService.addOrEdit(bank);
		return callback(callback, JsonUtils.objectToJson(response));
	}

	@RequestMapping(value="bank/able",produces= MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	@Link(name = "银行字典-启用",code = "admin-bank-able",
		level = Link.LEVEL_EDIT,  parent = "admin-bank-index")
	public String able(Long id,String callback){
		HttpResponse response = bankService.editStatus(id,1);
		return callback(callback, JsonUtils.objectToJson(response));
	}

	@RequestMapping(value="bank/disable",produces= MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	@Link(name = "银行字典-禁用",code = "admin-bank-disable",
		level = Link.LEVEL_EDIT,  parent = "admin-bank-index")
	public String disable(Long id,String callback){
		HttpResponse response = bankService.editStatus(id,0);
		return callback(callback, JsonUtils.objectToJson(response));
	}
}
