package com.template.context.processor;

import com.ich.view.pojo.ViewPostion;
import com.ich.view.service.ViewPostionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;

public class ViewPostionProcessor implements ApplicationListener<ContextRefreshedEvent> {

    protected final Logger logger = Logger.getLogger(ViewPostionProcessor.class);


    @Autowired
    ViewPostionService viewPostionService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        logger.info("开始初始化[页面位置]");
        List<ViewPostion> list = getViewPostions();
        for(ViewPostion viewPostion : list){
            viewPostionService.addOrEditPostion(viewPostion);
        }
        logger.info("完成初始化[页面位置]");
    }

    private List<ViewPostion> getViewPostions() {
        List<ViewPostion> list = new ArrayList<>();
        list.add(new ViewPostion("PHOME_BROAD",1, "首页轮播位", "APP-首页-轮播"));
        list.add(new ViewPostion("PHOME_AD", 1,"首页广告位", "APP-首页-第一列广告栏"));
        return list;
    }
}
