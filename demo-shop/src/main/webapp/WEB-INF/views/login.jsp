<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/common/header.jspf" %>
<div class="auth-card">
    <div class="auth-logo">🛍</div>
    <h1>Chào mừng trở lại</h1>
    <p class="sub">Đăng nhập để tiếp tục</p>

    <c:if test="${param.error != null}"><div class="alert alert-err">Sai username hoặc mật khẩu</div></c:if>
    <c:if test="${param.logout != null}"><div class="alert alert-info">Bạn đã đăng xuất thành công</div></c:if>
    <c:if test="${not empty message}"><div class="alert alert-ok"><c:out value="${message}"/></div></c:if>

    <form action="<c:url value='/login'/>" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="field"><label>Username</label>
            <input class="input" name="username" placeholder="Nhập username" required autofocus></div>
        <div class="field"><label>Mật khẩu</label>
            <input class="input" type="password" name="password" placeholder="Nhập mật khẩu" required></div>
        <button class="btn btn-block" type="submit">Đăng nhập</button>
    </form>
    <p class="switch">Chưa có tài khoản? <a href="<c:url value='/register'/>">Đăng ký ngay</a></p>
</div>
<%@ include file="/WEB-INF/views/common/footer.jspf" %>
