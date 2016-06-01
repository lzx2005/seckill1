package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


/**
 * Created by Administrator on 2016/6/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-*.xml"})
public class SeckillServiceTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        for (Seckill seckill : seckillList){
            System.out.println(seckill);
        }
    }

    @Test
    public void getSeckillById() throws Exception {
        Seckill seckill = seckillService.getSeckillById(1000L);
        System.out.println(seckill);
    }




    @Test
    public void exportSeckillUrl() throws Exception {
        Exposer exposer = seckillService.exportSeckillUrl(1003L);
        System.out.println(exposer);

    }

    @Test
    public void executeSeckill() throws Exception {
        String md5 = "bdc6fea936045322aec581630d5117d6";
        SeckillExecution execution = seckillService.executeSeckill(1003L, 13758212195L, md5);
        System.out.println(execution);
    }

    @Test
    public void testAll(){
        long seckillId = 1001L;
        long userPhone = 13758212195L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            //可以秒杀
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, exposer.getMd5());
            System.out.println(seckillExecution);
        }else{
            //不可以秒杀
            System.out.println(exposer);
        }
    }

}