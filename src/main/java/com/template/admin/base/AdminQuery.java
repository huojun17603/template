package com.template.admin.base;

import com.ich.core.base.TimeUtil;

import java.util.Date;

public class AdminQuery {
	
	private String id;
	
	private String userid;
	
	private String shopid;
	
	private String companyid;
	
	private String searchkey;
	
	private Integer classes;
	
	private Integer status;
	
	private String btime;
	
	private String etime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSearchkey() {
		return searchkey;
	}

	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}

	public Integer getClasses() {
		return classes;
	}

	public void setClasses(Integer classes) {
		this.classes = classes;
	}

	public String getBtime() {
		return btime;
	}

	public void setBtime(String btime) {
		this.btime = btime;
	}

	public String getEtime() {
		return etime;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public void setEtime(String etime) {
		try{
			Date time = TimeUtil.parse(etime, "yyyy-MM-dd");
			this.etime = TimeUtil.format(new Date(time.getTime()+TimeUtil.ONE_DAY), "yyyy-MM-dd");
		}catch(Exception e){
			this.etime = "";
		}
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
