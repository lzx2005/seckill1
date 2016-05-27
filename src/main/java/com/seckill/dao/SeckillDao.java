package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by lzx2005 on 2016/5/26.
 */
public interface SeckillDao {

    /**
     * 减库存
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    /**
     * 查找某个
     */
    Seckill queryById(long seckillId);


    /**
     * 根据偏移量查找商品列表
     */
    List<Seckill> queryAll(@Param("offet")int offet,@Param("limit")int limit);
}
