package com.template.dictionary.controller;

import com.ich.core.base.JsonUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.core.http.entity.SortView;
import com.ich.dictionary.pojo.IBank;
import com.ich.dictionary.pojo.IBankExample;
import com.ich.dictionary.service.IBankService;
import com.ich.module.annotation.Link;
import com.template.admin.base.AdminController;
import com.template.admin.base.AdminQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("dictionary")
public class BankController extends AdminController {

	@Autowired
	private IBankService bankService;

	@RequestMapping(value="bank/query",produces= MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	@Link(name = "银行字典-分页列表",code = "admin-bank-query",
		level = Link.LEVEL_READ,  parent = "admin-bank-index")
	public String query(PageView page, SortView sort, AdminQuery query, String callback){
		IBankExample example = new IBankExample();
		if(ObjectHelper.isNotEmpty(query.getSearchkey())) {
			example.createCriteria().andNameLike("%" + query.getSearchkey() + "%");
		}
		List<IBank> list = bankService.findList(page,sort,example);
		HttpEasyUIResponse response = new HttpEasyUIResponse(HttpEasyUIResponse.HTTP_OK,HttpEasyUIResponse.HTTP_MSG_OK,null);
		response.setRows(list);
		response.setTotal(page.getRowCount());
		return callback(callback, JsonUtils.objectToJson(list));
	}

	@RequestMapping(value="bank/save",produces= MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	@Link(name = "银行字典-新增",code = "admin-bank-save",
		level = Link.LEVEL_WRITE,  parent = "admin-bank-index")
	public String save(IBank bank, String callback){
		HttpResponse response = bankService.add(bank);
		return callback(callback, JsonUtils.objectToJson(response));
	}

	@RequestMapping(value="bank/update",produces= MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	@Link(name = "银行字典-修改",code = "admin-bank-update",
			level = Link.LEVEL_WRITE,  parent = "admin-bank-index")
	public String update(IBank bank, String callback){
		HttpResponse response = bankService.edit(bank);
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
