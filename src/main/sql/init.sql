CREATE DATABASE seckill;

use seckill;

CREATE TABLE seckill(
  'seckill_id' bigint NOT NULL AUTO_INCREMENT COMMENT='商品库存ID',
  'name' varchar(120) NOT NULL COMMENT='商品标题',
  'number' int NOT NULL COMMENT='商品剩余总量'

)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀商品表'