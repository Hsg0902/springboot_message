<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="menu">
    <span>当前用户：<a href="${pageContext.request.contextPath}/main.jsp">${sessionScope.user.name}</a></span> <span><a
        href="${pageContext.request.contextPath}/user/queryAllUsers">发短消息</a></span> <span><a href="${pageContext.request.contextPath}/user/logout">退出</a></span>
</div>
