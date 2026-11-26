<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/common/header.jspf" %>
<div class="page-head"><h1>${isNew ? 'Thêm' : 'Sửa'} User</h1></div>
<form method="post" action="<c:url value='/admin/users/save'/>" class="card form-card">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="id" value="${user.id}"/>
    <div class="field"><label>Username *</label>
        <input class="input" name="username" value="<c:out value='${user.username}'/>" required></div>
    <div class="field"><label>Họ tên</label>
        <input class="input" name="fullName" value="<c:out value='${user.fullName}'/>"></div>
    <div class="field"><label>Email *</label>
        <input class="input" type="email" name="email" value="<c:out value='${user.email}'/>" required></div>
    <div class="field"><label>Mật khẩu ${isNew ? '*' : ''} <c:if test="${!isNew}"><span class="hint">(để trống nếu không đổi)</span></c:if></label>
        <input class="input" type="password" name="password" ${isNew ? 'required' : ''}></div>
    <div class="field"><label>Role</label>
        <select class="input" name="role">
            <option value="USER" ${user.role == 'USER' ? 'selected' : ''}>USER</option>
            <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
        </select></div>
    <div class="check">
        <input type="checkbox" name="enabled" value="true" id="en" ${user.enabled ? 'checked' : ''}>
        <input type="hidden" name="_enabled" value="on"/>
        <label for="en">Kích hoạt tài khoản</label>
    </div>
    <div class="actions">
        <button class="btn">Lưu</button>
        <a class="btn btn-light" href="<c:url value='/admin/users'/>">Hủy</a>
    </div>
</form>
<%@ include file="/WEB-INF/views/common/footer.jspf" %>
