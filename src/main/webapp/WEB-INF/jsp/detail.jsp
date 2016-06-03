<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="common/head.jsp"%>

    <title>秒杀页面</title>
</head>
<body>

<div class="container">

    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h1>${seckill.name}</h1>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">
                <span class="glyphicon glyphicon-time"></span>

                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
    </div>

</div>

<div class="modal fade" id="login-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><span class="glyphicon glyphicon-phone"></span>请填写手机号码</h4>
            </div>
            <div class="modal-body">
                <p>
                    <input type="text" class="form-control" name="killPhone" id="killPhone"/>
                </p>
            </div>
            <div class="modal-footer">
                <span id="tips"></span>
                <button type="button" class="btn btn-primary">
                    <span class="glyphicon glyphicon-phone"></span>
                    登录
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
<%@include file="common/script.jsp"%>
<script src="/resources/script/seckill.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
       seckill.detail.init({
           seckillId : ${seckill.seckillId},
           startTime : ${seckill.startTime.time},
           endTime : ${seckill.endTime.time}
       });
    });
</script>
</html>