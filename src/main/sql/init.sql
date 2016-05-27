CREATE DATABASE seckill;

use seckill;
--秒杀商品表
CREATE TABLE seckill(
  `seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
  `name` varchar(120) NOT NULL COMMENT '商品标题',
  `number` int NOT NULL COMMENT '商品剩余总量',
  `start_time` TIMESTAMP NOT NULL DEFAULT COMMENT '开始时间',
  `end_time` TIMESTAMP NOT NULL DEFAULT COMMENT '结束时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀商品表';

--初始化数据
INSERT INTO
  seckill(name,number,start_time,end_time)
VALUES
  ('1000元秒杀iPhone6',100,'2016-5-30 00:00:00','2016-7-30 00:00:00'),
  ('500元秒杀iPad2',200,'2016-5-30 00:00:00','2016-7-30 00:00:00'),
  ('300元秒杀小米5',300,'2016-5-30 00:00:00','2016-7-30 00:00:00'),
  ('100元秒杀红米note3',400,'2016-5-30 00:00:00','2016-7-30 00:00:00');

--秒杀结果表
--信息记录表
CREATE TABLE success_killed(
  `seckill_id` bigint NOT NULL COMMENT '商品库存',
  `phone` bigint NOT NULL COMMENT '用户手机号码',
  `state` tinyint NOT NULL DEFAULT -1 COMMENT '状态:-1无效,0成功,1已付款',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY(seckill_id,phone),/*联合主键*/
  key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';