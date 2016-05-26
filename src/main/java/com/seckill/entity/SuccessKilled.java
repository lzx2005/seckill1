package com.seckill.entity;

import java.util.Date;

/**
 * Created by lzx2005 on 2016/5/26.
 */
public class SuccessKilled {
    private long seckillId;
    private long phone;
    private short state;
    private Date create_time;

    private Seckill seckill;



    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", phone=" + phone +
                ", state=" + state +
                ", create_time=" + create_time +
                ", seckill=" + seckill +
                '}';
    }
}
