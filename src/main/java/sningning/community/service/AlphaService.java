package sningning.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sningning.community.dao.AlphaDao;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @ClassName: AlphaService
 * @Author: Song Ningning
 * @Date: 2020-04-21 0:09
 */
@Service
public class AlphaService {

    public AlphaService() {
        System.out.println("实例化AlphaService");
    }

    @PostConstruct
    public void init() {
        System.out.println("初始化AlphaService");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("销毁AlphaService");
    }

    @Autowired
    private AlphaDao alphaDao;

    public String find() {
        return alphaDao.select();
    }

}
