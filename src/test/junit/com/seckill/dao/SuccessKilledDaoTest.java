package com.seckill.dao;

import com.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/5/27.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertRecord() throws Exception {
        int result = successKilledDao.insertRecord(1000L,13758212194L);
        System.out.println(result);
    }

    @Test
    public void queryBySeckillId() throws Exception {
        List<SuccessKilled> successKilleds = successKilledDao.queryBySeckillId(1000L);
        System.out.println(successKilleds.size());
        for (SuccessKilled successKilled : successKilleds){
            System.out.println(successKilled);
        }
    }

}