package com.template.admin.base;

import com.ich.core.http.controller.CoreController;
import com.ich.core.http.entity.HttpResponse;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdminController extends CoreController {

	public static String SESSION_ADMIN_NAME = "SESSION_ADMIN_NAME";

	protected Map<String,Object> getFailMap() {
		Map<String,Object> model = new HashMap<String, Object>();
		model.put(HttpResponse.RETURN_STATUS, HttpResponse.HTTP_ERROR);
		model.put(HttpResponse.RETURN_MSG, HttpResponse.HTTP_MSG_ERROR);
		return model;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new MyCustomDateEditor());
	}
}
