package com.template.dictionary.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ich.core.http.entity.PageView;
import com.ich.dictionary.dao.AddressCNMapper;
import com.ich.dictionary.pojo.AddressCN;
import com.template.dictionary.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressCNMapper addressCNMapper;

    @Override
    public List<AddressCN> query(PageView view,Integer type, String searchkey) {
        PageHelper.startPage(view.getPage(),view.getRows());
        List<AddressCN> list = addressCNMapper.selectAddressCNofType(type);
        PageInfo<?> pageInfo = new PageInfo<>(list);
        view.setRowCount(pageInfo.getTotal());
        return list;
    }
}
