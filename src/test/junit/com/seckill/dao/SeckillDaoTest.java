package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by lzx2005 on 2016/5/27.
 * 配置Spring和junit整合，junit启动时加载springIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    //注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;


    @Test
    public void queryById() throws Exception {
        int id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill);
        System.out.println(seckill.getName());
    }

    @Test
    public void queryAll() throws Exception {

    }

    @Test
    public void reduceNumber() throws Exception {


    }
}