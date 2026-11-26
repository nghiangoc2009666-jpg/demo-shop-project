<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/common/header.jspf" %>
<div class="auth-card wide">
    <div class="auth-logo">✨</div>
    <h1>Tạo tài khoản</h1>
    <p class="sub">Đăng ký chỉ mất vài giây</p>

    <c:if test="${not empty error}"><div class="alert alert-err"><c:out value="${error}"/></div></c:if>

    <form action="<c:url value='/register'/>" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="field"><label>Username *</label>
            <input class="input" name="username" value="<c:out value='${form.username}'/>" placeholder="Tối thiểu 3 ký tự" required></div>
        <div class="field"><label>Họ tên</label>
            <input class="input" name="fullName" value="<c:out value='${form.fullName}'/>" placeholder="Nguyễn Văn A"></div>
        <div class="field"><label>Email *</label>
            <input class="input" type="email" name="email" value="<c:out value='${form.email}'/>" placeholder="ban@example.com" required></div>
        <div class="field"><label>Mật khẩu *</label>
            <input class="input" type="password" name="password" placeholder="Tối thiểu 6 ký tự" required></div>
        <div class="field"><label>Xác nhận mật khẩu *</label>
            <input class="input" type="password" name="confirmPassword" placeholder="Nhập lại mật khẩu" required></div>
        <button class="btn btn-block" type="submit">Đăng ký</button>
    </form>
    <p class="switch">Đã có tài khoản? <a href="<c:url value='/login'/>">Đăng nhập</a></p>
</div>
<%@ include file="/WEB-INF/views/common/footer.jspf" %>
