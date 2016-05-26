package com.seckill.dao;

import com.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by lzx2005 on 2016/5/26.
 */
public interface SeckillDao {

    /**
     * 减库存
     */
    int reduceNumber(long seckillId,Date killtime);

    /**
     * 查找某个
     */
    Seckill queryById(long seckillId);


    /**
     * 根据偏移量查找商品列表
     */
    List<Seckill> queryAll(int offet,int limit);
}
