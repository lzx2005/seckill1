<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="common/head.jsp"%>
    <title>秒杀列表</title>
</head>
<body>
<div class="container">

    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>

        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>标题</th>
                    <th>剩余数量</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sk" items="${list}">
                    <tr>
                        <th scope="row">${sk.seckillId}</th>
                        <td>${sk.name}</td>
                        <td>${sk.number}</td>
                        <td>
                            <fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>

                        </td>
                        <td>
                            <fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td><a class="btn btn-primary" href="/seckill/${sk.seckillId}/detail">进入秒杀页</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>
</body>
<%@include file="common/script.jsp"%>
</html>