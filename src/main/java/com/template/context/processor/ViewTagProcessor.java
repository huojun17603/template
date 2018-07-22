package com.template.context.processor;

import com.ich.view.pojo.ViewTag;
import com.ich.view.service.ViewTagService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;

public class ViewTagProcessor implements ApplicationListener<ContextRefreshedEvent> {

    protected final Logger logger = Logger.getLogger(ViewTagProcessor.class);


    @Autowired
    ViewTagService viewTagService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        logger.info("开始初始化[文章标记]");
        List<ViewTag> list = getViewTags();
        for(ViewTag viewTag : list){
            viewTagService.addOrEditPostion(viewTag);
        }
        logger.info("完成初始化[文章标记]");
    }

    private List<ViewTag> getViewTags() {
        List<ViewTag> list = new ArrayList<>();
        list.add(new ViewTag("A", "热点", "用于列表"));
        list.add(new ViewTag("B", "置顶", "用于列表"));
        list.add(new ViewTag("C", "首页大图", "用于首页"));
        return list;
    }
}