<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/common/header.jspf" %>
<div class="page-head"><h1>${empty category.id ? 'Thêm' : 'Sửa'} Category</h1></div>
<form method="post" action="<c:url value='/admin/categories/save'/>" class="card form-card">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="id" value="${category.id}"/>
    <div class="field"><label>Tên *</label>
        <input class="input" name="name" value="<c:out value='${category.name}'/>" required></div>
    <div class="field"><label>Mô tả</label>
        <textarea class="input" name="description" rows="4"><c:out value="${category.description}"/></textarea></div>
    <div class="actions">
        <button class="btn">Lưu</button>
        <a class="btn btn-light" href="<c:url value='/admin/categories'/>">Hủy</a>
    </div>
</form>
<%@ include file="/WEB-INF/views/common/footer.jspf" %>
