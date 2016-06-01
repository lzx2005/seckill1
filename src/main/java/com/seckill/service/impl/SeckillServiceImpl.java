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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/1.
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //注入service依赖
    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    String salt = "dsadsadawd21d21dyg21bs1g2s7621";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getSeckillById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    /**
     * 获得秒杀接口
     * @param seckillId
     * @return
     */
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

    /**
     * 使用注解控制事务方法的优点
     * 1:开发团队达成一致约定，明确标注事务方法的编程风格
     * 2:保证事务方法的执行时间尽可能短，不要穿插其他的网络操作例如cache、http等操作或者剥离到方法外部
     * 3:不是所有的方法都需要事务，如只有一条修改操作，只读操作等
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return SeckillExecution
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    @Transactional
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

    /**
     * 获取MD5
     * @param seckillId
     * @return
     */
    private String getMd5(long seckillId) {
        String base = seckillId + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
