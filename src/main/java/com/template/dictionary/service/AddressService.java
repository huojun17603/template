package com.template.dictionary.service;

import com.ich.core.http.entity.PageView;
import com.ich.dictionary.pojo.AddressCN;

import java.util.List;

public interface AddressService {

    public List<AddressCN> query(PageView view,Integer type,String searchkey);

}
