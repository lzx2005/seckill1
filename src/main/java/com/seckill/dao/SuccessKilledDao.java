package com.seckill.dao;

import com.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/26.
 */
public interface SuccessKilledDao {
    /**
     * 插入一条记录
     * */
    int insertRecord(@Param("seckillId")long seckillId,@Param("phone")long phone);

    /**
     * 查询记录
     */
    List<SuccessKilled> queryBySeckillId(long seckillId);
}
