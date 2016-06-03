/**
 * Created by Administrator on 2016/6/3.
 */
//存放前端交互逻辑
var seckill = {
    //封装秒杀相关地址
    URL : {
    },
    detail : {
        //详情页面初始化
        init : function (params) {
            //验证手机号是否在Cookie
            var killPhone = $.cookie('killPhone');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
        }
    }
}