package com.seckill.service.impl;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillState;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/1.
 */
public class SeckillServiceImpl implements SeckillService {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SeckillDao seckillDao;

    private SuccessKilledDao successKilledDao;

    String salt = "dsadsadawd21d21dyg21bs1g2s7621";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getSeckillById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            //秒杀未开始，或者秒杀已经结束
            return new Exposer(
                    false,
                    seckillId,
                    nowTime.getTime(),
                    startTime.getTime(),
                    endTime.getTime()
            );
        }
        //MD5编码
        String md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑
        //1、减库存
        try {
            int updateCount = seckillDao.reduceNumber(seckillId, new Date());
            if (updateCount <= 0) {
                //没有更新的记录，秒杀结束
                throw new SeckillCloseException("seckill is close");
            } else {
                //2、记录购买行为
                int insertCount = successKilledDao.insertRecord(seckillId, userPhone);
                if (insertCount <= 0) {
                    //重复秒杀，无法插入
                    throw new RepeatKillException("repeat kill");
                } else {
                    SuccessKilled successKilled = successKilledDao.queryBySeckillId(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillState.SUCCESS, successKilled);
                }
            }

        } catch (SeckillCloseException e1) {
            logger.error(e1.getMessage(), e1);
            throw e1;
        } catch (RepeatKillException e2) {
            logger.error(e2.getMessage(), e2);
            throw e2;
        } catch (Exception e) {
            //所有编译器异常转化为运行期异常,以便事务ROLLBACK
            logger.error(e.getMessage(), e);
            throw new SeckillException("inner error:" + e.getMessage());
        }
    }

    private String getMd5(long seckillId) {
        String base = seckillId + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
