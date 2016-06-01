package com.seckill.exception;

/**
 * 秒杀结束异常
 * Created by Administrator on 2016/6/1.
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
