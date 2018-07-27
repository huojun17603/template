package com.template.dictionary.controller;

import com.ich.core.base.JsonUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.core.http.entity.SortView;
import com.ich.dictionary.pojo.IAddresscn;
import com.ich.dictionary.pojo.IAddresscnExample;
import com.ich.dictionary.service.IAddresscnService;
import com.ich.module.annotation.Link;
import com.template.admin.base.AdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("dictionary")
public class AddressController extends AdminController {

	@Autowired
	private IAddresscnService addressCNService;

	@RequestMapping(value="address/query",produces= MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	@Link(name = "地址字典-分页列表",code = "admin-address-query",
		level = Link.LEVEL_READ,  parent = "admin-address-index")
	public String query(PageView page, SortView sort, Integer type, String searchkey, String callback){
		IAddresscnExample example = new IAddresscnExample();
		IAddresscnExample.Criteria Criteria = example.createCriteria();
		if(ObjectHelper.isNotEmpty(searchkey))Criteria.andNameLike("%"+searchkey+"%");
		if(ObjectHelper.isNotEmpty(type))Criteria.andTypeEqualTo(type);
		List<IAddresscn> list = addressCNService.findList(page,sort,example);
		HttpEasyUIResponse response = new HttpEasyUIResponse(HttpEasyUIResponse.HTTP_OK,HttpEasyUIResponse.HTTP_MSG_OK,null);
		response.setRows(list);
		response.setTotal(page.getRowCount());
		return callback(callback, JsonUtils.objectToJson(list));
	}

	@RequestMapping(value="address/list",produces= MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	@Link(name = "地址字典-列表",code = "admin-address-list",
		level = Link.LEVEL_READ,  parent = "admin-address-index")
	public String list(Long pid,String callback){
		List<IAddresscn> list = addressCNService.findAddressCNofPid(pid);
		return callback(callback, JsonUtils.objectToJson(list));
	}

	@RequestMapping(value="address/save",produces= MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	@Link(name = "地址字典-新增",code = "admin-address-save",
			level = Link.LEVEL_WRITE,  parent = "admin-address-index")
	public String save(IAddresscn address, String callback){
		HttpResponse response = addressCNService.add(address);
		return callback(callback, JsonUtils.objectToJson(response));
	}

	@RequestMapping(value="address/update",produces= MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	@Link(name = "地址字典-修改",code = "admin-address-update",
			level = Link.LEVEL_WRITE,  parent = "admin-address-index")
	public String update(IAddresscn address, String callback){
		HttpResponse response = addressCNService.edit(address);
		return callback(callback, JsonUtils.objectToJson(response));
	}
}
