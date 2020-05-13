package com.turbid.explore.service.impl;

import com.turbid.explore.pojo.ProjectNeeds;
import com.turbid.explore.repository.ProjectNeedsRepositroy;
import com.turbid.explore.service.ProjectNeedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectNeedsServiceImpl implements ProjectNeedsService {

    @Autowired
    private ProjectNeedsRepositroy needsRepositroy;

    private Integer size=15;

    @Override
    public ProjectNeeds save(ProjectNeeds projectNeeds) {
       return needsRepositroy.saveAndFlush(projectNeeds);
    }

    @Override
//    @Cacheable(cacheNames = {"redis_cache"}, key = "'ProjectNeedslistByPage'+#page+#style+#category+#type+#search")
    public List<ProjectNeeds> listByPage(Integer page, String style, String category, String type, String search) {
        Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"create_time");
        Page<ProjectNeeds> pages=  needsRepositroy.listByPage(pageable,style,category,type);
        return pages.getContent();
    }

    @Override
//    @Cacheable(cacheNames = {"redis_cache"}, key = "'getNeedsByCode'+#code")
    public ProjectNeeds getNeedsByCode(String code) {
        return needsRepositroy.getOne(code);
    }

    @Override
//    @Cacheable(cacheNames = {"redis_cache"}, key = "'getMyNeeds'+#name+#page+#status")
    public List<ProjectNeeds> getMyNeeds(String name, Integer page, Integer status) {
        Pageable pageable =null;
        if(status==0) {
            pageable = new PageRequest(page, 10, Sort.Direction.DESC, "create_time");
        }else {
            pageable = new PageRequest(page, 10, Sort.Direction.DESC, "overtime");
        }
        Page<ProjectNeeds> pages=  needsRepositroy.getMyNeeds(pageable,name,status);
        return pages.getContent();
    }

    @Override
    public int countByStatus(String name, int status) {
        return  needsRepositroy.countByStatus(name,status);
    }

    @Override
    @Transactional
    public void updateURGENT(String orderno) {
        needsRepositroy.updateURGENT(orderno);
    }
}
