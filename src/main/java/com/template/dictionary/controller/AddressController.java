package com.template.dictionary.controller;

import com.ich.core.base.JsonUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.dictionary.pojo.AddressCN;
import com.ich.dictionary.service.AddressCNService;
import com.ich.module.annotation.Link;
import com.template.admin.base.AdminController;
import com.template.dictionary.service.AddressService;
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
	private AddressCNService addressCNService;
	@Autowired
	private AddressService addressService;

	@RequestMapping(value="address/query",produces= MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	@Link(name = "地址字典-分页列表",code = "admin-address-query",
		level = Link.LEVEL_READ,  parent = "admin-address-index")
	public String query(PageView pageView, Integer type,String searchkey, String callback){
		List<AddressCN> list = addressService.query(pageView,type,searchkey);
		HttpEasyUIResponse response = new HttpEasyUIResponse(HttpEasyUIResponse.HTTP_OK,HttpEasyUIResponse.HTTP_MSG_OK,null);
		response.setRows(list);
		response.setTotal(pageView.getRowCount());
		return callback(callback, JsonUtils.objectToJson(list));
	}

	@RequestMapping(value="address/list",produces= MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	@Link(name = "地址字典-列表",code = "admin-address-list",
		level = Link.LEVEL_READ,  parent = "admin-address-index")
	public String list(Long pid,String callback){
		List<AddressCN> list = addressCNService.findAddressCNofPid(pid);
		return callback(callback, JsonUtils.objectToJson(list));
	}

	@RequestMapping(value="address/saveOrUpdate",produces= MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	@Link(name = "地址字典-新增修改",code = "admin-address-saveOrUpdate",
		level = Link.LEVEL_WRITE,  parent = "admin-address-index")
	public String saveOrUpdate(AddressCN address, String callback){
		if(ObjectHelper.isEmpty(address.getName())) return callback(callback, JsonUtils.objectToJson(getFailMap("请输入名称！")));
		if(ObjectHelper.isEmpty(address.getLetter())) return callback(callback, JsonUtils.objectToJson(getFailMap("请输入名称手写字母！")));
		if(ObjectHelper.isEmpty(address.getType())) return callback(callback, JsonUtils.objectToJson(getFailMap("请输入区域级别！")));
		if(address.getType()==1){
			if(ObjectHelper.isEmpty(address.getParentid())) return callback(callback, JsonUtils.objectToJson(getFailMap("不可自定义省级区域！")));
		}else{
			if(ObjectHelper.isEmpty(address.getParentid())) return callback(callback, JsonUtils.objectToJson(getFailMap("请输入父级！")));
		}
		HttpResponse response = addressCNService.addOrEdit(address);
		return callback(callback, JsonUtils.objectToJson(response));
	}

}
